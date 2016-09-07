package com.eden.adapter;

import java.util.List;

import com.eden.R;
import com.eden.domain.User;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendAdapter extends BaseAdapter {
	private List<User> friends;
	private LayoutInflater inflater;
	private Activity activity;
	public FriendAdapter() {
		// TODO Auto-generated constructor stub
	}
	public FriendAdapter(Activity activity) {
		inflater=LayoutInflater.from(activity);
		this.activity = activity;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.listitem_friend, null);
		}
		CircleImageView imv_friendicon=(CircleImageView) convertView.findViewById(R.id.imv_friendicon);
		TextView tv_friendname=(TextView) convertView.findViewById(R.id.tv_friendname);
		TextView tv_friendstate=(TextView) convertView.findViewById(R.id.tv_friendstate);
		User friend= friends.get(position);
		MyAsyncTask asyncTask=new MyAsyncTask(imv_friendicon, activity);
		asyncTask.execute(MyGetUrl.getUrl()+friend.getUserIcon());
		tv_friendname.setText(friend.getUserNickname());
		if (friend.getUserState().equals("1")) {
			tv_friendstate.setText("在线");
		}else{
			tv_friendstate.setText("离线");
		}
		return convertView;
	}

}
