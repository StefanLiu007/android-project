package com.tdActivity.android.activity;

import java.util.ArrayList;

import com.kyleduo.switchbutton.SwitchButton;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.JSONUtils;
import com.tdActivity.android.util.ListUtils;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.PreferenceUtils;
import com.tdActivity.android.util.Setting;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 备份设置
 * 
 * @author leo.huang
 *
 */
public class SynchSettingActivity extends BaseActivity implements OnClickListener {
	private TextView mTvBack;
	private TextView mTvTitle;
	private Button mBtnLocalSynch, mSaveButton;
	private SwitchButton mToggle;
	private ImageView mLeftMenu;
	private boolean synchSetting;
	private MyApplication myApplication;
	private ArrayList<UserInfo> userInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_synch_setting);
		assignViews();
		initEvents();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SyncSettingActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);
		synchSetting = myApplication.synchSetting;
		mToggle.setChecked(synchSetting);

	}

	private void assignViews() {
		myApplication = (MyApplication) getApplication();
		mTvBack = (TextView) findViewById(R.id.click_back_btn);
		mTvTitle = (TextView) findViewById(R.id.tv_headadd);
		mTvTitle.setText("备份设置");
		mSaveButton = (Button) findViewById(R.id.save_btn);
		mSaveButton.setVisibility(View.GONE);
		mLeftMenu = (ImageView) findViewById(R.id.back_addu_btn);
		mLeftMenu.setBackgroundResource(R.drawable.backnew);
		mToggle = (SwitchButton) findViewById(R.id.sb_ios_synch);
		mBtnLocalSynch = (Button) findViewById(R.id.btn_local_synch);
	}

	private void initEvents() {
		mTvBack.setOnClickListener(this);
		mBtnLocalSynch.setOnClickListener(this);
		mToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				myApplication.synchSetting = isChecked;
				PreferenceUtils.synchSetting(myApplication, isChecked);
				// TODO 云端同步添加
				if (isChecked) {
					mBtnLocalSynch.setVisibility(View.VISIBLE);
					boolean firstTime = PreferenceUtils.isFirstTime(SynchSettingActivity.this);// 判断是否是第一次
					// TODO 逻辑有问题 由于每次都会
					if (firstTime) {
						long time=System.currentTimeMillis();
						PreferenceUtils.setLocalSynchTime(SynchSettingActivity.this, time);
						// 如果开启同步进行第一次同步 如果是第一次进行备份
						if (myApplication.userInfos.size() > 0) {// 如果缓存中有数据
							LocalSynchUtils.copyNewToOld(myApplication.oldUserInfos, myApplication.userInfos);// 更新缓存
							// 重新备份
							userInfos = (ArrayList<UserInfo>) ListUtils.copyToAnother(myApplication.userInfos);
							fixedThreadPool.execute(new Runnable() {
								@Override
								public void run() {
									// TODO 使用MyApp 中的UserInfos还是创建一个新的备份
									String json = JSONUtils.generateJson(userInfos);
									LocalSynchUtils.addLoaclFile(SynchSettingActivity.this, json);
									userInfos = null;
								}
							});
						}
						PreferenceUtils.setIsFirstTime(SynchSettingActivity.this);
					} 
				} else {
					mBtnLocalSynch.setVisibility(View.GONE);
					LocalSynchUtils.copyNewToOld(myApplication.oldUserInfos, myApplication.userInfos);// 更新缓存
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.click_back_btn:
			finish();
			break;
		case R.id.btn_local_synch:
			// TODO 跳转到本地同步记录上
			Intent intent = new Intent(this, LocalSynchActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("SyncSettingActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
    MobclickAgent.onPause(this);
}
}
