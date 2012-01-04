package com.andrios.pregnancyjournal.Controllers;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Adapters.JournalEntryAdapter;
import com.andrios.pregnancyjournal.Adapters.JournalEntryListCursorAdapter;
import com.andrios.pregnancyjournal.Database.JournalDBAdapter;
import com.andrios.pregnancyjournal.Models.JournalEntry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class JournalListActivity extends Activity {
	
	protected static final int NOTEVIEW = 0;
	ArrayList<JournalEntry> notesList;
	ListView listView;
	JournalEntryAdapter journalEntryListAdapter;
	Button newBTN;
	int position;
	JournalDBAdapter dbAdapter;
	Cursor cursor;
	JournalEntryListCursorAdapter journalCursorAdapter;
	ArrayList<Integer> untouchables;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.journallistactivity);
        
        dbAdapter = new JournalDBAdapter(this);
        dbAdapter.open();
        SharedPreferences prefs = getSharedPreferences("profile", 0);
        untouchables = new ArrayList<Integer>();
        untouchables.add(prefs.getInt("lmp", -1));
        untouchables.add(prefs.getInt("due", -1));
       
        
        setConnections();
        setOnClickListeners();
    }
    
    public void onDestroy(){
    	super.onDestroy();
    	dbAdapter.close();
    }
    

	private void setConnections() {
		
		fillData();
		newBTN = (Button) findViewById(R.id.journalListActivityNewBTN);
		listView = (ListView) findViewById(R.id.journalListActivityListview);
		

    	// the desired columns to be bound
    	String[] columns = new String[] { dbAdapter.KEY_DATE, dbAdapter.KEY_TITLE };
    	// the XML defined views which the data will be bound to
    	int[] to = new int[] { R.id.journal_entry_list_item_dateLBL, R.id.journal_entry_list_item_titleLBL };
	    	
	    	journalCursorAdapter = new JournalEntryListCursorAdapter(this, R.layout.journal_entry_list_item, cursor, columns, to);
	    	
	    	listView.setAdapter(journalCursorAdapter);
	
		
	}
	
	
	private void setOnClickListeners() {
			newBTN.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), JournalEntryViewActivity.class);
					
					startActivityForResult(intent, NOTEVIEW);
				}
			});
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View v, int row,
						long arg3) {
					Intent intent = new Intent(v.getContext(), JournalEntryViewActivity.class);
					cursor.moveToPosition(row);
					position = cursor.getInt(cursor.getColumnIndex(JournalDBAdapter.KEY_ROWID));
					
					intent.putExtra("index", position);
					startActivityForResult(intent, NOTEVIEW);
				}

			});
			
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int row, long arg3) {
					cursor.moveToPosition(row);
					position = cursor.getInt(cursor.getColumnIndex(JournalDBAdapter.KEY_ROWID));
					if(untouchables.contains(position)){
						
					}else{
						setAlertDialog();
					}
					
					return false;
				}

			});
	}

	
	private void setAlertDialog() {
    	AlertDialog alertDialog = new AlertDialog.Builder(JournalListActivity.this).create();
    	alertDialog.setTitle("Delete Entry");
    	alertDialog.setMessage("Are you sure you want to remove "+ dbAdapter.fetchJournalEntry(position).getTitle());
    	alertDialog.setButton("Remove", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dbAdapter.deleteJournalEntry(position);
				setConnections();
			}
		});
    	alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	alertDialog.show();
	}
	
	 private void fillData(){
		
        
     		try {
     			FileInputStream fis = openFileInput("notes");
     			ObjectInputStream ois = new ObjectInputStream(fis);
     			
     			notesList = (ArrayList<JournalEntry>) ois.readObject();
     			
     			for(int i = 0; i < notesList.size(); i++){
     				dbAdapter.convertOldJournalEntry(notesList.get(i));
     			}
     			
     			ois.close();
     			fis.close();
     			deleteFile("notes");
     			
     		} catch (Exception e) {
     		
     			e.printStackTrace();
     		}
     		
     		
     	
			
			cursor = dbAdapter.fetchAllJournalEntries();
			startManagingCursor(cursor);
	    }
	 
	 @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			
	    	setConnections();
	    }
	 
	   
}