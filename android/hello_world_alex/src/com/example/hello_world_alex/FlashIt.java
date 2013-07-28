package com.example.hello_world_alex;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FlashIt extends Activity {
	public final String LOG_TAG = "HELLO_TESTER"; 
	
	private int flash_delay;
	public static final int MILLISECOND_AT_BPM = 60000;
	public static final int DEFAULT_BPM = 60;
	public static final int ON_TIME =  100;
	private TextView flashView;
	private TextView audioView;
	private TextView startView;
	private TextView endView;
	private Handler mHandler;
	
	private static final int RECORDER_SAMPLERATE = 44100;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private AudioRecord recorder = null;
	private Thread recordingThread = null;
	private boolean isRecording = false;

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
		flashView = (TextView) findViewById(R.id.flash_text);
		audioView = (TextView) findViewById(R.id.audio_feedback);
		startView= (TextView) findViewById(R.id.start_time);
		endView= (TextView) findViewById(R.id.end_time);
		
		
		mHandler = new Handler();
		mHandler.removeCallbacks(flashStart);
		mHandler.postDelayed(flashStart, flash_delay);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//for audiorecord apparently returns error if these can't be met
		int bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
	            RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
		Log.d(LOG_TAG,"buffer size is "+bufferSize);
	}

	private Runnable flashStart = new Runnable() {
		public void run() {
			Log.d(LOG_TAG,"in flashStart");
			flashView.setText(R.string.flash_string_on);
			mHandler.removeCallbacks(flashStart);
			mHandler.postDelayed(flashStop, ON_TIME);
			startTime = System.currentTimeMillis();
			int howLoud = recordSome();
			endTime = System.currentTimeMillis();
			startView.setText("start: "+startTime);
			endView.setText("end  : "+endTime);
			Log.d(LOG_TAG,"duration:"+(endTime-startTime)+" start: "+startTime+" end: "+endTime);
			audioView.setText("Loudness: "+howLoud);
		}
	};

	public void startThread(View view){
    	startActivity(new Intent(this, ThreadTester.class));
		
	}
	
	
	private Runnable flashStop = new Runnable() {
		public void run() {
			flashView.setText(R.string.flash_string_off);
			mHandler.removeCallbacks(flashStart);
			mHandler.postDelayed(flashStart, flash_delay-ON_TIME);
		}
	};
	
	//AUDIO CODE
	int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
	int BytesPerElement = 2; // 2 bytes in 16bit format
	
	long startTime;
	long endTime;
	private int recordSome(){
		short sData[] = new short[BufferElements2Rec];
		Log.d(LOG_TAG,"in recordSome");
		Log.d(LOG_TAG,"try buffer size: "+(BufferElements2Rec * BytesPerElement));
		try{
		recorder = findAudioRecord();
				
				//new AudioRecord(MediaRecorder.AudioSource.MIC,RECORDER_SAMPLERATE, RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		Log.d(LOG_TAG,"AudioRecorder after intialize");
		recorder.startRecording();
		recorder.read(sData, 0, BufferElements2Rec);
		recorder.stop();
		
		long sum = 0;
		for(int i = 0; i<sData.length; i++){
			sum +=Math.abs(sData[i]);
		}
		return (int)(sum/sData.length);
	}
	
	//Find usable settings
	
	private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };
	public AudioRecord findAudioRecord() {
	    for (int rate : mSampleRates) {
	        for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
	            for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
	                try {
	                    Log.d(LOG_TAG, "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
	                            + channelConfig);
	                    int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

	                    if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
	                        // check if we can instantiate and have a success
	                        AudioRecord recorder = new AudioRecord(AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

	                        if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
	                            return recorder;
	                    }
	                } catch (Exception e) {
	                    Log.e(LOG_TAG, rate + "Exception, keep trying.",e);
	                }
	            }
	        }
	    }
	    return null;
	}
	
	
	//Stop always run
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    mHandler.removeCallbacks(flashStart);
	    mHandler.removeCallbacks(flashStop);
	}
	
	
	//ANDRIOD DEFAULT CODE
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    mHandler = new Handler();
		mHandler.removeCallbacks(flashStart);
		mHandler.postDelayed(flashStart, flash_delay);
	   
	}

	
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
