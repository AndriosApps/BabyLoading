package com.andrios.pregnancyjournal.Controllers;


import java.util.ArrayList;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.BabyName;
import com.andrios.pregnancyjournal.R.drawable;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyNameViewActivity extends Activity {
	
	ArrayList<BabyName> nameList;
	int index;
	
	TextView nameLBL;
	EditText commentsTXT;
	ImageView genderIMG;
	ImageView mom1, mom2, mom3, mom4, mom5;
	ImageView dad1, dad2, dad3, dad4, dad5;
	BabyName name;
	Button saveBTN;
	OnClickListener myOnClickListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.babynameviewactivity);
        
        getExtras();
        setConnections();
        setOnClickListeners();
    }

	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		nameList = (ArrayList<BabyName>) intent.getSerializableExtra("list");
		index = intent.getIntExtra("index", -1);
		if(index == -1){
			name = new BabyName();
			
		}else{
			name = nameList.get(index);
		}
		
	}

	private void setConnections() {
	
		saveBTN = (Button) findViewById(R.id.babyNameViewActivitySaveBTN);
		
		nameLBL = (TextView) findViewById(R.id.babyNameViewActivityNameLBL);
		
		nameLBL.setText(name.getName());
		
		
		
		commentsTXT = (EditText) findViewById(R.id.babyNameViewActivityCommentsTXT);
		commentsTXT.setText(name.getComments());
		
		genderIMG = (ImageView) findViewById(R.id.babyNameViewActivityGenderIMG);
		if(name.getGender()){
    		genderIMG.setImageResource(R.drawable.boy);
    	}else{
    		genderIMG.setImageResource(R.drawable.girl);
    	}
		
		
		/*
		 * Star Ratings
		 */
		mom1 = (ImageView) findViewById(R.id.mom1);
		mom2 = (ImageView) findViewById(R.id.mom2);
		mom3 = (ImageView) findViewById(R.id.mom3);
		mom4 = (ImageView) findViewById(R.id.mom4);
		mom5 = (ImageView) findViewById(R.id.mom5);
		
		dad1 = (ImageView) findViewById(R.id.dad1);
		dad2 = (ImageView) findViewById(R.id.dad2);
		dad3 = (ImageView) findViewById(R.id.dad3);
		dad4 = (ImageView) findViewById(R.id.dad4);
		dad5 = (ImageView) findViewById(R.id.dad5);
		setStarRatings();
		
		myOnClickListener = new OnClickListener(){

			public void onClick(View v) {
				if(v == mom1){
					name.setMomRating(1);
				}else if(v == mom2){
					name.setMomRating(2);
				}else if(v == mom3){
					name.setMomRating(3);
				}else if(v == mom4){
					name.setMomRating(4);
				}else if(v == mom5){
					name.setMomRating(5);
				}else if(v == dad1){
					name.setDadRating(1);
				}else if(v == dad2){
					name.setDadRating(2);
				}else if(v == dad3){
					name.setDadRating(3);
				}else if(v == dad4){
					name.setDadRating(4);
				}else if(v == dad5){
					name.setDadRating(5);
				}
				
				setStarRatings();
				
			}
			
		};
	}

	private void setOnClickListeners() {
		nameLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				createDialog();
				
			}
			
		});
		
		genderIMG.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				name.setGender(!name.getGender());
				if(name.getGender()){
		    		genderIMG.setImageResource(R.drawable.boy);
		    	}else{
		    		genderIMG.setImageResource(R.drawable.girl);
		    	}
			}
			
		});
		
		saveBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(checkFormat()){
					Intent intent = new Intent();
					name.setComments(commentsTXT.getText().toString().trim());
					
					if(index == -1){
						nameList.add(name);
					}else{
						nameList.set(index, name);
					}
					
					
					intent.putExtra("list", nameList);
				System.out.println("SAVE BUTTON CLICKED");
					BabyNameViewActivity.this.setResult(RESULT_OK, intent);
					BabyNameViewActivity.this.finish();
				}
				
			}

			
			
		});
		
		mom1.setOnClickListener(myOnClickListener);
		mom2.setOnClickListener(myOnClickListener);
		mom3.setOnClickListener(myOnClickListener);
		mom4.setOnClickListener(myOnClickListener);
		mom5.setOnClickListener(myOnClickListener);
		dad1.setOnClickListener(myOnClickListener);
		dad2.setOnClickListener(myOnClickListener);
		dad3.setOnClickListener(myOnClickListener);
		dad4.setOnClickListener(myOnClickListener);
		dad5.setOnClickListener(myOnClickListener);
		
		
	}
	
	private void setStarRatings(){
		if(name.getMomRating() >= 1){
			mom1.setImageResource(R.drawable.star_lit);
		}else{
			mom1.setImageResource(R.drawable.star_outline);
		}
		if(name.getMomRating() >= 2){
			mom2.setImageResource(R.drawable.star_lit);
		}else{
			mom2.setImageResource(R.drawable.star_outline);
		}
		if(name.getMomRating() >= 3){
			mom3.setImageResource(R.drawable.star_lit);
		}else{
			mom3.setImageResource(R.drawable.star_outline);
		}
		if(name.getMomRating() >= 4){
			mom4.setImageResource(R.drawable.star_lit);
		}else{
			mom4.setImageResource(R.drawable.star_outline);
		}
		if(name.getMomRating() >= 5){
			mom5.setImageResource(R.drawable.star_lit);
		}else{
			mom5.setImageResource(R.drawable.star_outline);
		}
		if(name.getDadRating() >= 1){
			dad1.setImageResource(R.drawable.star_lit);
		}else{
			dad1.setImageResource(R.drawable.star_outline);
		}
		if(name.getDadRating() >= 2){
			dad2.setImageResource(R.drawable.star_lit);
		}else{
			dad2.setImageResource(R.drawable.star_outline);
		}
		if(name.getDadRating() >= 3){
			dad3.setImageResource(R.drawable.star_lit);
		}else{
			dad3.setImageResource(R.drawable.star_outline);
		}
		
		if(name.getDadRating() >= 4){
			dad4.setImageResource(R.drawable.star_lit);
		}else{
			dad4.setImageResource(R.drawable.star_outline);
		}
		if(name.getDadRating() >= 5){
			dad5.setImageResource(R.drawable.star_lit);
		}else{
			dad5.setImageResource(R.drawable.star_outline);
		}
	}
	
	private boolean checkFormat() {
		// TODO Auto-generated method stub
		return true;
	}
	
	private void createDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setTitle("Edit Name");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString().trim();
				name.setName(value);
				try{
					nameLBL.setText(name.getName());
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
}
