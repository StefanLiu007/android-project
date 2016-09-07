package com.eden.vollay;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.ListPreference;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.eden.activity.MainActivity;
import com.eden.activity.problem_wall_main;
import com.eden.adapter.ExpertAdapter;
import com.eden.adapter.ExpertCommntAdapter;
import com.eden.adapter.FriendAdapter;
import com.eden.adapter.MyRecycleAdapter;
import com.eden.adapter.ProblemCommentAdapter;
import com.eden.collection.CollectionActivity;
import com.eden.collection.CollectionContentFragment;
import com.eden.collection.CollectionBean;
import com.eden.collection.CollectionContent;
import com.eden.collection.CollectionProblem;
import com.eden.collection.CollectionProblemSilde;
import com.eden.collection.CollecttionContentAdapter;
import com.eden.collection.ListViewCollectionContent;
import com.eden.collection.CollectionContentSilde;
import com.eden.domain.Answer;
import com.eden.domain.AnswerEden;
import com.eden.domain.AskedExpert;
import com.eden.domain.Collection;
import com.eden.domain.CommentEden;
import com.eden.domain.Expert;
import com.eden.domain.Problem;
import com.eden.domain.ProblemEden;
import com.eden.domain.User;
import com.eden.myproblem.ListViewProblem;
import com.eden.myproblem.MyProblemAdapter;
import com.eden.myproblem.ProblemSide;
import com.eden.util.GsonUtil;
import com.eden.util.MyApplication;
import com.eden.util.MyGetUrl;
import com.eden.view.NoScrollListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class XutilRequest {

	public static void xutilRequest(String url,String expertAccount,final ListView lv_words,final ExpertCommntAdapter adapter,final Context context){
		RequestParams params = new RequestParams();
		//		params.addHeader("name", "value");
		//		params.addQueryStringParameter("name", "value");
		params.addBodyParameter("case","1");
		params.addBodyParameter("ExpertAccount", expertAccount);

		// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
		//		params.addBodyParameter("file", new File("path"));
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onStart() {
				//				testTextView.setText("conn...");
			}        
			@Override
			public void onLoading(long total, long current, boolean isUploading) {            
				if (isUploading) {
					//					testTextView.setText("upload: " + current + "/" + total);
				} else {
					//					testTextView.setText("reply: " + current + "/" + total);
				}
			}        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json=responseInfo.result;
				Gson gson= new Gson();
				Type classOfT = new TypeToken<List<CommentEden>>(){}.getType();
				List<CommentEden> comments=gson.fromJson(json, classOfT);
				Collections.reverse(comments);
				if (adapter.getComments()==null) {
					adapter.setComments(comments);
					lv_words.setAdapter(adapter);
				}else {
					adapter.setComments(comments);
					adapter.notifyDataSetChanged();
				}

			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	public static void xutilRequest1(String url,String expertAccount,final TextView name1,final TextView comment1,final TextView name2,final TextView comment2,final Context context,final LinearLayout comment_1,final LinearLayout comment_2){
		RequestParams params = new RequestParams();
		params.addBodyParameter("case","1");
		params.addBodyParameter("ExpertAccount", expertAccount);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onStart() {
				//				testTextView.setText("conn...");
			}        
			@Override
			public void onLoading(long total, long current, boolean isUploading) {            
				if (isUploading) {
					//					testTextView.setText("upload: " + current + "/" + total);
				} else {
					//					testTextView.setText("reply: " + current + "/" + total);
				}
			}        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json=responseInfo.result;
				Gson gson= new Gson();
				Type classOfT = new TypeToken<List<CommentEden>>(){}.getType();
				List<CommentEden> comments=gson.fromJson(json, classOfT);
				Collections.reverse(comments);
				if (comments.size()>0) {
					if(comments.get(0) != null){
						comment_1.setVisibility(View.VISIBLE);
						name1.setText(comments.get(0).getName());
						comment1.setText(":"+comments.get(0).getComment().getCommentContent());

					}else {
						comment_1.setVisibility(View.GONE);
					}
				}

				if (comments.size()>1) {
					if(comments.get(0) != null){
						comment_1.setVisibility(View.VISIBLE);
						name1.setText(comments.get(0).getName());
						comment1.setText(":"+comments.get(0).getComment().getCommentContent());

					}else {
						comment_1.setVisibility(View.GONE);
					}
					if(comments.get(1) != null){
						comment_2.setVisibility(View.VISIBLE);
						name2.setText(comments.get(1).getName());
						comment2.setText(":"+comments.get(1).getComment().getCommentContent());
					}else {
						comment_2.setVisibility(View.GONE);

					}
				}
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void chattingRequest(String url,String userId,String userName,String portraitUri ,final Activity activity){
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		params.addBodyParameter("userName", userName);
		params.addBodyParameter("portraitUri", portraitUri);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onStart() {

			}        
			@Override
			public void onLoading(long total, long current, boolean isUploading) {            

			}        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result=responseInfo.result;
				Log.i("main", result);
				try {
					if (result != null) {
						JSONObject object = new JSONObject(result);
						JSONObject jobj = object.getJSONObject("result");

						if (object.getInt("code") == 200) {
							String token = jobj.getString("token");
							connect(token,activity);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(activity, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	/**
	 * 建立与融云服务器的连接
	 *
	 * @param token
	 */
	private static void connect(String token,final Activity activity) {

		if (activity.getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(activity.getApplicationContext()))) {

			/**
			 * IMKit SDK调用第二步,建立与服务器的连接
			 */
			RongIM.connect(token, new RongIMClient.ConnectCallback() {

				/**
				 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
				 */
				@Override
				public void onTokenIncorrect() {

					Log.d("LoginActivity", "--onTokenIncorrect");
				}

				/**
				 * 连接融云成功
				 * @param userid 当前 token
				 */
				@Override
				public void onSuccess(String userid) {

					Log.d("LoginActivity", "--onSuccess" + userid);
					//					RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
					//
					//						@Override
					//						public UserInfo getUserInfo(String userId) {
					//							SharedPreferences sp= activity.getSharedPreferences("user", activity.MODE_PRIVATE);
					//							String id=sp.getString("account", null);
					//							String name=sp.getString("nickname", null);
					//							Uri portraitUri=Uri.parse(MyGetUrl.getUrl()+sp.getString("picture", null));
					//							UserInfo userInfo=new UserInfo(id, name, portraitUri);
					//							return userInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
					//						}
					//
					//					}, false);
					Toast.makeText(activity, "登陆成功", Toast.LENGTH_LONG).show();
					activity.startActivity(new Intent(activity,MainActivity.class));
					activity.finish();
				}

				/**
				 * 连接融云失败
				 * @param errorCode 错误码，可到官网 查看错误码对应的注释
				 */
				@Override
				public void onError(RongIMClient.ErrorCode errorCode) {

					Log.d("LoginActivity", "--onError" + errorCode);
				}
			});
		}
	}

	public static void FriendRequest(String url,final ListView lv_friend,final FriendAdapter adapter,final Context context){
		RequestParams params = new RequestParams();
		SharedPreferences sp = context.getSharedPreferences("user", context.MODE_PRIVATE);
		params.addBodyParameter("userAccount", sp.getString("account",null));
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result=responseInfo.result;
				SharedPreferences preferences=context.getSharedPreferences("MyFriends", context.MODE_PRIVATE);
				Editor editor=preferences.edit();
				editor.putString("friends", result);
				editor.commit();
				Type classOfT = new TypeToken<List<User>>(){}.getType();
				List<User> friends=(List<User>) GsonUtil.fromJson(result,classOfT);
				if (adapter.getFriends()==null) {
					adapter.setFriends(friends);
					lv_friend.setAdapter(adapter);
				}else {
					adapter.setFriends(friends);
					adapter.notifyDataSetChanged();
				}
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void UpdateExpertRequest(String url,Expert expert,final Context context,final ExpertAdapter adapter){
		RequestParams params = new RequestParams();
		String json=GsonUtil.toJson(expert);
		params.addBodyParameter("expert",json);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				Type classOfT = new TypeToken<List<Expert>>(){}.getType();
				List<Expert> data = (List<Expert>) GsonUtil.fromJson(result, classOfT);
				LinkedList<Expert> experts=new LinkedList<Expert>();
				for (int i = data.size()-1; i >=0; i--) {
					experts.add(data.get(i));
				}
				adapter.setItems(experts);
				adapter.notifyDataSetChanged();

			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void sendProblem(final String url,final Context context,Problem problem){
		RequestParams params = new RequestParams();
		params.addBodyParameter("acode", "4");
		String json = GsonUtil.toJson(problem);
		params.addBodyParameter("problem", json);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String re = responseInfo.result;
				if (re != null) {
					loadProblemCircle(MyGetUrl.getLoadProblem(), context, problem_wall_main.view, problem_wall_main.adapter);
					Toast.makeText(context, "提问成功", Toast.LENGTH_SHORT).show();

				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();

			}
		});
	}

	public static void loadProblemCircle(String url, final Context context,
			final RecyclerView recycle, final MyRecycleAdapter adapter) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("acode", "1");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			Gson g = new Gson();
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;

				Type classOfT = new TypeToken<List<ProblemEden>>(){}.getType();
				List<ProblemEden> problems =   g.fromJson(result, classOfT);

				if(problems == null){
					Toast.makeText(context, "没有问题", Toast.LENGTH_SHORT).show();
				}else{
					if(adapter.getData() == null){
						adapter.setData(problems);
						recycle.setAdapter(adapter);
					}else{
						adapter.setData(problems);
						adapter.notifyDataSetChanged();
					}
				}
				////				Log.i("MainActivity", problems.get(location))


			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();

			}
		});

	}
	public static void addCommentReply(final String url,final Context context,Answer answer,final String ID,final NoScrollListView list,final ProblemCommentAdapter adapter){
		RequestParams params = new RequestParams();
		params.addBodyParameter("acode", "3");
		String json = GsonUtil.toJson(answer);
		params.addBodyParameter("answer",json);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String re = responseInfo.result;
				if(re != null){
					Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
					loadComment(url, context, ID, list, adapter);
				}


			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();

			}
		});
	}



	public static void loadComment(String url,final Context context,String ID,final NoScrollListView list,final ProblemCommentAdapter adapter){
		RequestParams params = new RequestParams();
		params.addBodyParameter("acode", "2");
		params.addBodyParameter("ID",ID);

		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String re = responseInfo.result;

				Type classOfT = new TypeToken<List<AnswerEden>>(){}.getType();
				List<AnswerEden> problems = (List<AnswerEden>) GsonUtil.fromJson(re, classOfT);

				if (problems == null) {
					Toast.makeText(context, "没人回答", Toast.LENGTH_SHORT).show();
				} else {
					if (adapter.getAnswers() == null) {
						adapter.setAnswers(problems);
						list.setAdapter(adapter);
					} else {
						adapter.setAnswers(problems);
						adapter.notifyDataSetChanged();
					}
				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();

			}
		});
	}

	public static void showCollection(final String url,final Context context){
		RequestParams params = new RequestParams();
		SharedPreferences sp=context.getSharedPreferences("user", context.MODE_PRIVATE);
		params.addBodyParameter("collection_account",sp.getString("account", null));
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				//				Toast.makeText(context, result, Toast.LENGTH_LONG).show();
				//				CollectionBean collectionBean=(CollectionBean) GsonUtil.fromJson(result, CollectionBean.class);
				//				List<CollectionContentSilde> collectionContentSildes=new ArrayList<CollectionContentSilde>();
				//				List<CollectionProblemSilde> collectionProblemSildes=new ArrayList<CollectionProblemSilde>();
				//				List<CollectionContent>  collectionContents=collectionBean.getContents();
				//				List<CollectionProblem> collectionProblems=collectionBean.getProblemEdens();
				//				Collections.reverse(collectionContents);
				//				Collections.reverse(collectionProblems);
				//				for (CollectionContent collectionContent : collectionContents) {
				//					collectionContentSildes.add(new CollectionContentSilde(collectionContent));
				//				}
				//				for (CollectionProblem collectionProblem : collectionProblems) {
				//					collectionProblemSildes.add(new CollectionProblemSilde(collectionProblem));
				//				}
				//				SharedPreferences sp=context.getSharedPreferences("mycollection", context.MODE_PRIVATE);
				//				Editor editor=sp.edit();
				//				editor.putString("collectioncontent", GsonUtil.toJson(collectionContentSildes));
				//				editor.putString("collectionproblem", GsonUtil.toJson(collectionProblemSildes));
				//				editor.commit();
				SharedPreferences sp=context.getSharedPreferences("mycollection", Context.MODE_PRIVATE);
				Editor editor=sp.edit();
				editor.putString("collection", result);
				editor.commit();
				Intent intent2 = new Intent(context,CollectionActivity.class);
				context.startActivity(intent2);
			}
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();

			}
		});
	}
	public static void insertAskedExpert(String url, AskedExpert asked,
			final Context context) {
		RequestParams params = new RequestParams();
		String json=GsonUtil.toJson(asked);
		params.addBodyParameter("asked",json);
		params.addBodyParameter("acode", "0");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});

	}
	public static void searchProblem(final Context context,String url, String userAccount,
			final TextView tv_friend_problem,final TextView tv_friend_time) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("account",userAccount);
		params.addBodyParameter("acode", "1");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String s = responseInfo.result;
				Problem p = (Problem) GsonUtil.fromJson(s, Problem.class);
				if (p!=null) {
					tv_friend_problem.setText(p.getProblemContent());
					tv_friend_time.setText(p.getProblemLastTime());
				}
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});


	}
	public static void searchAskedExpert(final Context context,String url, String userAccount,
			final ListView list,final ExpertAdapter adapter) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("account",userAccount);
		params.addBodyParameter("acode", "2");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String s = responseInfo.result;
				Type classOfT = new TypeToken<LinkedList<Expert>>(){}.getType();
				@SuppressWarnings("unchecked")
				LinkedList<Expert> experts = (LinkedList<Expert>) GsonUtil.fromJson(s, classOfT);
				if(experts != null){
					if(adapter.getItems() == null){
						adapter.setItems(experts);
						list.setAdapter(adapter);
					}else {
						adapter.setItems(experts);
						adapter.notifyDataSetChanged();
					}
				}


			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void addFriend(final Context context,String url, String friendAccount) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("friendaccount",friendAccount);
		SharedPreferences sp=context.getSharedPreferences("user", context.MODE_PRIVATE);
		params.addBodyParameter("useraccount",sp.getString("account", null));
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result=responseInfo.result;
				Boolean isSuccess=(Boolean) GsonUtil.fromJson(result, Boolean.class);
				if (isSuccess) {
					Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
					Intent intent= new Intent(context,problem_wall_main.class);
					context.startActivity(intent);
				}
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	public static void showMyProblems(final Context context,String url,final ListViewProblem listViewProblem,final MyProblemAdapter adapter) {
		RequestParams params = new RequestParams();
		SharedPreferences sp=context.getSharedPreferences("user", context.MODE_PRIVATE);
		params.addBodyParameter("useraccount",sp.getString("account", null));
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {        
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result=responseInfo.result;
				Type classOfT = new TypeToken<List<ProblemEden>>(){}.getType();
				List<ProblemEden> myProblems=(List<ProblemEden>) GsonUtil.fromJson(result, classOfT);
				Collections.reverse(myProblems);
				List<ProblemSide> problemSides= new ArrayList<ProblemSide>();
				for (ProblemEden problemEden : myProblems) {
					ProblemSide problemSide= new ProblemSide(problemEden, null);
					problemSides.add(problemSide);
				}
				if (adapter.getmMessageItems()==null) {
					adapter.setmMessageItems(problemSides);
					listViewProblem.setAdapter(adapter);
				}else{
					adapter.setmMessageItems(problemSides);
					adapter.notifyDataSetChanged();
				}
			}        
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		});

	}
}
