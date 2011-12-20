package com.andrios.pregnancyjournal.Controllers;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.Profile;
import com.andrios.pregnancyjournal.R.color;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;
import com.andrios.pregnancyjournal.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		TextView weekText = (TextView) week.getChildAt(profile.getWeek());
		weekText.setBackgroundColor(Color.CYAN);
		
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
		Bitmap b = Bitmap.createBitmap(timelineLL.getDrawingCache());
		ImageView i = new ImageView(this);
		i.setImageBitmap(b);
		alert.setView(i);
		alert.setMessage(R.string.about_timeline);
		alert.setTitle("Mother's Timeline");
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
			}
		});
		
		alert.show();
	}
}