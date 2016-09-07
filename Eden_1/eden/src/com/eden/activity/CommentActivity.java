package com.eden.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.adapter.ArticalCommntAdapter;
import com.eden.base.BaseActivity;
import com.eden.domain.Comment;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class CommentActivity extends BaseActivity implements OnClickListener,OnItemClickListener,OnRefreshListener<ListView>, OnLastItemVisibleListener{
	private PullToRefreshListView pullView;
	private ImageView left_back;
	private EditText edit;
	private ImageButton send;
	private ListView listView;
	private ArticalCommntAdapter myadapter;
	private TextView commentCount;
	
	private String contentId;
	private int mItemCount=9;
	private SharedPreferences sp;
	private Calendar cal;
	private int year,month,day;
	private int cCount;
	private String c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_artical_comment);
		MyApplication.AddActivity(this);
		initView();
	}

	private void initView() {
		cCount = getIntent().getIntExtra("count", 0);
		contentId = getIntent().getStringExtra("contentId");
		pullView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		left_back = (ImageView) findViewById(R.id.btn_back);
		edit = (EditText) findViewById(R.id.artical_commentEdit);
		send = (ImageButton) findViewById(R.id.artical_commentSend);
		commentCount = (TextView) findViewById(R.id.comment_count);
		 c = String.valueOf(cCount);
		commentCount.setText(c+"人评论");
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		sp = getSharedPreferences("user", MODE_PRIVATE);
		myadapter = new ArticalCommntAdapter(this);
		left_back.setOnClickListener(this);
		edit.setOnClickListener(this);
		send.setOnClickListener(this);
		pullView.setOnRefreshListener(this);
		pullView.setOnLastItemVisibleListener(this);
		listView = pullView.getRefreshableView();		
		VolleyRequest.commentDetail(this, listView, myadapter, MyGetUrl.getCommentServlet(), contentId);
//		listView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.artical_commentSend:addComment();
			
			break;
		case R.id.btn_back:finish();
		break;

		default:
			break;
		}
		
	}

	private void addComment() {
		String Id = this.contentId;
		int editContent = edit.getText().toString().trim().length();
		String time  =	new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
     	String	account = sp.getString("account", "");
     	int type = 0;
     	if(0 == editContent){
     		Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_LONG).show();
     	}else {
     		Comment comment = new Comment(Id, account, edit.getText().toString(), type, time);
         	VolleyRequest.addComment(this, MyGetUrl.getAddCommentServlet(), comment, listView, myadapter, MyGetUrl.getCommentServlet(), contentId);
         	this.cCount++;
         	c = String.valueOf(cCount);
    		commentCount.setText(c+"人评论");
    		edit.setText(null);
    		InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    		// 显示或者隐藏输入法
    		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
     	
	
	}

	@Override
	public void onLastItemVisible() {
		Toast.makeText(this, "最后一条了", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy()
		.setLastUpdatedLabel(label);
		new GetDataTask().execute();
		
	}
	private class GetDataTask extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return "" + (mItemCount++);
		}
		@Override
		protected void onPostExecute(String result) {
			VolleyRequest.commentDetail(CommentActivity.this, listView, myadapter, MyGetUrl.getCommentServlet(), contentId);
			pullView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this,CommentReplyActivity.class);
		String account = myadapter.getComments().get(position).getComment().getUserAccount();
		//Log.i("MMMM", myadapter.getComments().get(position).getComment().getUserAccount()+"222222222222222222222222");
		String Id = contentId;
		intent.putExtra("account", account);
		intent.putExtra("id", Id);
		startActivity(intent);		
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
