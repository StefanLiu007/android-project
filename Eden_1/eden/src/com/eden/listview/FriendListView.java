package com.eden.listview;

import com.eden.R;
import com.eden.activity.PersonHomePageActivity;
import com.eden.adapter.FriendAdapter;

import com.eden.domain.User;
import com.eden.util.MyGetUrl;
import com.eden.vollay.XutilRequest;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FriendListView implements OnItemClickListener {
	private View view;
	private Activity activity;
	private ListView lv_friend;
	private FriendAdapter adapter;
	public FriendListView(View view,Activity activity) {
		this.view=view;
		this.activity=activity;
	}
	public  void  initView(){
		lv_friend = (ListView) view.findViewById(R.id.lv_friend);
		lv_friend.setOnItemClickListener(this);
		adapter = new FriendAdapter(activity);
		XutilRequest.FriendRequest(MyGetUrl.getFriendServlet(), lv_friend, adapter, activity);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		User friend=adapter.getFriends().get(position);
		Intent intent =new Intent(activity,PersonHomePageActivity.class);
		intent.putExtra("friendInfor", friend);
		activity.startActivity(intent);
	}
}
