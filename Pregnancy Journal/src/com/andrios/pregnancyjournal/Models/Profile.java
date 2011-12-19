package com.andrios.pregnancyjournal.Models;

import java.io.Serializable;
import java.util.Calendar;


import android.graphics.Bitmap;

public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8939542873830414073L;

	
	String name;
	Calendar c;
	private boolean isLMPDate;
	public boolean firstRun;

	SerialBitmap image;
	
	public Profile(){
		this.name = "Click to Set Name";
		c = getToday();
		this.isLMPDate = true;
		this.firstRun = true;
	}
	
	/*
	 * Getter Methods
	 */
	
	public String getName(){
		return name;
	}
	
	public Calendar getDate(){
		return c;
	}
	
	public boolean isLMPDate(){
		return this.isLMPDate;
	}
	
	public String getDateString(){
		String dateString = "";
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
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
	
	public int getTrimester(){
		int week = this.getWeek();
		if(week < 14){
			return 0;
		}else if (week < 28){
			return 1;
		}else{
			return 2;
		}
		
	}
	
	public int getWeek(){
		Calendar today = getToday();
		long miliToday = today.getTimeInMillis();
		long miliLMP = c.getTimeInMillis();
		long diff = miliToday - miliLMP;
		long diffWeek = diff /(7*24*60*60*1000);
		int returnValue = (int) diffWeek;
		return returnValue;
	}
	
	public int getMonth(){
		Calendar today = getToday();
		
		int y1 = c.get(Calendar.YEAR);
		int y2 = today.get(Calendar.YEAR);
		int m1 = c.get(Calendar.MONTH) + 1;
		int m2 = today.get(Calendar.MONTH) + 1;
		
		int returnValue = (y2 - y1) * 12 + (m2 - m1);

		return returnValue;
	}
	
	public int getDays(){
		Calendar today = getToday();
		
		long miliToday = today.getTimeInMillis();
		long miliLMP = c.getTimeInMillis();
		long diff = miliToday - miliLMP;
		long diffDay = diff /(24*60*60*1000);
		int returnValue = (int) diffDay;

		System.out.println("Days: " + returnValue);
		return returnValue;
	}
	
	public int getWeekDays(){
		Calendar today = getToday();
		
		long miliToday = today.getTimeInMillis();
		long miliLMP = c.getTimeInMillis();
		long diff = miliToday - miliLMP;
		long diffDay = diff /(24*60*60*1000);
	
		
		int returnValue = (int) diffDay % 7;
		System.out.println("Weekdays: " + returnValue);
		return returnValue;
	}
	
	public String getDueDate(){
		Calendar temp = (Calendar) c.clone();
		temp.add(Calendar.DAY_OF_YEAR, 280);
		
		return formatDateString(temp);
	}
	
	public Bitmap getBitmap(){
		try{
			return image.bitmap;
		}catch(Exception e){
			return null;
		}
		
	}
	
	
	/*
	 * Setter Methods
	 */
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDate(Calendar c){
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		this.c = c;
	}
	
	public void setDate(int mYear, int mMonth, int mDay){
		c.set(Calendar.DAY_OF_MONTH, mDay);
		c.set(Calendar.MONTH, mMonth);
		c.set(Calendar.YEAR, mYear);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
	}
	
	public void setisLMPDate(boolean isLMPDate){
		this.isLMPDate = isLMPDate;
	}
	
	
	
	
	private String formatDateString(Calendar temp){
		
		String dateString = "";
		int day = temp.get(Calendar.DAY_OF_MONTH);
		int month = temp.get(Calendar.MONTH)+1;
		int year = temp.get(Calendar.YEAR);
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
	
	public void setBitmap(Bitmap profileBitmap){
		
		this.image= new SerialBitmap(profileBitmap);
	}
	
	private Calendar getToday(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c;
	}
	
}
