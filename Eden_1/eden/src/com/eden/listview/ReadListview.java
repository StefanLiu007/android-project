package com.eden.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eden.activity.ReadActivity;
import com.eden.activity.VideoActivity;
import com.eden.adapter.ContentAdapter;
import com.eden.adapter.VideoAdapter;
import com.eden.base.BaseContentAdapter;
import com.eden.domain.ContentEden;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ReadListview implements OnRefreshListener<ListView>, OnLastItemVisibleListener, OnItemClickListener {
	private View view;
	private Activity activity;
	private PullToRefreshListView pull_read_refresh_list;
	private ListView lv_read;
	private BaseContentAdapter adapter;
	private int mItemCount=9;
	private int a;
	public ReadListview(View view,Activity activity,int a) {
		this.view=view;
		this.activity=activity;
		this.a = a;
	}

	public  void  initView(){
		pull_read_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		pull_read_refresh_list.setOnRefreshListener(this);
		pull_read_refresh_list.setMode(Mode.BOTH);
		pull_read_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel(activity.getString(R.string.pull_to_load));  
		pull_read_refresh_list.setOnLastItemVisibleListener(this);
		lv_read = pull_read_refresh_list.getRefreshableView();
		lv_read.setOnItemClickListener(this);
		if(a == 0){
			adapter = new ContentAdapter(activity);
			new GetHead().getPage(activity, lv_read);
			VolleyRequest.contentpage=5;
			VolleyRequest.contentRequest(activity, MyApplication.getHttpQueue(),MyGetUrl.getContentServlet(), lv_read,adapter,0);
		}else{
			VolleyRequest.contentpage=5;
			adapter = new VideoAdapter(activity);
			VolleyRequest.contentRequest(activity, MyApplication.getHttpQueue(),MyGetUrl.getContentServlet(), lv_read,adapter,1);
		}



	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(activity.getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy()
		.setLastUpdatedLabel(label);
		new GetDataTask().execute();
	}
	private class GetDataTask extends AsyncTask<Void, Void, String>
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
			if(a == 0){
				if (pull_read_refresh_list.isHeaderShown()) {
					VolleyRequest.contentpage=5;
				}else if(pull_read_refresh_list.isFooterShown()){
					if (VolleyRequest.totalpage-VolleyRequest.contentpage>5) {
						VolleyRequest.contentpage=VolleyRequest.contentpage+5;
					}else {
						VolleyRequest.contentpage=VolleyRequest.totalpage;
					}
				}
				VolleyRequest.contentRequest(activity, MyApplication.getHttpQueue(),MyGetUrl.getContentServlet(), lv_read,adapter,0);
			}else{
				if (pull_read_refresh_list.isHeaderShown()) {
					VolleyRequest.contentpage=5;
				}else if(pull_read_refresh_list.isFooterShown()){
					if (VolleyRequest.totalpage-VolleyRequest.contentpage>5) {
						VolleyRequest.contentpage=VolleyRequest.contentpage+5;
					}else {
						VolleyRequest.contentpage=VolleyRequest.totalpage;
					}
				}
				VolleyRequest.contentRequest(activity, MyApplication.getHttpQueue(),MyGetUrl.getContentServlet(), lv_read,adapter,1);
			}
			pull_read_refresh_list.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	@Override
	public void onLastItemVisible() {
		if (VolleyRequest.contentpage==VolleyRequest.totalpage) {
			Toast.makeText(activity, "最后一条了", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(a == 0){
			Intent intent = new Intent(activity,ReadActivity.class);
			ContentEden content=adapter.getItems().get(position-2);
			intent.putExtra("ReadActivity", content);
			activity.startActivity(intent);
		}if(a ==1){
			Intent intent = new Intent(activity,VideoActivity.class);
			ContentEden content=adapter.getItems().get(position-1);
			intent.putExtra("VideoActivity", content);
			activity.startActivity(intent);
		}


	}

}
