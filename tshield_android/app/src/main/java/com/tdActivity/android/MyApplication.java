package com.tdActivity.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bouncycastle.util.test.FixedSecureRandom;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.test.PerformanceTestCase;
import android.util.Log;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.AES;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.AppUtil;
import com.tdActivity.android.util.Constant;
import com.tdActivity.android.util.PreferenceUtils;
import com.tdActivity.android.util.Setting;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;

public class MyApplication extends Application {
	private final static String TAG = "MyApplication";
	private static VersionOneDao dao;
	private SharedPreferences uuidPreferences;
	private String uuid;
	public List<UserInfo> userInfos;
	public List<UserInfo> oldUserInfos;
	private int count = 0;
	private boolean needGesture;
	private boolean hasData;
	public boolean synchSetting;
	private String appID = "2882303761517441767";
	private String appKey = "5821744140767";
	public boolean isChanged = false;
	public static String key;
	public static AES aes;
	public ExecutorService fixedThreadPool;
	public String password;
	@Override
	public void onCreate() {
		super.onCreate();
		fixedThreadPool = Executors.newFixedThreadPool(5);
		TestinAgent.init(this, "b33e985339fcbde79fbc49400f33856b", null);
		//禁止友盟默认的页面统计
		MobclickAgent.openActivityDurationTrack(false);
		aes = new AES(Setting.AES_256_KEY.toCharArray());
		// 获取UUID
		if (userInfos == null) {
			userInfos = new ArrayList<UserInfo>();
			oldUserInfos = new ArrayList<UserInfo>();
		}
		uuidPreferences = getSharedPreferences("uuid", 0);
		uuid = uuidPreferences.getString("uuid", "");
		if (uuid == null || uuid.equals("")) {
			uuidPreferences.edit().putString("uuid", AppUtil.getuuid(getApplicationContext())).commit();
		} else {
			Constant.UUID = uuid;
		}

		synchSetting = PreferenceUtils.getSynchSetting(getApplicationContext());

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static void VersionOneDaoInstance(Context context) {
		if (dao == null) {
			dao = new VersionOneDao(context);
			if (!dao.versionHasData()) {
				dao.versionInsert(Setting.DB_VERSION);
			}
		}
	}

	public static VersionOneDao getDbHelper() {
		return dao;
	}

	public boolean isNeedGesture() {
		return needGesture;
	}

	public void setNeedGesture(boolean needGesture) {
		this.needGesture = needGesture;
	}

	public boolean isHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}
	
}
