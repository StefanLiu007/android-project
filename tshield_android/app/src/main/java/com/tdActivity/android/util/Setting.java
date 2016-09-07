package com.tdActivity.android.util;

public interface Setting {
	/**
	 * 锁屏时间
	 */
	int LOCK_SCREEN_TIME=60;
	/**
	 * 后台时间
	 */
	int BACKGROUND_TIME=60;
	/**
	 * 数据库版本
	 */
	int DB_VERSION=1;
	/**
	 * 
	 */
	String GESTURE_SP="gesture_sp";
	String GESTURE_HAS_DATA="gesture_has_data";
	String GESTURE_NEED="gesture_need";
	
	String FROM_ACTIVITY="from_activity";
	/**
	 * comfirmActivity
	 */
	int COMFIRM=1;
	/**
	 * LoginActivity
	 */
	int LOGIN=2;
	
	
	String MAIN_ACTIVITY="main_activity";
	
	int  MAIN_FRAGMENT=0;
	int SETTING_GESTURE=2;
	
	/**
	 * sharedPerference
	 */
	String SYNCH_PREF="synch_pref";
	String SYNCH_SETTING="synch";
	String FIRST_TIME="first_time";

	String AES_256_KEY="trustasiatshield";
	
	String SYNCH_SET_TIME_PREF="synch_set_time_pref";
	String SYNCH_SET_TIME="Synch_set_time";
	
	long INTERVAL_TIME=60*60*1000;
}
