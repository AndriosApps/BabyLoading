package com.andrios.pregnancyjournal.Controllers;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.andrios.pregnancyjournal.BackupHelper;
import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.BabyName;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends Activity {
  
	protected static final int BACKUP = 0;
	protected static final int PROFILE = 1;
	
	Button saveBTN, aboutBTN, backupBTN, dropboxBTN;
	ImageView profileBTN;
	ArrayList<BabyName> nameList;
	ArrayList<JournalEntry> journalList;
	Profile profile;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingsactivity);
        

       getExtras();
        
    }
    
    public void onResume(){
    	super.onResume();
    	
        setConnections();
        setOnClickListeners();

        
        
    }

	private void getExtras() {
		Intent intent = this.getIntent();
		profile = (Profile) intent.getSerializableExtra("profile");
		
		
	}

	private void setConnections() {
		dropboxBTN = (Button) findViewById(R.id.settingsActivityDropboxBTN);
		saveBTN = (Button) findViewById(R.id.settingsActivitySaveBTN);
		aboutBTN = (Button) findViewById(R.id.settingsActivityAboutBTN);
		backupBTN = (Button) findViewById(R.id.settingsActivityBackupBTN);
		profileBTN = (ImageView) findViewById(R.id.settingsActivityProfileBTN);
		if(profile.getBitmap() != null){
			profileBTN.setImageBitmap(profile.getBitmap());
			
		}
		LinearLayout hideme = (LinearLayout)findViewById(R.id.settingsActivityHideMe);
		hideme.setVisibility(View.GONE);
	}

	private void setOnClickListeners() {
		dropboxBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), DropboxActivity.class);
				
				startActivityForResult(intent, BACKUP);
			}
		});
		backupBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BackupController.class);
				intent.putExtra("profile", profile);
				startActivityForResult(intent, BACKUP);
				
			}
		});
		
		profileBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileActivity.class);
				intent.putExtra("profile", profile);
				startActivityForResult(intent, PROFILE);
				
			}
		});
		aboutBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AboutActivity.class);
				
				startActivity(intent);
				
			}
		});
		saveBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				BackupHelper.writeNames(SettingsActivity.this);

				BackupHelper.writeJournal(SettingsActivity.this);

				BackupHelper.writeProfile(SettingsActivity.this);
				Toast.makeText(SettingsActivity.this, "Files have been written to SDCard/baby_loading",
						Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	

	
	private void readProfile() {
		try {
			FileInputStream fis = openFileInput("profile");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			profile = (Profile) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		
		
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
    	if (requestCode == BACKUP || requestCode == PROFILE) {
    		if (resultCode == RESULT_OK) {
    			readProfile();
    		} else {
    			
    			//Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    		}
    	}
    }
	
	
}
