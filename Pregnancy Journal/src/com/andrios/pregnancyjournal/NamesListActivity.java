package com.andrios.pregnancyjournal;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NamesListActivity extends Activity {
	
	ListView listView;
	BabyNameAdapter babyNameListAdapter;
	ArrayList<BabyName> nameList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nameslistactivity);
        
        getExtras();
        setConnections();
        setOnClickListeners();
    }

	private void setOnClickListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View v, int row,
					long arg3) {
				Intent intent = new Intent(v.getContext(), BabyNameViewActivity.class);
				intent.putExtra("list", nameList);
				intent.putExtra("index", row);
				startActivity(intent);
			}

		});
		
	}

	private void getExtras() {
		createTestNames();
		
	}

	

	private void setConnections() {
		listView = (ListView) findViewById(R.id.babyNamesActivityListview);
		babyNameListAdapter = new BabyNameAdapter(this, R.layout.baby_name_list_item,
				nameList);
		listView.setAdapter(babyNameListAdapter);
		babyNameListAdapter.setNotifyOnChange(true);
		
	}
	
	/*
	 * TEST FUNCTIONS REMOVE AT PRODUCTION
	 *TODO
	 */
	
	private void createTestNames() {
		nameList = new ArrayList<BabyName>();
		BabyName a = new BabyName();
		a.setName("Trillion");
		a.setGender(false);
		BabyName b = new BabyName();
		b.setName("John");
		b.setGender(true);
		nameList.add(a);
		nameList.add(b);
	}
}
