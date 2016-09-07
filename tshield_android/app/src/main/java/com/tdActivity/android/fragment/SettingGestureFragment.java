package com.tdActivity.android.fragment;

import com.kyleduo.switchbutton.SwitchButton;
import com.tdActivity.android.activity.R;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.ConfirmActivity;
import com.tdActivity.android.activity.FindPassWord;
import com.tdActivity.android.activity.GestureActivity;
import com.tdActivity.android.activity.LoginActivity;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.activity.SynchSettingActivity;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.util.FontManager;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.view.CustomProgressDialog;
import com.tdActivity.android.view.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 手势密码设置
 * 
 * @author leo.huang
 *
 */
public class SettingGestureFragment extends Fragment implements OnClickListener {
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private TextView tvTitle;
	private LinearLayout gestureRelative;
	private LinearLayout synchRelative;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (SlidingMenu.ignoredViews != null) {
			SlidingMenu.removeAllIgnoredView();
		}
		View view = inflater.inflate(R.layout.activity_gesture_settion, null, false);
		back_addu_btn = (ImageButton) view.findViewById(R.id.back_addu_btn);
		click_back_btn = (TextView) view.findViewById(R.id.click_back_btn);
		gestureRelative=(LinearLayout) view.findViewById(R.id.gesture_relative);
		tvTitle = (TextView) view.findViewById(R.id.tv_headadd);
		synchRelative=(LinearLayout) view.findViewById(R.id.synch_relative);
		initEvents();
		return view;
	}

	private void initEvents() {
		tvTitle.setText("设置");
		click_back_btn.setOnClickListener(this);
		gestureRelative.setOnClickListener(this);
		synchRelative.setOnClickListener(this);
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SettingGestureFragment"); 
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SettingGestureFragment");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click_back_btn:
			MainActivity.mMenu.toggle();
			break;
		case R.id.gesture_relative:
			Intent intent =new Intent(getActivity(),GestureActivity.class);
			startActivity(intent);
			break;
		case R.id.synch_relative:
			Intent synchIntent=new Intent(getActivity(),SynchSettingActivity.class);
			startActivity(synchIntent);
			break;
		default:
			break;
		}

	}

	/**
	 * 设置进入动画
	 * 
	 * @param view
	 */
	private void setViewInAnimation(View view) {

	}

	/**
	 * 设置出去动画
	 * 
	 * @param view
	 */
	private void setViewOutAnimation(View view) {

	}
}
