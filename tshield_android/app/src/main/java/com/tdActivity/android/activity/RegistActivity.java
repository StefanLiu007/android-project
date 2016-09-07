package com.tdActivity.android.activity;

import com.tdActivity.android.activity.R;

import java.util.concurrent.ExecutorService;

import com.tdActivity.android.AppManager;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.util.Setting;
import com.tdActivity.android.view.CustomProgressDialog;
import com.tdActivity.android.view.GestureContentView;
import com.tdActivity.android.view.GestureDrawline.GestureCallBack;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;

import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

//手势密码
public class RegistActivity extends Activity {
	private TextView txt_cans_result;
	private MyApplication myApplication;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;

	private boolean mIsFirstInput = true;
	private String mFirstPassword = null;
	private ExecutorService fixedThreadPool;
	private VersionOneDao dao;
	private Handler mHandler;
	private CustomProgressDialog progressDialog = null;
	private final int GO_INTENT = 0x01;
	private int fromActivity;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist_main);
		dao = MyApplication.getDbHelper();
		AppManager.getAppManager().addActivity(this);
		myApplication = (MyApplication) getApplication();
		fixedThreadPool = myApplication.fixedThreadPool;
		intviews();
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case GO_INTENT:
					ProgressShowUtils.stopProgressDialog(progressDialog);
					Toast.makeText(RegistActivity.this, "设置成功", Toast.LENGTH_SHORT).show();

					Intent intent = new Intent(RegistActivity.this, MainActivity.class);
					intent.putExtra(Setting.MAIN_ACTIVITY, Setting.MAIN_ACTIVITY);
					startActivity(intent);
					finish();
					break;
				}
			}

		};
	}

	private void intviews() {
		mIntent = getIntent();
		fromActivity = mIntent.getIntExtra(Setting.FROM_ACTIVITY, -1);
		txt_cans_result = (TextView) findViewById(R.id.txt_cans_result);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "", new GestureCallBack() {

			@Override
			public void onGestureCodeInput(String inputCode) {
				if (!isInputPassValidate(inputCode)) {
					txt_cans_result.setText(Html.fromHtml("<font color='#c70c1e'>最少链接4个点, 请重新输入</font>"));
					mGestureContentView.clearDrawlineState(0L, false);
					return;
				}
				if (mIsFirstInput) {
					mFirstPassword = inputCode;

					mGestureContentView.clearDrawlineState(0L, true);

					txt_cans_result.setText("请再次绘制启动密码");
				} else {
					if (inputCode.equals(mFirstPassword)) {
						myApplication.password = inputCode;
						fixedThreadPool.execute(new Runnable() {

							@Override
							public void run() {
								if (dao.gestureHasData()) {
									dao.gestureDelete();
								}
								dao.gestureInsert(mFirstPassword, VersionOneDao.BIND_SUCCESS);
								// 设置需要手势密码
								dao.gestureNeedUpdate(VersionOneDao.NEED_GESTURE);
								myApplication.setHasData(true);
								myApplication.setNeedGesture(true);
								mHandler.sendEmptyMessage(GO_INTENT);
							}

						});
						txt_cans_result.setText(Html.fromHtml("<font color='#15d066'>绘制成功</font>"));

						mGestureContentView.clearDrawlineState(1000L, true);
						progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, RegistActivity.this,
								ProgressShowUtils.DATABASE);

					} else {
						txt_cans_result.setText(Html.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils.loadAnimation(RegistActivity.this, R.anim.shake);
						txt_cans_result.startAnimation(shakeAnimation);
						// 保持绘制的线，1.5秒后清除
						mGestureContentView.clearDrawlineState(1500L, false);
					}
				}
				mIsFirstInput = false;
			}

			@Override
			public void checkedSuccess() {

			}

			@Override
			public void checkedFail() {

			}
		});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);

	}

	private boolean isInputPassValidate(String inputPassword) {
		if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
			return false;
		}
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("RejistActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);
	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("RejistActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
    MobclickAgent.onPause(this);
}
}
