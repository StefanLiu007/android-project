package com.eden.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eden.R;
import com.eden.base.BaseContentAdapter;
import com.eden.domain.ContentEden;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;

public class VideoAdapter extends BaseContentAdapter {
	private Context context;
	private List<ContentEden> contents;
	private MyAsyncTask task;
	private LayoutInflater inflater;
	public VideoAdapter(Context context){
		this.context = context;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public void setItems(List<ContentEden> items) {
		this.contents = items;
		
	}
	@Override
	public List<ContentEden> getItems() {
		// TODO Auto-generated method stub
		return contents;
	}

	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return contents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.list_video_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.v_title.setText(contents.get(position).getContent().getContentTitle());
		holder.v_time.setText(contents.get(position).getContent().getContentLastTime());
		
		holder.count.setText(contents.get(position).getContent().getThumbUpNum()+"人评论");
		task = new MyAsyncTask(holder.v_image, context);
		task.execute(MyGetUrl.getUrl()+contents.get(position).getContent().getContentPicture());
		return convertView;
	}
	static class ViewHolder{
		ImageView v_image;
		TextView v_title;
		TextView count,v_time;
		
		public ViewHolder(View v){
			v_image = (ImageView) v.findViewById(R.id.v_imageView1);
			v_title = (TextView) v.findViewById(R.id.v_title);
			count = (TextView) v.findViewById(R.id.v_comment_count11);
			v_time = (TextView) v.findViewById(R.id.v_time);
		
			
		}
	}
	
	

}
