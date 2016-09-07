package com.eden.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eden.R;
import com.eden.activity.PersonHomePageActivity;
import com.eden.activity.IsNotFriendActivity;
import com.eden.activity.PersonalProblemInfo;
import com.eden.domain.ProblemEden;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder>{
	private LayoutInflater inflater;
	private Context context;
	private List<ProblemEden> data;

	private static final int ITEM_VIEW_TYPE_HEADER = 0;
	private static final int ITEM_VIEW_TYPE_ITEM = 1;
	private List<Integer> mHight;
	private final View header;
	private  String b;
	private Handler hand;


	public MyRecycleAdapter(View header,Context context) {
		if (header == null) {
			throw new IllegalArgumentException("header may not be null");
		}
		this.header = header;
		this.context = context;

	}
	public boolean isHeader(int position) {
		return position == 0;
	}
	public List<ProblemEden> getData() {
		return data;
	}


	public void setData(List<ProblemEden> data) {
		this.data = data;
		Collections.reverse(data);
		//		  mHight = new ArrayList<>();
		//		    for(int i=0;i<data.size();i++){
		//		    	mHight.add((int) (100+Math.random()*200));
		//		    }
	}


	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return data.size()+1;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder arg0, final int arg1) {

		if (isHeader(arg1)) {
			return;
		}

		arg0.name.setText(data.get(arg1-1).getUse().getUserName());
		arg0.content.setText(data.get(arg1-1).getProblem().getProblemContent());
		arg0.time.setText(data.get(arg1-1).getProblem().getProblemLastTime());
		arg0.zan.setText(data.get(arg1-1).getProblem().getProblemAnswerNum()+"人评论过");
		arg0.content.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,PersonalProblemInfo.class);
				intent.putExtra("problem", data.get(arg1-1));
				context.startActivity(intent);
			}
		});
		MyAsyncTask asyncTask= new MyAsyncTask(arg0.image, context);
		asyncTask.execute(MyGetUrl.getUrl()+data.get(arg1-1).getUse().getUserIcon());
		final Runnable update = new Runnable() {

			@SuppressLint("UseValueOf")
			@Override
			public void run() {

				URL url=null;
				HttpURLConnection con =null;
				InputStream in = null;
				try {
					url = new URL(MyGetUrl.getIsFriendServlet()+"?friendAccount="+data.get(arg1-1).getProblem().getUserAccou()+"&userAccount="+
							context.getSharedPreferences("user", context.MODE_PRIVATE).getString("account", ""));
					con = (HttpURLConnection) url.openConnection();
					in = con.getInputStream();
					byte[] b = new byte[1024];
					int a = in.read(b);
					String s = new String(b);
					Message mess = Message.obtain();
					mess.obj = s;
					hand.sendMessage(mess);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(in != null){
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}


			}
		};
		arg0.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(update).start();
				hand = new Handler(){
					public void handleMessage(android.os.Message msg) {
						b = (String) msg.obj;
						if(b.contains("true")){
							Intent intent = new Intent(context,PersonHomePageActivity.class);
							intent.putExtra("friendInfor", data.get(arg1-1).getUse());
							context.startActivity(intent);
						}else {
							SharedPreferences sp=context.getSharedPreferences("user", context.MODE_PRIVATE);
							if(sp.getString("account", null).equals(data.get(arg1-1).getUse().getUserAccount())){

							}else{
								Intent intent = new Intent(context,IsNotFriendActivity.class);
								intent.putExtra("friendInfor", data.get(arg1-1).getUse());
								context.startActivity(intent);	
							}

						}
					};
				};
			}

		});




	}

	@Override
	public int getItemViewType(int position) {
		return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		if (arg1 == ITEM_VIEW_TYPE_HEADER) {
			return new MyViewHolder(header);
		}
		View view = LayoutInflater.from(context).inflate(R.layout.problem_wall_item, arg0, false);
		return new MyViewHolder(view);
	}
}
class MyViewHolder extends ViewHolder{
	ImageView image;
	TextView name;
	TextView content;
	TextView time;
	TextView zan;
	public MyViewHolder(View arg0) {
		super(arg0);
		image = (ImageView) arg0.findViewById(R.id.wall_userface);
		name = (TextView) arg0.findViewById(R.id.wall_name);
		content = (TextView) arg0.findViewById(R.id.wall_content);
		time = (TextView) arg0.findViewById(R.id.wall_time);
		zan = (TextView) arg0.findViewById(R.id.wall_zan1);
	}


}
