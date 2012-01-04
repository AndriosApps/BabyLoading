package com.andrios.pregnancyjournal.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = DatabaseConstants.DATABASE_NAME;
	private static final int DATABASE_VERSION = DatabaseConstants.DATABASE_VERSION;
	
	//Database Creation SQL statement
	private static final String DATABASE_CREATE = "create table journal (" +
			"_id integer primary key autoincrement, " +
			"date text not null, " +
			"notes text, " +
			"mood text, " +
			"title text," +
			"weight integer," +
			"waist integer," +
			"isdoctor integer not null," +
			"isultrasound integer not null," +
			"isimportant integer not null," +
			"isweighin integer not null," +
			"istimelapse integer not null," +
			"image text"+
			");";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//Method is called during the creation of the database
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	//Method is called during an upgrade of the database, eg. if you increase the 
	//database version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS");
		onCreate(db);
		
	}
	



}
