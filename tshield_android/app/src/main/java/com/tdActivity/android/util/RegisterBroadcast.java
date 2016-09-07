package com.tdActivity.android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class RegisterBroadcast {
	public static void activityRegisterBroadcast(BroadcastReceiver broadcastReceiver,Context context){
		final IntentFilter filter=new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		context.registerReceiver(broadcastReceiver, filter);
	}
	
}
