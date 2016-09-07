package com.tdActivity.android.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 对SharedPreference的操作
 * 
 * @author leo.huang
 *
 */
public class PreferenceUtils {
	/**
	 * 设置是否开启数据备份
	 * 
	 * @param context
	 * @param open
	 */
	public static void synchSetting(Context context, boolean open) {
		SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SYNCH_PREF, Context.MODE_PRIVATE);
		mSharedPreferences.edit().putBoolean(Setting.SYNCH_SETTING, open).commit();

	}

	/**
	 * 获取是否开启备份数据
	 * @param context
	 * @return
	 */
	public static boolean getSynchSetting(Context context) {
		SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SYNCH_PREF, Context.MODE_PRIVATE);
		return mSharedPreferences.getBoolean(Setting.SYNCH_SETTING, false);
	}
	
	/**
	 * 获取上次设置备份的时间
	 * @param context
	 * @return
	 */
	public static long getLocalSynchTime(Context context){
		SharedPreferences mSharedPreferences=context.getSharedPreferences(Setting.SYNCH_SET_TIME_PREF, Context.MODE_PRIVATE);
		return mSharedPreferences.getLong(Setting.SYNCH_SET_TIME, 0); 
	}
	
	/**
	 * 设置备份时间
	 * @param context
	 * @param time
	 */
	public static void setLocalSynchTime(Context context,long time){
		SharedPreferences mSharedPreferences=context.getSharedPreferences(Setting.SYNCH_SET_TIME_PREF, Context.MODE_PRIVATE);
		mSharedPreferences.edit().putLong(Setting.SYNCH_SET_TIME, time).commit();
	}
	
	/**
	 * 是否是第一次启动设置备份
	 * @param context
	 * @return true 表示是第一次
	 */
	public static boolean isFirstTime(Context context){
		SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SYNCH_PREF, Context.MODE_PRIVATE);
		return mSharedPreferences.getBoolean(Setting.FIRST_TIME, true);
	}
	
	public static void setIsFirstTime(Context context){
		SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SYNCH_PREF, Context.MODE_PRIVATE);
		mSharedPreferences.edit().putBoolean(Setting.FIRST_TIME, false).commit();
	}
}
