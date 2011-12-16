package com.andrios.pregnancyjournal;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button namesBTN, journalBTN, settingsBTN, emailBTN;
	ImageView profileBTN;
	TextView dueDateLBL;
	Profile profile;
	String email;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        
        
    }

	

	private void setConnections() {
		dueDateLBL = (TextView) findViewById(R.id.mainActivityDueDateLBL);
		
		
		namesBTN = (Button) findViewById(R.id.mainActivityNamesBTN);
		journalBTN = (Button) findViewById(R.id.mainActivityJournalBTN);
		settingsBTN = (Button) findViewById(R.id.mainActivitySettingsBTN);
		emailBTN = (Button) findViewById(R.id.mainActivityEmailBTN);
		
		
		profileBTN = (ImageView) findViewById(R.id.mainActivityProfileBTN);
		
		if(profile.getBitmap() != null){
			profileBTN.setImageBitmap(profile.getBitmap());
			
		}
	}
	
	public void onResume(){
		super.onResume();
		readData();
		setConnections();
        setOnClickListeners();
		buildTimeline();
	}

	private void setOnClickListeners() {
		profileBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileActivity.class);
				intent.putExtra("profile", profile);
				startActivity(intent);
				
			}
		});
		

		
		emailBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				SharedPreferences prefs = MainActivity.this.getSharedPreferences("email", 0);
		        
		        if (prefs.getString("email", null) != null) { 
		        	email = prefs.getString("email", null);
		        	System.out.println("email: " + email);
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
		
		settingsBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SettingsActivity.class);
				
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
	
		dueDateLBL.setText(profile.getDueDate());
		
		LinearLayout trimester = (LinearLayout) findViewById(R.id.mainActivityTrimesterLL);
		for(int i = 0; i < trimester.getChildCount(); i++){
			trimester.getChildAt(i).setBackgroundColor(R.color.andrios_grey);
		}
		TextView trimesterText = (TextView) trimester.getChildAt(profile.getTrimester());
		trimesterText.setBackgroundColor(Color.CYAN);
		
		LinearLayout week = (LinearLayout) findViewById(R.id.mainActivityWeekLL);
		for(int i = 0; i < week.getChildCount(); i++){
			week.getChildAt(i).setBackgroundColor(R.color.andrios_grey);
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
		        
		        System.out.println("Email " + input.getText().toString());
		        editor.putString("email", input.getText().toString());
		        editor.commit();
			}
		});
		
		alert.show();
	}
}