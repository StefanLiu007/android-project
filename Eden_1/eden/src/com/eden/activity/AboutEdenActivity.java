package com.eden.activity;

import com.eden.R;
import com.eden.util.MyApplication;

import android.app.Activity;
import android.os.Bundle;

public class AboutEdenActivity extends Activity {
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_abouteden);
    	MyApplication.AddActivity(this);
      }
}
