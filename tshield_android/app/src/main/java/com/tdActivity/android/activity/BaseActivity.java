 package com.tdActivity.android.activity;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.receiver.ScreenBroadCastReceiver;
import com.tdActivity.android.util.AppUtil;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.RegisterBroadcast;
import com.tdActivity.android.util.Setting;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

public class BaseActivity extends FragmentActivity {
	private SharedPreferences mSharedPreferences ;
	private boolean isActive=true;
	private ScreenBroadCastReceiver mReceiver;
	protected MyApplication myApplication;
	protected ExecutorService  fixedThreadPool;
	private static final String TAG="BaseActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		myApplication=(MyApplication) getApplication();
		fixedThreadPool=myApplication.fixedThreadPool;
		mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
		mReceiver=new ScreenBroadCastReceiver((MyApplication)getApplication());
		RegisterBroadcast.activityRegisterBroadcast(mReceiver, this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	@Override
	protected void onStop() {
		super.onStop();

		if (!isAppOnForeground()) {
			// app 进入后台
			long currentTime = System.currentTimeMillis() / 1000;
			mSharedPreferences.edit().putLong("backTime", currentTime).commit();
			isActive = false;
			// 全局变量isActive = false 记录当前已经进入后台
			LocalSynchUtils.threadCopyToFile(myApplication);
		}
	
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isActive) {
			isActive=true;
			long thisTime = System.currentTimeMillis() / 1000;
			long oldTime = mSharedPreferences.getLong("backTime", -1);
			boolean need=((MyApplication)getApplication()).isNeedGesture();
			if(need){
				if (oldTime > -1) {// 有记录的时间
					if (thisTime - oldTime > Setting.BACKGROUND_TIME) {// 超过一分钟时间
						Intent mIntent = new Intent(this, LoginActivity.class);
						mIntent.putExtra("lock", true);
						startActivity(mIntent);
					}
				}
			}
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public  boolean isAppOnForeground() {

		ActivityManager activityManager = (ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}
}
