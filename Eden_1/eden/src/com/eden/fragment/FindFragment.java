package com.eden.fragment;

import com.eden.R;
import com.eden.activity.AskedExpertActivity;
import com.eden.activity.FriendActivity;
import com.eden.activity.MessageActivity;
import com.eden.activity.MyDownloadActivity;
import com.eden.activity.problem_wall_main;
import com.eden.base.BaseFragment;
import com.eden.collection.CollectionContentFragment;
import com.eden.domain.Collection;
import com.eden.myproblem.MyProblemActivity;
import com.eden.util.MyGetUrl;
import com.eden.vollay.XutilRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FindFragment extends BaseFragment implements OnClickListener
{
	private View view;
	private LinearLayout ll_find_01;
	private LinearLayout ll_find_02;
	private LinearLayout ll_find_03;
	private LinearLayout ll_find_04;
	private LinearLayout ll_find_05;
	private LinearLayout ll_find_06;
	private LinearLayout ll_find_07;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		if(view==null)
		{
			view= inflater.inflate(R.layout.fragment_find, container, false);
		}
		initView();
		return view;
	}
	public void initView() {
		ll_find_01=(LinearLayout)view.findViewById(R.id.ll_find_01);
		ll_find_02=(LinearLayout)view.findViewById(R.id.ll_find_02);
		ll_find_03=(LinearLayout)view.findViewById(R.id.ll_find_03);
		ll_find_04=(LinearLayout)view.findViewById(R.id.ll_find_04);
		ll_find_05=(LinearLayout)view.findViewById(R.id.ll_find_05);
		ll_find_06=(LinearLayout)view.findViewById(R.id.ll_find_06);
		ll_find_07=(LinearLayout)view.findViewById(R.id.ll_find_07);
		ll_find_01.setOnClickListener(this);
		ll_find_02.setOnClickListener(this);
		ll_find_03.setOnClickListener(this);
		ll_find_04.setOnClickListener(this);
		ll_find_05.setOnClickListener(this);
		ll_find_06.setOnClickListener(this);
		ll_find_07.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_find_01:
			Intent intent = new Intent(getActivity(),problem_wall_main.class);
			startActivity(intent);
			break;
		case R.id.ll_find_02:
			XutilRequest.showCollection(MyGetUrl.getCollectionServlet(), getActivity());
			break;
		case R.id.ll_find_03:
			Intent intent1 = new Intent(getActivity(),AskedExpertActivity.class);
			startActivity(intent1);
			break;
		case R.id.ll_find_04:
			Intent intent3 = new Intent(getActivity(),FriendActivity.class);
			startActivity(intent3);
			break;
		case R.id.ll_find_05:
			Intent intent4 = new Intent(getActivity(),MyDownloadActivity.class);
			startActivity(intent4);
			break;
		case R.id.ll_find_06:
			Intent intent5 = new Intent(getActivity(),MyProblemActivity.class);
			startActivity(intent5);
			break;
		case R.id.ll_find_07:
			Intent intent6 = new Intent(getActivity(),MessageActivity.class);
			startActivity(intent6);
			break;

		}
	}
}