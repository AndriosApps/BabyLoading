package com.andrios.pregnancyjournal.Controllers;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.BabyName;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.Profile;
import com.andrios.pregnancyjournal.BackupHelper;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;
import com.andrios.pregnancyjournal.UploadFolder;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.android.AuthActivity;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;
import com.dropbox.client2.session.Session.AccessType;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DropboxActivity extends Activity {
    private static final String TAG = "Baby Loading";

    ///////////////////////////////////////////////////////////////////////////
    //                      Your app-specific settings.                      //
    ///////////////////////////////////////////////////////////////////////////

    // Replace this with your app key and secret assigned by Dropbox.
    // Note that this is a really insecure way to do this, and you shouldn't
    // ship code which contains your key & secret in such an obvious way.
    // Obfuscation is good.
    final static private String APP_KEY = "wj9976r8u8ak1h4";
    final static private String APP_SECRET = "0dkvl4t469kxzy3";

    // If you'd like to change the access type to the full Dropbox instead of
    // an app folder, change this value.
    final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;

    ///////////////////////////////////////////////////////////////////////////
    //                      End app-specific settings.                       //
    ///////////////////////////////////////////////////////////////////////////
    // You don't need to change these, leave them alone.
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";
    
    private boolean mLoggedIn;

    DropboxAPI<AndroidAuthSession> mApi;

    private final String SAVE_DIR = "/";
	protected static final int BACKUP = 0;
	
	Button backupBTN, linkBTN;
	TextView outputTXT;
	ArrayList<BabyName> nameList;
	ArrayList<JournalEntry> journalList;
	Profile profile;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dropboxview);
        

        // We create a new AuthSession so that we can use the Dropbox API.
        AndroidAuthSession session = buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);

        getExtras();
        checkAppKeySetup();
        
    }
    
    public void onResume(){
    	super.onResume();
    	
        setConnections();
        setOnClickListeners();

        setLoggedIn(mApi.getSession().isLinked());
        AndroidAuthSession session = mApi.getSession();

        // The next part must be inserted in the onResume() method of the
        // activity from which session.startAuthentication() was called, so
        // that Dropbox authentication completes properly.
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();

                // Store it locally in our app for later use
                TokenPair tokens = session.getAccessTokenPair();
                storeKeys(tokens.key, tokens.secret);
                setLoggedIn(true);
            } catch (IllegalStateException e) {
                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                Log.i(TAG, "Error authenticating", e);
            }
        }
    }

	private void getExtras() {
		Intent intent = this.getIntent();
		profile = (Profile) intent.getSerializableExtra("profile");
		
		
	}

	private void setConnections() {
		linkBTN = (Button) findViewById(R.id.dropboxViewLinkBTN);
		backupBTN = (Button) findViewById(R.id.dropboxViewBackupBTN);
		outputTXT = (TextView) findViewById(R.id.dropboxOutputTXT);
		
	}

	private void setOnClickListeners() {
		linkBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(mLoggedIn){
					
					 logOut();
		                
				}else{
					 // Start the remote authentication
                    mApi.getSession().startAuthentication(DropboxActivity.this);
				}
				
			}
		});
		backupBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				outputTXT.append(BackupHelper.writeNames(DropboxActivity.this)+"\n");

				outputTXT.append(BackupHelper.writeJournal(DropboxActivity.this)+"\n");

				outputTXT.append(BackupHelper.writeProfile(DropboxActivity.this)+"\n");
				 Date date = new Date();
	             DateFormat df = new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss");
	             String folder = "/"+df.format(date);
	             File sdCard = Environment.getExternalStorageDirectory();
					File dir = new File(sdCard + "/baby_loading/");
					if (dir.isDirectory()){
						for (File child : dir.listFiles()) {
							if(child.isDirectory()){
								for (File subChild : child.listFiles()) {
									 if (".".equals(subChild.getName()) || "..".equals(subChild.getName())) {
									      continue;  // Ignore the self and parent aliases.
									    }
									    UploadFolder upload = new UploadFolder(DropboxActivity.this, mApi, SAVE_DIR+"/photos/", subChild);
					                    upload.execute();
								}
							}else{
								 if (".".equals(child.getName()) || "..".equals(child.getName())) {
								      continue;  // Ignore the self and parent aliases.
								    }
								    UploadFolder upload = new UploadFolder(DropboxActivity.this, mApi, SAVE_DIR, child);
				                    upload.execute();
							}
						   
						  }

					}

	                  
				
			}
		});
		

	}
	
	
	
	
	
	 private void logOut() {
	        // Remove credentials from the session
	        mApi.getSession().unlink();

	        // Clear our stored keys
	        clearKeys();
	        // Change UI state to display logged out version
	        setLoggedIn(false);
	    }

	    /**
	     * Convenience function to change UI state based on being logged in
	     */
	    private void setLoggedIn(boolean loggedIn) {
	    	mLoggedIn = loggedIn;
	    	if (loggedIn) {
	    		linkBTN.setText("Unlink from Dropbox");
	    		backupBTN.setVisibility(View.VISIBLE);
	    	} else {
	    		linkBTN.setText("Link with Dropbox");

	    		backupBTN.setVisibility(View.GONE);
	    	}
	    }

	    private void checkAppKeySetup() {
	        // Check to make sure that we have a valid app key
	        if (APP_KEY.startsWith("CHANGE") ||
	                APP_SECRET.startsWith("CHANGE")) {
	            showToast("You must apply for an app key and secret from developers.dropbox.com, and add them to the DBRoulette ap before trying it.");
	            finish();
	            return;
	        }

	        // Check if the app has set up its manifest properly.
	        Intent testIntent = new Intent(Intent.ACTION_VIEW);
	        String scheme = "db-" + APP_KEY;
	        String uri = scheme + "://" + AuthActivity.AUTH_VERSION + "/test";
	        testIntent.setData(Uri.parse(uri));
	        PackageManager pm = getPackageManager();
	        if (0 == pm.queryIntentActivities(testIntent, 0).size()) {
	            showToast("URL scheme in your app's " +
	                    "manifest is not set up correctly. You should have a " +
	                    "com.dropbox.client2.android.AuthActivity with the " +
	                    "scheme: " + scheme);
	            finish();
	        }
	    }

	    private void showToast(String msg) {
	        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
	        error.show();
	    }

	    /**
	     * Shows keeping the access keys returned from Trusted Authenticator in a local
	     * store, rather than storing user name & password, and re-authenticating each
	     * time (which is not to be done, ever).
	     *
	     * @return Array of [access_key, access_secret], or null if none stored
	     */
	    private String[] getKeys() {
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	        String key = prefs.getString(ACCESS_KEY_NAME, null);
	        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
	        if (key != null && secret != null) {
	        	String[] ret = new String[2];
	        	ret[0] = key;
	        	ret[1] = secret;
	        	return ret;
	        } else {
	        	return null;
	        }
	    }

	    /**
	     * Shows keeping the access keys returned from Trusted Authenticator in a local
	     * store, rather than storing user name & password, and re-authenticating each
	     * time (which is not to be done, ever).
	     */
	    private void storeKeys(String key, String secret) {
	        // Save the access key for later
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	        Editor edit = prefs.edit();
	        edit.putString(ACCESS_KEY_NAME, key);
	        edit.putString(ACCESS_SECRET_NAME, secret);
	        edit.commit();
	    }

	    private void clearKeys() {
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	        Editor edit = prefs.edit();
	        edit.clear();
	        edit.commit();
	    }

	    private AndroidAuthSession buildSession() {
	        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
	        AndroidAuthSession session;

	        String[] stored = getKeys();
	        if (stored != null) {
	            AccessTokenPair accessToken = new AccessTokenPair(stored[0], stored[1]);
	            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, accessToken);
	        } else {
	            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
	        }

	        return session;
	    }
	
}
