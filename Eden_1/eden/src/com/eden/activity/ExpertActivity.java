package com.eden.activity;

import java.text.SimpleDateFormat;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.R;
import com.eden.adapter.ExpertCommntAdapter;
import com.eden.domain.AskedExpert;
import com.eden.domain.Comment;
import com.eden.domain.Expert;
import com.eden.fragment.ExpertFragment;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;
import com.eden.vollay.VolleyRequest;
import com.eden.vollay.XutilRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ExpertActivity extends Activity implements OnLastItemVisibleListener,OnClickListener,OnRefreshListener<ListView> {
	String key = "0vnjpoadnwo6z";
	String secret = "c6LZe0BUGxvXi";
	String ss="";
	private CircleImageView civ_ei;
	private TextView tv_en;
	private TextView tv_es;
	private TextView tv_eads;
	private ImageView iv_chat;
	private ImageView iv_phone,i_comment;
	private ImageView iv_email;
	private TextView tv_ei;
	public static ListView lv_words;
	private Expert expert;
	public static ExpertCommntAdapter adapter;
	private ImageView btn_back;
	private TextView tv_title;
	private SharedPreferences sp;
	private PullToRefreshListView pull_read_refresh_list;
	private int mItemCount=9;
	private LinearLayout expert_commentEditL;
	private EditText expert_commentEdit;
	private ImageButton expert_commentSend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.expert_main);
		MyApplication.AddActivity(this);
		Intent intent = getIntent();
		expert = (Expert) intent.getSerializableExtra("ExpertActivity");
		sp = getSharedPreferences("eden", MODE_PRIVATE);
		initView();
	}
	public void initView(){
		pull_read_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list1);
		pull_read_refresh_list.setOnLastItemVisibleListener(this);
		lv_words = pull_read_refresh_list.getRefreshableView();
		View head = LayoutInflater.from(this).inflate(R.layout.activity_expert, lv_words, false);
		civ_ei=(CircleImageView)head.findViewById(R.id.civ_ei);
		tv_en=(TextView)head.findViewById(R.id.tv_en);
		tv_es=(TextView)head.findViewById(R.id.tv_es);
		tv_eads=(TextView)head.findViewById(R.id.tv_eads);
		iv_chat=(ImageView)head.findViewById(R.id.iv_chat);
		iv_phone=(ImageView)head.findViewById(R.id.iv_phone);
		iv_email=(ImageView)head.findViewById(R.id.iv_email);
		tv_ei=(TextView)head.findViewById(R.id.tv_ei);
		btn_back=(ImageView)findViewById(R.id.btn_back);
		tv_title=(TextView)findViewById(R.id.tv_title);
		expert_commentSend = (ImageButton) findViewById(R.id.expert_commentSend);
		expert_commentSend.setOnClickListener(this);
		expert_commentEdit = (EditText) findViewById(R.id.expert_commentEdit);
		expert_commentEditL = (LinearLayout) findViewById(R.id.expert_commentEditL);
		i_comment = (ImageView) head.findViewById(R.id.i_comment);
		tv_title.setText(expert.getExpertName()+"的咨询室");
		iv_chat.setOnClickListener(this);
		iv_phone.setOnClickListener(this);
		iv_email.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		i_comment.setOnClickListener(this);
		tv_en.setText("姓名:"+expert.getExpertName());
		tv_es.setText("性别:"+expert.getExpertSex());
		tv_eads.setText("地址:"+expert.getExpertAddress());
		tv_ei.setText(expert.getExpertIntroduce());
		
		MyAsyncTask asyncTask =new MyAsyncTask(civ_ei, this);
		asyncTask.execute(MyGetUrl.getUrl()+expert.getExpertIcon());
		adapter = new ExpertCommntAdapter(this);
		lv_words.addHeaderView(head);
		XutilRequest.xutilRequest(MyGetUrl.getexpertcomment(),expert.getExpertAccount(),lv_words,adapter,this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_chat:
			if (RongIM.getInstance() != null)
				
			RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

				@Override
				public UserInfo getUserInfo(String userId) {
					if (userId.equals(expert.getExpertAccount())) {
						String id=expert.getExpertAccount();
						String name=expert.getExpertName();
						Uri portraitUri=Uri.parse(MyGetUrl.getUrl()+expert.getExpertIcon());
						RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(id, name, portraitUri));
			    		RongIM.getInstance().setMessageAttachedUserInfo(true);
						return new io.rong.imlib.model.UserInfo(id, name, portraitUri);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
					}
					return null;
				}
			}, true);
			expert.setExpertPv(expert.getExpertPv()+1);
			XutilRequest.UpdateExpertRequest(MyGetUrl.updageExpertServlet(), expert, this, ExpertFragment.getAdapter());
			RongIM.getInstance().startPrivateChat(this, expert.getExpertAccount(), expert.getExpertName());
			AskedExpert asked = new AskedExpert();
			asked.setUserAccount(getSharedPreferences("user", MODE_PRIVATE).getString("account", ""));
			asked.setExpertAccount(expert.getExpertAccount());
			XutilRequest.insertAskedExpert(MyGetUrl.insertAskedExpertServlet(),asked,this);
			break;
			
		case R.id.iv_phone:
			expert.setExpertPv(expert.getExpertPv()+1);
			XutilRequest.UpdateExpertRequest(MyGetUrl.updageExpertServlet(), expert, this, ExpertFragment.getAdapter());
			Intent call = new Intent();
			call.setAction(call.ACTION_CALL);
			call.setData(Uri.parse("tel:"+expert.getExpertPhone()));		
			startActivity(call);
			AskedExpert asked1 = new AskedExpert();
			asked1.setUserAccount(getSharedPreferences("user", MODE_PRIVATE).getString("account", ""));
			asked1.setExpertAccount(expert.getExpertAccount());
			XutilRequest.insertAskedExpert(MyGetUrl.insertAskedExpertServlet(),asked1,this);
			break;
		case R.id.iv_email:
			expert.setExpertPv(expert.getExpertPv()+1);
			XutilRequest.UpdateExpertRequest(MyGetUrl.updageExpertServlet(), expert, this, ExpertFragment.getAdapter());
			Intent data=new Intent(Intent.ACTION_SENDTO);  
			data.setData(Uri.parse("mailto:"+expert.getExpertEmail()));
			data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");  
			data.putExtra(Intent.EXTRA_TEXT, "这是内容");  
			startActivity(data); 
			AskedExpert asked2 = new AskedExpert();
			asked2.setUserAccount(getSharedPreferences("user", MODE_PRIVATE).getString("account", ""));
			asked2.setExpertAccount(expert.getExpertAccount());
			XutilRequest.insertAskedExpert(MyGetUrl.insertAskedExpertServlet(),asked2,this);
			break;
		case R.id.btn_back:
			//			Intent intent = new Intent();
			//			intent.putExtra("expert", expert);
			//			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.i_comment:
//			addComment();	
			Intent intent = new Intent(this,CommentReplyActivity.class);
			intent.putExtra("expert", expert);
			intent.putExtra("account", getSharedPreferences("user", MODE_PRIVATE).getString("account", ""));
			intent.putExtra("a", "2");
			startActivity(intent);
			finish();
			break;
		case R.id.expert_commentSend:
//			String account =getSharedPreferences("user", MODE_PRIVATE).getString("account", "");
//			int editContent = expert_commentEdit.getText().toString().trim().length();
//			if(0 == editContent){
//	     		Toast.makeText(ExpertActivity.this, "评论内容不能为空", Toast.LENGTH_LONG).show();
//	     	}else {
//	     		String i  = expert.getExpertAccount();
//	    		String time  =	new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
//	    		Comment expert = new Comment(i, account, expert_commentEdit.getText().toString(),0, time);
//	         	VolleyRequest.replyComment(ExpertActivity.this, MyGetUrl.getAddCommentServlet(), expert);
//			}
//			InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//    		// 显示或者隐藏输入法
//    		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//			break;
		default:
			break;
		}

	}

//	private void addComment() {
//		expert_commentEditL.setVisibility(View.VISIBLE);
//		InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//		// 显示或者隐藏输入法
//		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
//		
//		
//		
//	}
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
			XutilRequest.xutilRequest(MyGetUrl.getexpertcomment(),expert.getExpertAccount(),lv_words,adapter,ExpertActivity.this);
			pull_read_refresh_list.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	@Override
	public void onLastItemVisible() {
		// TODO Auto-generated method stub
		
	}

}
