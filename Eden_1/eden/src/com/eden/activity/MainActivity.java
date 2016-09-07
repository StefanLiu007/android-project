package com.eden.activity;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.eden.R;
import com.eden.adapter.GroupAdapter;
import com.eden.fragment.ExpertFragment;
import com.eden.fragment.FindFragment;
import com.eden.fragment.HomePageFragment;
import com.eden.fragment.SettingFragment;
import com.eden.util.MyApplication;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;

public class MainActivity extends Activity implements OnClickListener
{
	private ListView lv_group;
	private List<String> groups;
	private PopupWindow popupWindow;
	private LinearLayout mTabWeixin;
	private LinearLayout mTabFrd;
	private LinearLayout mTabAddress;
	private LinearLayout mTabSettings;
	private TextView mTv;
	private ImageButton mImgWeixin;
	private ImageButton mImgFrd;
	private ImageButton mImgAddress;
	private ImageButton mImgSettings;
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener  = new MyLocationListenner();
	private HomePageFragment mTab01;
	private ExpertFragment mTab02;
	private FindFragment mTab03;
	private SettingFragment mTab04;
	private TextView head_title;
	private ImageView userIcon;
	private ImageView message;
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
		MyApplication.AddActivity(this);
		initView();
		initEvent();
		Log.i("MainActivity", "11");
		setSelect(0);
		mLocationClient.start();
	}
	private void setLocationOption() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setScanSpan(0);//打开gps
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setAddrType("all");
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setPriority(LocationClientOption.GpsFirst);       //gps 
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}
	
	public class MyLocationListenner implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location.getCity()==null){
				mLocationClient.requestLocation();
			}else{
				mTv.setText(""+location.getCity());
			}
		}
	}
	private void initEvent()
	{
		mTabWeixin.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabSettings.setOnClickListener(this);

	}

	private void initView()
	{
		mTv = (TextView) findViewById(R.id.tv);
		head_title=(TextView)findViewById(R.id.head_title);
		head_title.setText("主页");
		userIcon=(ImageView)findViewById(R.id.leftMenu);
		message = (ImageView) findViewById(R.id.teach_infor);
		message.setOnClickListener(this);
		mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);

		mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
		mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);

		MyAsyncTask asyncTask=new MyAsyncTask(userIcon, this);
		asyncTask.execute(MyGetUrl.getUrl()+getSharedPreferences("user", MODE_PRIVATE).getString("picture", ""));
	}

	private void setSelect(int i)
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (mTab01 == null)
			{
				mTab01 = new HomePageFragment();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				transaction.show(mTab01);
			}
			mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case 1:
			if (mTab02 == null)	
			{
				mTab02 = new ExpertFragment();transaction.add(R.id.id_content, mTab02);
			} else
			{
				transaction.show(mTab02);

			}
			mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 2:
			if (mTab03 == null)
			{
				mTab03 = new FindFragment();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				transaction.show(mTab03);
			}
			mImgAddress.setImageResource(R.drawable.tab_address_pressed);
			break;
		case 3:
			if (mTab04 == null)
			{
				mTab04 = new SettingFragment();
				transaction.add(R.id.id_content, mTab04);
			} else
			{
				transaction.show(mTab04);
			}
			mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
			break;


		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
		if (mTab04 != null)
		{
			transaction.hide(mTab04);
		}
	}

	@Override
	public void onClick(View v)
	{
		resetImgs();
		switch (v.getId())
		{
		case R.id.id_tab_weixin:
			head_title.setText("主页");
			setSelect(0);
			break;
		case R.id.id_tab_frd:
			head_title.setText("专家");
			setSelect(1);
			break;
		case R.id.id_tab_address:
			head_title.setText("发现");
			setSelect(2);
			break;
		case R.id.id_tab_settings:
			head_title.setText("个人中心");
			setSelect(3);
			break;
		case R.id.teach_infor:
			showWindow(v);
			break;

		default:
			break;
		}
	}

	/**
	 * 切换图片至暗色
	 */
	private void resetImgs()
	{
		mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
		mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
		mImgAddress.setImageResource(R.drawable.tab_address_normal);
		mImgSettings.setImageResource(R.drawable.tab_settings_normal);
	}

	private void showWindow(View parent) {

		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = layoutInflater.inflate(R.layout.group_list, null);

			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			// 加载数据
			groups = new ArrayList<String>();
			groups.add("我的微博");
			groups.add("好友");
			groups.add("亲人");
			groups.add("陌生人");

			GroupAdapter groupAdapter = new GroupAdapter(this, groups);
			lv_group.setAdapter(groupAdapter);
			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(view, 400, 500);
		}

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;


		popupWindow.showAsDropDown(parent, xPos, 0);

		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				Toast.makeText(MainActivity.this,
						"groups.get(position)" + groups.get(position), 1000)
						.show();

				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {


			final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
			alterDialog.setMessage("确定退出应用？");
			alterDialog.setCancelable(true);

			alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
//					File mCacheDir=null;
//					if (android.os.Environment.getExternalStorageState().
//							equals(android.os.Environment.MEDIA_MOUNTED))
//						mCacheDir = new File("/imagecache");
//					else
//						mCacheDir =getCacheDir();// 如何获取系统内置的缓存存储路径
//					if(!mCacheDir.exists())
//						mCacheDir.mkdirs();
//					
//					mCacheDir.deleteOnExit();
					MyApplication.finishAllActivity();
				}
			});
			alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			alterDialog.show();
		}

		return false;
	}
	@Override
	protected void onDestroy() {
		MyApplication.getHttpQueue().cancelAll("picture");
		mLocationClient.stop();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		MyApplication.getHttpQueue().cancelAll("picture");
		super.onStop();
	}

}
