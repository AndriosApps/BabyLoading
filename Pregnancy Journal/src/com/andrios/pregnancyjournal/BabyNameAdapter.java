package com.andrios.pregnancyjournal;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BabyNameAdapter extends ArrayAdapter<BabyName>{

    private ArrayList<BabyName> items;
	
   
	public BabyNameAdapter(Context context, int textViewResourceId,
			ArrayList<BabyName> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		
		// TODO Auto-generated constructor stub
	}
	
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.baby_name_list_item, null);
            }
            BabyName b;
            b = items.get(position);
      
            if (b != null) {
                    TextView nameTXT = (TextView) v.findViewById(R.id.baby_name_list_item_nameLBL);
                    LinearLayout background = (LinearLayout) v.findViewById(R.id.baby_name_list_item_backgroundLL);
                    ImageView genderIMG = (ImageView) v.findViewById(R.id.baby_name_list_item_displayIMG);
                    if (nameTXT != null) {
                          nameTXT.setText(b.getName());                            
                    }
                   
                    if(genderIMG != null){
                    	if(b.getGender()){
                    		genderIMG.setImageResource(R.drawable.boy);
                    	}else{
                    		genderIMG.setImageResource(R.drawable.girl);
                    	}
                    	
                    }
                    
            }
            return v;
    }

}
