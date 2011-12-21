package com.andrios.pregnancyjournal.Controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button namesBTN, journalBTN, settingsBTN, emailBTN, panicBTN;
	
	TextView dueDateLBL, ageLBL;
	Profile profile;
	String email;
	
	LinearLayout timelineLL;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        com.andrios.pregnancyjournal.AppRater.app_launched(this);
        
        
    }

	

	private void setConnections() {
		dueDateLBL = (TextView) findViewById(R.id.mainActivityDueDateLBL);
		ageLBL = (TextView) findViewById(R.id.mainActivityAgeLBL);
		

		panicBTN = (Button) findViewById(R.id.mainActivityPanicBTN);
		namesBTN = (Button) findViewById(R.id.mainActivityNamesBTN);
		journalBTN = (Button) findViewById(R.id.mainActivityJournalBTN);
		settingsBTN = (Button) findViewById(R.id.mainActivitySettingsBTN);
		emailBTN = (Button) findViewById(R.id.mainActivityEmailBTN);
		
		
		
		timelineLL = (LinearLayout) findViewById(R.id.mainActivityTimelineLL);
		
		
	}
	
	public void onResume(){
		super.onResume();
		readData();
		setConnections();
        setOnClickListeners();
		buildTimeline();
	}

	private void setOnClickListeners() {
		timelineLL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				timelineDialog();
				
			}
			
		});
		
		
		panicBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				panicDialog();
				
			}
			
		});
		

		
		emailBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				SharedPreferences prefs = MainActivity.this.getSharedPreferences("email", 0);
		        
		        if (prefs.getString("email", null) != null) { 
		        	email = prefs.getString("email", null);
		        	 final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				     
					    emailIntent .setType("plain/text");
					     
					    emailIntent .putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
					     
					    emailIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, "Baby Email");
					     
					    //emailIntent .putExtra(android.content.Intent.EXTRA_TEXT, myBodyText);
					     
					    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		        }else{
		        	emailDialog();
		        }
		        
		        
				
			   

			}

		});
		
		emailBTN.setOnLongClickListener(new OnLongClickListener(){

			public boolean onLongClick(View v) {
				emailDialog();
				return false;
			}
			
		});
		
		settingsBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SettingsActivity.class);
				intent.putExtra("profile", profile);
				startActivity(intent);
			}

		});
		

		
		namesBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NamesListActivity.class);
				
				startActivity(intent);
			}

		});
		
		journalBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), JournalListActivity.class);
				
				startActivity(intent);
			}

		});
		
	}
	
	private void buildTimeline(){
	
		profile.getDays();
		profile.getWeekDays();
		dueDateLBL.setText(profile.getDueDate());
		ageLBL.setText("" + profile.getWeek() + " weeks, " + profile.getWeekDays() + " days");
		
		LinearLayout trimester = (LinearLayout) findViewById(R.id.mainActivityTrimesterLL);
		for(int i = 0; i < trimester.getChildCount(); i++){
			trimester.getChildAt(i).setBackgroundColor(R.color.andrios_grey);
		}
		
		TextView trimesterText = (TextView) trimester.getChildAt(profile.getTrimester());
		trimesterText.setBackgroundColor(Color.CYAN);
		
		
		LinearLayout week = (LinearLayout) findViewById(R.id.mainActivityWeekLL);
		for(int i = 0; i < week.getChildCount(); i++){
			week.getChildAt(i).setBackgroundColor(R.color.andrios_grey);

			week.getChildAt(i).setVisibility(View.VISIBLE);
			if(profile.getTrimester() == 0){
				if(i>=14){
					week.getChildAt(i).setVisibility(View.GONE);
				}
			}else if(profile.getTrimester() == 1){
				if(i<14 || i >=28){
					week.getChildAt(i).setVisibility(View.GONE);
				}
			}else if(profile.getTrimester() == 2){
				if(i <28){
					week.getChildAt(i).setVisibility(View.GONE);
				}
			}
		}
		
		int thisWeek = profile.getWeek() - 1;
		if(thisWeek >= 0){
			TextView weekText = (TextView) week.getChildAt(thisWeek);
			weekText.setBackgroundColor(Color.CYAN);	
		}
		
		
		LinearLayout month = (LinearLayout) findViewById(R.id.mainActivityMonthLL);
		for(int i = 0; i < month.getChildCount(); i++){
			month.getChildAt(i).setBackgroundColor(R.color.andrios_grey);
		}
		TextView monthText = (TextView) month.getChildAt(profile.getMonth());
		monthText.setBackgroundColor(Color.CYAN);
	}
	
	private void readData() {
		try {
			FileInputStream fis = openFileInput("profile");
			ObjectInputStream ois = new ObjectInputStream(fis);

			profile = (Profile) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			profile = new Profile();
			createDialog();
			
		}
		
		
	}
	
	private void createDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Create Mother's Profile");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
				intent.putExtra("profile", profile);
				startActivity(intent);
			}
		});
		
		alert.show();
	}
	
	private void panicDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final CharSequence[] items = {"Baby's Coming...", "Quick Contacts..."}; 
		alert.setTitle("Stay Calm...");
		alert.setItems(items, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int item) {
				if(item == 0){
					Intent intent = new Intent(MainActivity.this, CheckListActivity.class);
					startActivity(intent);
				}else if(item == 1){
					Intent intent = new Intent(MainActivity.this, DoctorListActivity.class);
					startActivity(intent);
				}
				
			}
		});
		alert.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
			}
		});
		
		alert.show();
	}
	
	private void emailDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Email Your Baby");
		alert.setMessage(R.string.about_email);
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				SharedPreferences prefs = MainActivity.this.getSharedPreferences("email", 0);
		        SharedPreferences.Editor editor = prefs.edit();
		        editor.putString("email", input.getText().toString());
		        editor.commit();
			}
		});
		
		alert.show();
	}
	
	private void timelineDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		timelineLL.setDrawingCacheEnabled(true);
		final Bitmap b = Bitmap.createBitmap(timelineLL.getDrawingCache());
		ImageView i = new ImageView(this);
		i.setImageBitmap(b);
		alert.setView(i);
		alert.setMessage(R.string.about_timeline);
		alert.setTitle(profile.getName() + "'s Timeline");
		
		alert.setPositiveButton("Share", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				shareTimeline(b);
			}
		});
		
		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
			}
		});
		
		
		alert.show();
	}
	
	public void shareTimeline(Bitmap b){
		
		File root = android.os.Environment.getExternalStorageDirectory();               

		 File dir = new File (root.getAbsolutePath() + "/baby_loading/");
		try {
			
			b.compress(CompressFormat.PNG, 100, new FileOutputStream(dir+"timeline_"+ profile.getDateString()+"_.png"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);  
		picMessageIntent.setType("image/jpeg");  
		
		File downloadedPic =  new File(  
				dir+"timeline_"+ profile.getDateString()+"_.png");  
		  
		picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(downloadedPic)); 
		picMessageIntent.putExtra(Intent.EXTRA_SUBJECT, "My Wife is " + profile.getWeek() + " weeks, " + profile.getWeekDays() + " days along!");
		picMessageIntent.putExtra(Intent.EXTRA_TEXT, "AndriOS Apps Baby Loading for Android");
		 
	    startActivity(Intent.createChooser(picMessageIntent, "Share your timeline using:"));  
	}

}