package com.eden.activity;

import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.R;
import com.eden.adapter.ProblemCommentAdapter;
import com.eden.domain.Answer;
import com.eden.domain.Collection;
import com.eden.domain.ProblemEden;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.NoScrollListView;
import com.eden.vollay.VolleyRequest;
import com.eden.vollay.XutilRequest;

public class PersonalProblemInfo extends Activity{
	private ImageView image;
	private TextView name,time,content;
	
	protected boolean isReply;
	protected Integer position;
	private TextView collectionText;
	private LinearLayout bottomLinear;	
	private LinearLayout commentLinear;
	private Button commentButton;
	private NoScrollListView commentList;//评论按钮
	private EditText commentEdit;
	private TextView shareText;
	private TextView praiseText;	
	private String comment = "";
	private ProblemCommentAdapter adapter;
	private ProblemEden problem;
	private SharedPreferences sp;
	private TextView commentText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.probleminfoactivity_main);
		MyApplication.AddActivity(this);
		initView();
		initData();
		
	}

	private void initData() {
		Intent intent = getIntent();
		 problem = (ProblemEden) intent
				.getSerializableExtra("problem");
		time.setText(problem.getProblem().getProblemLastTime());
		name.setText(problem.getUse().getUserName());
		content.setText(problem.getProblem().getProblemContent());
		MyAsyncTask asyncTask= new MyAsyncTask(image, this);
		asyncTask.execute(MyGetUrl.getUrl()+problem.getUse().getUserIcon());
		adapter = new ProblemCommentAdapter(this, handler);
		XutilRequest.loadComment(MyGetUrl.getLoadProblem(), this, problem.getProblem().getProblemID(), commentList, adapter);
	}

	private void initView() {
		sp = getSharedPreferences("user", MODE_PRIVATE);
		collectionText = (TextView) findViewById(R.id.collectionText);
		image = (ImageView) findViewById(R.id.senderImg);
		name = (TextView) findViewById(R.id.senderNickname);
		commentList = (NoScrollListView) findViewById(R.id.commentList1);
		time = (TextView) findViewById(R.id.sendTime);
		content = (TextView) findViewById(R.id.sendContent);
		commentButton = (Button) findViewById(R.id.commentButton);
		commentEdit = (EditText) findViewById(R.id.commentEdit);
		bottomLinear = (LinearLayout) findViewById(R.id.bottomLinear);
		commentLinear = (LinearLayout) findViewById(R.id.commentLinear);
		commentText = (TextView) findViewById(R.id.commentText);
		
		ClickListener cl = new ClickListener();
		commentButton.setOnClickListener(cl);
		collectionText.setOnClickListener(cl);
		commentText.setOnClickListener(cl);		
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 10){
				isReply = true;
				position = (Integer)msg.obj;
				commentLinear.setVisibility(View.VISIBLE);
				bottomLinear.setVisibility(View.GONE);
				onFocusChange(true);
			}
		}
	};
	//键盘显示隐藏
	private void onFocusChange(boolean hasFocus){  
		final boolean isFocus = hasFocus;
	(new Handler()).postDelayed(new Runnable() {  
		public void run() {
			InputMethodManager imm = (InputMethodManager)  
					commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);  
			if(isFocus)  {
				//显示输入法
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}else{
				//隐藏输入法
				imm.hideSoftInputFromWindow(commentEdit.getWindowToken(),0);
			}  
		}  
	  }, 100);
	}  
	/**
	 * 回复评论
	 */
	private void replyComment(){
		Answer bean = new Answer();
		bean.setType(1);
		bean.setProblemId(problem.getProblem().getProblemID());
		bean.setAnswerAccout(adapter.getAnswer().get(position).getAnswer().getAnswerAccout());
		bean.setAnswerReplyAccount(sp.getString("account", ""));
		bean.setContent(comment);
		bean.setTime(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		bean.setReplyId(adapter.getAnswer().get(position).getAnswer().getId());
		XutilRequest.addCommentReply(MyGetUrl.getLoadProblem(), this, bean, problem.getProblem().getProblemID(), commentList, adapter);
		adapter.notifyDataSetChanged();
	}
	
	private void publishComment(){
		Answer bean1 = new Answer();
		bean1.setProblemId(problem.getProblem().getProblemID());
		bean1.setContent(comment);
		bean1.setAnswerAccout(sp.getString("account", ""));
		bean1.setType(0);
		bean1.setTime(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		XutilRequest.addCommentReply(MyGetUrl.getLoadProblem(), this, bean1, problem.getProblem().getProblemID(), commentList, adapter);
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 判断对话框中是否输入内容
	 */
	private boolean isEditEmply(){
		comment = commentEdit.getText().toString().trim();
		if(comment.equals("")){
			Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		commentEdit.setText("");
		return true;
	}
	
	private final class ClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.commentButton:	//发表评论按钮
				if(isEditEmply()){		//判断用户是否输入内容
					if(isReply){
						replyComment();
					}
					else{
						publishComment();
					}
					bottomLinear.setVisibility(View.VISIBLE);
					commentLinear.setVisibility(View.GONE);
					onFocusChange(false);
				}
				break;
			case R.id.commentText:		//底部评论按钮
				isReply = false;
				commentLinear.setVisibility(View.VISIBLE);
				bottomLinear.setVisibility(View.GONE);
				onFocusChange(true);
				break;
				//点赞按钮
		
			case R.id.collectionText:	//底部收藏按钮
				addCollection();
				break;
			}
		}

		private void addCollection() {
			Collection c = new Collection();
			c.setContentId(null);
			c.setProblemId(problem.getProblem().getProblemID());
			c.setUserAccount(sp.getString("account", ""));
			c.setTime(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
			VolleyRequest.addCollection(PersonalProblemInfo.this, MyGetUrl.getAddCollection(), c);
			
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//判断控件是否显示
		if(commentLinear.getVisibility() == View.VISIBLE){
			commentLinear.setVisibility(View.GONE);
			bottomLinear.setVisibility(View.VISIBLE);
		}
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
