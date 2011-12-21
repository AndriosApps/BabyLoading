package com.andrios.pregnancyjournal.Models;

import java.io.Serializable;

public class Week implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -8263043925632582737L;
	String name;
	String size;
	String length;
	String weight;
	String description;
	String whatToDo;
	int image;
	
	

	
	public Week(String name, String size, String length, String weight, String description, String whatToDo, int image){
		this.name = name;
		this.size = size;
		this.length = length;
		this.weight = weight;
		this.description = description;
		this.whatToDo = whatToDo;
		this.image = image;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getSize() {
		return size;
	}




	public void setSize(String size) {
		this.size = size;
	}




	public String getLength() {
		return length;
	}




	public void setLength(String length) {
		this.length = length;
	}




	public String getWeight() {
		return weight;
	}




	public void setWeight(String weight) {
		this.weight = weight;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getWhatToDo() {
		return whatToDo;
	}




	public void setWhatToDo(String whatToDo) {
		this.whatToDo = whatToDo;
	}




	public int getImage() {
		return image;
	}




	public void setImage(int image) {
		this.image = image;
	}
	
	

	
}
