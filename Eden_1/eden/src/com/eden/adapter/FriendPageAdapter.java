package com.eden.adapter;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

import java.lang.reflect.Type;
import java.util.List;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.eden.R;
import com.eden.base.BasePageAdapter;
import com.eden.domain.User;
import com.eden.listview.FriendListView;
import com.eden.util.GsonUtil;
import com.eden.util.MyGetUrl;
import com.google.gson.reflect.TypeToken;

public class FriendPageAdapter extends BasePageAdapter {
	private List<View> views;
	private FragmentActivity activity;
	public FriendPageAdapter(List<View> views,FragmentActivity activity) {
		this.views=views;
		this.activity=activity;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view==object;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view=views.get(position);
		if (position==0) {
			SharedPreferences sp=activity.getSharedPreferences("MyFriends", activity.MODE_PRIVATE);
			if (sp.getString("friends", null)!=null) {
				Type classOfT = new TypeToken<List<User>>(){}.getType();
				List<User> friends=(List<User>) GsonUtil.fromJson(sp.getString("friends", null),classOfT);
				for (final User friend : friends) {
					RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

						@Override
						public UserInfo getUserInfo(String userId) {
							if (userId.equals(friend.getUserAccount())) {
								String id=friend.getUserAccount();
								String name=friend.getUserNickname();
								Uri portraitUri=Uri.parse(MyGetUrl.getUrl()+friend.getUserIcon());
								RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(id, name, portraitUri));
								return new io.rong.imlib.model.UserInfo(id, name, portraitUri);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
							}
							return null;
						}

					}, true);
				}
			}
			FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
			ConversationListFragment fragment = new ConversationListFragment();
			Uri uri = Uri.parse("rong://" + activity.getApplicationInfo().packageName).buildUpon()
					.appendPath("conversationlist")
					.appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
					.appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
					.appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
					.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
					.build();
			fragment.setUri(uri);
			transaction.add(R.id.friend_conversationlist, fragment);
			transaction.commit();
		}else if (position==1) {
			new FriendListView(view, activity).initView();
		}
		ViewPager vp=(ViewPager) container;
		vp.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

}
