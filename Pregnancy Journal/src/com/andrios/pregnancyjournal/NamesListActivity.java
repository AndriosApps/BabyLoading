package com.andrios.pregnancyjournal;

import java.util.ArrayList;
import java.util.Comparator;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
