package com.eden.activity;


import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import com.eden.R;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;
import com.eden.vollay.XutilRequest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class IsNotFriendActivity extends Activity implements OnClickListener {
	private Button add_friend;
	private Button sendmessage;
	private User user;
	private CircleImageView friend_icon;
	private TextView friend_name;
	private TextView friend_sex;
	private TextView friend_age;
	private TextView friend_adress;
	private TextView friend_acount;
	private TextView friend_text;
	private TextView friend_problem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.no_friend_personhomepage);
		MyApplication.AddActivity(this);
		Intent intent=getIntent();
		user = (User) intent.getSerializableExtra("friendInfor");
		initView();
	}
	private void initView() {
		add_friend = (Button) findViewById(R.id.no_addfriend);
		sendmessage = (Button)findViewById(R.id.no_sendmessage);
		friend_icon=(CircleImageView)findViewById(R.id.no_friendicon);
		friend_name=(TextView)findViewById(R.id.no_friend_name);
		friend_sex=(TextView)findViewById(R.id.no_friend_sex);
		friend_age=(TextView)findViewById(R.id.no_friend_age);
		friend_adress=(TextView)findViewById(R.id.no_friend_adress);
		friend_acount=(TextView)findViewById(R.id.tv_friend_acount);
		friend_text=(TextView)findViewById(R.id.tv_friend_text);
		friend_problem=(TextView)findViewById(R.id.tv_friend_problem);
		MyAsyncTask asyncTask= new MyAsyncTask(friend_icon, this);
		asyncTask.execute(MyGetUrl.getUrl()+user.getUserIcon());
		friend_name.setText(user.getUserNickname());
		friend_sex.setText(user.getUserSex());
		friend_age.setText(user.getUserBirthday());
		friend_adress.setText(user.getUserSchool());
		friend_acount.setText(user.getUserAccount());
		friend_text.setText(user.getUserSignature());
		//		friend_problem.setText(user.get)
		add_friend.setOnClickListener(this);
		sendmessage.setOnClickListener(this);

	}
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.no_addfriend:
			XutilRequest.addFriend(this, MyGetUrl.addFriendServlet(), user.getUserAccount());
			break;
		case R.id.no_sendmessage:
			if (RongIM.getInstance() != null){
				RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
					@Override
					public UserInfo getUserInfo(String userId) {
						if (userId.equals(user.getUserAccount())) {
							String id=user.getUserAccount();
							String name=user.getUserNickname();
							Uri portraitUri=Uri.parse(MyGetUrl.getUrl()+user.getUserIcon());
							RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(id, name, portraitUri));
				    		RongIM.getInstance().setMessageAttachedUserInfo(true);
							return new io.rong.imlib.model.UserInfo(id, name, portraitUri);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
						}
						return null;
					}

				}, true);
				RongIM.getInstance().startPrivateChat(this,user.getUserAccount(),user.getUserNickname());
			}
			break;
		}

	}

}
