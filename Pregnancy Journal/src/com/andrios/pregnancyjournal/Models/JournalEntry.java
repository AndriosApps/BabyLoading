package com.andrios.pregnancyjournal.Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Calendar;


import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
	double weight;
	double waist;
	SerialBitmap entryBitmap;
	
	public JournalEntry(){
		this.date = Calendar.getInstance();
		this.notes = "";
		this.mood = "";
		this.isDrVisit = false;
		this.isUltrasound = false;
		this.title = "";
		//entryBitmap = new SerialBitmap();
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
		System.out.println("Month Is " + month);
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
	
	public double getWeight(){
		return weight;
	}
	
	public  double getWaist(){
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
	
	
	
	public void setNotes(String notes){
		this.notes = notes;
	}
	
	public void setMood(String mood){
		this.mood = mood;
	}
	
	
	public void setTitle(String title){
		this.title = title;
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
		
	}
	
	public String print(){
		File root = android.os.Environment.getExternalStorageDirectory();               

		 File dir = new File (root.getAbsolutePath() + "/baby_loading/");
		 dir.mkdirs();
		 File file = new File (dir, getDateString() + this.title + ".png");
		 
			 try {
					
					entryBitmap.bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file));
					System.out.println("Output: " +this.title + ".png");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
				
					e.printStackTrace();
				}
		 
		
		
		
		
		String myString = 
			getDateString() +
			"\n\n" + getTitle() +
			"\nMood: " + getMood() +
			"\n\n Notes:\n" + getNotes() +
			"\n\n"; 
		
		return myString;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	public void setWaist(double waist){
		this.waist = waist;
	}
	public void setDrVisit(boolean isDrVisit){
		this.isDrVisit = isDrVisit;
	}
	
	public void setUltrasound(boolean isUltrasound){
		this.isUltrasound = isUltrasound;
	}

	
	public void setBitmap(Bitmap profileBitmap){
		
		this.entryBitmap= new SerialBitmap(profileBitmap);
	}
	
	
}
