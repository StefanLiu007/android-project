package com.eden.activity;



import com.eden.R;
import com.eden.util.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomeActivity extends Activity {
	Button btn1;
	Button btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final Window window = getWindow();// ��ȡ��ǰ�Ĵ������
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// ������״̬��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// �����˱�����
		setContentView(R.layout.activity_welcome);
		MyApplication.AddActivity(this);
		btn1 = (Button) findViewById(R.id.welcome_register);
		btn2 = (Button) findViewById(R.id.welcome_login);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
				startActivity(intent);
				
				
			}
		});
//		welcomeUI();
	}

//	private void welcomeUI()
//	{
//		new Thread(new Runnable()
//		{
//
//			@Override
//			public void run()
//			{
//				try
//				{
//					Thread.sleep(2000);
//					Message message = new Message();
//					welHandler.sendMessage(message);// ������Ϣ�а���ʲô����������Ҫ����Ϊ���յĺ�������Ҫ�ò���
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	Handler welHandler = new Handler()
//	{
//
//		@Override
//		public void handleMessage(Message msg)
//		{
//			welcomeFunction();
//		}
//
//	};
//
//	public void welcomeFunction()
//	{
//		Intent intent = new Intent();
//		intent.setClass(WelcomeActivity.this, LoginRegist.class);
//		startActivity(intent);
//		WelcomeActivity.this.finish();
//	}



}