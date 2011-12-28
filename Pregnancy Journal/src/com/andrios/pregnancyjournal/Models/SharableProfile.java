package com.andrios.pregnancyjournal.Models;

import java.io.Serializable;
import java.util.Calendar;


import android.graphics.Bitmap;

public class SharableProfile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8939542873830414073L;

	
	public String name;
	public long milis;
	public boolean isLMPDate;
	public boolean firstRun;

	SerialBitmap image;
	
	public SharableProfile(Profile profile){
		this.name = profile.name;
		this.milis = profile.c.getTimeInMillis();
		this.isLMPDate = profile.isLMPDate();
		this.firstRun = profile.firstRun;
		this.image = profile.image;
	}
	
	
	
}
