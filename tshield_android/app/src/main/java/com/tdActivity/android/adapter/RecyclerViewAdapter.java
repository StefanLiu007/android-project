package com.tdActivity.android.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.tdActivity.android.activity.R;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.HanldAddActivity;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.fragment.AccountFragment;
import com.tdActivity.android.interfaceAll.ItemTouchHelperAdapter;
import com.tdActivity.android.interfaceAll.ItemTouchHelperViewHolder;
import com.tdActivity.android.interfaceAll.SimpleItemTouchHelperCallback;
import com.tdActivity.android.interfaceAll.StartDragListener;
import com.tdActivity.android.interfaceAll.UpdateIcon;
import com.tdActivity.android.util.ListUtils;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.view.CustomProgressDialog;
import com.tdActivity.android.view.SlidingMenu;
import com.tdActivity.android.view.SwipeMenuLayout;
import com.tdActivity.android.view.SwipeMenuRecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>
implements ItemTouchHelperAdapter ,UpdateIcon{
	private StartDragListener dragListener;
	private List<UserInfo> accounts ;
	private Activity context;
	private SwipeMenuRecyclerView mRecyclerView;
	private UpdateDate u;
	private Deletedate delte;
	private AccountFragment ac;
	private static final int VIEW_TYPE_ENABLE = 0;
	private static final int VIEW_TYPE_DISABLE = 1;
	private MyApplication myApplication;
	private CustomProgressDialog progressDialog = null;
	private long start = 0;
	private Timer mTimer;
	private int time;
	private Handler mHaddler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x1103) {// 删除
				myApplication.isChanged=true;
				delte.delete();
				//				ProgressShowUtils.stopProgressDialog(progressDialog);
			}
			if (msg.what == 0x1104) {
				if(time == 0){
					
						// 先进行排序操作
						//					progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, getActivity(),
						//							ProgressShowUtils.CAPTURE);
						myApplication.isChanged = true;// 有改变
						final ArrayList<UserInfo> userInfos=(ArrayList<UserInfo>) ListUtils.copyToAnother(myApplication.userInfos);
//						LocalSynchUtils.copyNewToOld(myApplication.oldUserInfos, myApplication.userInfos);//来数据进行更新
						myApplication.fixedThreadPool.execute(new Runnable() {

							@Override
							public void run() {
								int len = userInfos.size();
								for (int i = 0; i < len; i++) {
									MyApplication.getDbHelper().userInfoUpdate(userInfos.get(i), i);
								}
								//			ProgressShowUtils.stopProgressDialog(progressDialog);
							}

						});
						// 再进行备份操作		
						LocalSynchUtils.updateCacheFile(myApplication,userInfos);//写进临时文件中
						//					userInfos=null;
						mTimer.cancel();
				}
			}
		};
	};
	private View itemView1;

	public RecyclerViewAdapter(Activity context, List<UserInfo> accounts, SwipeMenuRecyclerView mRecyclerView,
			AccountFragment dragListener) {
		myApplication=(MyApplication) context.getApplication();
		this.dragListener = dragListener;
		this.context = context;
		this.accounts = accounts;
		LayoutInflater.from(context);
		this.mRecyclerView = mRecyclerView;
		this.ac = dragListener;

	}

	public void SetAccount(List<UserInfo> accounts) {
		this.accounts = accounts;
	}

	/**
	 * 设置点击条目事件
	 * 
	 * @param u
	 */
	public void setU(UpdateDate u) {
		this.u = u;
	}

	/**
	 * 设置删除数据时回调的点击事件
	 * 
	 * @param detete
	 */
	public void setD(Deletedate delete) {
		this.delte = delete;
	}

	public List<UserInfo> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<UserInfo> accounts) {
		this.accounts = accounts;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public int getItemCount() {
		if (accounts.size() == 0) {
			return 0;
		} else {
			return accounts.size();
		}
	}
	@Override
	public void onBindViewHolder(final ItemViewHolder arg0, final int position) {
		ItemViewHolder myViewHolder = (ItemViewHolder) arg0;
		final SwipeMenuLayout itemView  = (SwipeMenuLayout) myViewHolder.itemView;
		itemView.setTag(arg0.getAdapterPosition());
		MainActivity.mMenu.addIgnoredView(itemView);
		itemView.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager()
						.beginTransaction();
				u.update(accounts.get(arg0.getAdapterPosition()));
				ac.f.setSelect(0);
				MainActivity.mMenu.closeMenu();
			}
		});
		myViewHolder.tv_onclicktouch.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
					
					dragListener.startDrag(arg0);

				}
				return false;
			}
		});
		myViewHolder.btOpen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HanldAddActivity.class);
				intent.putExtra("userInfo", accounts.get(position));
				intent.putExtra("code", 1);
				context.startActivity(intent);
			}
		});
		myViewHolder.btDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mMenu.removeIgnoredView(itemView);
				final List<UserInfo> list = ListUtils.copyToAnother(accounts);
				final int size = arg0.getAdapterPosition();
				accounts.remove(accounts.get(size));
				RecyclerViewAdapter.this.notifyItemRemoved(size);
				new Thread() {
					public void run() {
						Message msg = Message.obtain();
						msg.what = 0x1103;
						// 这儿是耗时操作，完成之后更新UI；
						MyApplication.getDbHelper().userInfoDeleteItem(list.get(size));// 删除数据库
						List<UserInfo> user = ListUtils.copyToAnother(accounts);
						for (int i = 0; i < user.size(); i++) {
							MyApplication.getDbHelper().userInfoUpdate(user.get(i), i);// 删除后从新排序
						}
						LocalSynchUtils.updateCacheFileNoThread(myApplication, user);
						mHaddler.sendMessage(msg);
					}
				}.start();
			}
		});
		myViewHolder.yytitle.setText(accounts.get(position).yy);
		myViewHolder.title.setText(accounts.get(position).name);
		boolean swipeEnable = swipeEnableByViewType(getItemViewType(position));
		itemView.setSwipeEnable(swipeEnable);
		itemView.setOpenInterpolator(mRecyclerView.getOpenInterpolator());
		itemView.setCloseInterpolator(mRecyclerView.getCloseInterpolator());

	}

	public boolean swipeEnableByViewType(int viewType) {
		if (viewType == VIEW_TYPE_ENABLE)
			return true;
		else if (viewType == VIEW_TYPE_DISABLE)
			return false;
		else
			return true; // default
	}


	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		itemView1 = LayoutInflater.from(context).inflate(R.layout.item_simple, arg0, false);
		return new ItemViewHolder(itemView1);
	}

	@Override
	public int getItemViewType(int position) {
		return VIEW_TYPE_ENABLE;
	}

	public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

		public final TextView title, yytitle;
		public final ImageView handleDrag, titleImage;
		public final TextView tv_onclicktouch;
		View btOpen;
		View btDelete;

		public ItemViewHolder(View itemView) {
			super(itemView);
			titleImage = (ImageView) itemView.findViewById(R.id.icon);
			handleDrag = (ImageView) itemView.findViewById(R.id.imageDrag);
			yytitle = (TextView) itemView.findViewById(R.id.yytitle);
			title = (TextView) itemView.findViewById(R.id.title);
			btOpen = itemView.findViewById(R.id.btOpen);
			btDelete = itemView.findViewById(R.id.btDelete);
			tv_onclicktouch = (TextView) itemView.findViewById(R.id.tv_onclicktouch);
		}

		@Override 
		public void onItemSelect() {
			itemView.setBackgroundColor(Color.LTGRAY); 
		}

		@Override
		public void itemClear() {
			itemView.setBackgroundColor(0);
		}
	}
	
	@Override
	public synchronized boolean onItemMove(final int fromPosition, final int toPosition) {// 排序
		delay();
		Collections.swap(accounts, fromPosition, toPosition);
		notifyItemMoved(fromPosition, toPosition);
		return true;
	}

	@Override
	public void onItemDismiss(int position) {
		accounts.remove(position);
		notifyItemRemoved(position);
	}

	/**
	 * 
	 * @author 点击每个条目时更新mainfragment的回调
	 *
	 */
	public interface UpdateDate {
		public void update(UserInfo user);
	}

	/**
	 * 
	 * @author 删除条目时，mainfargment更新界面
	 *
	 */
	public interface Deletedate {
		public void delete();
	}
	/**
	 * 点击账号排序时的回调函数
	 */
	@Override
	public void openOrCloseIcon() {
//				ImageView image = (ImageView) itemView1.findViewById(R.id.imageDrag);
//				TextView text = (TextView) itemView1.findViewById(R.id.tv_onclicktouch);
//				if(AccountFragment.type == 0){
//					setDisEnable(image, text);
//				}else {
//					setEnable(image, text);
//				}
		for(int i = 0;i<SlidingMenu.ignoreViewCopy.size();i++){
			
			ImageView image = (ImageView) SlidingMenu.ignoreViewCopy.get(i).findViewById(R.id.imageDrag);
			TextView text = (TextView) SlidingMenu.ignoreViewCopy.get(i).findViewById(R.id.tv_onclicktouch);
			//			Log.i("Main", SlidingMenu.ignoreViewCopy.size()+"");
			//				text.setText(i+""+SlidingMenu.ignoreViewCopy.get(i).getTag());

			if(AccountFragment.type == 1){
				setEnable(image, text);
			}else {
				setDisEnable(image, text);
			}
		}

	}
	/**
	 * 设置可排序
	 * @param image
	 * @param text
	 */
	public void setDisEnable(ImageView image,TextView text){
		ObjectAnimator o4 = ObjectAnimator
				.ofFloat(image, "scaleX", 1.0f, 0.0f);
		ObjectAnimator o5 = ObjectAnimator
				.ofFloat(image, "scaleY", 1.0f, 0.0f);
		o5.setInterpolator(new DecelerateInterpolator());
		// o6.setInterpolator(new AccelerateInterpolator());
		o4.setInterpolator(new DecelerateInterpolator());
		AnimatorSet set = new AnimatorSet();
		set.playTogether(o4, o5);
		set.setDuration(500);
		set.start();
		text.setVisibility(View.GONE);
		SimpleItemTouchHelperCallback.LONG = false;
		notifyDataSetChanged();
	}
	/**
	 * 设置不可排序
	 * @param image
	 * @param text
	 */
	public void setEnable(ImageView image,TextView text){
		image.setVisibility(View.VISIBLE);
		ObjectAnimator o4 = ObjectAnimator
				.ofFloat(image, "scaleX", 0.0f, 1.0f);
		ObjectAnimator o5 = ObjectAnimator
				.ofFloat(image, "scaleY", 0.0f, 1.0f);
		o5.setInterpolator(new DecelerateInterpolator());
		o4.setInterpolator(new AccelerateInterpolator());
		//o4.setInterpolator(new DecelerateInterpolator());
		AnimatorSet set = new AnimatorSet();
		set.playTogether(o4, o5);
		set.setDuration(500);
		set.start();
		text.setVisibility(View.VISIBLE);
		SimpleItemTouchHelperCallback.LONG = true;
		notifyDataSetChanged();
	}
	//定时器
	public void delay() {
		if (mTimer != null) {
			mTimer.cancel();
		}
		time = 3;
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				time--;
				mHaddler.sendEmptyMessage(0x1104);
			}

		}, 1, 1000);
	}
}
