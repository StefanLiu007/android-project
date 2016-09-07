package com.eden.activity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.vollay.VolleyRequest;
import com.eden.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetSecretActivity extends Activity {
	Button finish;
    String mobile;
	EditText reset_passwd;
	EditText reset_confirm;
	RequestQueue rq;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_reset);
    	reset_passwd = (EditText) findViewById(R.id.reset_passwd);
		reset_confirm = (EditText) findViewById(R.id.reset_confirm);
		MyApplication.AddActivity(this);
		finish = (Button) findViewById(R.id.finish);
		rq = Volley.newRequestQueue(this);
		mobile = getIntent().getStringExtra("刘傻逼");
//		Toast.makeText(ResetSecret.this, mobile, Toast.LENGTH_LONG).show();
		//锟斤拷取锟斤拷锟斤拷锟斤拷只锟斤拷锟�
		finish.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				User user = new User(mobile,reset_passwd.getText().toString(), reset_confirm.getText().toString());
				String url = "http://10.202.1.44:8080/eden/ResetServlet";
				if (reset_passwd.getText().toString().equals(reset_confirm.getText().toString())) {					
					VolleyRequest.JsonRequestForgetSecret(ResetSecretActivity.this, rq, url, user);
				}else{
					Toast.makeText(ResetSecretActivity.this, "两次输入的密码不一致，重新输入", Toast.LENGTH_LONG).show();	
				}
			}
		});
       
    }  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
