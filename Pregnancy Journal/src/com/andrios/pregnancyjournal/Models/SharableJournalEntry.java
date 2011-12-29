package com.andrios.pregnancyjournal.Models;

import java.io.Serializable;


public class SharableJournalEntry implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2099733640169970542L;
	
	public long date;
	public String notes;
	public String mood;
	public String title;
	public boolean isImportant;
	public boolean isDrVisit;
	public boolean isUltrasound;
	public double weight;
	public double waist;
	public SerialBitmap entryBitmap;
	
	public SharableJournalEntry(JournalEntry entry){
		this.date = entry.getDate().getTimeInMillis();
		this.notes = entry.getNotes();
		this.mood = entry.getMood();
		this.isDrVisit = entry.isDrVisit;
		this.isUltrasound = entry.isUltrasound;
		this.isImportant = entry.isImportant;
		this.title = entry.getTitle();
		if(entry.entryBitmap != null){
			entryBitmap = entry.entryBitmap;
		}
		
	}
	
	
}
