package com.eden.myproblem;

import java.util.List;

import com.eden.R;
import com.eden.collection.SlideView;
import com.eden.collection.SlideView.OnSlideListener;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MyProblemAdapter extends BaseAdapter implements
OnSlideListener {
	private static final String TAG = "SlideAdapter";

	private Context mContext;
	private LayoutInflater mInflater;

	private List<ProblemSide> mMessageItems ;
	private SlideView mLastSlideViewWithStatusOn;

	MyProblemAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	public void setmMessageItems(List<ProblemSide> mMessageItems) {
		this.mMessageItems = mMessageItems;
	}


	public List<ProblemSide> getmMessageItems() {
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
			View itemView = mInflater.inflate(R.layout.myproblem_item,
					null);

			slideView = new SlideView(mContext);
			slideView.setContentView(itemView);

			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(this);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		final ProblemSide item = mMessageItems.get(position);
		item.setSlideView(slideView);
		item.getSlideView().shrink();
				holder.user_name.setText(item.getProblem().getUse().getUserNickname());
				holder.myproblem.setText(item.getProblem().getProblem().getProblemContent());
				holder.time.setText("时间:"+item.getProblem().getProblem().getProblemLastTime());
				holder.id.setText(item.getProblem().getProblem().getProblemAnswerNum()+"回答过");
				MyAsyncTask asyncTask= new MyAsyncTask(holder.icon,mContext);
				asyncTask.execute(MyGetUrl.getUrl()+item.getProblem().getUse().getUserIcon());

				holder.deleteHolder.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						SharedPreferences sp=mContext.getSharedPreferences("user", mContext.MODE_PRIVATE);
//						Collection collection= new Collection(sp.getString("account", null), item.getCollectionContent().getContent().getContentId());
//						VolleyRequest.deleteCollection(mContext, collection, MyGetUrl.getDeleteCollection());
						mMessageItems.remove(position);
						notifyDataSetChanged();
					}
				});

		return slideView;
	}

	private static class ViewHolder {
		public CircleImageView icon;
		public TextView user_name;
		public TextView myproblem;
		public TextView time;
		public ViewGroup deleteHolder;
		public TextView id;
		ViewHolder(View view) {
			icon = (CircleImageView) view.findViewById(R.id.icon);
			user_name = (TextView) view.findViewById(R.id.user_name);
			myproblem = (TextView) view.findViewById(R.id.myproblem);
			time = (TextView) view.findViewById(R.id.time);
			id=(TextView)view.findViewById(R.id.id);
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
