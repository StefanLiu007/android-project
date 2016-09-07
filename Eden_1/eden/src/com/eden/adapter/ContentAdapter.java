package com.eden.adapter;


import java.util.List;

import com.eden.R;
import com.eden.base.BaseContentAdapter;
import com.eden.domain.Content;
import com.eden.domain.ContentEden;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentAdapter extends BaseContentAdapter {
	private List<ContentEden> items;
	private LayoutInflater inflater;
	private Activity activity;
	String cachepath ;  
	public ContentAdapter(Activity activity) {
		super();
		this.activity=activity;
		inflater=LayoutInflater.from(activity);
		cachepath=activity.getCacheDir().getPath();
	}

	public ContentAdapter(List<ContentEden> items,Activity activity) {
		this.items=items;
		inflater=LayoutInflater.from(activity);
	}
	@Override
	public List<ContentEden> getItems() {
		return items;
	}
	
	@Override
	public void setItems(List<ContentEden> items) {
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.main_item, null);
		}
		TextView title=(TextView) convertView.findViewById(R.id.title);
		TextView tv_content=(TextView) convertView.findViewById(R.id.content);
		TextView date=(TextView) convertView.findViewById(R.id.date);
		TextView id=(TextView) convertView.findViewById(R.id.id);
		ImageView imv_blogger =(ImageView) convertView.findViewById(R.id.imv_blogger);
		ImageView  blogImg=(ImageView) convertView.findViewById(R.id.blogImg);
		BitmapUtils bitmapUtils = new BitmapUtils(activity,cachepath);
		ContentEden  item=items.get(position);
		Content content=item.getContent();
		bitmapUtils.display(blogImg, MyGetUrl.getUrl()+content.getContentPicture());
		title.setText(content.getContentTitle());
		tv_content.setText(content.getContentText());
		date.setText(content.getContentLastTime());
		id.setText(String.valueOf(content.getThumbUpNum()+"人评论"));
		MyAsyncTask picture= new MyAsyncTask(imv_blogger,activity);
		picture.execute(MyGetUrl.getUrl()+content.getContentPicture());
		return convertView;
	}

	
}
