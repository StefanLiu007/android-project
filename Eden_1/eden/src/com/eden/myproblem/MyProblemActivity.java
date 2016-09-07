package com.eden.myproblem;

import com.eden.R;
import com.eden.util.MyGetUrl;
import com.eden.vollay.XutilRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProblemActivity extends Activity {
	private ListViewProblem listViewProblem;
	private MyProblemAdapter adapter;
	private ImageView btn_back;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myproblem);
		initView();
	}

	private void initView() {
		listViewProblem = (ListViewProblem) findViewById(R.id.list_myproblem);
//		btn_back = (ImageView) findViewById(R.id.btn_back);
//		tv_title=(TextView)findViewById(R.id.tv_title);
//		tv_title.setText("我曾提问过的问题");
//		btn_back.setOnClickListener(this);
		adapter = new MyProblemAdapter(this);
		XutilRequest.showMyProblems(this,MyGetUrl.showMyProblemServlet(),listViewProblem,adapter);
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_back:
//			finish();
//			break;
//
//		default:
//			break;
//		}
//
//	}

}
