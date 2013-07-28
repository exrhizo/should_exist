package com.example.hello_world_alex;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ThreadTester extends Activity {

	public static final String CAT_TAG = "Thread Tester";

	AtomicBoolean running;
	FindABeatThread fab_thread;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			TextView myTextView = (TextView) findViewById(R.id.myTextView);
			myTextView.setText("Result: " + msg.what);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_tester);
		// Show the Up button in the action bar.
		setupActionBar();

		// Thread stuff
		running = new AtomicBoolean(false);

	}

	public void startThread(View view) {
		if (running.get() == true) {
			Log.d(CAT_TAG, "Thread is currently running");

		} else {
			fab_thread = new FindABeatThread("Find a Beat", handler);
			fab_thread.start();
		}
		/*
		 * Runnable runnable = new Runnable() { public void run() {
		 * 
		 * long endTime = System.currentTimeMillis() + 20 * 1000;
		 * 
		 * while (System.currentTimeMillis() < endTime) { synchronized (this) {
		 * try { wait(endTime - System.currentTimeMillis()); } catch (Exception
		 * e) { } } } handler.sendEmptyMessage(0); } }; Thread mythread = new
		 * Thread(runnable); // mythread. mythread.start();
		 */

	}

	Runnable pullAudioStream = new Runnable() {
		public void run() {

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
		getMenuInflater().inflate(R.menu.thread_tester, menu);
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
