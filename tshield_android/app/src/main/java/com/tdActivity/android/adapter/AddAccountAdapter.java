package com.tdActivity.android.adapter;

import java.util.List;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.entity.Operation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddAccountAdapter extends BaseAdapter {
	
	private Context context;
	private List<Operation> data;
	private LayoutInflater inflater;
	public AddAccountAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
		
	}
	
	public List<Operation> getData() {
		return data;
	}

	
	public void setData(List<Operation> data) {
		this.data = data;
		for (Operation iterable_element : data) {
		}
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.add_account_item, parent, false);
			holder = new ViewHolder(convertView);
			holder.image.setBackground(context.getResources().getDrawable(data.get(position).icon));
			holder.text.setText(data.get(position).text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	static class  ViewHolder{
		 ImageView image;
		 TextView text;
		public ViewHolder(View view){
			image = (ImageView) view.findViewById(R.id.handig);
			text = (TextView) view .findViewById(R.id.option);
		}
	}

}
