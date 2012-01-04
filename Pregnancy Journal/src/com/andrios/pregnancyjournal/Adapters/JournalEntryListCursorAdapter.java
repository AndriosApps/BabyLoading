package com.andrios.pregnancyjournal.Adapters;

import java.util.Calendar;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Database.JournalDBAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class JournalEntryListCursorAdapter extends SimpleCursorAdapter
		implements Filterable {

	private Context context;
	private int layout;

	public JournalEntryListCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		this.layout = layout;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Cursor c = getCursor();
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(layout, parent, false);
		Calendar date = Calendar.getInstance();
		
		date.setTimeInMillis(Long.parseLong(c.getString(1)));
		
		
		/**
		 * Next set the name of the entry.
		 */
		TextView titleTXT = (TextView) v.findViewById(R.id.journal_entry_list_item_titleLBL);
       // LinearLayout background = (LinearLayout) v.findViewById(R.id.baby_name_list_item_backgroundLL);
        LinearLayout dateLL = (LinearLayout) v.findViewById(R.id.journal_entry_list_item_dateLL);
        TextView dateLBL = (TextView) v.findViewById(R.id.journal_entry_list_item_dateLBL);
        TextView yearLBL = (TextView) v.findViewById(R.id.journal_entry_list_item_yearLBL);
        ImageView importantIMG = (ImageView) v.findViewById(R.id.journal_entry_list_item_importantIMG);
        
        
        titleTXT.setText(c.getString(4));                            
      
        dateLBL.setText(Integer.toString(date.get(Calendar.DAY_OF_MONTH)));
    	yearLBL.setText(Integer.toString(date.get(Calendar.YEAR)));
    	
        if(date.get(Calendar.MONTH) == 0){
        	dateLL.setBackgroundResource(R.drawable.cal_0);
        }else if(date.get(Calendar.MONTH) == 1){
        	dateLL.setBackgroundResource(R.drawable.cal_1);
        }else if(date.get(Calendar.MONTH) == 2){
        	dateLL.setBackgroundResource(R.drawable.cal_2);
        }else if(date.get(Calendar.MONTH) == 3){
        	dateLL.setBackgroundResource(R.drawable.cal_3);
        }else if(date.get(Calendar.MONTH) == 4){
        	dateLL.setBackgroundResource(R.drawable.cal_4);
        }else if(date.get(Calendar.MONTH) == 5){
        	dateLL.setBackgroundResource(R.drawable.cal_5);
        }else if(date.get(Calendar.MONTH) == 6){
        	dateLL.setBackgroundResource(R.drawable.cal_6);
        }else if(date.get(Calendar.MONTH) == 7){
        	dateLL.setBackgroundResource(R.drawable.cal_7);
        }else if(date.get(Calendar.MONTH) == 8){
        	dateLL.setBackgroundResource(R.drawable.cal_8);
        }else if(date.get(Calendar.MONTH) == 9){
        	dateLL.setBackgroundResource(R.drawable.cal_9);
        }else if(date.get(Calendar.MONTH) == 10){
        	dateLL.setBackgroundResource(R.drawable.cal_10);
        }else if(date.get(Calendar.MONTH) == 11){
        	dateLL.setBackgroundResource(R.drawable.cal_11);
        }
        
        System.out.println(c.getString(4) + " is important: " + c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_IMPORTANT)));
        if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_IMPORTANT)) == 1){
        	importantIMG.setImageResource(R.drawable.exclamation);
        }else if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_DOCTOR)) == 1){
        	importantIMG.setImageResource(R.drawable.doctor);
        }else if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_ULTRASOUND)) == 1){
        	importantIMG.setImageResource(R.drawable.ultrasound);
        }else if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_WEIGH_IN)) == 1){
        	importantIMG.setImageResource(R.drawable.nothing);
        }else if(c.getInt(11) == 1){
        	importantIMG.setImageResource(R.drawable.nothing);
        }else{
        	importantIMG.setImageResource(R.drawable.nothing);
        }
        //background.setBackgroundResource(R.drawable.nothing);

		return v;
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {

		Calendar date = Calendar.getInstance();
		
		date.setTimeInMillis(Long.parseLong(c.getString(1)));
		
		/**
		 * Next set the name of the entry.
		 */
		TextView titleTXT = (TextView) v.findViewById(R.id.journal_entry_list_item_titleLBL);
        //LinearLayout background = (LinearLayout) v.findViewById(R.id.baby_name_list_item_backgroundLL);
        LinearLayout dateLL = (LinearLayout) v.findViewById(R.id.journal_entry_list_item_dateLL);
        TextView dateLBL = (TextView) v.findViewById(R.id.journal_entry_list_item_dateLBL);
        TextView yearLBL = (TextView) v.findViewById(R.id.journal_entry_list_item_yearLBL);
        ImageView importantIMG = (ImageView) v.findViewById(R.id.journal_entry_list_item_importantIMG);
        
        
        titleTXT.setText(c.getString(4));                            
      
        dateLBL.setText(Integer.toString(date.get(Calendar.DAY_OF_MONTH)));
    	yearLBL.setText(Integer.toString(date.get(Calendar.YEAR)));
    	
        if(date.get(Calendar.MONTH) == 0){
        	dateLL.setBackgroundResource(R.drawable.cal_0);
        }else if(date.get(Calendar.MONTH) == 1){
        	dateLL.setBackgroundResource(R.drawable.cal_1);
        }else if(date.get(Calendar.MONTH) == 2){
        	dateLL.setBackgroundResource(R.drawable.cal_2);
        }else if(date.get(Calendar.MONTH) == 3){
        	dateLL.setBackgroundResource(R.drawable.cal_3);
        }else if(date.get(Calendar.MONTH) == 4){
        	dateLL.setBackgroundResource(R.drawable.cal_4);
        }else if(date.get(Calendar.MONTH) == 5){
        	dateLL.setBackgroundResource(R.drawable.cal_5);
        }else if(date.get(Calendar.MONTH) == 6){
        	dateLL.setBackgroundResource(R.drawable.cal_6);
        }else if(date.get(Calendar.MONTH) == 7){
        	dateLL.setBackgroundResource(R.drawable.cal_7);
        }else if(date.get(Calendar.MONTH) == 8){
        	dateLL.setBackgroundResource(R.drawable.cal_8);
        }else if(date.get(Calendar.MONTH) == 9){
        	dateLL.setBackgroundResource(R.drawable.cal_9);
        }else if(date.get(Calendar.MONTH) == 10){
        	dateLL.setBackgroundResource(R.drawable.cal_10);
        }else if(date.get(Calendar.MONTH) == 11){
        	dateLL.setBackgroundResource(R.drawable.cal_11);
        }
    
        System.out.println(c.getString(4) + " is BINDING important: " + c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_IMPORTANT)));
        if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_IMPORTANT)) == 1){
        	importantIMG.setImageResource(R.drawable.exclamation);
        }else if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_DOCTOR)) == 1){
        	importantIMG.setImageResource(R.drawable.doctor);
        }else if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_ULTRASOUND)) == 1){
        	importantIMG.setImageResource(R.drawable.ultrasound);
        }else if(c.getInt(c.getColumnIndex(JournalDBAdapter.KEY_IS_WEIGH_IN)) == 1){
        	importantIMG.setImageResource(R.drawable.nothing);
        }else if(c.getInt(11) == 1){
        	importantIMG.setImageResource(R.drawable.nothing);
        }else{
        	importantIMG.setImageResource(R.drawable.nothing);
        }
        //background.setBackgroundResource(R.drawable.nothing);
	}

	

}
