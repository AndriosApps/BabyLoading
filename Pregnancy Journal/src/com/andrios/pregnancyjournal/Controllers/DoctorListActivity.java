package com.andrios.pregnancyjournal.Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.Doctor;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DoctorListActivity extends Activity {
	

	protected static final int NAMEVIEW = 0;
	protected static final int PICK_CONTACT = 1;
	ListView listView;
	ArrayAdapter<Doctor> myAdapter;
	ArrayList<Doctor> doctorList;
	Button newBTN, pickBTN;
	int position;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.doctorlistactivity);
        
        readData();
        setConnections();
        setOnClickListeners();
    }

	private void setOnClickListeners() {
		
		newBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
				intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
				startActivityForResult(intent, PICK_CONTACT);

				
			}
			
		});
		
		pickBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

				startActivityForResult(intent, PICK_CONTACT);

				
			}
			
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View v, int row,
					long arg3) {
				 Intent intent = new Intent(Intent.ACTION_VIEW);
				    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(myAdapter.getItem(row).getId()));
				    intent.setData(uri);
				DoctorListActivity.this.startActivity(intent);

			}

		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				position = arg2;
				setAlertDialog();
				return false;
			}

		});
		
	}



	

	private void setConnections() {
		
		newBTN = (Button) findViewById(R.id.doctorListActivityNewBTN);
		pickBTN = (Button) findViewById(R.id.doctorListActivityPickBTN);
		listView = (ListView) findViewById(R.id.babyNamesActivityListview);
		myAdapter = new ArrayAdapter<Doctor>(this,
                android.R.layout.simple_list_item_1, doctorList);
		myAdapter.setNotifyOnChange(true);
		listView.setAdapter(myAdapter);
		
	}
	
	/*
	 * TEST FUNCTIONS REMOVE AT PRODUCTION
	 *TODO
	 */
	

	

	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch (requestCode) {
		    case (PICK_CONTACT) :
		    	System.out.println("Pick Contact returned");
			      if (resultCode == Activity.RESULT_OK) {
			    	  Cursor cursor =  managedQuery(intent.getData(), null, null, null, null);
			          cursor.moveToNext();
			          String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			           String  name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)); 
			           String lookupKey = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.LOOKUP_KEY));
			           myAdapter.add(new Doctor(name, contactId, lookupKey));
			           write(DoctorListActivity.this);
			          // TODO Whatever you want to do with the selected contact name.
			        }
	      
	
	  }

    }
	
	private void readData() {
		try {
			FileInputStream fis = openFileInput("quick_contacts");
			ObjectInputStream ois = new ObjectInputStream(fis);

			doctorList = (ArrayList<Doctor>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			doctorList = new ArrayList<Doctor>();
			
			
		}
		
		
	}
	public void write(Context ctx){
		
		try {
		
			FileOutputStream fos;
			fos = ctx.openFileOutput("quick_contacts", Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(doctorList);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Error: Writing to file",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void setAlertDialog() {
    	AlertDialog alertDialog = new AlertDialog.Builder(DoctorListActivity.this).create();
    	alertDialog.setTitle("Delete Name");
    	alertDialog.setMessage("Are you sure you want to remove "+ doctorList.get(position).toString());
    	alertDialog.setButton("Remove", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				myAdapter.remove(doctorList.get(position));
				write(DoctorListActivity.this);
			}
		});
    	alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	alertDialog.show();
	}
}
