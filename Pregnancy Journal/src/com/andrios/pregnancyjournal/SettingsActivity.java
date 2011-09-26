package com.andrios.pregnancyjournal;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	Button saveBTN, aboutBTN;
	ArrayList<BabyName> nameList;
	ArrayList<JournalEntry> journalList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingsactivity);
        
     
       
        setConnections();
        setOnClickListeners();
        
    }

	private void setConnections() {
		saveBTN = (Button) findViewById(R.id.settingsActivitySaveBTN);
		aboutBTN = (Button) findViewById(R.id.settingsActivityAboutBTN);
	}

	private void setOnClickListeners() {
		
		aboutBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AboutActivity.class);
				
				startActivity(intent);
				
			}
		});
		saveBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				writeJournal();
				writeNames();
				Toast.makeText(SettingsActivity.this, "Files have been written",
						Toast.LENGTH_SHORT).show();
				//TODO
			}
		});
	}
	
	private void writeNames() {
		readNameData();
		try {
			
			File sdCard = Environment.getExternalStorageDirectory();
			//File dir = new File(sdCard + "/Pregnancy Diary");
			//dir.mkdirs();

			if(sdCard.canWrite()){
				File nameFile = new File(sdCard, "BabyNames.txt");
				FileWriter myWriter = new FileWriter(nameFile);
				BufferedWriter out = new BufferedWriter(myWriter);
				for(int i = 0; i < nameList.size(); i++){
					out.write(Integer.toString(i));
					out.write(nameList.get(i).print());
				}
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	private void writeJournal() {
		readJournalData();
		try {
			 Toast t = new Toast(SettingsActivity.this);
			 
			File sdCard = Environment.getExternalStorageDirectory();
			//File dir = new File(sdCard + "/Pregnancy Diary");
			//dir.mkdirs();

			if(sdCard.canWrite()){
				File nameFile = new File(sdCard, "MyJournal.txt");
				FileWriter myWriter = new FileWriter(nameFile);
				BufferedWriter out = new BufferedWriter(myWriter);
				for(int i = 0; i < journalList.size(); i++){
					out.write(journalList.get(i).print());
				}
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	private void readNameData() {
		try {
			FileInputStream fis = openFileInput("names");
			ObjectInputStream ois = new ObjectInputStream(fis);

			nameList = (ArrayList<BabyName>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		
		
	}
	
	private void readJournalData() {
		try {
			FileInputStream fis = openFileInput("notes");
			ObjectInputStream ois = new ObjectInputStream(fis);

			journalList = (ArrayList<JournalEntry>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		
		
	}
}
