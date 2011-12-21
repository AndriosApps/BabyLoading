package com.andrios.pregnancyjournal.Controllers;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.Profile;
import com.andrios.pregnancyjournal.Models.Week;
import com.andrios.pregnancyjournal.R.anim;
import com.andrios.pregnancyjournal.R.drawable;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;

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

public class InfoActivity extends Activity {
	

    
	ViewFlipper flipper;
	TextView previousLBL, currentLBL, nextLBL;
	Profile profile;
	int currentWeek;
	
	ArrayList<Week> weekList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.infoview);
        
     
        getExtras();
        setConnections();
        setOnClickListeners();
        
    }
    
	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		profile = (Profile) intent.getSerializableExtra("profile");
		currentWeek = profile.getWeek() - 1;
		
		buildWeeks();
	}
	
	private void setConnections() {
		
		previousLBL = (TextView) findViewById(R.id.infoViewPreviousLBL);
		
		currentLBL = (TextView) findViewById(R.id.infoViewCurrentLBL);
		
		
		nextLBL = (TextView) findViewById(R.id.infoViewNextLBL);
		
		
		
		flipper = (ViewFlipper) findViewById(R.id.details); 
		setNextWeekView(flipper.getChildAt(flipper.getDisplayedChild()));
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		
		
		
		
	
	}
	
	private void setOnClickListeners(){
		previousLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				previous();
				
				
			}

			
			
		});
		
		nextLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				next();
				
				
			}

			
			
		});
		
	}
	
	private void previous() {
		View v = null;
		if(currentWeek >= 2){
			if(flipper.getChildAt(flipper.getDisplayedChild() + 1) != null){
				v = flipper.getChildAt(flipper.getDisplayedChild() + 1);
				
			}else{
				v = flipper.getChildAt(flipper.getDisplayedChild() - 1);
			}
			currentWeek -=1;
			setNextWeekView(v);
			flipper.showNext();
		}
		
	}
	
	
	private void next(){
		View v = null;
		if(currentWeek <= 38){
			if(flipper.getChildAt(flipper.getDisplayedChild() + 1) != null){
				v = flipper.getChildAt(flipper.getDisplayedChild() + 1);
				
			}else{
				v = flipper.getChildAt(flipper.getDisplayedChild() - 1);
			}
			currentWeek += 1;
			setNextWeekView(v);
			flipper.showNext();
		}
	}
	
	private void setNextWeekView(View v){
		
		if(currentWeek >= 2){
			previousLBL.setText(weekList.get(currentWeek -1).getName());
		}else{
			previousLBL.setText("      ");
		}
		
		currentLBL.setText(weekList.get(currentWeek).getName());
		
		if(currentWeek <= 38){
			nextLBL.setText(weekList.get(currentWeek +1).getName());
		}else{
			nextLBL.setText("      ");
		}
		
		TextView size = (TextView) v.findViewById(R.id.infoViewSizeLBL);
		size.setText(weekList.get(currentWeek).getSize());
		
		TextView length = (TextView) v.findViewById(R.id.infoViewLengthLBL);
		length.setText(weekList.get(currentWeek).getLength());
		
		TextView weight = (TextView) v.findViewById(R.id.infoViewWeightLBL);
		weight.setText(weekList.get(currentWeek).getWeight());
		
		TextView description = (TextView) v.findViewById(R.id.infoViewNotesTXT);
		description.setText(weekList.get(currentWeek).getDescription());
		
		
		ImageView image = (ImageView) v.findViewById(R.id.infoViewImageView);
		image.setImageResource(weekList.get(currentWeek).getImage());
	}
	
	
	private void buildWeeks(){
		weekList = new ArrayList<Week>();
		
		//Week 1
		weekList.add(new Week("Week 1", "N/A", "N/A", "N/A", "Not Yet Pregnent... Get to work...", "Week 1 What to Do", R.drawable.week1));

		//Week 2
		weekList.add(new Week("Week 2", "N/A", "N/A", "N/A", "Not Yet Pregnant... Get to work...", "Week 2 What to Do", R.drawable.week2));

		//Week 3
		weekList.add(new Week("Week 3", "Poppyseed", "N/A", "N/A", "Your little zygote is settling into your uterus...", "Week 3 What to Do", R.drawable.week3));

		//Week 4
		weekList.add(new Week("Week 4", "Poppyseed", "N/A", "N/A", "Your little zygote is settling into your uterus...", "Week 4 What to Do", R.drawable.week4));

		//Week 5
		weekList.add(new Week("Week 5", "Appleseed", ".13 in", "N/A", "Major organs and systems are forming...", "Week 5 What to Do", R.drawable.week5));

		//Week 6
		weekList.add(new Week("Week 6", "Sweet Pea", ".25 in", "N/A", "Blood is starting to circulate... ", "Week 6 What to Do", R.drawable.week6));

		//Week 7
		weekList.add(new Week("Week 7", "Blueberry", ".51 in", "N/A", "Baby's brain is growing fast...", "Week 7 What to Do", R.drawable.week7));

		//Week 8
		weekList.add(new Week("Week 8", "Raspberry", ".63 in", ".04 oz", "Little arms and legs are moving like crazy... ", "Week 8 What to Do", R.drawable.week8));

		//Week 9
		weekList.add(new Week("Week 9", "Green Olive", ".9 in", ".07 oz", "A Doppler device might pick up a heartbeat...", "Week 9 What to Do", R.drawable.week9));

		//Week 10
		weekList.add(new Week("Week 10", "Prune", " 1.2 in", ".14 oz", "Arm joints are working, and soon legs will too...", "Week 10 What to Do", R.drawable.week10));

		//Week 11
		weekList.add(new Week("Week 11", "Lime", "1.6 in", ".25 oz", "Fingers and toes are no longer webbed... ", "Week 11 What to Do", R.drawable.week11));

		//Week 12
		weekList.add(new Week("Week 12", "Plum", "2.1 in", ".49 oz", "Almost all vital systems are fully formed... ", "Week 12 What to Do", R.drawable.week12));

		//Week 13
		weekList.add(new Week("Week 13", "Peach", "2.9 in", ".81 oz", "Teeth and vocal cords are appearing... ", "Week 13 What to Do", R.drawable.week13));

		//Week 14
		weekList.add(new Week("Week 14", "Lemon", "3.4 in", "1.5 oz", "Liver, kidney and spleen are continuing to develop...", "Week 14 What to Do", R.drawable.week14));

		//Week 15
		weekList.add(new Week("Week 15", "Naval Orange", "4.0 in", "2.5 oz", "Legs are finally longer than arms...", "Week 15 What to Do", R.drawable.week15));

		//Week 16
		weekList.add(new Week("Week 16", "Avocado", "4.6 in", "3.5 oz", "Eyebrows, lashes and hair are filling in... ", "Week 16 What to Do", R.drawable.week16));

		//Week 17
		weekList.add(new Week("Week 17", "Onion", "5.1 in", "5.9 oz", "Skeleton is hardening, and fat is accumulating...", "Week 17 What to Do", R.drawable.week17));

		//Week 18
		weekList.add(new Week("Week 18", "Sweet Potato", "5.6 in", "6.7 oz", "Baby's moving like crazy -- feel anything yet?", "Week 18 What to Do", R.drawable.week18));

		//Week 19
		weekList.add(new Week("Week 19", "Mango", "6.0 in", "8.5 oz", "Vernix caseosa is coating baby's skin...", "Week 19 What to Do", R.drawable.week19));

		//Week 20
		weekList.add(new Week("Week 20", "Cantaloupe", "6.5 in", "10.6 oz", "Boy or girl, genitals are fully formed...", "Week 20 What to Do", R.drawable.week20));

		//Week 21
		weekList.add(new Week("Week 21", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 21 What to Do", R.drawable.week21));

		//Week 22
		weekList.add(new Week("Week 22", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 22 What to Do", R.drawable.week22));

		//Week 23
		weekList.add(new Week("Week 23", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 23 What to Do", R.drawable.week23));

		//Week 24
		weekList.add(new Week("Week 24", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 24 What to Do", R.drawable.week24));

		//Week 25
		weekList.add(new Week("Week 25", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 25 What to Do", R.drawable.week25));

		//Week 26
		weekList.add(new Week("Week 26", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 26 What to Do", R.drawable.week26));

		//Week 27
		weekList.add(new Week("Week 27", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 27 What to Do", R.drawable.week27));

		//Week 28
		weekList.add(new Week("Week 28", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 28 What to Do", R.drawable.week28));

		//Week 29
		weekList.add(new Week("Week 29", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Week 29 What to Do", R.drawable.week29));

		//Week 30
		weekList.add(new Week("Week 30", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Week 30 What to Do", R.drawable.week30));

		//Week 31
		weekList.add(new Week("Week 31", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Week 31 What to Do", R.drawable.week31));

		//Week 32
		weekList.add(new Week("Week 32", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Week 32 What to Do", R.drawable.week32));

		//Week 33
		weekList.add(new Week("Week 33", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 33 What to Do", R.drawable.week33));

		//Week 34
		weekList.add(new Week("Week 34", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 34 What to Do", R.drawable.week34));

		//Week 35
		weekList.add(new Week("Week 35", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 35 What to Do", R.drawable.week35));

		//Week 36
		weekList.add(new Week("Week 36", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 36 What to Do", R.drawable.week36));

		//Week 37
		weekList.add(new Week("Week 37", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 37 What to Do", R.drawable.week37));

		//Week 38
		weekList.add(new Week("Week 38", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 38 What to Do", R.drawable.week38));

		//Week 39
		weekList.add(new Week("Week 39", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 39 What to Do", R.drawable.week39));

		//Week 40
		weekList.add(new Week("Week 40", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 40 What to Do", R.drawable.week40));
	}
	

	
	

	


		
		
}
