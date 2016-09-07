package com.eden.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eden.R;
import com.eden.adapter.ExpertAdapter;
import com.eden.domain.Expert;
import com.eden.util.MyGetUrl;
import com.eden.vollay.XutilRequest;

public class AskedExpertActivity extends Activity implements OnItemClickListener{
	private ImageView btn_back;
	private TextView tv_title;
	public static ExpertAdapter adapter ;
	private ListView asked_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.asked_expert);
		init();
	}
	private void init() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		asked_list = (ListView) findViewById(R.id.asked_list);
		adapter =new ExpertAdapter(this);
		tv_title.setText("我咨询过的专家");
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		String account = getSharedPreferences("user", MODE_PRIVATE).getString("account", null);
		XutilRequest.searchAskedExpert(this, MyGetUrl.insertAskedExpertServlet(), account, asked_list, adapter);
		asked_list.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent= new Intent(this,ExpertActivity.class);
		Expert experts=(Expert) adapter.getItem(position);
		intent.putExtra("ExpertActivity", experts);
		startActivity(intent);;
		
	}

}
