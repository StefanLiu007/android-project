package com.eden.activity;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login;	
	private TextView eden;
	private TextView dsf;
	private RequestQueue rq;	
	private EditText aa;
	private EditText bb;
	private CheckBox checkbox;
	private TextView forgetpasswd;
	String AppKEY = "aeb5746bc4dc";
	String AppSecret = "323640aae004c09b1681d2767164cdb3";
	


	@Override
	protected void onCreate(Bundle savedInstanceState) 	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		MyApplication.AddActivity(this);
		checkbox = (CheckBox) findViewById(R.id.checkBox);
		dsf = (TextView) findViewById(R.id.dsf);
		aa = (EditText) findViewById(R.id.login_account);
		bb = (EditText) findViewById(R.id.login_passwd);
		eden = (TextView) findViewById(R.id.eden);
		SMSSDK.initSDK(this,AppKEY, AppSecret);//初始化
		forgetpasswd = (TextView) findViewById(R.id.forgetpasswd);
		
		forgetpasswd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				//注册手机号
				RegisterPage registerPage = new RegisterPage();
				//注册回调事件
				registerPage.setRegisterCallback(new EventHandler(){
					@Override
					public void afterEvent(int event, int result, Object data) {
						//判断结果是否已经完成
						if (result==SMSSDK.RESULT_COMPLETE) {
							//获取数据data
							HashMap<String,Object> maps = (HashMap<String,Object>)data;
							String country = (String) maps.get("country");
							String phone = (String) maps.get("phone");
							submitUserInfo(country, phone);
							//将手机号传入重置密码界面
							String number = aa.getText().toString();
							Intent mobile= new Intent(LoginActivity.this,ResetSecretActivity.class);
							mobile.putExtra("刘傻逼", number);				
							startActivity(mobile);
							
//							Intent intent = new Intent(Login.this,ResetSecret.class);
//							startActivity(intent);
						
						}
					}

					private void submitUserInfo(String country, String phone) {
						Random r = new Random();
				    	String uid = Math.abs(r.nextInt())+"";
				    	String nickName = "Imooc";
				    	SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
						
					}
				});
				//显示登录界面
				registerPage.show(LoginActivity.this);			
				
			}				
		});		
		login = (Button) findViewById(R.id.login);
		rq = Volley.newRequestQueue(this);
		final SharedPreferences spf = getSharedPreferences("userInfo", MODE_PRIVATE);
		String name = spf.getString("username",null);
		String password = spf.getString("password",null);
		if (name==null) {
		    checkbox.setChecked(false);
		}else{
			checkbox.setChecked(true);
			aa.setText(name);
			bb.setText(password);
		}

		login.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				User user = new User(aa.getText().toString(), bb.getText().toString());
                int a = aa.getText().toString().trim().length();
                int b = bb.getText().toString().trim().length();
                if(a==0||b==0){
                	Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                	VolleyRequest.JsonRequestLogin(LoginActivity.this, rq,MyGetUrl.getLoginServlet(), user,spf,checkbox);
                }
				
				
				

			}
		});
		ScaleAnimation anim = new ScaleAnimation(1,2,1,2,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		anim.setDuration(2000);
		eden.startAnimation(anim);
		//qq登录
				dsf.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(LoginActivity.this,QQLoginActivity.class);
						startActivity(intent);					
					}
				});
		
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
