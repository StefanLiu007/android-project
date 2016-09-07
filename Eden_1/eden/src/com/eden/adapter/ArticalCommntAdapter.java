package com.eden.adapter;

import java.util.List;

import com.eden.domain.CommentEden;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticalCommntAdapter extends BaseAdapter {
	private List<CommentEden> comments;
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	
	public ArticalCommntAdapter(Context context){
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	public List<CommentEden> getComments() {
		return comments;
	}

	public void setComments(List<CommentEden> comments) {
		this.comments = comments;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentEden item = comments.get(position);
		if(null == convertView){
			viewHolder = new ViewHolder();
			switch (item.getComment().getCommentReply()){
			case 0:
				convertView = inflater.inflate(R.layout.listitem_comment_artical, null);
				viewHolder.name = (TextView) convertView.findViewById(R.id.artical_name);
				viewHolder.content = (TextView) convertView.findViewById(R.id.artical_content);
				viewHolder.date = (TextView) convertView.findViewById(R.id.artical_time);
				viewHolder.userface = (ImageView) convertView.findViewById(R.id.artical_userface);
				break;
			case 1:
				convertView = inflater.inflate(R.layout.listitem_comment_child, null);
				viewHolder.name = (TextView) convertView.findViewById(R.id.artical_name1);
				viewHolder.content = (TextView) convertView.findViewById(R.id.artical_content1);
				viewHolder.date = (TextView) convertView.findViewById(R.id.artical_time1);
				break;
			default:
				break;
			}
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (null != item) {
			switch (item.getComment().getCommentReply()) {
			case 0:
				viewHolder.name.setText(item.getName());
				viewHolder.content.setText(item.getComment().getCommentContent());
				viewHolder.date.setText(item.getComment().getTime());
				MyAsyncTask asyncTask=new MyAsyncTask(viewHolder.userface, context);
				asyncTask.execute(MyGetUrl.getUrl()+item.getPicture());
				break;
			case 1:
				viewHolder.name.setText(item.getName());
				viewHolder.content.setText(item.getComment().getCommentContent());
				viewHolder.date.setText(item.getComment().getTime());

				break;

			default:
				break;
			}
		}
		return convertView;
	}
	
	@Override
	public int getItemViewType(int position) {

		switch (comments.get(position).getComment().getCommentReply()) {
		case 0:
			return 0;

		
		case 1:
			return 1;

		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	class ViewHolder{
		TextView date;
		TextView name;
		TextView content;
		ImageView userface;
	}

}
