package com.andrios.pregnancyjournal.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.andrios.pregnancyjournal.Constants;
import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.Profile;
import com.andrios.pregnancyjournal.Models.SharableJournalEntry;
import com.andrios.pregnancyjournal.Models.SharableProfile;

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
				writeOut(BackupController.this);
				if(username.length()<5 || password.length()<5){
					Toast.makeText(BackupController.this, "Username & Password must be 5 characters", Toast.LENGTH_SHORT).show();
				}else{
					
					try {
						//PUT PROFILE
	                	s3Client.createBucket( Constants.getBabyLoadingBucket(username, password) );
	                	System.out.println("Backing up Profile");
	                	PutObjectRequest por = new PutObjectRequest(Constants.getBabyLoadingBucket(username, password), Constants.PROFILE_NAME, openFileInput("temp"), null);
	                	s3Client.putObject( por );
	                	
	                	//PUT BABY NAME LIST
	                	System.out.println("Backing up Baby Name List");
	                	por = new PutObjectRequest(Constants.getBabyLoadingBucket(username, password), Constants.BABY_NAME, openFileInput("names"), null);
	                	s3Client.putObject( por );
	                	
	                	//PUT CHECKLIST
	                	System.out.println("Backing up Check List");
	                	por = new PutObjectRequest(Constants.getBabyLoadingBucket(username, password), Constants.CHECKLIST_NAME, openFileInput("checklist"), null);
	                	s3Client.putObject( por );
	                	
	                	//PUT CHECKLIST
	                	System.out.println("Backing up Quick Contacts");
	                	por = new PutObjectRequest(Constants.getBabyLoadingBucket(username, password), Constants.CONTACTS_NAME, openFileInput("quick_contacts"), null);
	                	s3Client.putObject( por );
	                	
	                	//PUT Journal
	                	System.out.println("Backing up Journal");
	                	convertJournalEntries();
	                	por = new PutObjectRequest(Constants.getBabyLoadingBucket(username, password), Constants.JOURNAL_NAME, openFileInput("temp"), null);
	                	s3Client.putObject( por );
	                	
	                	System.out.println("BACKUP COMPLETE");
	                }
	                catch ( Exception exception ) {
	                	displayAlert( "Upload Failure", exception.getMessage() );
	                	exception.printStackTrace();
	                }
				}
				
				
			}
			
		});
		
		restoreBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				String username = usernameTXT.getText().toString().trim();
				String password = passwordTXT.getText().toString().trim();
				if(username.length()<5 || password.length()<5){
					Toast.makeText(BackupController.this, "Username & Password must be 5 characters", Toast.LENGTH_SHORT).show();
				}else{
					try{
						//GET Profile
						S3Object objectComplete = s3Client.getObject(Constants.getBabyLoadingBucket(username, password), Constants.PROFILE_NAME);
						writeFile(objectComplete, "temp");
						
						//GET Baby Name List
						objectComplete = s3Client.getObject(Constants.getBabyLoadingBucket(username, password), Constants.BABY_NAME);
						writeFile(objectComplete, "names");
						
						//GET Baby Coming Check List
						objectComplete = s3Client.getObject(Constants.getBabyLoadingBucket(username, password), Constants.CHECKLIST_NAME);
						writeFile(objectComplete, "checklist");
						
						//GET Quick Contacts
						objectComplete = s3Client.getObject(Constants.getBabyLoadingBucket(username, password), Constants.CONTACTS_NAME);
						writeFile(objectComplete, "quick_contacts");
						
						//GET Journal
						System.out.println("Getting Journal Entries");
						objectComplete = s3Client.getObject(Constants.getBabyLoadingBucket(username, password), Constants.JOURNAL_NAME);
						writeFile(objectComplete, "temp");
						revertJournalEntries();
						
						System.out.println("Restore Complete");

					}catch(Exception e){
						e.printStackTrace();
					}
					convertToProfile();
				}
				
			}
			
		});
	}
	
	private void writeFile(S3Object objectComplete, String fileName){
		try{
			InputStream in = objectComplete.getObjectContent();
			byte[] buf = new byte[1024];
			OutputStream out = openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
			int count;
			while( (count = in.read(buf)) != -1)
			{
			   if( Thread.interrupted() )
			   {
			       throw new InterruptedException();
			   }
			   out.write(buf, 0, count);
			}
			out.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
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
    
    private void convertToProfile() {
		try {
			FileInputStream fis = openFileInput("temp");
			ObjectInputStream ois = new ObjectInputStream(fis);

			SharableProfile sharedProfile = (SharableProfile) ois.readObject();
			System.out.println("Shared Profile Name: " + sharedProfile.name);
			profile.setEverything(sharedProfile);
			
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			
			
		}
		writeIn(BackupController.this);
		
	}
    
    public void writeOut(Context ctx){
    		SharableProfile sharedProfile = new SharableProfile(profile);
			try {
				
				FileOutputStream fos;
				fos = ctx.openFileOutput("temp", Context.MODE_WORLD_WRITEABLE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				oos.writeObject(sharedProfile);
	
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(ctx, "Error: Writing to file",
						Toast.LENGTH_SHORT).show();
			}
		
	}
	
    public void writeIn(Context ctx){
		
		try {
			
			FileOutputStream fos;
			fos = ctx.openFileOutput("profile", Context.MODE_WORLD_WRITEABLE);
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
    
    public void convertJournalEntries(){
    	System.out.println("Converting Journal Entries");
    	ArrayList<JournalEntry> notesList = null;
    	ArrayList<SharableJournalEntry> tempList = new ArrayList<SharableJournalEntry>();
    	
    	try {
			FileInputStream fis = openFileInput("notes");
			ObjectInputStream ois = new ObjectInputStream(fis);

			notesList = (ArrayList<JournalEntry>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
    	
    	
    	for(int i = 0; i < notesList.size(); i++){
    		System.out.println(notesList.get(i).getTitle());
    		tempList.add(new SharableJournalEntry(notesList.get(i)));
    	}
    	
    	try {
    		
			FileOutputStream fos;
			fos = openFileOutput("temp", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(tempList);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
    }
    
    public void revertJournalEntries(){
    	System.out.println("Reverting Journal Entries");
    	ArrayList<JournalEntry> notesList = new ArrayList<JournalEntry>();
    	ArrayList<SharableJournalEntry> tempList = new ArrayList<SharableJournalEntry>();
    	
    	try {
			FileInputStream fis = openFileInput("temp");
			ObjectInputStream ois = new ObjectInputStream(fis);

			tempList = (ArrayList<SharableJournalEntry>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			
			
			
		}
    	
    	for(int i = 0; i < tempList.size(); i++){
    		System.out.println(tempList.get(i).title);
    		notesList.add(new JournalEntry(tempList.get(i)));
    	}
    	
    	try {
    		
			FileOutputStream fos;
			fos = openFileOutput("notes", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(notesList);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
    }
   
}
