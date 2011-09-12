package com.andrios.pregnancyjournal;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class AboutActivity extends Activity {
	
	protected static final int NOTEVIEW = 0;
	ArrayList<JournalEntry> notesList;
	ListView listView;
	JournalEntryAdapter journalEntryListAdapter;
	Button newBTN;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aboutactivity);
        /*
        readData();
        setConnections();
        setOnClickListeners();
    */
    }
    
    
}
