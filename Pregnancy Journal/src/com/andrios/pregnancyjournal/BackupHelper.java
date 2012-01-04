package com.andrios.pregnancyjournal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import com.andrios.pregnancyjournal.Database.JournalDBAdapter;
import com.andrios.pregnancyjournal.Models.BabyName;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.Profile;

public class BackupHelper {

	
	
	public static String writeNames(Context context){
		ArrayList<BabyName> nameList = null;
		try {
			FileInputStream fis = context.openFileInput("names");
			ObjectInputStream ois = new ObjectInputStream(fis);

			nameList = (ArrayList<BabyName>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		
		try {
			
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File(sdCard + "/baby_loading");
			dir.mkdirs();

			if(sdCard.canWrite()){
				File nameFile = new File(dir, "BabyNames.txt");
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
		
		return "BabyNames.txt Written to: SDCard/baby_loading";
	}
	
	public static String writeJournal(Context context){
		JournalDBAdapter dbAdapter = new JournalDBAdapter(context);
        dbAdapter.open();
		Cursor cursor = dbAdapter.fetchAllJournalEntries();
		System.out.println("Backup Helper Cursor Size: " + cursor.getCount());
		
		
		try {
			 
			 
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File(sdCard + "/baby_loading");
			dir.mkdirs();

			if(sdCard.canWrite()){
				File nameFile = new File(dir, "MyJournal.txt");
				FileWriter myWriter = new FileWriter(nameFile);
				BufferedWriter out = new BufferedWriter(myWriter);
				cursor.moveToFirst();
				while(cursor.isAfterLast() == false){
					int position = cursor.getInt(cursor.getColumnIndex(JournalDBAdapter.KEY_ROWID));
					JournalEntry j = dbAdapter.fetchJournalEntry(position);
					System.out.println(j.getTitle() + " printing");
					out.write(j.print());
					cursor.moveToNext();
				}
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		dbAdapter.close();
		return "MyJournal.txt Written to: SDCard/baby_loading";
	}

	
	public static String writeProfile(Context context) {
		Profile profile = null;
		try {
			FileInputStream fis = context.openFileInput("profile");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			profile = (Profile) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		
		try {
			 
			 
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File(sdCard + "/baby_loading");
			dir.mkdirs();

			if(sdCard.canWrite()){
				File nameFile = new File(dir, "MyProfile.txt");
				FileWriter myWriter = new FileWriter(nameFile);
				BufferedWriter out = new BufferedWriter(myWriter);
				
				out.write(profile.print());
				
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "Profile Written to: SDCard/baby_loading";
		
		
	}
	
	
	
}
