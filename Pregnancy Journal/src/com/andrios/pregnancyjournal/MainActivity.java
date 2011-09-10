package com.andrios.pregnancyjournal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button namesBTN, journalBTN;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        setConnections();
        setOnClickListeners();
    }

	private void setConnections() {
		namesBTN = (Button) findViewById(R.id.mainActivityNamesBTN);
		
	}

	private void setOnClickListeners() {
		namesBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NamesListActivity.class);
				startActivity(intent);
			}

		});
		
	}
}