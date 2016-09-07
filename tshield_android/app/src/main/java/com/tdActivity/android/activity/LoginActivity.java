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

//登录界面
public class LoginActivity extends Activity {
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
		myApplication=(MyApplication) getApplication();
		lock = intent.getBooleanExtra("lock", false);
		txt_login_result = (TextView) findViewById(R.id.txt_login_result);
		resetpassdworld = (Button) findViewById(R.id.resetpassdworld);
		// saveMemberInfo = getSharedPreferences("member", 0);
		password = myApplication.password;// 获取手势密码
		count = ((MyApplication) getApplication()).getCount();
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
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else if (lock) {// 如果是要求解锁界面
					finish();
				} else {
					Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
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
				Animation shakeAnimation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
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
			public void onClick(View v) { //锁屏界面
				Intent intent = new Intent(LoginActivity.this, FindPassWord.class);
				intent.putExtra(Setting.FROM_ACTIVITY, Setting.LOGIN);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - exitTime) >= 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();

			} else {
				LoginActivity.this.finish();
				AppManager.getAppManager().AppExit(getApplicationContext());
				// System.exit(0);
				super.onBackPressed();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("LoginActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);
	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("LoginActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
    MobclickAgent.onPause(this);
}
}
