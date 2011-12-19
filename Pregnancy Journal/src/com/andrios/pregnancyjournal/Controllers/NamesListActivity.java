package com.andrios.pregnancyjournal.Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Adapters.BabyNameAdapter;
import com.andrios.pregnancyjournal.Models.BabyName;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NamesListActivity extends Activity {
	
	ListView listView;
	BabyNameAdapter babyNameListAdapter;
	ArrayList<BabyName> nameList;
	Button newBTN, boySortBTN, girlSortBTN, allSortBTN;
	AlertDialog deleteDialog;
	protected static final int NAMEVIEW = 0;
	int position;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nameslistactivity);
        
        readData();
        setConnections();
        setOnClickListeners();
    }

	private void setOnClickListeners() {
		boySortBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				babyNameListAdapter.sort(new Comparator<BabyName>() {

					public int compare(BabyName object1, BabyName object2) {
						boolean gender1 = object1.getGender();
						boolean gender2 = object2.getGender();
						if(gender1 && !gender2){
							return-1;
						}else if( gender2 && !gender1){
							return 1;
						}
						
						 double score1 = object1.getRating();
				            double score2 = object2.getRating();
				            if (score1 > score2) {
				                return -1;
				            } else if (score1 < score2) {
				                return 1;
				            } else {
				                return 0;
				            }
						
					}

				});
				
			}
			
		});
		
		girlSortBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				babyNameListAdapter.sort(new Comparator<BabyName>() {

					public int compare(BabyName object1, BabyName object2) {
						boolean gender1 = object1.getGender();
						boolean gender2 = object2.getGender();
						if(gender1 && !gender2){
							return 1;
						}else if( gender2 && !gender1){
							return -1;
						}
						
						 double score1 = object1.getRating();
				            double score2 = object2.getRating();
				            if (score1 > score2) {
				                return -1;
				            } else if (score1 < score2) {
				                return 1;
				            } else {
				                return 0;
				            }
						
					}

				});
				
			}
			
		});
		
		allSortBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				babyNameListAdapter.sort(new Comparator<BabyName>() {

					public int compare(BabyName object1, BabyName object2) {
						 double score1 = object1.getRating();
				            double score2 = object2.getRating();
				            if (score1 > score2) {
				                return -1;
				            } else if (score1 < score2) {
				                return 1;
				            } else {
				                return 0;
				            }
						
					}

				});
				
			}
			
		});
		newBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BabyNameViewActivity.class);
				intent.putExtra("list", nameList);
				startActivityForResult(intent, NAMEVIEW);
				
			}
			
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View v, int row,
					long arg3) {
				Intent intent = new Intent(v.getContext(), BabyNameViewActivity.class);
				intent.putExtra("list", nameList);
				intent.putExtra("index", row);
				startActivityForResult(intent, NAMEVIEW);
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
		boySortBTN = (Button) findViewById(R.id.namesListActivityBoySortBTN);

		girlSortBTN = (Button) findViewById(R.id.namesListActivityGirlSortBTN);
		allSortBTN = (Button) findViewById(R.id.namesListActivityAllSortBTN);
		newBTN = (Button) findViewById(R.id.namesListActivityNewBTN);
		listView = (ListView) findViewById(R.id.babyNamesActivityListview);
		babyNameListAdapter = new BabyNameAdapter(this, R.layout.baby_name_list_item,
				nameList);
		listView.setAdapter(babyNameListAdapter);
		babyNameListAdapter.setNotifyOnChange(true);
		babyNameListAdapter.sort(new Comparator<BabyName>() {

			public int compare(BabyName object1, BabyName object2) {
				 double score1 = object1.getRating();
		            double score2 = object2.getRating();
		            if (score1 > score2) {
		                return -1;
		            } else if (score1 < score2) {
		                return 1;
		            } else {
		                return 0;
		            }
				
			}

		});
	}
	
	/*
	 * TEST FUNCTIONS REMOVE AT PRODUCTION
	 *TODO
	 */
	
	private void createTestNames() {
		nameList = new ArrayList<BabyName>();
		BabyName a = new BabyName();
		a.setName("Trillian");
		a.setGender(false);
		
		
		
		BabyName b = new BabyName();
		b.setName("John");
		
		
		nameList.add(a);
		nameList.add(b);
	}
	

	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
    	if (requestCode == NAMEVIEW) {
    		if (resultCode == RESULT_OK) {
    			nameList = (ArrayList<BabyName>) intent.getSerializableExtra("list");
    			setConnections();
    			write(NamesListActivity.this);
    		} else {
    			
    			Toast.makeText(this, "Add  Canceled", Toast.LENGTH_SHORT).show();
    		}
    	}
    }
	
	private void readData() {
		try {
			FileInputStream fis = openFileInput("names");
			ObjectInputStream ois = new ObjectInputStream(fis);

			nameList = (ArrayList<BabyName>) ois.readObject();
			ois.close();
			fis.close();
			
		} catch (Exception e) {
			createTestNames();
			
			
		}
		
		
	}
	public void write(Context ctx){
		
		try {
		
			FileOutputStream fos;
			fos = ctx.openFileOutput("names", Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(nameList);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Error: Writing to file",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void setAlertDialog() {
    	AlertDialog alertDialog = new AlertDialog.Builder(NamesListActivity.this).create();
    	alertDialog.setTitle("Delete Name");
    	alertDialog.setMessage("Are you sure you want to remove "+ nameList.get(position).getName());
    	alertDialog.setButton("Remove", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				babyNameListAdapter.remove(nameList.get(position));
				write(NamesListActivity.this);
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
