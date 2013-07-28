package com.example.hello_world_alex;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FindABeatThread extends Thread{
	public static final String CAT_TAG = "Find a Beat Thread";
	private Handler parent_handle;
	public FindABeatThread() {
		// TODO Auto-generated constructor stub
	}
	public FindABeatThread(String threadName, Handler parent_handle){
		super(threadName);
		this.parent_handle=parent_handle;
		Log.v(CAT_TAG,"initialize the thread");
	}
	public void run() {
		Log.v(CAT_TAG,"running the thread");
		long endTime = System.currentTimeMillis() + 6 * 1000;
		//while()
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (Exception e) {
				}
			}
		}
		Log.v(CAT_TAG,"send the message");
		Message msg = parent_handle.obtainMessage((int)(Math.random()*20));
		parent_handle.sendMessage(msg);
	}
}
