package com.andrios.pregnancyjournal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JournalListActivity extends Activity {
	
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
        setContentView(R.layout.journallistactivity);
        
        readData();
        setConnections();
        setOnClickListeners();
    }
    
    

	private void setConnections() {
		
		
		newBTN = (Button) findViewById(R.id.journalListActivityNewBTN);
		listView = (ListView) findViewById(R.id.journalListActivityListview);
		journalEntryListAdapter = new JournalEntryAdapter(this, R.layout.journal_entry_list_item,
				notesList);
		listView.setAdapter(journalEntryListAdapter);
		journalEntryListAdapter.setNotifyOnChange(true);
		journalEntryListAdapter.sort(new Comparator<JournalEntry>() {

			public int compare(JournalEntry object1, JournalEntry object2) {
				 Calendar c1 = object1.getDate();
		            Calendar c2 = object2.getDate();
		            if (c1.after(c2)) {
		                return -1;
		            } else if (c1.before(c2)) {
		                return 1;
		            } else {
		                return 0;
		            }
				
			}

		});
		
	}
	
	
	private void setOnClickListeners() {
			newBTN.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), JournalEntryViewActivity.class);
					intent.putExtra("list", notesList);
					startActivityForResult(intent, NOTEVIEW);
				}
			});
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View v, int row,
						long arg3) {
					Intent intent = new Intent(v.getContext(), JournalEntryViewActivity.class);
					intent.putExtra("list", notesList);
					intent.putExtra("index", row);
					startActivityForResult(intent, NOTEVIEW);
				}

			});
	}





	private void createTestNote() {
		JournalEntry test = new JournalEntry();
		test.setTitle("Introduction");
		test.setNotes("Welcome to Pregnancy Diary!");
		notesList.add(test);
	}

    
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
    	if (requestCode == NOTEVIEW) {
    		if (resultCode == RESULT_OK) {
    			notesList = (ArrayList<JournalEntry>) intent.getSerializableExtra("list");
    			setConnections();
    			write(JournalListActivity.this);
    		} else {
    			
    			Toast.makeText(this, "Add  Canceled", Toast.LENGTH_SHORT).show();
    		}
    	}
    }
	
	
    private void readData() {
		try {
			FileInputStream fis = openFileInput("notes");
			ObjectInputStream ois = new ObjectInputStream(fis);

			notesList = (ArrayList<JournalEntry>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			notesList = new ArrayList<JournalEntry>();
			createTestNote();
			
			
		}
		
		
	}
	






	public void write(Context ctx){
		
		try {
		
			FileOutputStream fos;
			fos = ctx.openFileOutput("notes", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(notesList);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Error: Writing to file",
					Toast.LENGTH_SHORT).show();
		}
	}
}