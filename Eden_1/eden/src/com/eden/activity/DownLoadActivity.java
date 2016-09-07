package com.eden.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.R;
import com.eden.base.BaseActivity;
import com.eden.domain.ContentEden;
import com.eden.domain.DownloadInfo;
import com.eden.domain.FileInfo;
import com.eden.download.db.DownloadDao;
import com.eden.download.db.DownloadDaoImpl;
import com.eden.service.DownloadService;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;


public class DownLoadActivity extends BaseActivity {
	private TextView title;
	private Button down,stop;
	private ProgressBar pro;
	private ContentEden content;
	public static DownLoadActivity download;
	private DownloadDao mDao = null;
	FileInfo fileInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_down_item);
		MyApplication.AddActivity(this);
		init();
		IntentFilter filter = new IntentFilter();
		filter.addAction(DownloadService.ACTION_UPDATE);
		registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
	private void init() {
		download = this;
		content = (ContentEden) getIntent().getSerializableExtra("content");
		down = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		title = (TextView) findViewById(R.id.filename);
		pro = (ProgressBar) findViewById(R.id.down_progress);
		pro.setMax(100);
		title.setText(content.getContent().getContentText());
		fileInfo = new FileInfo(content.getContent().getThumbUpNum(), MyGetUrl.getUrl()+content.getContent().getContentVideo(), content.getContent().getContentText(), 0, 0);
		mDao = new DownloadDaoImpl(this);
		down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DownLoadActivity.this,DownloadService.class);
				intent.setAction(DownloadService.ACTION_START);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
				
			}
		});
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DownLoadActivity.this,
						DownloadService.class);
				intent.setAction(DownloadService.ACTION_STOP);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);

			}
		});
		
	} 
	
	public   Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
				Toast.makeText(DownLoadActivity.this, "下载完成", 1000).show();
				DownloadInfo info = new DownloadInfo(fileInfo.getId(), content.getContent().getContentPicture(),fileInfo.getFileName());
				mDao.insertDownLoad(info);
				finish();
			
		};
	};
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		
		@Override
		public void onReceive(Context arg0, Intent intent) {
			if(DownloadService.ACTION_UPDATE.equals(intent.getAction())){
				int finished = intent.getIntExtra("finished", 0);
				pro.setProgress(finished);
				
			}
			
		}
	};
}
