package com.eden.vollay;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eden.R;
import com.eden.activity.LoginActivity;
import com.eden.adapter.ArticalCommntAdapter;
import com.eden.adapter.ExpertAdapter;
import com.eden.base.BaseContentAdapter;
import com.eden.domain.Collection;
import com.eden.domain.Comment;
import com.eden.domain.CommentEden;
import com.eden.domain.ContentEden;
import com.eden.domain.Expert;
import com.eden.domain.User;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;
import com.eden.view.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class VolleyRequest {
	public static int contentpage=0;
	public static int totalpage=0;
	public static int expertpage=0;
	public static int experttotalpage=0;
	public static SharedPreferences sp ;

	public static void JsonRequestRegister(final Context context,RequestQueue rq,String url,User user){
		final Gson g = new Gson();
		String json = g.toJson(user);
		try {
			JSONObject jo = new JSONObject(json);
			JsonObjectRequest jar = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					String json = jsonObject.toString();
					User user = g.fromJson(json, User.class);
					if (user!=null) {

						Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(context,LoginActivity.class);
						context.startActivity(intent);	
					}			
				}
			},new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("注册失败");
					builder.setPositiveButton("确定",null);
					AlertDialog dialog = builder.create();
					dialog.show();	

				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			rq.add(jar);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//登录功能
	public static void JsonRequestLogin(final Activity activity,RequestQueue rq,String url ,User user, final SharedPreferences spf, final CheckBox checkbox){
		final Gson g = new Gson();
		String json = g.toJson(user);

		JSONObject jo = null;
		try {
			jo = new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject o) {				
					String json = o.toString();

					User user = g.fromJson(json, User.class);
					Editor editor = spf.edit();

					if (checkbox.isChecked()) {
						editor.putString("username", user.getUserAccount());
						editor.putString("password", user.getUserPassword());
						editor.commit();
					}else{
						editor.clear();
						editor.commit();
					}
					SharedPreferences preferences=activity.getSharedPreferences("user", activity.MODE_PRIVATE);
					Editor edit = preferences.edit();
					edit.putString("currentuser", json);
					edit.putString("name", user.getUserName());
					edit.putString("account", user.getUserAccount());
					edit.putString("nickname", user.getUserNickname());
					edit.putString("password", user.getUserPassword());
					edit.putString("picture", user.getUserIcon());
					edit.commit();
					XutilRequest.chattingRequest(MyGetUrl.getchatting(), user.getUserAccount(), user.getUserNickname(), MyGetUrl.getUrl()+user.getUserIcon(), activity);
					//						if(user!=null){
					//							Toast.makeText(activity, "登陆成功", Toast.LENGTH_LONG).show();
					//							Intent intent = new Intent();
					//							intent.setClass(activity, MainActivity.class);
					//							activity.startActivity(intent);
					//						}

				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setMessage("您输入的账号或密码有误，请重新输入");
					builder.setPositiveButton("确定",null);
					AlertDialog dialog = builder.create();
					dialog.show();				
				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			rq.add(jor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	//忘记密码功能
	public static void JsonRequestForgetSecret(final Context context,RequestQueue rq,String url,User user ){
		final Gson g = new Gson();
		String json = g.toJson(user);
		try {
			JSONObject jo = new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject nn) {
					String json = nn.toString();
					User user = g.fromJson(json, User.class);
					if (user!=null) {					
						Toast.makeText(context, "密码重置成功", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(context,LoginActivity.class);
						context.startActivity(intent);	
					}		

				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "重置失败", Toast.LENGTH_LONG).show();
				}
			})
			{
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			rq.add(jor);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//专家
	public static void expertRequest(final Activity activity,
			RequestQueue rq, String url,final ListView actualListView,final ExpertAdapter adapter,final View head) {
		final Gson g = new Gson();
		JsonArrayRequest jar = new JsonArrayRequest(Method.POST, url, null, new Listener<JSONArray>() {
			private CircleImageView expecial_image;
			private TextView name;
			private TextView suggest_count;
			private TextView name1;
			private TextView name2;
			private TextView comment1;
			private TextView comment2;
			private TextView expecial_info;
			private LinearLayout comment_1;
			private LinearLayout comment_2;

			@Override
			public void onResponse(JSONArray array) {
				String json = array.toString();
				Type classOfT = new TypeToken<List<Expert>>(){}.getType();
				List<Expert> data = g.fromJson(json, classOfT);
				LinkedList<Expert> experts=new LinkedList<Expert>();
				experttotalpage=data.size();
				Collections.reverse(data);
				for (int i = 0; i < expertpage; i++) {
					experts.add(data.get(i));
				}
				if (adapter.getItems()==null) {
					adapter.setItems(experts);
					comment_1=(LinearLayout) head.findViewById(R.id.comment_1);
					comment_2=(LinearLayout) head.findViewById(R.id.comment_2);
					expecial_image = (CircleImageView) head.findViewById(R.id.expecial_image);
					name = (TextView) head.findViewById(R.id.expecial_name);
					suggest_count = (TextView) head.findViewById(R.id.expecial_suggest_count);
					name1 = (TextView) head.findViewById(R.id.expecial_comment_name1);
					name2 = (TextView) head.findViewById(R.id.expecial_comment_name2);
					comment1 = (TextView) head.findViewById(R.id.expecial_comment1);
					comment2 = (TextView) head.findViewById(R.id.expecial_comment2);
					expecial_info = (TextView) head.findViewById(R.id.expecial_info);
					System.out.println(MyGetUrl.getUrl()+adapter.getItems().get(2).getExpertIcon());
					MyAsyncTask task = new MyAsyncTask(expecial_image, activity);
					task.execute(MyGetUrl.getUrl()+adapter.getItems().get(2).getExpertIcon());
					name.setText(adapter.getItems().get(2).getExpertName());
					suggest_count.setText("推荐指数："+adapter.getItems().get(2).getExpertPv());
					expecial_info.setText(adapter.getItems().get(2).getExpertIntroduce());
					XutilRequest.xutilRequest1(MyGetUrl.getexpertcomment(), adapter.getItems().get(2).getExpertAccount(), name1, comment1, name2, comment2, activity,comment_1,comment_2);
					actualListView.setAdapter(adapter);
				}else {
					adapter.setItems(experts);
					comment_1=(LinearLayout) head.findViewById(R.id.comment_1);
					comment_2=(LinearLayout) head.findViewById(R.id.comment_2);
					expecial_image = (CircleImageView) head.findViewById(R.id.expecial_image);
					name = (TextView) head.findViewById(R.id.expecial_name);
					suggest_count = (TextView) head.findViewById(R.id.expecial_suggest_count);
					name1 = (TextView) head.findViewById(R.id.expecial_comment_name1);
					name2 = (TextView) head.findViewById(R.id.expecial_comment_name2);
					comment1 = (TextView) head.findViewById(R.id.expecial_comment1);
					comment2 = (TextView) head.findViewById(R.id.expecial_comment2);
					expecial_info = (TextView) head.findViewById(R.id.expecial_info);
					System.out.println(MyGetUrl.getUrl()+adapter.getItems().get(2).getExpertIcon());
					MyAsyncTask task = new MyAsyncTask(expecial_image, activity);
					task.execute(MyGetUrl.getUrl()+adapter.getItems().get(2).getExpertIcon());
					name.setText(adapter.getItems().get(2).getExpertName());
					suggest_count.setText("推荐指数："+adapter.getItems().get(2).getExpertPv());
					expecial_info.setText(adapter.getItems().get(2).getExpertIntroduce());
					XutilRequest.xutilRequest1(MyGetUrl.getexpertcomment(), adapter.getItems().get(2).getExpertAccount(), name1, comment1, name2, comment2, activity,comment_1,comment_2);
					adapter.notifyDataSetChanged();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(activity,
						"onErrorResponse" + error.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String,String> map = new HashMap<String,String>();
				map.put("accept", "application/json");
				map.put("content-type", "application/json;charset=utf-8");
				return map;
			}
		};
		rq.add(jar);
	}
	//	登录
	public static void loginRequest(final Activity activity,RequestQueue rq,String url ,User user){
		final Gson g = new Gson();
		String json = g.toJson(user);
		sp = activity.getSharedPreferences("user", activity.MODE_PRIVATE);
		JSONObject jo = null;
		try {
			jo = new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject o) {				
					String json = o.toString();
					System.out.println(json);
					User user = g.fromJson(json, User.class);
					if(user!=null){
						Editor edit = sp.edit();
						edit.putString("account", user.getUserAccount());
						edit.putString("nickname", user.getUserNickname());
						edit.putString("password", user.getUserPassword());
						edit.putString("picture", user.getUserIcon());
						edit.putString("phone", user.getUserMobile());
						edit.putString("email", user.getUserMail());
						edit.putString("name", user.getUserName());
						edit.putString("sex", user.getUserSex());
						edit.commit();
						XutilRequest.chattingRequest(MyGetUrl.getchatting(), user.getUserAccount(), user.getUserNickname(), MyGetUrl.getUrl()+user.getUserIcon(), activity);

					}

				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setMessage("您输入的账号或密码有误，请重新输入");
					builder.setPositiveButton("确定",null);
					AlertDialog dialog = builder.create();
					dialog.show();				
				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			rq.add(jor);
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}


	//评论详情
	public static void commentDetail(final Context context,final ListView listView,final ArticalCommntAdapter adapter,String url,String contentId){
		final Gson g = new Gson();
		List<String> list = new ArrayList<>();
		list.add(contentId);
		String json = g.toJson(list);
		JSONArray ja = new JSONArray(list);
		JsonArrayRequest jar = new JsonArrayRequest(Method.POST, url, ja, new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray arg0) {
				String com = arg0.toString();
				Type classOfT = new TypeToken<List<CommentEden>>(){}.getType();
				List<CommentEden> data = g.fromJson(com, classOfT);
				Collections.reverse(data);
				if (adapter.getComments()==null) {
					adapter.setComments(data);
					listView.setAdapter(adapter);

					LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
					lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
					listView.setLayoutAnimation(lac);
					listView.startLayoutAnimation();
				}else {
					adapter.setComments(data);
					adapter.notifyDataSetChanged();
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(context,
						"onErrorResponse" + arg0.getMessage(),
						Toast.LENGTH_LONG).show();

			}
		}){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String,String> map = new HashMap<String, String>();
				map.put("accept", "application/json");
				map.put("content-type", "application/json;charset=utf-8");
				return map;
			}
		};
		jar.setTag("commentDetail");
		MyApplication.getHttpQueue().add(jar);
	}
	//添加评论
	public static void addComment(final Context context,String url,Comment comment,final ListView listView,final ArticalCommntAdapter adapter,final String url1,final String contentId){
		Gson g = new Gson();
		String json = g.toJson(comment);
		JSONObject jo = null;
		try {
			jo = new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject arg0) {
					if(arg0 != null){
						Toast.makeText(context,
								"评论成功" ,
								Toast.LENGTH_LONG).show();
						commentDetail(context, listView, adapter, url1, contentId);



					}

				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Toast.makeText(context,
							"评论失败" ,
							Toast.LENGTH_LONG).show();


				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			jor.setTag("addComment");
			MyApplication.getHttpQueue().add(jor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//回复
	public static void replyComment(final Context context,String url,Comment comment){
		Gson g = new Gson();
		String json = g.toJson(comment);
		JSONObject jo = null;
		try {
			jo = new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject arg0) {
					if(arg0 != null){
						Toast.makeText(context,
								"回复成功" ,
								Toast.LENGTH_LONG).show();

                  


					}

				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Toast.makeText(context,
							"回复失败" ,
							Toast.LENGTH_LONG).show();


				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			jor.setTag("addComment");
			MyApplication.getHttpQueue().add(jor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//添加收藏
	public static void addCollection(final Context context,String url,Collection collection){
		Gson g = new Gson();
		String json = g.toJson(collection);
		JSONObject jo =null;
		try {
			jo = new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject arg0) {
					Toast.makeText(context,
							"收藏成功" ,
							Toast.LENGTH_SHORT).show();


				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Toast.makeText(context,
							"失败" ,
							Toast.LENGTH_SHORT).show();

				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			jor.setTag("addCollection");
			MyApplication.getHttpQueue().add(jor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//判断是否已收藏
	public static void deleteCollection(final Context context,Collection collection,String url){
		final Gson g = new Gson();
		String json = g.toJson(collection);
		JSONObject jo =null;
		try {
			jo =new JSONObject(json);
			JsonObjectRequest jor = new JsonObjectRequest(Method.POST, url, jo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject arg0) {
					Toast.makeText(context,
							"取消收藏" ,
							Toast.LENGTH_SHORT).show();

				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Toast.makeText(context,
							"取消收藏失败"+arg0.getMessage() ,
							Toast.LENGTH_LONG).show();

				}
			}){
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String,String> map = new HashMap<String, String>();
					map.put("accept", "application/json");
					map.put("content-type", "application/json;charset=utf-8");
					return map;
				}
			};
			jor.setTag("deleteCollection");
			MyApplication.getHttpQueue().add(jor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void contentRequest(final Activity activity,
			RequestQueue rq, String url,final ListView actualListView,final BaseContentAdapter adapter,final int a) {
		final Gson g = new Gson();
		JsonArrayRequest jar = new JsonArrayRequest(Method.POST, url, null, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray array) {
				String json = array.toString();
				Type classOfT = new TypeToken<LinkedList<ContentEden>>() {
				}.getType();
				List<ContentEden> data = g.fromJson(json, classOfT);
				List<ContentEden> reads = new ArrayList<ContentEden>();
				List<ContentEden> videos = new ArrayList<ContentEden>();
				totalpage=data.size();
				Collections.reverse(data);
				for (int i = 0; i < contentpage; i++) {
					if (data.get(i).getContent().getContentVideo() == null) {
						reads.add(data.get(i));
					} else {
						videos.add(data.get(i));

					}
				}
				if(a == 0){
					if (adapter.getItems() == null) {
						adapter.setItems(reads);

						actualListView.setAdapter(adapter);
					} else {
						adapter.setItems(reads);
						adapter.notifyDataSetChanged();
					}
				}else{
					if (adapter.getItems() == null) {
						adapter.setItems(videos);

						actualListView.setAdapter(adapter);
					} else {
						adapter.setItems(videos);
						adapter.notifyDataSetChanged();
					}
				}



			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(activity,
						"onErrorResponse" + error.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String,String> map = new HashMap<String,String>();
				map.put("accept", "application/json");
				map.put("content-type", "application/json;charset=utf-8");
				return map;
			}
		};
		rq.add(jar);
	}
}
