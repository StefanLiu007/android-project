package com.eden.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.eden.R;
import com.eden.activity.VideoActivity;
import com.eden.base.BasePageAdapter;
import com.eden.listview.ReadListview;

public class ContentPagerAdapter extends BasePageAdapter {
	private List<View> views;
	private Activity activity;

	public ContentPagerAdapter(List<View> views ,Activity activity) {
		this.views=views;
		this.activity=activity;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view==object;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = views.get(position);
		if (position==0) {
			new ReadListview(view, activity,0).initView();
		}else if (position==1) {
			new ReadListview(view, activity,1).initView();

		}
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}


}
