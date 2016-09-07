package com.eden.collection;

import java.util.ArrayList;
import java.util.List;

import com.eden.R;
import com.eden.collection.SlideView.OnSlideListener;
import com.eden.domain.Collection;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.vollay.XutilRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CollecttionContentAdapter extends BaseAdapter implements
OnSlideListener {
	private static final String TAG = "SlideAdapter";

	private Context mContext;
	private LayoutInflater mInflater;

	private List<CollectionContentSilde> mMessageItems ;
	private SlideView mLastSlideViewWithStatusOn;

	CollecttionContentAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	public void setmMessageItems(List<CollectionContentSilde> mMessageItems) {
		this.mMessageItems = mMessageItems;
	}


	public List<CollectionContentSilde> getmMessageItems() {
		return mMessageItems;
	}

	@Override
	public int getCount() {
		//		if (mMessageItems == null) {
		//			mMessageItems = new ArrayList<CollectionContentSilde>();
		//		}
		return mMessageItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		SlideView slideView = (SlideView) convertView;
		if (slideView == null) {
			View itemView = mInflater.inflate(R.layout.collecttion_content_item,
					null);

			slideView = new SlideView(mContext);
			slideView.setContentView(itemView);

			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(this);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		final CollectionContentSilde item = mMessageItems.get(position);
		item.setSlideView(slideView);
		item.getSlideView().shrink();
		if(item.getCollectionContent().getContent().getContentVideo()!=null){
            holder.play.setVisibility(View.VISIBLE);
		}else{
			holder.play.setVisibility(View.INVISIBLE);
		}
		holder.title.setText(item.getCollectionContent().getContent().getContentTitle());
		holder.msg.setText(item.getCollectionContent().getContent().getContentText());
		holder.time.setText(item.getCollectionContent().getTime());
		MyAsyncTask asyncTask= new MyAsyncTask(holder.icon,mContext);
		asyncTask.execute(MyGetUrl.getUrl()+item.getCollectionContent().getContent().getContentPicture());

		holder.deleteHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences sp=mContext.getSharedPreferences("user", mContext.MODE_PRIVATE);
				Collection collection= new Collection(sp.getString("account", null), item.getCollectionContent().getContent().getContentId());
				VolleyRequest.deleteCollection(mContext, collection, MyGetUrl.getDeleteCollection());
				mMessageItems.remove(position);
				notifyDataSetChanged();
			}
		});

		return slideView;
	}

	private static class ViewHolder {
		public ImageView icon;
		public TextView title;
		public TextView msg;
		public TextView time;
		public ViewGroup deleteHolder;
		public ImageView play;
		ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
			msg = (TextView) view.findViewById(R.id.msg);
			time = (TextView) view.findViewById(R.id.time);
			play=(ImageView)view.findViewById(R.id.play);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}

	}

	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}

		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}
}
