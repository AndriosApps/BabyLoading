package com.andrios.pregnancyjournal;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyNameViewActivity extends Activity {
	
	ArrayList<BabyName> nameList;
	int index;
	
	TextView nameLBL;
	ImageView genderIMG;
	
	
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
	}

	private void setConnections() {
		BabyName name = nameList.get(index);
		
		nameLBL = (TextView) findViewById(R.id.babyNameViewActivityNameLBL);
		nameLBL.setText(nameList.get(index).getName());
		
		genderIMG = (ImageView) findViewById(R.id.babyNameViewActivityGenderIMG);
		if(name.getGender()){
    		genderIMG.setImageResource(R.drawable.boy);
    	}else{
    		genderIMG.setImageResource(R.drawable.girl);
    	}
	}

	private void setOnClickListeners() {
		// TODO Auto-generated method stub
		
	}
}
