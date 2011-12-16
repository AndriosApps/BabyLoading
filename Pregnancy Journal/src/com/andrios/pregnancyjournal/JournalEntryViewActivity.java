package com.andrios.pregnancyjournal;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
	
	ArrayList<JournalEntry> noteList;
	int index, mMonth, mDay, mYear;
	
	TextView dateLBL;
	EditText commentsTXT, titleTXT;
	JournalEntry note;
	Button saveBTN,flipBTN;
	ViewFlipper flipper;
	OnClickListener myOnClickListener;
	ImageView entryIMG;
	TextView moodTXT;
	CheckBox importantCheckBox, ultrasoundCheckBox, drVisitCheckBox;
	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.journalentryviewactivity);
        
     
        getExtras();
        setConnections();
        setOnClickListeners();
        
    }
    
	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		noteList = (ArrayList<JournalEntry>) intent.getSerializableExtra("list");
		index = intent.getIntExtra("index", -1);
		if(index == -1){
			note = new JournalEntry();
			
		}else{
			note = noteList.get(index);
		}
		
	}
	
	private void setConnections() {
		
		flipBTN = (Button) findViewById(R.id.journalEntryActivityFlipBTN);
		flipper = (ViewFlipper) findViewById(R.id.details); 
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		
		
		
		
		entryIMG = (ImageView) findViewById(R.id.journalEntryViewActivityEntryIMG);
		if(note.getBitmap() != null){
			entryIMG.setImageBitmap(note.getBitmap());
		}
		
		saveBTN = (Button) findViewById(R.id.journalEntryViewActivitySaveBTN);
		
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
				
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			}
			
		});
				
		flipBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				flipper.showNext();
				if(flipper.getDisplayedChild() == 0){
					flipBTN.setBackgroundResource(R.drawable.button_page);
				}else{
					flipBTN.setBackgroundResource(R.drawable.button_clipboard);
				}
				
			}
			
		});
		dateLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				
			}
			
		});
		
		saveBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(checkFormat()){
					
					System.out.println("SAVE 1");
					Intent intent = new Intent();
					note.setNotes(commentsTXT.getText().toString().trim());
					note.setMood(moodTXT.getText().toString().trim());
					note.setDrVisit(drVisitCheckBox.isChecked());
					note.setUltrasound(ultrasoundCheckBox.isChecked());
					note.setTitle(titleTXT.getText().toString().trim());
					note.setImportant(importantCheckBox.isChecked());
					

					System.out.println("SAVE 2");
					if(index == -1){
						noteList.add(note);
					}else{
						noteList.set(index, note);
					}
					
					Toast.makeText(JournalEntryViewActivity.this, "saving entry", Toast.LENGTH_SHORT).show();//TODO

					intent.putExtra("list", noteList);
					JournalEntryViewActivity.this.setResult(RESULT_OK, intent);
					JournalEntryViewActivity.this.finish();
				}
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
		            Bitmap photo = (Bitmap) intent.getExtras().get("data"); 
		            note.setBitmap(photo);
		            entryIMG.setImageBitmap(photo);
		        }  
	    }

	


		
		
}
