package com.andrios.pregnancyjournal.Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Calendar;

import com.andrios.pregnancyjournal.Database.JournalDBAdapter;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Toast;

public class JournalEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221453924685089279L;

	private Calendar date;
	private String notes;
	private String mood;
	private String title;
	boolean isImportant;
	boolean isDrVisit;
	boolean isUltrasound;
	boolean isWeighIn;
	boolean isTimeLapse;
	int weight;
	int waist;
	String imageFile;
	SerialBitmap entryBitmap;
	
	public JournalEntry(){
		this.date = Calendar.getInstance();
		this.notes = "";
		this.mood = "";
		this.isDrVisit = false;
		this.isUltrasound = false;
		this.isImportant = false;
		this.isWeighIn = false;
		this.isTimeLapse = false;
		this.title = "";
		//entryBitmap = new SerialBitmap();
	}
	
	public JournalEntry(Cursor c){
		c.moveToFirst();
		System.out.println("Journal Entry Count: " + c.getCount());
		this.date = Calendar.getInstance();
		String dateString = c.getString(c.getColumnIndex(JournalDBAdapter.KEY_DATE));
		this.date.setTimeInMillis(Long.parseLong(dateString));
		
		this.notes = c.getString(c.getColumnIndex(JournalDBAdapter.KEY_NOTES));
		this.mood = c.getString(c.getColumnIndex(JournalDBAdapter.KEY_MOOD));
		this.title = c.getString(c.getColumnIndex(JournalDBAdapter.KEY_TITLE));
		this.weight = c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_WEIGHT));
		this.waist = c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_WAIST));
		if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_DOCTOR)) == 1){
			this.isDrVisit = true;
		}else{
			this.isDrVisit = false;
		}
		if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_ULTRASOUND)) == 1){
			this.isUltrasound = true;
		}else{
			this.isUltrasound = false;
		}
		if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_IMPORTANT)) == 1){
			this.isImportant = true;
		}else{
			this.isImportant = false;
		}
		if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_WEIGH_IN)) == 1){
			this.isWeighIn = true;
		}else{
			this.isWeighIn = false;
		}
		if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_TIMELAPSE)) == 1){
			this.isTimeLapse = true;
		}else{
			this.isTimeLapse = false;
		}

		this.imageFile = c.getString(c.getColumnIndex(JournalDBAdapter.KEY_IMAGE));
	}
	
	public JournalEntry(SharableJournalEntry entry){
		this.date = Calendar.getInstance();
		this.date.setTimeInMillis(entry.date);
		this.notes = entry.notes;
		this.mood = entry.mood;
		this.isDrVisit = entry.isDrVisit;
		this.isUltrasound = entry.isUltrasound;
		this.isImportant = entry.isImportant;
		this.title = entry.title;
		if(entry.entryBitmap != null){
			entryBitmap = entry.entryBitmap;
		}
	}
	
	/*
	 * Getter Methods
	 */
	
	public Calendar getDate(){
		return date;
	}
	
	public String getNotes(){
		return notes;
	}
	
	public String getMood(){
		return mood;
	}
	
	
	public String getTitle(){
		return title;
	}
	
	
	
	public boolean getImportant(){
		return isImportant;
	}
	
	public String getDateString(){
		String dateString = "";
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH)+1;
		int year = date.get(Calendar.YEAR);
		String monthString = "";
		if(month == 1){
			monthString = "January";
		}else if(month == 2){
			monthString = "February";
		}else if(month == 3){
			monthString = "March";
		}else if(month == 4){
			monthString = "April";
		}else if(month == 5){
			monthString = "May";
		}else if(month == 6){
			monthString = "June";
		}else if(month == 7){
			monthString = "July";
		}else if(month == 8){
			monthString = "August";
		}else if(month == 9){
			monthString = "September";
		}else if(month == 10){
			monthString = "October";
		}else if(month == 11){
			monthString = "November";
		}else if(month == 12){
			monthString = "December";
		}
		dateString = day + " " + monthString + " " + year; 
        
		return dateString;
	}
	
	public String getImageDateString(){
		String dateString = "";
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH)+1;
		int year = date.get(Calendar.YEAR);
		
		dateString = year + " " + month + " " + day + " "; 
        
		return dateString;
		
	}
	
	public int getWeight(){
		return weight;
	}
	
	public  int getWaist(){
		return waist;
	}
	
	public boolean isDrVisit(){
		return isDrVisit;
	}
	
	public boolean isUltrasound(){
		return isUltrasound;
	}
	
	
	public Bitmap getBitmap(){
		try{
			return entryBitmap.bitmap;
		}catch(Exception e){
			return null;
		}
		
	}

	
	
	
	/*
	 * Setter Methods
	 */
	
	
	
	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public boolean isWeighIn() {
		return isWeighIn;
	}

	public void setWeighIn(boolean isWeighIn) {
		this.isWeighIn = isWeighIn;
	}

	public boolean isTimeLapse() {
		return isTimeLapse;
	}

	public void setTimeLapse(boolean isTimeLapse) {
		this.isTimeLapse = isTimeLapse;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}
	
	public void setMood(String mood){
		this.mood = mood;
	}
	
	
	public void setTitle(String title){
		this.title = title;
		if(imageFile != null){
			
			File root = android.os.Environment.getExternalStorageDirectory();               

			 File dir = new File (root.getAbsolutePath() + "/baby_loading/photos");
			 dir.mkdirs();
			 
			File file = new File(imageFile);
			 File newFile = new File (dir, "/"+getImageDateString() + this.title + ".png");
			 file.renameTo(newFile);
			 setImageFile(dir+"/"+getImageDateString()+getTitle()+".png");
		}
		
	}

	public void setDate(Calendar date){
		this.date = date;
		
	}
	
	
	public void setImportant(boolean isImportant){
		this.isImportant = isImportant;
	}

	public void setDate(int dayOfMonth, int monthOfYear, int year) {
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, monthOfYear);
		date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		if(imageFile != null){
			
			File root = android.os.Environment.getExternalStorageDirectory();               

			 File dir = new File (root.getAbsolutePath() + "/baby_loading/photos");
			 dir.mkdirs();
			 
			File file = new File(imageFile);
			 File newFile = new File (dir, "/"+getImageDateString() + this.title + ".png");
			 file.renameTo(newFile);
			 setImageFile(dir+"/"+getImageDateString()+getTitle()+".png");
		}
		
	}
	
	public String print(){
		
		 
		String important = "";
		String doctorVisit = "";
		String ultrasound = "";
		String imageName = "";
		
		if(isImportant){
			important = "          **IMPORTANT**";
		}
		if(this.isDrVisit){
			doctorVisit = "\nDoctor Visit";
		}
		
		if(this.isUltrasound){
			ultrasound = "\nUltrasound";
		}
		
		if(imageFile != null){
			imageName = imageFile;
		}
		
		String myString = 
			getDateString() +
			"\n\n" + getTitle() +
			important +
			doctorVisit+
			ultrasound+
			"\nMood: " + getMood() +
			imageName+
			"\n\n Notes:\n" + getNotes() +
			"\n\n"; 
		
		return myString;
	}
	

	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public void setWaist(int waist){
		this.waist = waist;
	}
	public void setDrVisit(boolean isDrVisit){
		this.isDrVisit = isDrVisit;
	}
	
	public void setUltrasound(boolean isUltrasound){
		this.isUltrasound = isUltrasound;
	}

	
	public void setBitmap(Bitmap profileBitmap){
		if(this.title.equals("")){
			
		}
		File root = android.os.Environment.getExternalStorageDirectory();               

		 File dir = new File (root.getAbsolutePath() + "/baby_loading/photos");
		 dir.mkdirs();
		 File file = new File (dir, "/"+getImageDateString() + this.title + ".png");
		 
			 try {
					
					profileBitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
				
					e.printStackTrace();
				}
		setImageFile(dir+"/"+getImageDateString()+getTitle()+".png");
		
	}
	
	
}
