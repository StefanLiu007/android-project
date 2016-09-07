package com.tdActivity.android.activity;

import java.util.concurrent.ExecutorService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.util.ListUtils;
import com.umeng.analytics.MobclickAgent;

public class FlashActivity extends Activity {
	private String uuid;
	private VersionOneDao dao;
	private MyApplication myApplication;
	private long start, end;
	private ExecutorService fixedThreadPool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flash_main);
		start = System.currentTimeMillis();
		myApplication = (MyApplication) getApplication();
		fixedThreadPool = myApplication.fixedThreadPool;
		MyApplication.VersionOneDaoInstance(getApplicationContext());
		dao = MyApplication.getDbHelper();
		welcomeUI();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("FlashActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("FlashActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
	    MobclickAgent.onPause(this);
	}

	private void welcomeUI() {

		// 开启线程
		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				myApplication.setHasData(dao.gestureHasData());
				myApplication.setNeedGesture(dao.gestureNeedGet() == VersionOneDao.NEED_GESTURE);
				myApplication.password = dao.gestureGet();
				myApplication.userInfos = dao.userInfosFrom();// 在splash界面中
				myApplication.oldUserInfos = ListUtils
						.copyToAnother(myApplication.userInfos);
				Message message = new Message();
				end = System.currentTimeMillis();
				if (end - start > 2000) {
					welHandler.sendMessage(message);
				} else {
					try {
						Thread.sleep(2000 - (end - start));
						welHandler.sendMessage(message);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	Handler welHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			welcomeFunction();
		}

	};

	public void welcomeFunction() {
		if (myApplication.isHasData() && myApplication.isNeedGesture()) {
			startActivity(new Intent(getBaseContext(), LoginActivity.class));
		} else {
			startActivity(new Intent(getBaseContext(), MainActivity.class));
		}
		finish();
	}

}
