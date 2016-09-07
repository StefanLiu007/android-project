package com.tdActivity.android.receiver;

import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.LoginActivity;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.Setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ScreenBroadCastReceiver extends BroadcastReceiver {

	private static final String TAG="ScreenBroadCastReceiver";
	
	private SharedPreferences mSharedPreferences;
	private MyApplication myApplication;
	
	public ScreenBroadCastReceiver(MyApplication myApplication) {
		this.myApplication=myApplication;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {//亮屏
			// 读取记录的事件，看是否超过一分钟
			long thisTime = System.currentTimeMillis() / 1000;
			long oldTime = mSharedPreferences.getLong("time", -1);
			// 不做数据库操作，使用缓存
			boolean need=((MyApplication)context.getApplicationContext()).isNeedGesture();
			if (need) {
				if (oldTime > -1) {// 有记录的时间
					if (thisTime - oldTime > Setting.LOCK_SCREEN_TIME) {// 超过�?分钟时间
						Intent mIntent = new Intent(context, LoginActivity.class);
						mIntent.putExtra("lock", true);
						context.startActivity(mIntent);
					}
				}
			}
		} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {// 锁屏
			Log.i(TAG, "Intent.ACTION_SCREEN_OFF");
			//进行备份
			LocalSynchUtils.threadCopyToFile(myApplication);
			
			// 进行时间记录
			long currentTime = System.currentTimeMillis() / 1000;
			mSharedPreferences.edit().putLong("time", currentTime).commit();
		} else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {// 解锁

		}

	}

}
