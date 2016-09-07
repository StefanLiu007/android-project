package com.tdActivity.android.activity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdActivity.android.activity.R;
import com.baidu.android.common.logging.Log;
import com.tdActivity.android.AppManager;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.adapter.RecyclerViewAdapter;
import com.tdActivity.android.adapter.RecyclerViewAdapter.UpdateDate;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.JSONUtils;
import com.tdActivity.android.util.ListUtils;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.view.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

public class HanldAddActivity extends BaseActivity implements OnClickListener {
	private final static String TAG="HanldAddActivity";
	// private Button back_btn, save_btn;
	// private Button back_btn;
	private ImageButton back_addu_btn;
	private ImageButton save_btn;
	private TextView click_back_btn, tv_finish_click;
	private TextView tv_headadd;
	private EditText ed_acount, ed_passdworld, ed_yyanme;
	private UserInfo userInfo;
	private int a;
	private LinearLayout ll_username, ll_account, ll_key;
	private CustomProgressDialog progressDialog = null;

	private VersionOneDao mDao;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			myApplication.isChanged = true;
			if (msg.what == 0x1201) {
				Toast.makeText(HanldAddActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
				ProgressShowUtils.stopProgressDialog(progressDialog);
				HanldAddActivity.this.finish();
			}
			if (msg.what == 0x1202) {
				Toast.makeText(HanldAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				ProgressShowUtils.stopProgressDialog(progressDialog);
				if (1 == getIntent().getIntExtra("AddUserFragment", 0)) {
					Intent intent = new Intent();
					setResult(0x112, intent);
				}
				HanldAddActivity.this.finish();
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.handadd_main);
		Log.i(TAG, "onCreate");
		AppManager.getAppManager().addActivity(this);
		intviews();

	}

	private void intviews() {
		mDao = myApplication.getDbHelper();
		userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
		a = getIntent().getIntExtra("code", 0);
		back_addu_btn = (ImageButton) findViewById(R.id.back_addu_btn);
		back_addu_btn.setBackgroundResource(R.drawable.backnew);
		click_back_btn = (TextView) findViewById(R.id.click_back_btn);
		click_back_btn.setOnClickListener(this);
		save_btn = (ImageButton) findViewById(R.id.save_btn);
		tv_finish_click = (TextView) findViewById(R.id.tv_finish_click);
		tv_finish_click.setOnClickListener(this);
		tv_headadd = (TextView) findViewById(R.id.tv_headadd);
		tv_headadd.setText("手动添加");
		ed_acount = (EditText) findViewById(R.id.ed_acount);
		ed_passdworld = (EditText) findViewById(R.id.ed_passworld);
		ed_yyanme = (EditText) findViewById(R.id.ed_yyanme);
		ll_username = (LinearLayout) findViewById(R.id.ll_username);
		ll_account = (LinearLayout) findViewById(R.id.ll_account);
		ll_key = (LinearLayout) findViewById(R.id.ll_key);
		ll_username.setOnClickListener(this);
		ll_account.setOnClickListener(this);
		ll_key.setOnClickListener(this);
		onFocusChange(true, ed_yyanme);
		if (userInfo != null) {
			ed_acount.setText(userInfo.yy);
			ed_yyanme.setText(userInfo.name);
			ed_passdworld.setText(userInfo.password);
			ed_passdworld.setEnabled(false);
			ed_passdworld.setTextColor(Color.GRAY);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.click_back_btn:
			View view = getWindow().peekDecorView();
			if (view != null) {
				InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
			finish();
			break;
		case R.id.tv_finish_click:
			String username = ed_acount.getText().toString().trim();
			String passdworld = ed_passdworld.getText().toString().trim();
			String yyanme = ed_yyanme.getText().toString().trim();
			Pattern p1 = Pattern.compile("[a-zA-Z2-7]+");
			Matcher matcher = p1.matcher(passdworld);
			if (TextUtils.isEmpty(yyanme) || TextUtils.isEmpty(username) || TextUtils.isEmpty(passdworld)) {
				Toast.makeText(HanldAddActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
			} else if (!matcher.matches()) {
				Toast.makeText(HanldAddActivity.this, "密码格式有误", Toast.LENGTH_SHORT).show();
			} else {
				final UserInfo info = new UserInfo();
				info.name = username;
				info.password = passdworld;
				info.yy = yyanme;
				if (1 == a) {
					progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, this,
							ProgressShowUtils.CAPTURE);
					fixedThreadPool.execute(new Runnable() {

						@Override
						public void run() {
							MyApplication.getDbHelper().userInfoUpdateItem(info);
							myApplication.userInfos = MyApplication.getDbHelper().userInfosFrom();
							ArrayList<UserInfo> userInfos=(ArrayList<UserInfo>) ListUtils.copyToAnother(myApplication.userInfos);
							Message msg = Message.obtain();
							msg.what = 0x1201;
							handler.sendMessage(msg);
							// TODO 进行临时文件写
							LocalSynchUtils.updateCacheFileNoThread(myApplication,userInfos);
						}
					});

				} else {
					progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, this,
							ProgressShowUtils.CAPTURE);
					fixedThreadPool.execute(new Runnable() {

						@Override
						public void run() {
							int index = MyApplication.getDbHelper().userInfoGetCount();
							MyApplication.getDbHelper().userInfoInsert(info, index);
							myApplication.userInfos = MyApplication.getDbHelper().userInfosFrom();
							ArrayList<UserInfo> userInfos=(ArrayList<UserInfo>) ListUtils.copyToAnother(myApplication.userInfos);
							Message msg = Message.obtain();
							msg.what = 0x1202;
							handler.sendMessage(msg);
							// TODO 进行临时文件写
							LocalSynchUtils.updateCacheFileNoThread(myApplication,userInfos);
						}
					});

				}

			}

			break;
		case R.id.ll_username:
			onFocusChange(true, ed_yyanme);
			break;
		case R.id.ll_account:
			onFocusChange(true, ed_acount);
			break;
		case R.id.ll_key:
			onFocusChange(true, ed_passdworld);
			break;
		default:
			break;
		}
	}

	private void onFocusChange(boolean hasFocus, final EditText commentEdit) {
		final boolean isFocus = hasFocus;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager) commentEdit.getContext()
						.getSystemService(INPUT_METHOD_SERVICE);
				if (isFocus) {
					commentEdit.setFocusable(true);
					commentEdit.setFocusableInTouchMode(true);
					commentEdit.requestFocus();
					commentEdit.requestFocusFromTouch();
					// 显示输入
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					// 隐藏输入
					imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
				}
			}
		}, 100);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("HandAddActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);

	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("HandAddActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
    MobclickAgent.onPause(this);
}
}
