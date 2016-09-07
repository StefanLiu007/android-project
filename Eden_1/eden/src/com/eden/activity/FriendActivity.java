package com.eden.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.eden.R;
import com.eden.adapter.FriendAdapter;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.XutilRequest;

public class FriendActivity extends FragmentActivity implements OnItemClickListener, OnClickListener{
//	@ViewInject(R.id.tv_thenews)
//	public static TextView tv_thenews;
//	@ViewInject(R.id.tv_friend)
//	public static TextView tv_friend;
//	@ViewInject(R.id.vp_friend)
//	private ViewPager vp_friend;
//	private List<View> list;
//	private FriendPageAdapter adapter;
//
	private View view;
	private ListView lv_friend;
	private FriendAdapter adapter;
	private ImageView btn_back;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview_friend);
//		setContentView(R.layout.activity_friend);
		MyApplication.AddActivity(this);
//		ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		lv_friend = (ListView) findViewById(R.id.lv_friend);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我关注的人");
		btn_back.setOnClickListener(this);
		lv_friend.setOnItemClickListener(this);
		adapter = new FriendAdapter(this);
		XutilRequest.FriendRequest(MyGetUrl.getFriendServlet(), lv_friend, adapter, this);
//		list = new ArrayList<View>();
//		LayoutInflater inflater = LayoutInflater.from(this);
//		View view1 = inflater.inflate(R.layout.friend_conversationlist, null);
//		View view2 = inflater.inflate(R.layout.listview_friend, null);
//		list.add(view1);
//		list.add(view2);
//		adapter = new FriendPageAdapter(list, this); 
//		vp_friend.setAdapter(adapter);
		
	}

//	@OnClick(R.id.tv_thenews)
//	public void btn1Click(View arg0) {
//		vp_friend.setCurrentItem(0);
//	}
//	@OnClick(R.id.tv_friend)
//	public void btn2Click(View arg0) {
//		vp_friend.setCurrentItem(1);
//	}
	
	
	
	

	
	@Override
	protected void onDestroy() {
		MyApplication.getHttpQueue().cancelAll("picture");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		MyApplication.getHttpQueue().cancelAll("picture");
		super.onStop();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		User friend=adapter.getFriends().get(position);
		Intent intent =new Intent(this,PersonHomePageActivity.class);
		intent.putExtra("friendInfor", friend);
		startActivity(intent);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}
}
