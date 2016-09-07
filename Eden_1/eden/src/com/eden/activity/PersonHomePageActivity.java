package com.eden.activity;


import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eden.R;
import com.eden.base.BaseActivity;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;
import com.eden.vollay.XutilRequest;

public class PersonHomePageActivity extends BaseActivity implements OnClickListener {
	private ImageView btn_back;
	private TextView tv_title;
	private User friend;
	private RelativeLayout rl_background;
	private CircleImageView imv_friendicon;
	private TextView tv_friend_acount;
	private TextView tv_send_message;
	private TextView tv_friend_name;
	private TextView tv_friend_sex;
	private TextView tv_friend_age;
	private TextView tv_friend_adress;
	private TextView tv_friend_text;
	private TextView tv_friend_problem,tv_friend_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personhomepage);
		MyApplication.AddActivity(this);
		Intent intent=getIntent();
		friend = (User) intent.getSerializableExtra("friendInfor");
		initView();
	}

	private void initView() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_friend_time = (TextView) findViewById(R.id.tv_friend_time);
		rl_background=(RelativeLayout)findViewById(R.id.rl_background);
		imv_friendicon=(CircleImageView)findViewById(R.id.imv_friendicon1);
		tv_friend_name=(TextView)findViewById(R.id.tv_friend_name);
		tv_friend_sex=(TextView)findViewById(R.id.tv_friend_sex);
		tv_friend_age=(TextView)findViewById(R.id.tv_friend_age);
		tv_friend_adress=(TextView)findViewById(R.id.tv_friend_adress);
		tv_friend_acount=(TextView)findViewById(R.id.tv_friend_acount1);
		tv_friend_text=(TextView)findViewById(R.id.tv_friend_text1);
		tv_friend_problem=(TextView)findViewById(R.id.tv_friend_problem1);
		tv_send_message=(TextView)findViewById(R.id.tv_send_message);
		btn_back.setOnClickListener(this);
		tv_send_message.setOnClickListener(this);
		tv_title.setText(friend.getUserNickname()+"的个人资料");
		MyAsyncTask asyncTask=new MyAsyncTask(imv_friendicon,this);
		asyncTask.execute(MyGetUrl.getUrl()+friend.getUserIcon());
		tv_friend_name.setText(friend.getUserNickname());
		tv_friend_sex.setText(friend.getUserSex());
		tv_friend_age.setText(friend.getUserBirthday());
		tv_friend_adress.setText(friend.getUserSchool());
		tv_friend_acount.setText("帐号信息:"+friend.getUserAccount());
		tv_friend_text.setText("个性签名:"+friend.getUserSignature());
		tv_friend_problem.setText("最新的问题:");
		XutilRequest.searchProblem(this,MyGetUrl.insertAskedExpertServlet(),friend.getUserAccount(),tv_friend_problem,tv_friend_time);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.tv_send_message:
			if (RongIM.getInstance() != null){
				RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
					@Override
					public UserInfo getUserInfo(String userId) {
						if (userId.equals(friend.getUserAccount())) {
							String id=friend.getUserAccount();
							String name=friend.getUserNickname();
							Uri portraitUri=Uri.parse(MyGetUrl.getUrl()+friend.getUserIcon());
							RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(id, name, portraitUri));
				    		RongIM.getInstance().setMessageAttachedUserInfo(true);
							return new io.rong.imlib.model.UserInfo(id, name, portraitUri);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
						}
						return null;
					}

				}, true);
				RongIM.getInstance().startPrivateChat(this,friend.getUserAccount(),friend.getUserNickname());
			}
			break;
		default:
			break;
		}

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
}
