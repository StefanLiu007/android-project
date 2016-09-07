package com.eden.fragment;

import java.util.ArrayList;
import java.util.List;

import com.eden.R;
import com.eden.adapter.ContentPagerAdapter;
import com.eden.adapter.GroupAdapter;
import com.eden.base.BaseFragment;
import com.eden.collection.CollectionContentFragment;
import com.eden.collection.CollectionProblemFragment;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomePageFragment extends BaseFragment implements OnClickListener{
	private View view;
	private List<View> list;
	public ImageView userIcon;
	private ViewPager vp_content;
	private ContentPagerAdapter contentPagerAdapter;
	private TextView tv_read;
	private TextView tv_video;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if(view == null){
			view = inflater.inflate(R.layout.fragment_homepage, container, false);
		}
		initView();
		return view;
	}
	@SuppressWarnings("deprecation")
	private void initView() {
		tv_read=(TextView)view.findViewById(R.id.tv_read);
		tv_video=(TextView)view.findViewById(R.id.tv_video);
		vp_content=(ViewPager) view.findViewById(R.id.vp_content);
		tv_read.setOnClickListener(this);
		tv_video.setOnClickListener(this);
		list = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view1 = inflater.inflate(R.layout.listview_read, null);
		View view2 = inflater.inflate(R.layout.video_teach, null);
		list.add(view1);
		list.add(view2);
		contentPagerAdapter = new ContentPagerAdapter(list,getActivity());
		vp_content.setAdapter(contentPagerAdapter);
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if(arg0 == 0){
					tv_read.setBackgroundResource(R.drawable.ful1);
					tv_video.setBackgroundResource(R.drawable.fur);
				}
				if(arg0 ==1){
					tv_read.setBackgroundResource(R.drawable.ful);
					tv_video.setBackgroundResource(R.drawable.fur1);
				}
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		setSelect(0);
	}
	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_read:
			vp_content.setCurrentItem(0);
			setSelect(0);
			tv_video.setBackgroundResource(R.drawable.ful);
			break;
		case R.id.tv_video:
			vp_content.setCurrentItem(1);
			tv_read.setBackgroundResource(R.drawable.fur);
			setSelect(1);
			break;
		}
	}
	private void resetImgs()
	{
		tv_read.setBackgroundResource(R.drawable.ful);
		tv_video.setBackgroundResource(R.drawable.fur);
	}
	private void setSelect(int i)
	{
		
		switch (i)
		{
		case 0:
			tv_read.setBackgroundResource(R.drawable.ful1);
			break;
		case 1:
			tv_video.setBackgroundResource(R.drawable.fur1);
			break;
		default:
			break;
		}
	}

}
