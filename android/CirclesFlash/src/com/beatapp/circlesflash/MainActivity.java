package com.beatapp.circlesflash;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	final String CAT_TAG = "STOPPED HERE";
	final Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		final int[] imageArray = { R.drawable.filledcircle,
				R.drawable.opencircle };

		Runnable runnable = new Runnable() {
			int i = 0;
			int last_i = 1;
			ArrayList<ImageView> circles= new ArrayList<ImageView>();
			public void run() {
				if (circles.isEmpty()){
					circles.add((ImageView) findViewById(R.id.CircleView1));
					circles.add((ImageView) findViewById(R.id.CircleView2));
					circles.add((ImageView) findViewById(R.id.CircleView3));
				}
				

				ImageView iv1 = (ImageView) findViewById(R.id.CircleView1);
				iv1.setImageResource(imageArray[i]);

				
				ImageView iv2 = (ImageView) findViewById(R.id.CircleView2);
				
				iv2.setImageResource(imageArray[last_i]);
				ImageView iv3 = (ImageView) findViewById(R.id.CircleView3);
				iv3.setImageResource(imageArray[last_i]);
				last_i = i;
				i++;
				if (i > imageArray.length - 1) {
			//		circles.get(j).setImageResource(imageArray[i]);

					i = 0;
				}
				handler.postDelayed(this, 500); // for interval...
				Log.v(CAT_TAG, "Second delay");
				
			}
		};

		handler.postDelayed(runnable, 2000); // for initial delay..
		Log.v(CAT_TAG, "Initial delay");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
