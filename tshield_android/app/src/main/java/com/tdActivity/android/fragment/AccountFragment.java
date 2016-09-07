package com.tdActivity.android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.CaptureActivity;
import com.tdActivity.android.activity.HanldAddActivity;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.activity.R;
import com.tdActivity.android.adapter.AddAccountAdapter;
import com.tdActivity.android.adapter.RecyclerViewAdapter;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.Operation;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.interfaceAll.SimpleItemTouchHelperCallback;
import com.tdActivity.android.interfaceAll.StartDragListener;
import com.tdActivity.android.interfaceAll.UpdateIcon;
import com.tdActivity.android.util.JSONUtils;
import com.tdActivity.android.util.ListUtils;
import com.tdActivity.android.util.LocalSynchUtils;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.view.CustomProgressDialog;
import com.tdActivity.android.view.ItemDecoration;
import com.tdActivity.android.view.PopMenu;
import com.tdActivity.android.view.SlidingMenu;
import com.tdActivity.android.view.SwipeMenuRecyclerView;
import com.umeng.analytics.MobclickAgent;

public class AccountFragment extends Fragment implements View.OnClickListener, StartDragListener {

	private SharedPreferences mSharedPreferences;
	private boolean isActive = true;
	private PopupWindow window;
	private View view = null, v, itemView;
	ItemTouchHelper itemTouchHelper;
	private SwipeMenuRecyclerView mRecyclerView;
	public static RecyclerViewAdapter mAdapter;
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private TextView add_btn;
	private TextView title;
	private PopMenu mPopmenu;
	private AddAccountAdapter aAdapter;
	private ImageView icon;
	private List<Operation> data;
	public SlideMenuFragment f = null;
	private UpdateIcon uIcon;
	public static int type = 1;
	private VersionOneDao mDao;
	private CustomProgressDialog progressDialog = null;
	private MyApplication myApplication;
	boolean flag=true;
	ImageView img_add;
	public AccountFragment(SlideMenuFragment f) {
		this.f = f;
	}

	public AccountFragment() {

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x101) {
				mAdapter.setAccounts(((MyApplication) getActivity().getApplication()).userInfos);
				mAdapter.notifyDataSetChanged();
			}

		};
	};

	@Override
	public void startDrag(RecyclerView.ViewHolder viewHolder) {
		itemTouchHelper.startDrag(viewHolder);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication = (MyApplication) getActivity().getApplication();
		mDao = myApplication.getDbHelper();
	};

	@SuppressLint("NewApi")
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_account, null, false);
			itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_simple, null);
		}
		icon = (ImageView) itemView.findViewById(R.id.imageDrag);
		if (SlidingMenu.ignoredViews != null) {
			SlidingMenu.removeAllIgnoredView();
		}
		v = view.findViewById(R.id.swipeRefreshLayout);
		back_addu_btn = (ImageButton) view.findViewById(R.id.back_addu_btn2);
		click_back_btn = (TextView) view.findViewById(R.id.click_back_btn2);
		click_back_btn.setOnClickListener(this);
		add_btn = (TextView) view.findViewById(R.id.save_btn2);
		//		add_btn.setText("＋");
		//		add_btn.setTextColor(Color.WHITE);
		//		add_btn.setTextSize(36);
		add_btn.setOnClickListener(this);
		title = (TextView) view.findViewById(R.id.tv_headadd2);
		title.setText(" 账户管理");
		mPopmenu = new PopMenu(getActivity());
		mRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView);
		ItemDecoration dividerLine = new ItemDecoration(ItemDecoration.VERTICAL);
		dividerLine.setSize(1);
		dividerLine.setColor(0xFFDDDDDD);
		mRecyclerView.addItemDecoration(dividerLine);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setOpenInterpolator(new BounceInterpolator());
		mRecyclerView.setCloseInterpolator(new BounceInterpolator());
		setRecyclerview(mRecyclerView);
		img_add=(ImageView) view.findViewById(R.id.img_add);
		return view;
	}

	private void setRecyclerview(SwipeMenuRecyclerView mRecyclerView) {
		mAdapter = new RecyclerViewAdapter(getActivity(), ((MyApplication) getActivity().getApplication()).userInfos,
				mRecyclerView, AccountFragment.this);
		uIcon = mAdapter;
		mAdapter.setU(MainActivity.mainFragment);
		mAdapter.setD(MainActivity.mainFragment);
		itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mAdapter));
		itemTouchHelper.attachToRecyclerView(mRecyclerView);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click_back_btn2:
			MainActivity.mMenu.toggle();
			break;
		case R.id.save_btn2:
//			if ("+".equals(add_btn.getText())) {
//				showWindow();
//			} 
			if (flag==true) {
				showWindow();
			}
			else {
				img_add.setVisibility(View.VISIBLE);
				add_btn.setText("");
				flag=true;
				//add_btn.setText("＋");
				//add_btn.setTextSize(36);
				// 进行判读
	//			type = 0;
	//			setVisiable();
	//			uIcon.openOrCloseIcon();
				//				setRecyclerview(mRecyclerView);
//				if (!LocalSynchUtils.isSame(myApplication.oldUserInfos, myApplication.userInfos)) {// 确实做出了改变进行更新
//					// 先进行排序操作
//					//					progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, getActivity(),
//					//							ProgressShowUtils.CAPTURE);
//					myApplication.isChanged = true;// 有改变
//					final ArrayList<UserInfo> userInfos=(ArrayList<UserInfo>) ListUtils.copyToAnother(myApplication.userInfos);
//					LocalSynchUtils.copyNewToOld(myApplication.oldUserInfos, myApplication.userInfos);//来数据进行更新
//					myApplication.fixedThreadPool.execute(new Runnable() {
//
//						@Override
//						public void run() {
//							int len = userInfos.size();
//							for (int i = 0; i < len; i++) {
//								mDao.userInfoUpdate(userInfos.get(i), i);
//							}
//							//			ProgressShowUtils.stopProgressDialog(progressDialog);
//						}
//
//					});
//					// 再进行备份操作		
//					LocalSynchUtils.updateCacheFile(myApplication,userInfos);//写进临时文件中
//					//					userInfos=null;
//				}
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("AccountFragment"); 
		handler.sendEmptyMessage(0x101);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("AccountFragment");
	}

	protected void showWindow() {
		data = new ArrayList<Operation>();
		Operation op = new Operation();
		op.icon = R.drawable.smewm;
		op.text = "扫二维码";
		data.add(op);
		Operation op1 = new Operation();
		op1.icon = R.drawable.iconfont_tianjia;
		op1.text = "手动添加";
		data.add(op1);
//		Operation op2 = new Operation();
//		op2.icon = R.drawable.iconfont_paixu;
//		op2.text = "账号排序";
//		data.add(op2);
		aAdapter = new AddAccountAdapter(getActivity());
		aAdapter.setData(data);
		mPopmenu.setAdapter(aAdapter);
		mPopmenu.setOnItemClickListener(popmenuItemClickListener);
		aAdapter.notifyDataSetChanged();
		mPopmenu.showAsDropDown(add_btn);

	}

	// mPopmenu每个条目的点击事件
	AdapterView.OnItemClickListener popmenuItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (data.get(position).text.equals("扫二维码")) {
				Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
				intent1.putExtra("AddUserFragment", 2);
				startActivity(intent1);
				mPopmenu.dismiss();
			}
			if (data.get(position).text.equals("手动添加")) {
				Intent intent1 = new Intent(getActivity(), HanldAddActivity.class);
				intent1.putExtra("AddUserFragment", 2);
				startActivity(intent1);
				mPopmenu.dismiss();
			}
//			if (data.get(position).text.equals("账号排序")) {
//				img_add.setVisibility(View.GONE);
//				flag=false;
//				add_btn.setText(" 完成");
//				add_btn.setTextColor(Color.WHITE);
//				add_btn.setTextSize(18);
////				type = 1;
////				//				setVisiable();
////				uIcon.openOrCloseIcon();
////				//				setRecyclerview(mRecyclerView);
//				mPopmenu.dismiss();
//			}
		}
	};
	public void	setVisiable(){
		View itemView1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_simple, null);
		ImageView image = (ImageView) itemView1.findViewById(R.id.imageDrag);
		TextView text = (TextView)itemView1.findViewById(R.id.tv_onclicktouch);
		if(type == 0){
			setDisEnable(image, text);
		}else {
			setEnable(image, text);
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
		mAdapter.notifyDataSetChanged();
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
		mAdapter.notifyDataSetChanged();
	}
}
