package com.andrios.pregnancyjournal.Adapters;

import java.util.ArrayList;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.BabyName;
import com.andrios.pregnancyjournal.R.drawable;
import com.andrios.pregnancyjournal.R.id;
import com.andrios.pregnancyjournal.R.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
                    ImageView genderIMG = (ImageView) v.findViewById(R.id.baby_name_list_item_genderIMG);
                    ImageView mom1 = (ImageView) v.findViewById(R.id.listmom1);
                    ImageView mom2 = (ImageView) v.findViewById(R.id.listmom2);
                    ImageView mom3 = (ImageView) v.findViewById(R.id.listmom3);
                    ImageView mom4 = (ImageView) v.findViewById(R.id.listmom4);
                    ImageView mom5 = (ImageView) v.findViewById(R.id.listmom5);
            		
                    ImageView dad1 = (ImageView) v.findViewById(R.id.listdad1);
                    ImageView dad2 = (ImageView) v.findViewById(R.id.listdad2);
                    ImageView dad3 = (ImageView) v.findViewById(R.id.listdad3);
                    ImageView dad4 = (ImageView) v.findViewById(R.id.listdad4);
                    ImageView dad5 = (ImageView) v.findViewById(R.id.listdad5);
                    
                    if (nameTXT != null) {
                          nameTXT.setText(b.getName());                            
                    }
                   
                    if(background != null){
                    	Resources res = parent.getResources();
                    	Drawable d;
                    	if(b.getGender()){
                    		genderIMG.setImageResource(R.drawable.boy);
                    		d = res.getDrawable(R.drawable.background_blanket_blue);
                    		
                    	}else{
                    		genderIMG.setImageResource(R.drawable.girl);
                    		d = res.getDrawable(R.drawable.background_blanket_pink);
                    	}
                    	background.setBackgroundDrawable(d);
                    	                    	
                    }
                    System.out.println(b.getName() + "Mom: " + b.getMomRating() + " Dad: " +b.getDadRating());
                    if(b.getMomRating() >= 1){
            			mom1.setImageResource(R.drawable.star_lit);
            		}else{
            			mom1.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getMomRating() >= 2){
            			mom2.setImageResource(R.drawable.star_lit);
            		}else{
            			mom2.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getMomRating() >= 3){
            			mom3.setImageResource(R.drawable.star_lit);
            		}else{
            			mom3.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getMomRating() >= 4){
            			mom4.setImageResource(R.drawable.star_lit);
            		}else{
            			mom4.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getMomRating() >= 5){
            			mom5.setImageResource(R.drawable.star_lit);
            		}else{
            			mom5.setImageResource(R.drawable.star_outline);
            		}
            		
            		
            		if(b.getDadRating() >= 1){
            			dad1.setImageResource(R.drawable.star_lit);
            		}else{
            			dad1.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getDadRating() >= 2){
            			dad2.setImageResource(R.drawable.star_lit);
            		}else{
            			dad2.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getDadRating() >= 3){
            			dad3.setImageResource(R.drawable.star_lit);
            		}else{
            			dad3.setImageResource(R.drawable.star_outline);
            		}
            		
            		if(b.getDadRating() >= 4){
            			dad4.setImageResource(R.drawable.star_lit);
            		}else{
            			dad4.setImageResource(R.drawable.star_outline);
            		}
            		if(b.getDadRating() >= 5){
            			dad5.setImageResource(R.drawable.star_lit);
            		}else{
            			dad5.setImageResource(R.drawable.star_outline);
            		}
                    
            }
            return v;
    }
    
	

}
