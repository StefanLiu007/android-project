package com.eden.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eden.R;
import com.eden.adapter.MyRecycleAdapter;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.PullRefreshLayout;
import com.eden.vollay.XutilRequest;


public class problem_wall_main extends Activity implements OnClickListener{
    public static RecyclerView view;
    private GridLayoutManager manager;
    public static MyRecycleAdapter adapter;
    private ImageView image;
    private LinearLayout dropDownView;
    private boolean showItems = false;
    private ImageView back;
    private ImageView push;
    private View bgView;
    SharedPreferences sp;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friend_problem_circle);
		MyApplication.AddActivity(this);
		init();
		loadData();
		
	}
	private void loadData() {
		XutilRequest.loadProblemCircle(MyGetUrl.getLoadProblem(), this, view, adapter);
		final PullRefreshLayout layout  = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener(){
			@Override
			public void onRefresh() {

				layout.postDelayed(new Runnable() {
					@Override
					public void run() {
						XutilRequest.loadProblemCircle(MyGetUrl.getLoadProblem(), problem_wall_main.this, view, adapter);
						Toast.makeText(problem_wall_main.this, "刷新成功", Toast.LENGTH_SHORT).show();
						  
						layout.setRefreshing(false);
					}
				}, 2000);
		
			}
        });
       layout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
	}
	private void init() {
		findViewById(R.id.dropDown_item1).setOnClickListener(this);
		sp = getSharedPreferences("user", MODE_PRIVATE);
		view =  (RecyclerView) findViewById(R.id.recycleView);
		back = (ImageView) findViewById(R.id.main_backData);
		push = (ImageView) findViewById(R.id.main_newData);
		dropDownView = (LinearLayout) findViewById(R.id.dropDownView);
		bgView = findViewById(R.id.main_bg);
		manager = new GridLayoutManager(this, 1);
		view.setLayoutManager(manager);
		back.setOnClickListener(this);
		push.setOnClickListener(this);
		view.setItemAnimator(new DefaultItemAnimator());
		View header = LayoutInflater.from(this).inflate(
			    R.layout.head, view, false);
		image = (ImageView) header.findViewById(R.id.head_image);

		MyAsyncTask asyncTask=new MyAsyncTask(image, this);
		asyncTask.execute(MyGetUrl.getUrl()+sp.getString("picture", ""));
		header.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
			    Toast.makeText(v.getContext(), "tou", 
			        Toast.LENGTH_SHORT).show();
			  }
			});
		 adapter = new MyRecycleAdapter(header,this);
		
		manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			  @Override
			  public int getSpanSize(int position) {
			    return adapter.isHeader(position) ? manager.getSpanCount() : 1;
			  }
			});
		 bgView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                if (showItems) {
	                    dismissView();
	                }
	            }
	        });
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_backData:
			finish();
			
			break;
		case R.id.main_newData:
			if (!showItems) {
                showView();
            } else {
                dismissView();
            }
			break;
		case R.id.dropDown_item1:
			Intent intent = new Intent(this,SendProblem.class);
			startActivity(intent);
			finish();
			

		default:
			break;
		}
		
	}
	 //显示下拉菜单
    private void showView() {
    	push.startAnimation(AnimationUtils.loadAnimation(problem_wall_main.this, R.anim.rotate_open));

        dropDownView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_down));
        dropDownView.setVisibility(View.VISIBLE);

        bgView.startAnimation(AnimationUtils.loadAnimation(problem_wall_main.this, R.anim.fade_dark));
        bgView.setVisibility(View.VISIBLE);

        showItems = true;
    }
	
    //隐藏下拉菜单
    private void dismissView() {
    	push.startAnimation(AnimationUtils.loadAnimation(problem_wall_main.this, R.anim.rotate_close));

        dropDownView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_up));
        dropDownView.setVisibility(View.INVISIBLE);

        bgView.startAnimation(AnimationUtils.loadAnimation(problem_wall_main.this, R.anim.fade_light));
        bgView.setVisibility(View.INVISIBLE);

        showItems = false;
    }
    @Override
	protected void onDestroy() {
		MyApplication.getHttpQueue().cancelAll("picture");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		MyApplication.getHttpQueue().cancelAll("picture");
		super.onStop();
	}
}
