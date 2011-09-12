package com.andrios.pregnancyjournal;

import java.io.Serializable;
import java.util.Calendar;

public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8939542873830414073L;

	
	String name;
	Calendar c;
	
	
	public Profile(){
		this.name = "Click to Set Name";
		c = Calendar.getInstance();
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
	
	
	/*
	 * Setter Methods
	 */
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDate(Calendar c){
		this.c = c;
	}
	
	
	
	
}
