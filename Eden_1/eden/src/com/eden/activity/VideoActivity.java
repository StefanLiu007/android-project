package com.eden.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.eden.R;
import com.eden.adapter.ArticalCommntAdapter;
import com.eden.domain.Collection;
import com.eden.domain.ContentEden;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class VideoActivity extends Activity implements OnClickListener,OnRefreshListener<ListView>, OnLastItemVisibleListener,OnCheckedChangeListener {
	private ListView lv_video;
	private VideoView videoView1;
	private ToggleButton tb_collect;
	private ImageView iv_comment,iv_down;
	private PullToRefreshListView pull_read_refresh_list;
	private int mItemCount=9;
	private ArticalCommntAdapter adapter;
	private SharedPreferences sp;
	private boolean isFirstCheck = false;
    private ContentEden contents;
    private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		MyApplication.AddActivity(this);
		InitView();
		System.out.println(MyGetUrl.getUrl()+contents.getContent().getContentVideo());
		videoView1.setVideoURI(Uri.parse(MyGetUrl.getUrl()+contents.getContent().getContentVideo())); 
        MediaController mediaController = new MediaController(this); 
        videoView1.setMediaController(mediaController); 
        videoView1.start(); 

      
	}

	private void InitView() {
	    contents = (ContentEden) getIntent().getSerializableExtra("VideoActivity");
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
							+ contents.getContent().getContentId());
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
					tb_collect.setChecked(true);
					
				}
			}
		};
	
		videoView1 = (VideoView) findViewById(R.id.videoView1);
		tb_collect = (ToggleButton) findViewById(R.id.tb_collect);
		iv_comment = (ImageView) findViewById(R.id.v_comment11);
		iv_down = (ImageView) findViewById(R.id.v_down11);
		tb_collect.setOnCheckedChangeListener(this);
		iv_comment.setOnClickListener(this);
		iv_down.setOnClickListener(this);
		pull_read_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_read_refresh_list.setOnLastItemVisibleListener(this);
		lv_video = pull_read_refresh_list.getRefreshableView();
		adapter = new ArticalCommntAdapter(this);
		VolleyRequest.commentDetail(this, lv_video, adapter, MyGetUrl.getCommentServlet(),contents.getContent().getContentId());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.v_comment11:
			Intent intent = new Intent(this,CommentReplyActivity.class);
			String account = sp.getString("account", "");
			String Id = contents.getContent().getContentId();
			intent.putExtra("account", account);
			intent.putExtra("id", Id);
			intent.putExtra("content", contents);
			intent.putExtra("a", "0");
			startActivity(intent);
			finish();
			break;
		case R.id.v_down11:
			Intent intent1 = new Intent(this,DownLoadActivity.class);
			intent1.putExtra("content", contents);
			startActivity(intent1);
			break;
		default:
			break;
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
	public class GetDataTask extends AsyncTask<Void, Void, String>
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
			//VolleyRequest.contentRequest(activity, MyApplication.getHttpQueue(),MyGetUrl.getContentServlet(), lv_read,adapter);
			pull_read_refresh_list.onRefreshComplete();
			super.onPostExecute(result);
		}
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

	private void addCollection() {

		String time = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(System
				.currentTimeMillis());
		String account = sp.getString("account", "");
		String contentId = contents.getContent().getContentId();
		Collection c = new Collection(account, contentId, time);
		VolleyRequest.addCollection(this, MyGetUrl.getAddCollection(), c);
	}

	private void deleteColection() {
		String account = sp.getString("account", "");
		String contentId = contents.getContent().getContentId();
		Collection c = new Collection(account, contentId);
		VolleyRequest.deleteCollection(this, c, MyGetUrl.getDeleteCollection());
	}

}
