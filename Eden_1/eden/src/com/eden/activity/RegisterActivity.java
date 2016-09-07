package com.eden.activity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	Button btn;
	private RequestQueue rq;	
	private EditText mm;
	private EditText nn;
	private EditText kk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication.AddActivity(this);
		setContentView(R.layout.register);
		rq = Volley.newRequestQueue(this);
		btn = (Button) findViewById(R.id.register);
		mm = (EditText) findViewById(R.id.register_account);
		nn = (EditText) findViewById(R.id.register_passwd);
		kk = (EditText) findViewById(R.id.confirm_passwd);
		btn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				int a = mm.getText().toString().trim().length();
				int b = nn.getText().toString().trim().length();
				int c = kk.getText().toString().trim().length();
				if(a==0||a>11){
					Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
				}else{
					if(b==0||c==0){
						Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();	
					}else{
						String s = nn.getText().toString();
						String q = kk.getText().toString();
						if(s.equals(q)){
							User user = new User(mm.getText().toString().trim(), nn.getText().toString().trim());
							VolleyRequest.JsonRequestRegister(RegisterActivity.this, rq, MyGetUrl.getRegisterServlet(), user);
						}else{
							Toast.makeText(RegisterActivity.this, "两次输入的密码不统一，请重新输入", Toast.LENGTH_LONG).show();
							
						}
					}
				}
//				User user = new User(mm.getText().toString(), nn.getText().toString());				
//				if (nn.getText().toString().equals(kk.getText().toString())) {			
//					VolleyRequest.JsonRequestRegister(Register.this, rq, MyGetUrl.getRegisterServlet(), user);
//				}else if(mm.getText().toString().length()>11){
//					Toast.makeText(Register.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
//				}else if(mm.getText().toString()==null&&nn.getText().toString()==null){
//					Toast.makeText(Register.this, "账号不能为空", Toast.LENGTH_LONG).show();					
//				}else{
//					Toast.makeText(Register.this, "两次输入的密码不统一，请重新输入", Toast.LENGTH_LONG).show();					
//				}
				
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
