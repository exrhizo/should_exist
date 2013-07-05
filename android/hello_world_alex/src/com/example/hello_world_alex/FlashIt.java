package com.example.hello_world_alex;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class FlashIt extends Activity {
	private int flash_delay;
	public static final int MILLISECOND_AT_BPM = 60000;
	public static final int DEFAULT_BPM = 60;
	public static final int ON_TIME =  100;
	private TextView flashString;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_it);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		try {
			flash_delay = MILLISECOND_AT_BPM / Integer.parseInt(message);
		} catch (NumberFormatException nfe) {
			flash_delay = MILLISECOND_AT_BPM / DEFAULT_BPM;
		}
		if (flash_delay-2*ON_TIME<0){
			flash_delay = ON_TIME;
		}
		flashString = (TextView) findViewById(R.id.flash_text);
		mHandler = new Handler();
		mHandler.removeCallbacks(flashStart);
		mHandler.postDelayed(flashStart, flash_delay);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	private Runnable flashStart = new Runnable() {
		public void run() {
			flashString.setText(R.string.flash_string_on);
			mHandler.removeCallbacks(flashStart);
			mHandler.postDelayed(flashStop, ON_TIME);
		}
	};

	private Runnable flashStop = new Runnable() {
		public void run() {
			flashString.setText(R.string.flash_string_off);
			mHandler.removeCallbacks(flashStart);
			mHandler.postDelayed(flashStart, flash_delay-ON_TIME);
		}
	};
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		getMenuInflater().inflate(R.menu.flash_it, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
