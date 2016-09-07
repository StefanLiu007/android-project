package com.example.leohuang.password_manager.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 注册广播
 */
public class RegisterBroadcast {
	/**
	 * 注册锁屏广播
	 * @param broadcastReceiver
	 * @param context
	 */
	public static void activityRegisterBroadcast(BroadcastReceiver broadcastReceiver,Context context){
		final IntentFilter filter=new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		context.registerReceiver(broadcastReceiver, filter);
	}

	/**
	 * 注册闲置广播
	 * @param mReceiver
	 * @param context
	 */
	public static void activityRegisterFreeBroadCase(BroadcastReceiver mReceiver,Context context){
		final IntentFilter filter=new IntentFilter();
		filter.addAction(Setting.MINTENT);
		context.registerReceiver(mReceiver,filter);
	}
	
}
