package com.eden.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.R;
import com.eden.domain.DownloadInfo;
import com.eden.download.db.DBhelper;
import com.eden.download.db.DownloadDao;
import com.eden.download.db.DownloadDaoImpl;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;

public class MyDownloadActivity extends Activity {
	private DownloadDao mDao = null;
	private TextView title;
	private ImageView back;
	private ListView list;
	private DownAdapter adapter;
	
	private List<DownloadInfo> downInfo;
	public static final String DOWNLOAD_PATH=
			Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/downloads/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_download);
		MyApplication.AddActivity(this);
		mDao = new DownloadDaoImpl(this);
		init();
	}
	private void init() {
		
		downInfo = mDao.getDownLoad();
		list = (ListView) findViewById(R.id.down_list);
		back = (ImageView) findViewById(R.id.btn_back);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("我的离线下载");
		adapter = new DownAdapter();
		list.setAdapter(adapter);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	class DownAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return downInfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return downInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				convertView = LayoutInflater.from(MyDownloadActivity.this).inflate(R.layout.my_download_item, null);
				holder = new ViewHolder(convertView);
				holder.delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mDao.deleteDownLoad(downInfo.get(position).getName());
						
						
						File dir = new File(DOWNLOAD_PATH);
						if(!dir.exists()){
							dir.mkdir();
						}
						File file = new File(dir,downInfo.get(position).getName());
						file.delete();
						downInfo.remove(downInfo.get(position));
						Toast.makeText(MyDownloadActivity.this, "删除成功", 1000).show();
						notifyDataSetChanged();
					}
				});
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(downInfo.get(position).getName());
			MyAsyncTask task = new MyAsyncTask(holder.image, MyDownloadActivity.this);
			task.execute(MyGetUrl.getUrl()+downInfo.get(position).getPicture());
			holder.image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					File dir = new File(DOWNLOAD_PATH);
					if(!dir.exists()){
						dir.mkdir();
					}
					File file = new File(dir,downInfo.get(position).getName());
			    	Intent intent = getVideoFileIntent(file);
			    	startActivity(intent);
					
				}
			});
			return convertView;
		}
		
		public Intent getVideoFileIntent(File videoFile)
		{
		    Intent intent = new Intent(Intent.ACTION_VIEW);
		    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    intent.putExtra("oneshot", 0);
		    intent.putExtra("configchange", 0);
		    Uri uri = Uri.fromFile(videoFile);
		    intent.setDataAndType(uri, "video/*");
		    return intent;
		}
	}
	static class  ViewHolder{
		Button delete;
		ImageView image;
		TextView title;
		public ViewHolder(View v){
			delete = (Button) v.findViewById(R.id.down_delete);
			image = (ImageView) v.findViewById(R.id.down_image);
			title = (TextView) v.findViewById(R.id.down_title);
		}
	}

}
