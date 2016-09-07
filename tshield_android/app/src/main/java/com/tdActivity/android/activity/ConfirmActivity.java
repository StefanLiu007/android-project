package com.tdActivity.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.AppManager;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.util.Setting;
import com.tdActivity.android.view.GestureContentView;
import com.tdActivity.android.view.GestureDrawline.GestureCallBack;
import com.umeng.analytics.MobclickAgent;

//
public class ConfirmActivity extends Activity {
	private TextView txt_login_result;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private String password;
	private int timer = 5;
	private Button resetpassdworld;
	private int count;
	private Intent intent;
	private boolean changePassword;
	private boolean lock;
	private long exitTime;
	private MyApplication myApplication;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_main);
		AppManager.getAppManager().addActivity(this);
		intviews();
	}

	private void intviews() {
		intent = getIntent();
		changePassword = intent.getBooleanExtra("change_password", false);
		lock = intent.getBooleanExtra("lock", false);
		myApplication=(MyApplication) getApplication();
		txt_login_result =  findViewById(R.id.txt_login_result);
		resetpassdworld = (Button) findViewById(R.id.resetpassdworld);
		// saveMemberInfo = getSharedPreferences("member", 0);
		password = myApplication.password;
		count = myApplication.getCount();
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, true, password, new GestureCallBack() {

			@Override
			public void onGestureCodeInput(String inputCode) {

			}

			@Override
			public void checkedSuccess() {
				mGestureContentView.clearDrawlineState(1000L, true);
				txt_login_result.setText(Html.fromHtml("<font color='#15d066'>验证成功</font>"));
				// dao.errorCountUpdate(0);//重新设置手势密码尝试次数count
				// 应该在MainActivity中进行操作
				if (!changePassword) {// 如果不是修改密码
					Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else if (lock) {// 如果是要求解锁界面
					finish();
				} else {
					Intent intent = new Intent(ConfirmActivity.this, RegistActivity.class);//重设手势密码
					startActivity(intent);
					finish();
				}
			}

			@Override
			public void checkedFail() {
				count++;
				if (count == timer) {
					txt_login_result.setText("输入密码超过次数!");
					mGestureContainer.setVisibility(View.GONE);
				}
				mGestureContentView.clearDrawlineState(1500L, false);
				txt_login_result
						.setText(Html.fromHtml("<font color='#d8372e'>密码错误,您还可以输入" + (timer - count) + "次</font>"));
				((MyApplication) getApplication()).setCount(count);
				MyApplication.getDbHelper().errorCountUpdate(count);
				// 左右移动动画
				Animation shakeAnimation = AnimationUtils.loadAnimation(ConfirmActivity.this, R.anim.shake);
				txt_login_result.startAnimation(shakeAnimation);

			}
		});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
		if (count == timer) {
			txt_login_result.setText("请重新设置手势密码");
			resetpassdworld.setText("重新设置");
			mGestureContainer.setVisibility(View.GONE);
		}

		resetpassdworld.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConfirmActivity.this, FindPassWord.class);
				intent.putExtra(Setting.FROM_ACTIVITY, Setting.COMFIRM);
				startActivity(intent);
			}
		});
	}
  @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onPageStart("ConfirmActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
	MobclickAgent.onResume(this);
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("ConfirmActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
    MobclickAgent.onPause(this);
}
}
