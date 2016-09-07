package com.tdActivity.android.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.JSONUtils;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.view.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LocalSynchActivity extends BaseActivity implements OnClickListener {

	private final static String TAG = "LocalSynchActivity";

	private ImageButton mLeftMenu;
	private TextView mTvClickBack;
	private TextView mTvTitle;
	private Button mBtnSave;
	private ListView mLvLocal;
	private Handler mHandler;
	private final int THREAD_FILE = 0x0001;
	private final int BACKUPS = 0x0002;
	private final int UPDATA = 0x003;
	private final int ERROR = 0x004;
	private final String THREAD_FILE_STR = "filesName";
	private String[] fileNames;
	private ArrayAdapter mAdapter;
	private CustomProgressDialog mCustomProgressDialog;
	private VersionOneDao mDao;
	private MyApplication myApplication;

	private AlertDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_synch);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case THREAD_FILE:
					fileNames = msg.getData().getStringArray(THREAD_FILE_STR);
					if (fileNames.length != 0) {
						mAdapter = new ArrayAdapter<String>(LocalSynchActivity.this,
								android.R.layout.simple_list_item_1, fileNames);
						mLvLocal.setAdapter(mAdapter);
					}
					break;
				case BACKUPS:
					ProgressShowUtils.stopProgressDialog(mCustomProgressDialog);
					break;
				case UPDATA:
					if (fileNames.length == 0) {
						fileNames = new String[] {};
					}
					if (mAdapter != null) {
						mAdapter = null;
					}
					mAdapter = new ArrayAdapter<String>(LocalSynchActivity.this, android.R.layout.simple_list_item_1,
							fileNames);
					mLvLocal.setAdapter(mAdapter);
					break;
				case ERROR:
					Toast.makeText(LocalSynchActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
					break;
				default:
					super.handleMessage(msg);
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
		MobclickAgent.onPageStart("LocalAynchActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
		MobclickAgent.onResume(this);
		getLocalSynchFiles();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("LocalAynchActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证
														// onPageEnd 在onPause
														// 之前调用,因为 onPause
														// 中会保存信息。"SplashScreen"为页面名称，可自定义
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.click_back_btn:
			finish();
			break;
		default:
			break;
		}
	}

	private void getLocalSynchFiles() {
		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				String[] filesName = LocalSynchUtils.getSynchLocalFiles(LocalSynchActivity.this);
				Message message = mHandler.obtainMessage();
				message.what = THREAD_FILE;
				Bundle b = new Bundle();
				b.putStringArray(THREAD_FILE_STR, filesName);
				message.setData(b);
				mHandler.sendMessage(message);
			}
		});
	}

	private void assignViews() {
		mDao = MyApplication.getDbHelper();
		myApplication = (MyApplication) getApplication();
		mLeftMenu = (ImageButton) findViewById(R.id.back_addu_btn);
		mLeftMenu.setBackgroundResource(R.drawable.backnew);
		mTvClickBack = (TextView) findViewById(R.id.click_back_btn);
		mTvTitle = (TextView) findViewById(R.id.tv_headadd);
		mTvTitle.setText("本地备份");
		mBtnSave = (Button) findViewById(R.id.save_btn);
		mBtnSave.setVisibility(View.GONE);
		mLvLocal = (ListView) findViewById(R.id.lv_local_synch);
	}

	private void initEvents() {
		mTvClickBack.setOnClickListener(this);
		mLvLocal.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				String name = fileNames[position];
				showDialog(name);
				return false;
			}
		});
	}

	/**
	 * 显示Dialog
	 * 
	 * @param fileName
	 */
	private void showDialog(final String fileName) {
		mDialog = new AlertDialog.Builder(this).setTitle("从备份中恢复")
				.setMessage("请选择恢复方式\n\n覆盖：将您的账户数据恢复到指定备份。\n\n合并：将您现有的账户数据与备份中数据合并。")
				.setNegativeButton("删除", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (LocalSynchUtils.DeleteFile(LocalSynchActivity.this, fileName)) {
							fileNames = LocalSynchUtils.getSynchLocalFiles(LocalSynchActivity.this);
							mHandler.sendEmptyMessage(UPDATA);
						} else {
							mHandler.sendEmptyMessage(ERROR);
						}

					}
				}).setPositiveButton("覆盖", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCustomProgressDialog = ProgressShowUtils.startProgressDialog(mCustomProgressDialog,
								LocalSynchActivity.this, ProgressShowUtils.SYNCH_LOCAL);
						fixedThreadPool.execute(new Runnable() {

							@Override
							public void run() {
								String json = LocalSynchUtils.getAppointFile(LocalSynchActivity.this, fileName);
								ArrayList<UserInfo> userInfos = JSONUtils.analyseJson(json);
								Log.i(TAG, userInfos.toString());
								// TODO 更新缓存？
								LocalSynchUtils.copyAccountToUserInfo(userInfos, myApplication.userInfos);
								LocalSynchUtils.copyNewToOld(myApplication.oldUserInfos, myApplication.userInfos);
								myApplication.isChanged = false;
								Log.i(TAG, myApplication.userInfos.toString());
								mDao.cover(userInfos);
								mHandler.sendEmptyMessage(BACKUPS);
							}
						});
					}
				}).setNeutralButton("合并", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCustomProgressDialog = ProgressShowUtils.startProgressDialog(mCustomProgressDialog,
								LocalSynchActivity.this, ProgressShowUtils.SYNCH_LOCAL);
						fixedThreadPool.execute(new Runnable() {
							@Override
							public void run() {
								String json = LocalSynchUtils.getAppointFile(LocalSynchActivity.this, fileName);
								ArrayList<UserInfo> accounts = JSONUtils.analyseJson(json);
								Log.i(TAG, accounts.toString());
								// TODO 更新缓存？
								LocalSynchUtils.copyAccountToUserInfo(accounts, myApplication.userInfos);
								LocalSynchUtils.copyNewToOld(myApplication.oldUserInfos, myApplication.userInfos);
								myApplication.isChanged = false;
								Log.i(TAG, myApplication.userInfos.toString());
								mDao.merger(accounts);
								mHandler.sendEmptyMessage(BACKUPS);
							}
						});
					}
				}).create();
		mDialog.show();
	}

}
