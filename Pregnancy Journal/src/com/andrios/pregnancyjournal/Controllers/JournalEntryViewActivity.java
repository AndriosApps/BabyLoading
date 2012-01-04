package com.andrios.pregnancyjournal.Controllers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Database.JournalDBAdapter;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.R.anim;
import com.andrios.pregnancyjournal.R.drawable;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class JournalEntryViewActivity extends Activity {
	

    private static final int CAMERA_REQUEST = 1888; 
	static final int DATE_DIALOG_ID = 1;

	static final int SELECT_IMAGE = 2;
	
	
	int mMonth, mDay, mYear;
	long index;
	
	TextView dateLBL;
	EditText commentsTXT, titleTXT;
	JournalEntry note;
	ViewFlipper flipper;
	OnClickListener myOnClickListener;
	ImageView entryIMG;
	TextView moodTXT;
	CheckBox importantCheckBox, ultrasoundCheckBox, drVisitCheckBox;
	JournalDBAdapter dbAdapter;
	Cursor cursor;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.journalentryviewactivity);
        
        dbAdapter = new JournalDBAdapter(this);
        dbAdapter.open();
        getExtras();
        setConnections();
        setOnClickListeners();
        
    }
    
	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		index = intent.getIntExtra("index", -1);
		if(index == -1){
			note = new JournalEntry();
			
		}else{
			
			
			note = dbAdapter.fetchJournalEntry(index);
	    	
		}
		
	}
	
	
	
	private void setConnections() {
		
		//flipBTN = (Button) findViewById(R.id.journalEntryActivityFlipBTN);
		flipper = (ViewFlipper) findViewById(R.id.details); 
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		
		
		
		
		entryIMG = (ImageView) findViewById(R.id.journalEntryViewActivityEntryIMG);
		
			File root = android.os.Environment.getExternalStorageDirectory();               

			 File dir = new File (root.getAbsolutePath() + "/baby_loading/");
			 System.out.println("REading image file: " + note.getImageFile());
			 Bitmap bitmap = BitmapFactory.decodeFile(note.getImageFile());
			 if(bitmap != null){
				 entryIMG.setImageBitmap(bitmap);
			 }
			
		
		
		
		dateLBL = (TextView) findViewById(R.id.journalEntryViewActivityDateLBL);
		dateLBL.setText(note.getDateString());
		
		importantCheckBox = (CheckBox) findViewById(R.id.journalEntryViewActivityImportantCheckBox);
		importantCheckBox.setChecked(note.getImportant());
		
		ultrasoundCheckBox = (CheckBox) findViewById(R.id.journalEntryViewUltrasoundCheckBox);
		ultrasoundCheckBox.setChecked(note.isUltrasound());
		
		
		drVisitCheckBox = (CheckBox) findViewById(R.id.journalEntryViewDrVisitCheckBox);
		drVisitCheckBox.setChecked(note.isDrVisit());
		
		commentsTXT = (EditText) findViewById(R.id.journalEntryViewActivityCommentsTXT);
		commentsTXT.setText(note.getNotes());
		
		
		titleTXT = (EditText) findViewById(R.id.journalEntryViewActivityTitleTXT);
		titleTXT.setText(note.getTitle());
		
		moodTXT = (EditText) findViewById(R.id.journalEntryViewActivityMoodTXT);
		moodTXT.setText(note.getMood());
	
	}
	
	private void setOnClickListeners(){
		entryIMG.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				note.setTitle(titleTXT.getText().toString().trim());
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			}
			
		});
				
//		flipBTN.setOnClickListener(new OnClickListener(){
//
//			public void onClick(View arg0) {
//				flipper.showNext();
//				if(flipper.getDisplayedChild() == 0){
//					flipBTN.setBackgroundResource(R.drawable.button_page);
//				}else{
//					flipBTN.setBackgroundResource(R.drawable.button_clipboard);
//				}
//				
//			}
//			
//		});
		dateLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				
			}
			
		});
		
	
	}
	
	private boolean checkFormat() {
		// TODO Auto-generated method stub
		return true;
	}
	
	  @Override
	    protected Dialog onCreateDialog(int id) {
	            switch (id) {

	            case DATE_DIALOG_ID:
	                    return new DatePickerDialog(this,
	                            mDateSetListener,
	                            note.getDate().get(Calendar.YEAR), 
	                            note.getDate().get(Calendar.MONTH), 
	                            note.getDate().get(Calendar.DAY_OF_MONTH));
	            }
	            return null;
	    }
	    protected void onPrepareDialog(int id, Dialog dialog) {
	            switch (id) {

	            case DATE_DIALOG_ID:
	                    ((DatePickerDialog) dialog).updateDate(
	                    		note.getDate().get(Calendar.YEAR), 
	                            note.getDate().get(Calendar.MONTH), 
	                            note.getDate().get(Calendar.DAY_OF_MONTH));
	                    break;
	            }
	    }    
	   
	    private DatePickerDialog.OnDateSetListener mDateSetListener =
	            new DatePickerDialog.OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						
	                    note.setDate(dayOfMonth, monthOfYear, year);
	                    dateLBL.setText(note.getDateString());
						
					}

	          
	    };
	    
		@Override
	    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			
			 if (requestCode == CAMERA_REQUEST) {  
				 if(resultCode == RESULT_OK){
					
					 Bitmap photo = (Bitmap) intent.getExtras().get("data"); 
					 note.setBitmap(photo);
			         entryIMG.setImageBitmap(photo);
				 }
		           
		        }  
	    }

	
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event)  {
		   if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			   if(checkFormat()){
					
					Intent intent = new Intent();
					note.setNotes(commentsTXT.getText().toString().trim());
					note.setMood(moodTXT.getText().toString().trim());
					note.setDrVisit(drVisitCheckBox.isChecked());
					note.setUltrasound(ultrasoundCheckBox.isChecked());
					note.setTitle(titleTXT.getText().toString().trim());
					note.setImportant(importantCheckBox.isChecked());
					
					if(index >= 0){
						dbAdapter.updateJournalEntry(note, index);
					}else{
						dbAdapter.createJournalEntry(note);
					}
				
					
					JournalEntryViewActivity.this.setResult(RESULT_OK, intent);
					JournalEntryViewActivity.this.finish();
				}
		       return true;
		   }

		return super.onKeyDown(keyCode, event);
		}

		
		
}
