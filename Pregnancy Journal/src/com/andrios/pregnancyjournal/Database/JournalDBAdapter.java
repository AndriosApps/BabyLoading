package com.andrios.pregnancyjournal.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.andrios.pregnancyjournal.Models.JournalEntry;
import com.andrios.pregnancyjournal.Models.JournalEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class JournalDBAdapter {
	//Database Fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_NOTES = "notes";
	public static final String KEY_MOOD = "mood";
	public static final String KEY_TITLE = "title";
	public static final String KEY_WEIGHT = "weight";
	public static final String KEY_WAIST = "waist";
	public static final String KEY_IS_DOCTOR = "isdoctor";
	public static final String KEY_IS_ULTRASOUND = "isultrasound";
	public static final String KEY_IS_IMPORTANT = "isimportant";
	public static final String KEY_IS_WEIGH_IN = "isweighin";
	public static final String KEY_IS_TIMELAPSE = "istimelapse";
	public static final String KEY_IMAGE = "image";
	

	public static final String DATABASE_TABLE = "journal";
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public JournalDBAdapter(Context context){
		this.context = context;
	}
	
	public JournalDBAdapter open() throws SQLException{
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public long createJournalEntry(JournalEntry j){
		
			
		ContentValues initialValues = createContentValues(Long.toString(j.getDate().getTimeInMillis()), j.getNotes(), j.getMood(), j.getTitle(), j.getWeight(), j.getWaist(), j.isDrVisit()? 1 : 0, j.isUltrasound()? 1 : 0, j.getImportant()? 1 : 0, j.isWeighIn()? 1 : 0, j.isTimeLapse()? 1 : 0, j.getImageFile());
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public long convertOldJournalEntry(JournalEntry j){
		File root = android.os.Environment.getExternalStorageDirectory();     
		File dir = new File (root.getAbsolutePath() + "/baby_loading/photos");
		 File imageFile = new File(dir+"/"+j.getDateString()+j.getTitle());
		 dir.mkdirs();
		 String fileName = null;
		 try {
			 	Bitmap photo = j.getBitmap();
				photo.compress(CompressFormat.PNG, 100, new FileOutputStream(imageFile+".png"));
				fileName = j.getDateString()+j.getTitle()+".png";
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Adapter: Error converting bitmap");
			}
		
		ContentValues initialValues = createContentValues(Long.toString(j.getDate().getTimeInMillis()), j.getNotes(), j.getMood(), j.getTitle(), 0, 0, j.isDrVisit()? 1 : 0, j.isUltrasound()? 1 : 0, j.getImportant()? 1 : 0, 0, 0, dir+"/"+fileName);
		System.out.println("Converting: " + j.getTitle());
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	
	
	public boolean updateJournalEntry(JournalEntry j, long rowId){
	
		ContentValues updateValues = createContentValues(Long.toString(j.getDate().getTimeInMillis()), j.getNotes(), j.getMood(), j.getTitle(), j.getWeight(), j.getWaist(), j.isDrVisit()? 1 : 0, j.isUltrasound()? 1 : 0, j.getImportant()? 1 : 0, j.isWeighIn()? 1 : 0, j.isTimeLapse()? 1 : 0,j.getImageFile());
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) >0;
	}
	
	
	public boolean deleteJournalEntry(long rowId){
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null)>0;
	}
	
	
	/**
	 * Return a Cursor over the list of all Information Sets in the database
	 * @return Cursor over all Information Sets
	 */
	public Cursor fetchAllJournalEntries(){
		return database.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DATE, KEY_NOTES, KEY_MOOD, KEY_TITLE, KEY_WEIGHT, KEY_WAIST, KEY_IS_DOCTOR, KEY_IS_ULTRASOUND, KEY_IS_IMPORTANT, KEY_IS_WEIGH_IN, KEY_IS_TIMELAPSE, KEY_IMAGE}, null, null, null,null, KEY_DATE + " DESC", null);
	}
	
	public JournalEntry fetchJournalEntry(long rowId){
		Cursor c = database.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DATE, KEY_NOTES, KEY_MOOD, KEY_TITLE, KEY_WEIGHT, KEY_WAIST, KEY_IS_DOCTOR, KEY_IS_ULTRASOUND, KEY_IS_IMPORTANT, KEY_IS_WEIGH_IN, KEY_IS_TIMELAPSE, KEY_IMAGE}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		
		JournalEntry j = new JournalEntry(c);
		
		return j;
	}
	

	
	private ContentValues createContentValues(String date, String notes, String mood, String title, int weight, int waist, int isdoctor, int isultrasound, int isimportant, int isweighin, int istimelapse, String image){
		ContentValues values = new ContentValues();
		
		values.put(KEY_DATE, date);
		values.put(KEY_NOTES, notes);
		values.put(KEY_MOOD, mood);
		values.put(KEY_TITLE, title);
		values.put(KEY_WEIGHT, weight);
		values.put(KEY_WAIST, waist);
		values.put(KEY_IS_DOCTOR, isdoctor);
		values.put(KEY_IS_ULTRASOUND, isultrasound);
		values.put(KEY_IS_IMPORTANT, isimportant);
		values.put(KEY_IS_WEIGH_IN, isweighin);
		values.put(KEY_IS_TIMELAPSE, istimelapse);
		values.put(KEY_IMAGE, image);
		
		
		
		
		return values;
	}

}
