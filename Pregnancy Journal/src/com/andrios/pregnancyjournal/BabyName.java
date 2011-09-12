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
		this.name = "Click Here";
		this.isMale = true;
		this.momRating = 0;
		this.dadRating = 0;
		this.comments = "Add Comments Here!";
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
	
	public double getRating(){
		return (double) (momRating + dadRating)/2;
	}
	
	public String print(){
		String myString = "\n" + name +
						"\n Mom's Rating: " + Integer.toString(momRating)+
						"\n Dad's Rating: " + Integer.toString(dadRating)+
						"\n" + comments + "\n\n";
		
		
		return myString;
	}


}
