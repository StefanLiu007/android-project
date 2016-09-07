package com.eden.activity;


import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.domain.Comment;
import com.eden.domain.ContentEden;
import com.eden.domain.Expert;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.vollay.XutilRequest;
import com.eden.R;

public class CommentReplyActivity extends Activity{
	private EditText edit;
	private TextView btn;
	private TextView biaoti;
	Expert e ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_selectimg);
		MyApplication.AddActivity(this);
		edit = (EditText) findViewById(R.id.comment_selectimg_edit);
		btn = (TextView) findViewById(R.id.comment_selectimg_send);
		biaoti = (TextView)findViewById(R.id.biaoti);
		final String a = getIntent().getStringExtra("a");
		if("2".equals(a)){
			e = (Expert) getIntent().getSerializableExtra("expert");
 			biaoti.setText("评论"+e.getExpertName());
		}
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentEden contents = (ContentEden) getIntent().getSerializableExtra("content");
				
				String account = getIntent().getStringExtra("account");
				String id = getIntent().getStringExtra("id");
				String time  =	new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
				int editContent = edit.getText().toString().trim().length();
				if(0 == editContent){
		     		Toast.makeText(CommentReplyActivity.this, "评论内容不能为空", Toast.LENGTH_LONG).show();
		     	}else {
		     		if("0".equals(a)){
		     			Comment comment = new Comment(id, account, edit.getText().toString(), 0, time);
			         	VolleyRequest.replyComment(CommentReplyActivity.this, MyGetUrl.getAddCommentServlet(), comment);
			         	Intent intent = new Intent(CommentReplyActivity.this,VideoActivity.class);
			         	intent.putExtra("VideoActivity", contents);
			         	startActivity(intent);	
			         	finish();
		     		}
		     		if("1".equals(a)){
		     			Comment comment = new Comment(id, account, edit.getText().toString(),1, time);
			         	VolleyRequest.replyComment(CommentReplyActivity.this, MyGetUrl.getAddCommentServlet(), comment);
			         	Intent intent = new Intent(CommentReplyActivity.this,CommentActivity.class);
			         	startActivity(intent);
			         	finish();
		     		}
		     		
		     		if("2".equals(a)){
		     			
		     			
		     			String i = e.getExpertAccount();
		     		    
		     			Comment expert = new Comment(i, account, edit.getText().toString(),0, time);
			         	VolleyRequest.replyComment(CommentReplyActivity.this, MyGetUrl.getAddCommentServlet(), expert);
			         	Intent intent = new Intent(CommentReplyActivity.this,ExpertActivity.class);
			         	intent.putExtra("ExpertActivity", e);
			         	startActivity(intent);
			         	finish();
		     		}

				}
			}
		});
	}

}
