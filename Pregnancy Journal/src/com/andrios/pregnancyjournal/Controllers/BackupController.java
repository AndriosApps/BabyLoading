package com.andrios.pregnancyjournal.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.andrios.pregnancyjournal.Constants;
import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BackupController extends Activity {
	
	  private AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( Constants.ACCESS_KEY_ID, Constants.SECRET_KEY ) );
		
	Button backupBTN, restoreBTN;
	EditText usernameTXT, passwordTXT;
	Profile profile;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.backupview);
        
        getExtras();
        setConnections();
        setOnClickListeners();
    
    }

    private void getExtras(){
    	Intent intent = this.getIntent();
		profile = (Profile) intent.getSerializableExtra("profile");
    }
	private void setConnections() {
		usernameTXT = (EditText)findViewById(R.id.backupControllerUsernameTXT);
		passwordTXT = (EditText)findViewById(R.id.backupControllerPasswordTXT);
		
		backupBTN = (Button)findViewById(R.id.backupControllerBackupBTN);
		restoreBTN = (Button)findViewById(R.id.backupControllerRestoreBTN);
	}


	private void setOnClickListeners() {
		backupBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				String username = usernameTXT.getText().toString().trim();
				String password = passwordTXT.getText().toString().trim();
				if(username.length()<5 || password.length()<5){
					Toast.makeText(BackupController.this, "Username & Password must be 5 characters", Toast.LENGTH_SHORT).show();
				}else{
					
					try {
						write(BackupController.this);
	                	s3Client.createBucket( Constants.getPictureBucket(username, password) );
	                	String file = "profile.dat";
	                	PutObjectRequest por = new PutObjectRequest(Constants.getPictureBucket(username, password), Constants.PROFILE_NAME, openFileInput("profile"), null);
	                	//PutObjectRequest por = new PutObjectRequest( Constants.getPictureBucket(username, password), Constants.PROFILE_NAME, new java.io.File(file));  // Content type is determined by file extension.
	                	s3Client.putObject( por );
	                }
	                catch ( Exception exception ) {
	                	displayAlert( "Upload Failure", exception.getMessage() );
	                	exception.printStackTrace();
	                }
				}
				
				
			}
			
		});
	}
	
    // Display an Alert message for an error or failure.
    protected void displayAlert( String title, String message ) {
        AlertDialog.Builder confirm = new AlertDialog.Builder( this );
        confirm.setTitle( title);
        confirm.setMessage( message );
        confirm.setNegativeButton( "OK", new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int which ) {
            	finish();
            }
        } );
        confirm.show().show();                
    }
    
    private void readData() {
		try {
			FileInputStream fis = openFileInput("profile");
			ObjectInputStream ois = new ObjectInputStream(fis);

			profile = (Profile) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			
			
		}
		
		
	}
    
    public void write(Context ctx){
		if(profile.getDays() >= 0){
			try {
				
				FileOutputStream fos;
				fos = ctx.openFileOutput("profile.dat", Context.MODE_WORLD_WRITEABLE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				oos.writeObject(profile);
	
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(ctx, "Error: Writing to file",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
    
   
   
}
