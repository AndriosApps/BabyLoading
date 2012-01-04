package com.andrios.pregnancyjournal.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.SegmentedControlButton;
import com.andrios.pregnancyjournal.Database.JournalDBAdapter;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.Profile;
import com.andrios.pregnancyjournal.R.drawable;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;
import com.andrios.pregnancyjournal.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	

    private static final int CAMERA_REQUEST = 1888; 
	static final int DATE_DIALOG_ID = 1;
	
	Profile profile;
	TextView nameLBL, date2LBL, dateTypeLBL;
	
	SegmentedControlButton dueRDO, lmpRDO;
	
	int mYear, mMonth, mDay;

	DatePicker datePicker;

	ImageView profileIMG;
	JournalDBAdapter dbAdapter;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profileactivity);
        
     
        getExtras();
        setConnections();
        setOnClickListeners();
        
    }
    
    
	private void getExtras() {
		Intent intent = this.getIntent();
		profile = (Profile) intent.getSerializableExtra("profile");
		if(profile.firstRun){
			createDialog();
		}
	}
	
	public void onDestroy(){
		super.onDestroy();
		 updateJournalEntry();
	}
	
	private void updateJournalEntry(){
		 SharedPreferences prefs = getSharedPreferences("profile", 0);
	        int lmpId = prefs.getInt("lmp", -1);
	        int dueId = prefs.getInt("due", -1);
		dbAdapter = new JournalDBAdapter(this);
        dbAdapter.open();
        JournalEntry j = new JournalEntry();
        j.setTitle(profile.getName() + "'s LMP");
        j.setDate(profile.getDate());
        if(lmpId != -1){
        	dbAdapter.updateJournalEntry(j, lmpId);
        }else{
        	lmpId = (int) dbAdapter.createJournalEntry(j);
        	Editor edit = prefs.edit();
        	edit.putInt("lmp", lmpId);
            edit.commit();
        }
        
        //Set Due Date Entry
        JournalEntry j1 = new JournalEntry();
        j1.setTitle(profile.getName() + "'s Due Date");
        Calendar c = profile.getDate();
        c.add(Calendar.DAY_OF_YEAR, 280);
        j1.setDate(c);
        ;
        if(dueId != -1){
        	dbAdapter.updateJournalEntry(j1, dueId);
        }else{
        	dueId = (int) dbAdapter.createJournalEntry(j1);
        	Editor edit = prefs.edit();
        	edit.putInt("due", dueId);
            edit.commit();
        }
        
        dbAdapter.close();
        
        
	}


	private void setConnections() {
		profileIMG = (ImageView) findViewById(R.id.profileActivityImageView);
		if(profile.getBitmap() != null){
			profileIMG.setImageBitmap(profile.getBitmap());
		}
		nameLBL = (TextView) findViewById(R.id.ProfileActivityNameLBL);
		
		datePicker = (DatePicker) findViewById(R.id.profileActivityDatePicker);
		getCalendar();
		datePicker.init(mYear, mMonth, mDay, myOnDateChangedListener);
		
		
		
		date2LBL = (TextView) findViewById(R.id.profileActivityDate2LBL);
		dateTypeLBL = (TextView) findViewById(R.id.profileActivityDateTypeLBL);
	
		//dueRDO = (SegmentedControlButton) findViewById(R.id.profileActivityDueRDO);
		//dueRDO.setChecked(!profile.isLMPDate());
		//lmpRDO = (SegmentedControlButton) findViewById(R.id.profileActivityLMPRDO);
		//lmpRDO.setChecked(profile.isLMPDate());
		
		setDataField();
	}


	private void setOnClickListeners() {
		nameLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				createDialog();
				
			}
			
		});
		
		profileIMG.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
				
			}
			
		});
		
		profileIMG.setOnLongClickListener(new OnLongClickListener(){

			public boolean onLongClick(View v) {
				profile.setBitmap(null);
				profileIMG.setBackgroundResource(R.drawable.momprofile);
				return false;
				
			}
		});
		
//		dueRDO.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				profile.setisLMPDate(!dueRDO.isChecked());
//				setDataField();
//			}
//			
//		});
		
	}

	OnDateChangedListener myOnDateChangedListener = new OnDateChangedListener(){

		public void onDateChanged(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			System.out.println("Date Changed");
			profile.setDate(year, monthOfYear, dayOfMonth);
			getCalendar();
			setDataField();
			
		}
		
	};

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
	public void write(Context ctx){
		if(profile.getDays() >= 0){
			try {
			
				FileOutputStream fos;
				fos = ctx.openFileOutput("profile", Context.MODE_WORLD_WRITEABLE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				profile.firstRun = false;
				oos.writeObject(profile);
	
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(ctx, "Error: Writing to file",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void createDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setTitle("Mother's Name");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString().trim();
				profile.setName(value);
				try{
					nameLBL.setText(profile.getName());
					write(ProfileActivity.this);
				}catch(Exception e){
					
				}
				
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
	}
	
	private void setDataField(){
		
		nameLBL.setText(profile.getName());
		
		
		
		Calendar c = (Calendar) profile.getDate().clone();
		
		if(profile.isLMPDate()){
			c.add(Calendar.DAY_OF_YEAR, 280);
			dateTypeLBL.setText(R.string.title_due_date);
		}else{
			c.add(Calendar.DAY_OF_YEAR, -280);
			dateTypeLBL.setText("LMP Date");
		}
		
		String dateString = "";
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		String monthString = "";
		System.out.println("Month Is " + month);
		if(month == 1){
			monthString = "January";
		}else if(month == 2){
			monthString = "February";
		}else if(month == 3){
			monthString = "March";
		}else if(month == 4){
			monthString = "April";
		}else if(month == 5){
			monthString = "May";
		}else if(month == 6){
			monthString = "June";
		}else if(month == 7){
			monthString = "July";
		}else if(month == 8){
			monthString = "August";
		}else if(month == 9){
			monthString = "September";
		}else if(month == 10){
			monthString = "October";
		}else if(month == 11){
			monthString = "November";
		}else if(month == 12){
			monthString = "December";
		}
		dateString = day + " " + monthString + " " + year; 
        date2LBL.setText(dateString);
		
	}
	
	public void onPause(){
		super.onPause();
		write(ProfileActivity.this);
	}
	

	protected void getCalendar() {
		Calendar c = Calendar.getInstance();
    	c = profile.getDate();
		
		
		mYear = c.get(Calendar.YEAR);
    	mMonth = c.get(Calendar.MONTH);
    	mDay = c.get(Calendar.DAY_OF_MONTH);
		
		
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		 if (requestCode == CAMERA_REQUEST) {  
			 System.out.println("result Code" + resultCode);
			 if((resultCode == RESULT_OK)){
				 Bitmap photo = (Bitmap) intent.getExtras().get("data"); 
		            profile.setBitmap(photo);
		            profileIMG.setImageBitmap(photo);
			 }
	            
	            
	        }  
    }
	

    
}
