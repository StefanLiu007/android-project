package com.tdActivity.android.fragment;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.util.FontManager;
import com.tdActivity.android.view.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutUsFragment extends Fragment {
	// private Button back_about_btn;
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private TextView tv_headadd,about_fragment_net,about_fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (SlidingMenu.ignoredViews != null) {
			SlidingMenu.removeAllIgnoredView();
		}
		View view = inflater.inflate(R.layout.about_fragment, null);
		// back_about_btn = (Button) view.findViewById(R.id.back_btn);
		// back_about_btn.setText(getResources().getString(R.string.fa_icon_item));
		// back_about_btn.setTextColor(Color.WHITE);
		// back_about_btn.setTextSize(18);
		// FontManager.FONTAWESOME));
		back_addu_btn = (ImageButton) view.findViewById(R.id.back_addu_btn);
		click_back_btn = (TextView) view.findViewById(R.id.click_back_btn);
		tv_headadd = (TextView) view.findViewById(R.id.tv_headadd);
		tv_headadd.setText("关于T盾");
		back_addu_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.mMenu.toggle();
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("AboutUsFragment"); 
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("AboutUsFragment");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
