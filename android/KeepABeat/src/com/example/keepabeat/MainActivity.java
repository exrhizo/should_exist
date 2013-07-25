package com.example.keepabeat;

import com.example.keepabeat.GraphsActivity;

import com.example.keepabeat.R;
import com.example.keepabeat.R.layout;
import com.example.keepabeat.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Resources res = getResources();

		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */
	


        
    
	public void sendMessage(View view) {
		int time_array[] = {2,4,6};
		//Intent pass_array = new Intent(this, GraphsActivity.class);
			//pass_array.putExtra("numbers",time_array);
			 //startActivity(pass_array);
			
			 
			
				
	    // Do something in response to button
		 
	}
	
}
