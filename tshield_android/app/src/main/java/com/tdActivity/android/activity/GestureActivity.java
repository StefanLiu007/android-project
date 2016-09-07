package com.tdActivity.android.activity;

import com.kyleduo.switchbutton.SwitchButton;
import com.tdActivity.android.activity.R;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 手势密码设置
 * 
 * @author leo.huang
 *
 */
public class GestureActivity extends BaseActivity implements OnClickListener {
	private TextView mTvBack;
	private TextView mTvTitle;
	private Button mSaveButton, mChangeGesture;
	private SwitchButton mToggle;
	private Handler mHandler;
	private ImageView mLeftMenu;
	private MyApplication myApplication;
	private boolean needGesture;
	private boolean hasData;
	private VersionOneDao mDao;

	private final int NEED_AND_GET = 0x01;
	private final String NEED_AND_GET_STR = "need";
	private final int HAS_DATA = 0x02;
	private final String HAS_DATA_STR = "hasdata";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture);
		myApplication = (MyApplication) getApplication();
		mDao = myApplication.getDbHelper();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case NEED_AND_GET: {
					Bundle b = msg.getData();
					boolean need = b.getBoolean(NEED_AND_GET_STR);
					if (need) {
						mToggle.setChecked(true);
						mChangeGesture.setVisibility(View.VISIBLE);
					} else {
						mToggle.setChecked(false);
						mChangeGesture.setVisibility(View.GONE);
					}
				}
					break;
				}
			}

		};

		assignViews();
		initEvents();
	}

	@Override
	protected void onResume() {
		super.onResume();
		threadSetToggleStatue();
		MobclickAgent.onPageStart("GestureActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);

	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("GestureActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
    MobclickAgent.onPause(this);
}
	private void assignViews() {
		mTvBack = (TextView) findViewById(R.id.click_back_btn);
		mTvTitle = (TextView) findViewById(R.id.tv_headadd);
		mTvTitle.setText("手势密码");
		mSaveButton = (Button) findViewById(R.id.save_btn);
		mSaveButton.setVisibility(View.GONE);
		mLeftMenu = (ImageView) findViewById(R.id.back_addu_btn);
		mLeftMenu.setBackgroundResource(R.drawable.backnew);
		mToggle = (SwitchButton) findViewById(R.id.sb_ios);
		mChangeGesture = (Button) findViewById(R.id.btn_change_gesture);
	}

	private void initEvents() {
		mTvBack.setOnClickListener(this);
		mChangeGesture.setOnClickListener(this);
		mToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					if (myApplication.isHasData()) {
						mChangeGesture.setVisibility(View.VISIBLE);
						myApplication.setNeedGesture(true);
						updataNeedGesture(VersionOneDao.NEED_GESTURE);
					} else {
						Intent intent = new Intent(GestureActivity.this, FindPassWord.class);
						intent.putExtra("bind", true);
						startActivity(intent);
					}
				} else {
					mChangeGesture.setVisibility(View.GONE);
					myApplication.setNeedGesture(false);
					updataNeedGesture(VersionOneDao.NOT_NEED_GESTURE);
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
		case R.id.btn_change_gesture:
			Intent intent = new Intent(this, ConfirmActivity.class);
			intent.putExtra("change_password", true);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/* 通过线程更新数据 */
	public void updataNeedGesture(final int flag) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mDao.gestureNeedUpdate(flag);
			}
		}).start();
	}

	/**
	 * 通过线程去数据库操作
	 */
	private void threadSetToggleStatue() {
		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				boolean need = myApplication.isHasData() && myApplication.isNeedGesture();
				Message msg = mHandler.obtainMessage();
				msg.what = NEED_AND_GET;
				Bundle b = new Bundle();
				b.putBoolean(NEED_AND_GET_STR, need);
				msg.setData(b);
				mHandler.sendMessage(msg);
			}

		});
	}

}
