package com.eden.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eden.R;
import com.eden.activity.ExpertActivity;
import com.eden.adapter.ExpertAdapter;
import com.eden.base.BaseFragment;
import com.eden.domain.Expert;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.vollay.VolleyRequest;
import com.eden.vollay.XutilRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ExpertFragment extends BaseFragment implements OnRefreshListener<ListView>, OnLastItemVisibleListener, OnItemClickListener {
	private PullToRefreshListView pull_refresh_expert ;
	private View view;
	private Activity activity;
	private ListView lv_expert;
	private int mItemCount=9;
	private RequestQueue rq;
	private static ExpertAdapter expertAdapter;
	private TextView head_title;
	private ImageView expecial_image;
	private TextView name,suggest_count,name1,name2,comment1,comment2,expecial_info;
	private View head;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		rq=Volley.newRequestQueue(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view==null) {
			view=inflater.inflate(R.layout.fragment_expert, container, false);
		}
		initView();
		return view;
	}
	public void initView(){
		pull_refresh_expert=(PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		pull_refresh_expert.setOnRefreshListener(this);
		pull_refresh_expert.setOnLastItemVisibleListener(this);
		pull_refresh_expert.setMode(Mode.BOTH);
		pull_refresh_expert.getLoadingLayoutProxy(false, true).setPullLabel(activity.getString(R.string.pull_to_load));  
		lv_expert = pull_refresh_expert.getRefreshableView();
		head = LayoutInflater.from(activity).inflate(R.layout.expert_everyday, lv_expert, false);
		lv_expert.addHeaderView(head);
		lv_expert.setOnItemClickListener(this);
		expertAdapter= new ExpertAdapter(activity);
		VolleyRequest.expertpage=5;
		VolleyRequest.expertRequest(activity, rq,MyGetUrl.getExpertServlet(), lv_expert,expertAdapter,head);

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(activity.getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy()
		.setLastUpdatedLabel(label);

		// 模拟加载任务
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
			if (pull_refresh_expert.isHeaderShown()) {
				VolleyRequest.expertpage=5;
			}else if(pull_refresh_expert.isFooterShown()){
				if (VolleyRequest.experttotalpage-VolleyRequest.expertpage>5) {
					VolleyRequest.expertpage=VolleyRequest.expertpage+5;
				}else {
					VolleyRequest.expertpage=VolleyRequest.experttotalpage;
				}
			}
			VolleyRequest.expertRequest(activity, rq,MyGetUrl.getExpertServlet(), lv_expert,expertAdapter,head);
			pull_refresh_expert.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	@Override
	public void onLastItemVisible() {
		if (VolleyRequest.expertpage==VolleyRequest.experttotalpage) {
			Toast.makeText(activity, "最后一条了", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {



		Intent intent= new Intent(activity,ExpertActivity.class);
		Expert experts=null;
		Log.i("position", ""+position);
		if (position==1) {
			experts=expertAdapter.getItems().get(2);
		}else{
			experts=expertAdapter.getItems().get(position-2);
		}


		intent.putExtra("ExpertActivity", experts);
		activity.startActivity(intent);;
	}

	public static ExpertAdapter getAdapter(){
		return expertAdapter;
	}

}
