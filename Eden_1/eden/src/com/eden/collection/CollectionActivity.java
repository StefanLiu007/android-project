package com.eden.collection;

import com.eden.R;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionActivity extends Activity implements OnClickListener {
	private TextView tv_collection_content;
	private TextView tv_collection_problem;
	private CollectionContentFragment collectionContent;
	private CollectionProblemFragment collectionProblem;
	private ImageView btn_back;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_page);
		initView();
		setSelect(0);
	}

	private void initView() {
		tv_collection_content = (TextView) findViewById(R.id.tv_collection_content);
		tv_collection_problem = (TextView) findViewById(R.id.tv_collection_problem);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的收藏");
		tv_collection_content.setOnClickListener(this);
		tv_collection_problem.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId()) {
		case R.id.tv_collection_content:
			setSelect(0);
			break;
		case R.id.tv_collection_problem:
			setSelect(1);
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}

	}

	private void setSelect(int i)
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (collectionContent == null)
			{
				collectionContent = new CollectionContentFragment();
				transaction.add(R.id.fl_collection, collectionContent);
			} else
			{
				transaction.show(collectionContent);
			}
			tv_collection_content.setBackgroundResource(R.drawable.ful1);
			break;
		case 1:
			if (collectionProblem == null)	
			{
				collectionProblem = new CollectionProblemFragment();
				transaction.add(R.id.fl_collection, collectionProblem);
			} else
			{
				transaction.show(collectionProblem);

			}
			tv_collection_problem.setBackgroundResource(R.drawable.fur1);
			break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (collectionContent != null)
		{
			transaction.hide(collectionContent);
		}
		if (collectionProblem != null)
		{
			transaction.hide(collectionProblem);
		}
	}

	private void resetImgs()
	{
		tv_collection_content.setBackgroundResource(R.drawable.ful);
		tv_collection_problem.setBackgroundResource(R.drawable.fur);
	}
}
