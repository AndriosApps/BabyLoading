package com.andrios.pregnancyjournal;

import java.io.Serializable;

public class BabyName implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7580980267115197322L;

	private String name;
	private int momRating;
	private int dadRating;
	private boolean firstName;
	private boolean isMale;
	private String comments;
	
	
	public BabyName(){
		
	}
	
	/*
	 * Setter Methods
	 */
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setMomRating(int rating){
		this.momRating = rating;
	}
	
	public void setDadRating(int rating){
		this.dadRating = rating;
	}
	
	public void setGender(boolean isMale){
		this.isMale = isMale;
	}
	
	public void setFirstName(boolean isFirstName){
		this.firstName = isFirstName;
	}
	
	public void setComments(String comments){
		this.comments = comments;
	}
	
	/*
	 * Getter Methods
	 */

	public String getName(){
		return name;
	}
	
	public int getMomRating(){
		return momRating;
	}
	
	public int getDadRating(){
		return dadRating;
	}
	
	public boolean getGender(){
		return isMale;
	}
	
	public boolean getFirstName(){
		return firstName;
	}
	
	public String getComments(){
		return comments;
	}


}
