package com.eden.adapter;

import java.util.LinkedList;

import com.eden.R;
import com.eden.domain.Expert;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExpertAdapter extends BaseAdapter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Expert> items;
	private LayoutInflater inflater;
	private Activity activity;
	public ExpertAdapter() {
		// TODO Auto-generated constructor stub
	}
	public ExpertAdapter(Activity activity) {
		inflater=LayoutInflater.from(activity);
		this.activity = activity;
	}

	public LinkedList<Expert> getItems() {
		return items;
	}

	public void setItems(LinkedList<Expert> items) {
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
			convertView=inflater.inflate(R.layout.listitem_expert, null);
		}
		CircleImageView expert_userface=(CircleImageView) convertView.findViewById(R.id.expert_userface);
		TextView expert_name=(TextView) convertView.findViewById(R.id.expert_name);
		TextView expert_job=(TextView) convertView.findViewById(R.id.expert_job);
		TextView expert_content=(TextView) convertView.findViewById(R.id.expert_content);
//		TextView expert_replyCount=(TextView) convertView.findViewById(R.id.expert_replyCount);
		TextView expert_suggest=(TextView) convertView.findViewById(R.id.expert_suggest);
		Expert expert=items.get(position);
		MyAsyncTask asyncTask=new MyAsyncTask(expert_userface, activity);
		asyncTask.execute(MyGetUrl.getUrl()+expert.getExpertIcon());
		expert_name.setText(expert.getExpertName());
//		expert_job.setText(expert.getExpertState());
		expert_content.setText(expert.getExpertIntroduce());
		expert_suggest.setText(String.valueOf(expert.getExpertPv())+"人咨询过");
		return convertView;
	}

}
