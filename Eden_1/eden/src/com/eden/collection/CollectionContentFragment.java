package com.eden.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.eden.R;
import com.eden.activity.ReadActivity;
import com.eden.activity.VideoActivity;
import com.eden.base.BaseFragment;
import com.eden.domain.ContentEden;
import com.eden.util.GsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class CollectionContentFragment extends BaseFragment implements OnItemClickListener,
OnClickListener {

	private static final String TAG = "MainActivity";
	private ListViewCollectionContent mListView;
	private CollecttionContentAdapter mAdapter;
	private View view;
	private Activity activity;

	@Override
	public void onAttach(Activity activity) {
		this.activity=activity;
		super.onAttach(activity);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view==null) {
			view=inflater.inflate(R.layout.activity_collection_content, container, false);
		}
		initView();
		return view;
	}
	private void initView() {
		mListView = (ListViewCollectionContent) view.findViewById(R.id.list);
		mListView.setOnItemClickListener(this);
		SharedPreferences sp=activity.getSharedPreferences("mycollection", activity.MODE_PRIVATE);
		String json=sp.getString("collection", null);
		CollectionBean collectionBean=(CollectionBean) GsonUtil.fromJson(json, CollectionBean.class);
		List<CollectionContentSilde> collectionContentSildes=new ArrayList<CollectionContentSilde>();
		List<CollectionContent>  collectionContents=collectionBean.getContents();
		Collections.reverse(collectionContents);
		for (CollectionContent collectionContent : collectionContents) {
			collectionContentSildes.add(new CollectionContentSilde(collectionContent));
		}
		//Type classOfT = new TypeToken<List<CollectionContentSilde>>(){}.getType();
		//List<CollectionContentSilde> collectionContentSildes=(List<CollectionContentSilde>) GsonUtil.fromJson(json, classOfT);
		mAdapter = new CollecttionContentAdapter(activity);
		mAdapter.setmMessageItems(collectionContentSildes);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=null;
		ContentEden content=null;
		if (mAdapter.getmMessageItems().get(position).getCollectionContent().getContent().getContentVideo()!=null) {
			intent = new Intent(activity,VideoActivity.class);
			content=new ContentEden(mAdapter.getmMessageItems().get(position).getCollectionContent().getContent(),"");
			intent.putExtra("VideoActivity", content);
		}else {
			intent = new Intent(activity,ReadActivity.class);
			content=new ContentEden(mAdapter.getmMessageItems().get(position).getCollectionContent().getContent(),"");
			intent.putExtra("ReadActivity", content);
		}
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
