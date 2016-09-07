package com.eden.activity;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.R;
import com.eden.domain.Problem;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.XutilRequest;

public class SendProblem extends Activity {
	private TextView send;
    private EditText edit;
    SharedPreferences sp;
    private TextView title;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selectimg);
		MyApplication.AddActivity(this);
		send = (TextView) findViewById(R.id.activity_selectimg_send);
		edit = (EditText) findViewById(R.id.activity_selectimg_edit);
		sp = getSharedPreferences("user", MODE_PRIVATE);
		title = (TextView) findViewById(R.id.send_pro);
		title.setText("我要提问题");
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String time =	new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
				 
				String account = sp.getString("account", "");
				int len = edit.getText().toString().trim().length();
				if(len == 0){
					Toast.makeText(SendProblem.this, "内容不能为空", Toast.LENGTH_SHORT).show();
				}else{
					String content = edit.getText().toString();
					String problemId = UUID.randomUUID().toString();
					Problem problem = new Problem(problemId, account, content, time , 0);
					XutilRequest.sendProblem(MyGetUrl.getLoadProblem(), SendProblem.this, problem);
					Intent intent = new Intent(SendProblem.this,problem_wall_main.class);
					startActivity(intent);
					finish();
				}
				
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
