package com.tdActivity.android.fragment;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.util.Setting;
import com.tdActivity.android.view.SlidingMenu;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SlideMenuFragment extends Fragment implements OnClickListener {
	private RelativeLayout main_rvl, acount_rvl, hand_rvl, about_rvl;
	// public static AccountFragment account;
	private SettingGestureFragment gestureFragment;
	private AboutUsFragment abountFragment;
	private Intent mIntent;
	private int fragmentPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mIntent = getActivity().getIntent();
		fragmentPosition = mIntent.getIntExtra(Setting.MAIN_ACTIVITY, -1);
		if (fragmentPosition == Setting.MAIN_FRAGMENT) {
			if (((MyApplication) getActivity().getApplication()).userInfos != null
					&& ((MyApplication) getActivity().getApplication()).userInfos.size() > 0) {
				setSelect(0);
			} else {
				setSelect(4);
			}
		} else if (fragmentPosition == Setting.SETTING_GESTURE) {
			setSelect(2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.slid_menu, null);
		main_rvl = (RelativeLayout) view.findViewById(R.id.main_layout);
		acount_rvl = (RelativeLayout) view.findViewById(R.id.countmg_layout);
		hand_rvl = (RelativeLayout) view.findViewById(R.id.handpass_layout);
		about_rvl = (RelativeLayout) view.findViewById(R.id.about_layout);
		main_rvl.setOnClickListener(this);
		acount_rvl.setOnClickListener(this);
		hand_rvl.setOnClickListener(this);
		about_rvl.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();
	//	MiStatInterface.recordPageStart(getActivity(), "侧滑菜单fragment");
	}

	@Override
	public void onPause() {
		super.onPause();
	//	MiStatInterface.recordPageEnd();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.main_layout:
			if (((MyApplication) getActivity().getApplication()).userInfos != null
					&& ((MyApplication) getActivity().getApplication()).userInfos.size() > 0) {
				setSelect(0);
			} else {
				setSelect(4);
			}

			break;
		case R.id.countmg_layout:
			setSelect(1);
			break;
		case R.id.handpass_layout:
			setSelect(2);
			break;
		case R.id.about_layout:
			setSelect(3);
			break;

		default:
			break;
		}

	}

	private void hideFragment(FragmentTransaction transaction) {
		if (MainActivity.mainFragment != null) {
			transaction.hide(MainActivity.mainFragment);
		}
		if (MainActivity.accountFragment != null) {
			transaction.hide(MainActivity.accountFragment);
		}
		if (gestureFragment != null) {
			transaction.hide(gestureFragment);
		}
		if (MainActivity.aboutUsFragment != null) {
			transaction.hide(MainActivity.aboutUsFragment);
		}
		if (MainActivity.addFragment != null) {
			transaction.hide(MainActivity.addFragment);
		}
	}

	public void setSelect(int i) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i) {
		case 0:
			if (SlidingMenu.ignoredViews != null) {
				SlidingMenu.removeAllIgnoredView();
			}
			if (MainActivity.mainFragment == null) {
				MainActivity.mainFragment = new MainFragment(this);
				transaction.add(R.id.contentfag, MainActivity.mainFragment);
			} else {
				transaction.show(MainActivity.mainFragment);
			}
			break;
		case 1:
			if (SlidingMenu.ignoredViews == null || SlidingMenu.ignoredViews.size() == 0) {
				for (View v : SlidingMenu.ignoreViewCopy) {
					SlidingMenu.ignoredViews.add(v);
				}
			}
			if (MainActivity.accountFragment == null) {
				MainActivity.accountFragment = new AccountFragment(this);
				transaction.add(R.id.contentfag, MainActivity.accountFragment);
			} else {
				transaction.show(MainActivity.accountFragment);
			}
			break;
		case 2:
			if (SlidingMenu.ignoredViews != null) {
				SlidingMenu.removeAllIgnoredView();
			}
			if (gestureFragment == null) {
				gestureFragment = new SettingGestureFragment();
				transaction.add(R.id.contentfag, gestureFragment);
			} else {
				transaction.show(gestureFragment);
			}
			break;
		case 3:
			if (SlidingMenu.ignoredViews != null) {
				SlidingMenu.removeAllIgnoredView();
			}
			if (MainActivity.aboutUsFragment == null) {
				MainActivity.aboutUsFragment = new AboutUsFragment();
				transaction.add(R.id.contentfag, MainActivity.aboutUsFragment);
			} else {
				transaction.show(MainActivity.aboutUsFragment);
			}
			break;
		case 4:
			if (SlidingMenu.ignoredViews != null) {
				SlidingMenu.removeAllIgnoredView();
			}
			if (MainActivity.addFragment == null) {
				MainActivity.addFragment = new AddUserFragment(this);
				transaction.add(R.id.contentfag, MainActivity.addFragment);
			} else {
				transaction.show(MainActivity.addFragment);
			}

		default:
			break;
		}
		transaction.commit();
		MainActivity.mMenu.toggle();
	}

}
