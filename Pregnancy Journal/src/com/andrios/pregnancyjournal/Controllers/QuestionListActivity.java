package com.andrios.pregnancyjournal.Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QuestionListActivity extends Activity {
	
	ListView listView;
	ArrayList<String> checkList;
	Button newBTN;
	AlertDialog deleteDialog;
	int position;
	ArrayAdapter<String> myAdapter;
	ArrayList<Integer> checkedItems;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.babycominglistactivity);
        
        readData();
        setConnections();
        setOnClickListeners();
    }

	private void setOnClickListeners() {
		newBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				newDialog();
				
			}
			
		});
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View v, int row,
					long arg3) {


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
		TextView titleTXT = (TextView)findViewById(R.id.titleLBL);
		titleTXT.setText("Questions for Dr.");
		newBTN = (Button) findViewById(R.id.checkListActivityNewBTN);
		listView = (ListView) findViewById(R.id.checkListActivityListview);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, checkList);
		myAdapter.setNotifyOnChange(true);
		listView.setAdapter(myAdapter);
		for(int i = 0; i < checkedItems.size(); i++){
			System.out.println("Setting Check Box " + i);
	        if (checkedItems.get(i)==1) {

	            listView.setItemChecked(i, true);
	        }else{
	        	listView.setItemChecked(i, false);
	        }
	    

	}

		
	
	}
	
	/*
	 * TEST FUNCTIONS REMOVE AT PRODUCTION
	 *TODO
	 */
	
	private void createTestItems() {
		checkList = new ArrayList<String>();
		checkedItems = new ArrayList<Integer>();
		
		
		

		checkList.add("Wife's Hospital Bag");
		checkedItems.add(0);
		checkList.add("Your Hospital Bag");
		checkedItems.add(0);
		checkList.add("Notify Doctor");
		checkedItems.add(0);
		checkList.add("Notify Family / Friends");
		checkedItems.add(0);
		checkList.add("Wallet");
		checkedItems.add(0);
		checkList.add("Phone & Charger");
		checkedItems.add(0);
		checkList.add("Camera / Batteries / Memory Card");
		checkedItems.add(0);
		checkList.add("Car Seat");
		checkedItems.add(0);
		checkList.add("Entertainment");
		checkedItems.add(0);
		
	}
	

	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
//    	if (requestCode == NAMEVIEW) {
//    		if (resultCode == RESULT_OK) {
//    			nameList = (ArrayList<BabyName>) intent.getSerializableExtra("list");
//    			setConnections();
//    			write(CheckListActivity.this);
//    		} else {
//    			
//    			Toast.makeText(this, "Add  Canceled", Toast.LENGTH_SHORT).show();
//    		}
//    	}
    }
	
	private void readData() {
		try {
			FileInputStream fis = openFileInput("question_list");
			ObjectInputStream ois = new ObjectInputStream(fis);

			checkList = (ArrayList<String>) ois.readObject();
			checkedItems = (ArrayList<Integer>) ois.readObject();
			
			
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			createTestItems();
			
			
		}
		
		
		
		
	}
	public void write(Context ctx){
		SparseBooleanArray checked = listView.getCheckedItemPositions();
		checkedItems = new ArrayList<Integer>();
		for(int i = 0; i < checkList.size(); i++){
	        if (checked.valueAt(i)) {

				System.out.println("Setting Checked " + i + ": ");
	            checkedItems.add(1);
	        }else{

				System.out.println("Setting UnChecked " + i + ": ");
	        	checkedItems.add(0);
	        }
	    

	}
		try {
		
			FileOutputStream fos;
			fos = ctx.openFileOutput("question_list", Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(checkList);
			oos.writeObject(checkedItems);


			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Error: Writing to file",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void setAlertDialog() {
    	AlertDialog alertDialog = new AlertDialog.Builder(QuestionListActivity.this).create();
    	alertDialog.setTitle("Delete Item");
    	alertDialog.setMessage("Are you sure you want to remove "+ checkList.get(position));
    	alertDialog.setButton("Remove", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				String removeItem = myAdapter.getItem(position);
				myAdapter.remove(removeItem);
				
				write(QuestionListActivity.this);
			}
		});
    
    	if(position > 0){
       	alertDialog.setButton3("Move Up", new DialogInterface.OnClickListener() {
			
    			public void onClick(DialogInterface dialog, int which) {
    				String moveItem = myAdapter.getItem(position);
    				
    				myAdapter.remove(moveItem);
    				myAdapter.insert(moveItem, position -1);
    				
    				write(QuestionListActivity.this);
    				
    			}
    		});
    	}
    	
    	if(position < checkList.size()-1){
           	alertDialog.setButton3("Move Down", new DialogInterface.OnClickListener() {
    			
        			public void onClick(DialogInterface dialog, int which) {
        				String moveItem = myAdapter.getItem(position);
        				
        				myAdapter.remove(moveItem);
        				myAdapter.insert(moveItem, position +1);
        				
        				write(QuestionListActivity.this);
        				
        			}
        		});
        	}
    	
    	alertDialog.show();
	}
	
	private void newDialog() {
    	AlertDialog alertDialog = new AlertDialog.Builder(QuestionListActivity.this).create();
    	alertDialog.setTitle("Add Item");
    	final EditText input = new EditText(this);
		alertDialog.setView(input);
    	alertDialog.setButton("Add", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				myAdapter.add(input.getText().toString().trim());
				
				write(QuestionListActivity.this);
			}
		});
    	alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	alertDialog.show();
	}
	
	public void onBackPressed(){
		super.onBackPressed();
		write(QuestionListActivity.this);
	}
	
}
