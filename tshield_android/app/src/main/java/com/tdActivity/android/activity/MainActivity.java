package com.tdActivity.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tdActivity.android.AppManager;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.fragment.AboutUsFragment;
import com.tdActivity.android.fragment.AccountFragment;
import com.tdActivity.android.fragment.AddUserFragment;
import com.tdActivity.android.fragment.MainFragment;
import com.tdActivity.android.fragment.SlideMenuFragment;
import com.tdActivity.android.receiver.ScreenBroadCastReceiver;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.Setting;
import com.tdActivity.android.view.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends BaseActivity {

	private Fragment mContent;
	private VersionOneDao mDao;
	public static SlidingMenu mMenu;
	private FrameLayout main, slide;
	private ScreenBroadCastReceiver mReceiver;
	private boolean isActive = true;
	public static MainFragment mainFragment;
	public static AddUserFragment addFragment;
	public static AccountFragment accountFragment;
	public SlideMenuFragment slideFragment;
	private Intent mIntent;
	private int showFragment;
	private Handler mHandler;
	public static AboutUsFragment aboutUsFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		// customize the SlidingMenu
		AppManager.getAppManager().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content_fragment);
		mIntent = getIntent();
		showFragment = mIntent.getIntExtra(Setting.MAIN_ACTIVITY, -1);
		main = (FrameLayout) findViewById(R.id.contentfag);
		slide = (FrameLayout) findViewById(R.id.menufag);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMenu.closeMenu();

			}
		});
		slide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMenu.closeMenu();

			}
		});
		slideFragment = new SlideMenuFragment();
		mainFragment = new MainFragment(slideFragment);
		addFragment = new AddUserFragment(slideFragment);
		accountFragment = new AccountFragment(slideFragment);
        aboutUsFragment = new AboutUsFragment();
		if (((MyApplication) getApplication()).userInfos != null
				&& ((MyApplication) getApplication()).userInfos.size() > 0) {
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, mainFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, addFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, accountFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, aboutUsFragment).commit();
			getSupportFragmentManager().beginTransaction().hide(addFragment).commit();
			getSupportFragmentManager().beginTransaction().hide(accountFragment).commit();
			getSupportFragmentManager().beginTransaction().hide(aboutUsFragment).commit();
		} else {
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, mainFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, addFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, accountFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.contentfag, aboutUsFragment).commit();
			getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
			getSupportFragmentManager().beginTransaction().hide(accountFragment).commit();
			getSupportFragmentManager().beginTransaction().hide(aboutUsFragment).commit();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.menufag, slideFragment).commit();

		if (showFragment == Setting.SETTING_GESTURE) {

		} else if (showFragment == Setting.MAIN_FRAGMENT) {

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private long exitTime;

	@SuppressWarnings("static-access")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mMenu.isOpen) {
				mMenu.closeMenu();
			} else {
				if ((System.currentTimeMillis() - exitTime) >= 2000) {
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();

				} else {
					MainActivity.this.finish();
					AppManager.getAppManager().AppExit(getApplicationContext());
					super.onBackPressed();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		LocalSynchUtils.threadCopyToFile(myApplication);
		super.onDestroy();
	}
	
}
