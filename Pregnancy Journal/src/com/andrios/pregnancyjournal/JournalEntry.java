package com.andrios.pregnancyjournal;

import java.io.Serializable;
import java.util.Calendar;

public class JournalEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221453924685089279L;

	private Calendar date;
	private String notes;
	private int mood;
	private boolean isMorningSick;
	private String title;
	private boolean wishingBoy;
	boolean wishingChanged;
	boolean isImportant;
	boolean isDrVisit;
	boolean isUltrasound;
	double weight;
	double waist;
	
	public JournalEntry(){
		this.date = Calendar.getInstance();
		this.notes = "";
		this.mood = 0;
		this.isMorningSick = false;
		this.wishingChanged = false;
		this.isDrVisit = false;
		this.isUltrasound = false;
		this.title = "";
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
	
	public int getMood(){
		return mood;
	}
	
	public boolean getMorningSick(){
		return isMorningSick;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean getWishingBoy(){
		return wishingBoy;
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
	
	
	
	
	/*
	 * Setter Methods
	 */
	
	
	
	public void setNotes(String notes){
		this.notes = notes;
	}
	
	public void setMood(int mood){
		this.mood = mood;
	}
	
	public void setMorningSick(boolean isMorningSick){
		this.isMorningSick = isMorningSick;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

	public void setDate(Calendar date){
		this.date = date;
	}
	
	public void setWishingBoy(boolean wishingBoy){
		wishingChanged = true;
		this.wishingBoy = wishingBoy;
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
		String gender;
		if(getWishingBoy()){
			gender = "boy";
		}else{
			gender = "girl";
		}
		
		String mood = "";
		switch (getMood()) {
		  case 0: 
			  mood = "Excited";
		    break;
		  case 1: 
			  mood = "Happy";
		    break;
		  case 2: 
			  mood = "Scared";
		    break;
		  case 3: 
			  mood = "Hopefull";
		    break;
		  case 4: 
			  mood = "Worried";
		    break;
		  case 5: 
			  mood = "Crying";
		    break;
		  case 6: 
			  mood = "Uncertain";
		    break;
		  case 7: 
			  mood = "Mad";
		    break;
		  default: 
			  mood = "";
		    
		}
		
		String myString = 
			getDateString() +
			"\n\n" + getTitle() +
			"\nMood: " + getMood() +
			"\nWishing for a  " + gender +
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
	
	
}
