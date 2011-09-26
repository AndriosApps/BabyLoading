package com.andrios.pregnancyjournal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button namesBTN, journalBTN, settingsBTN, emailBTN, profileBTN;
	
	
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
		journalBTN = (Button) findViewById(R.id.mainActivityJournalBTN);
		settingsBTN = (Button) findViewById(R.id.mainActivitySettingsBTN);
		emailBTN = (Button) findViewById(R.id.mainActivityEmailBTN);

		profileBTN = (Button) findViewById(R.id.mainActivityProfileBTN);
	}

	private void setOnClickListeners() {
		profileBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileActivity.class);
				
				startActivity(intent);
				
			}
		});
		
		emailBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
			    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			     
			    emailIntent .setType("plain/text");
			     
			    //emailIntent .putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"miniMurph2012@gmail.com"});
			     
			    emailIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, "Baby Email");
			     
			    //emailIntent .putExtra(android.content.Intent.EXTRA_TEXT, myBodyText);
			     
			    startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			}

		});
		
		settingsBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SettingsActivity.class);
				
				startActivity(intent);
			}

		});
		

		
		namesBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NamesListActivity.class);
				
				startActivity(intent);
			}

		});
		
		journalBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), JournalListActivity.class);
				
				startActivity(intent);
			}

		});
		
	}
}