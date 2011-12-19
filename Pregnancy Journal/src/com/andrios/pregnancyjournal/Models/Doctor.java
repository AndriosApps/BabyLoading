package com.andrios.pregnancyjournal.Models;

import java.io.Serializable;

public class Doctor implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -8263043925632582737L;
	String name;
	String id; 
	String lookupKey;
	
	

	
	public Doctor(String name, String id, String lookupKey){
		this.name = name;
		this.id = id;
		this.lookupKey = lookupKey;
	}
	
	/*
	 * Getter Methods
	 */
	public String toString(){
		return name;
	}
	
	public String getKey(){
		return lookupKey;
	}
	
	public String getId(){
		return id;
	}
	
	
	/*
	 * Setter Methods
	 */
	

	
}
