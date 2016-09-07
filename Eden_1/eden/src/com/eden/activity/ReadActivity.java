package com.eden.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.eden.domain.Collection;
import com.eden.domain.Content;
import com.eden.domain.ContentEden;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

public class ReadActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
	private Content item;
	private String name;
	private TextView content_title;
	private TextView content_content;
	private ImageView content_picture;
	private TextView tv_title;
	private ImageView share;
	private ProgressBar blogContentPro;
	private ImageView btn_back;
	private ImageView iv_comment;
	private ContentEden content;
	private ToggleButton collection;
	
	private Handler handler;
	

	private SharedPreferences sp;
	private boolean isFirstCheck = false;
	

	

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(getApplicationContext());
		MyApplication.AddActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_article_detail);
		initView();		
		
		

	}
	public void initView(){
		Intent intent=getIntent();
		share = (ImageView) findViewById(R.id.iv_share);
		content = (ContentEden) intent.getSerializableExtra("ReadActivity");
		item = content.getContent();

		sp = getSharedPreferences("user", MODE_PRIVATE);

		Runnable update = new Runnable() {

			@Override
			public void run() {
				Gson g = new Gson();
				URL url = null;
				HttpURLConnection con = null;
				InputStream in = null;
				try {
					url = new URL(MyGetUrl.getIsCollection() + "?userAccount="
							+ sp.getString("account", "") +"&Id="
							+ item.getContentId());
					con = (HttpURLConnection) url.openConnection();
					in = con.getInputStream();
					byte[] b = new byte[1024];
					int a = in.read(b);
					String s = new String(b);
					Message mess = Message.obtain();
					mess.obj = s;
					handler.sendMessage(mess);
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					}
				}

			}
		};

		Thread thread = new Thread(update);
		thread.start();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				String b = (String) msg.obj;
				
				if(b.contains("true")){
					isFirstCheck = true;
					collection.setChecked(true);
					
				}
			}
		};
		tv_title=(TextView)findViewById(R.id.tv_title);
		content_title=(TextView)findViewById(R.id.content_title);
		content_picture = (ImageView) findViewById(R.id.content_picture);
		content_content=(TextView)findViewById(R.id.content_content);
		blogContentPro=(ProgressBar)findViewById(R.id.blogContentPro);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		iv_comment = (ImageView)findViewById(R.id.iv_comment);
		collection = (ToggleButton) findViewById(R.id.tb_collect);
		
		collection.setOnCheckedChangeListener(this);
		share.setOnClickListener(this);

		
		
		btn_back.setOnClickListener(this);
		iv_comment.setOnClickListener(this);
		
		name = content.getName();
		BitmapUtils bitmapUtils= new BitmapUtils(this);
		tv_title.setText("详情");
		content_title.setText(item.getContentTitle());
		if (item.getContentPicture()!=null) {
			content_picture.setVisibility(ImageView.VISIBLE);
			MyAsyncTask asyncTask= new MyAsyncTask(content_picture, this);
			asyncTask.execute(MyGetUrl.getUrl()+item.getContentPicture());
//			ImageLoderCache.setImage(content_picture, MyGetUrl.getUrl()+item.getContentPicture());
//			bitmapUtils.display(content_picture, MyGetUrl.getUrl()+item.getContentPicture());
		}
		content_content.setText(item.getContentText());
		blogContentPro.setVisibility(ProgressBar.INVISIBLE);
			
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();			
			break;
		case R.id.iv_comment:
			Intent intent = new Intent(this,CommentActivity.class);
			intent.putExtra("contentId", item.getContentId());
			intent.putExtra("count", item.getThumbUpNum());
			startActivity(intent);
			break;
		case R.id.iv_share:share();
			break;
		default:
			break;
		}
		
	}
	
	private void share() {
		OnekeyShare oShare = new OnekeyShare();
		oShare.setTitle("马致远是傻逼");
		oShare.setText(item.getContentText());
//		oShare.setImageUrl(imageUrl)
		oShare.show(ReadActivity.this);
		
	}
	private void addCollection() {
    
        		String time =	new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        		String account =sp.getString("account", "");
        		String contentId = item.getContentId();
        		Collection c = new Collection(account, contentId, time);
        		VolleyRequest.addCollection(this, MyGetUrl.getAddCollection(), c);
        	}
   private void deleteColection(){
	    String account =sp.getString("account", "");
		String contentId = item.getContentId();
		Collection c = new Collection(account, contentId);
		VolleyRequest.deleteCollection(this, c, MyGetUrl.getDeleteCollection());
   }
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isFirstCheck) {
			isFirstCheck = false;
			return;
		}

		if (isChecked) {
			addCollection();
			
		} else {
			deleteColection();
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


