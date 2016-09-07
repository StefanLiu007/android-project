package com.tdActivity.android.fragment;

import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.speech.SynthesizerPlayer;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.activity.R;
import com.tdActivity.android.adapter.RecyclerViewAdapter.Deletedate;
import com.tdActivity.android.adapter.RecyclerViewAdapter.UpdateDate;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.Base32String;
import com.tdActivity.android.util.Base32String.DecodingException;
import com.tdActivity.android.util.FontManager;
import com.tdActivity.android.util.PasscodeGenerator;
import com.tdActivity.android.view.CornerListView;
import com.tdActivity.android.view.Rotate3dAnimation;
import com.tdActivity.android.view.Rotate3dAnimation.InterpolatedTimeListener;
import com.tdActivity.android.view.RoundProgressBar;
import com.tdActivity.android.view.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

public class MainFragment extends Fragment implements OnClickListener,
InterpolatedTimeListener, UpdateDate, Deletedate {
	private RelativeLayout layout;
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private Button more_btn;
	private TextView tvtitle;
	private TextView dtcode1, dtcode2, dtcode3, dtcode4, dtcode5, dtcode6;
	private TextView read_ear;
	private LinearLayout ll_onclick_read;
	private RoundProgressBar roundProgressBar;
	private Boolean flag = true;
	private String dtpassdworld, name, yy;
	private int time;
	private Timer mTimer;
	protected static final int UI_LOG_TO_VIEW = 0;
	protected static final int UI_TOAST = 1;
	private PopupWindow window;

	private int index = 0;
	private boolean isPlaying = false;
	private static final String APPID = "appid=519328ab";
	private View view = null;
	/** 指定license路径，需要保证该路径的可读写权限 */
	private static final String LICENCE_FILE_NAME = Environment
			.getExternalStorageDirectory() + "/tts/baidu_tts_licence.dat";
	// private SharedPreferences preferences;

	/**
	 * Task that periodically notifies this activity about the amount of time
	 * remaining until the TOTP codes refresh. The task also notifies this
	 * activity when TOTP codes refresh.
	 */
	public SlideMenuFragment f = null;

	public MainFragment(SlideMenuFragment f) {
		this.f = f;
	}

	public MainFragment() {
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x1004:

				if (time == 0) {
					time = 30;
					try {
						setAnimation();
						dtcode1.setText(getCheckCode(dtpassdworld).charAt(0)
								+ "");
						dtcode2.setText(getCheckCode(dtpassdworld).charAt(1)
								+ "");
						dtcode3.setText(getCheckCode(dtpassdworld).charAt(2)
								+ "");
						dtcode4.setText(getCheckCode(dtpassdworld).charAt(3)
								+ "");
						dtcode5.setText(getCheckCode(dtpassdworld).charAt(4)
								+ "");
						dtcode6.setText(getCheckCode(dtpassdworld).charAt(5)
								+ "");
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
					} catch (DecodingException e) {
						e.printStackTrace();
					}
				}
				roundProgressBar.setProgress(time);
				roundProgressBar.setText(time + "");
				break;
			case 0x1001:
				tvtitle.setText(yy);
				if (dtpassdworld != null && !dtpassdworld.equals("")) {
					try {
						if (mTimer != null) {
							mTimer.cancel();
							delay();
						} else {
							delay();
						}
						// setAnimation();
						dtcode1.setText(getCheckCode(dtpassdworld).charAt(0)
								+ "");
						dtcode2.setText(getCheckCode(dtpassdworld).charAt(1)
								+ "");
						dtcode3.setText(getCheckCode(dtpassdworld).charAt(2)
								+ "");
						dtcode4.setText(getCheckCode(dtpassdworld).charAt(3)
								+ "");
						dtcode5.setText(getCheckCode(dtpassdworld).charAt(4)
								+ "");
						dtcode6.setText(getCheckCode(dtpassdworld).charAt(5)
								+ "");
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
					} catch (DecodingException e) {
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getActivity(), "请添加或者选择账号",
							Toast.LENGTH_SHORT).show();
				}
				roundProgressBar.setProgress(time);
				roundProgressBar.setText(time + "");

				break;
			case 0x1002:

				if (((MyApplication) getActivity().getApplication()).userInfos != null
				&& ((MyApplication) getActivity().getApplication()).userInfos
				.size() > 0) {
					dtpassdworld = ((MyApplication) getActivity()
							.getApplication()).userInfos.get(0).password;
					yy = ((MyApplication) getActivity().getApplication()).userInfos
							.get(0).yy;
				}

				tvtitle.setText(yy);
				if (dtpassdworld != null && !dtpassdworld.equals("")) {

					if (mTimer != null) {
						mTimer.cancel();
						delay();
					} else {
						delay();
					}
					try {
						dtcode1.setText(getCheckCode(dtpassdworld).charAt(0)
								+ "");
						dtcode2.setText(getCheckCode(dtpassdworld).charAt(1)
								+ "");
						dtcode3.setText(getCheckCode(dtpassdworld).charAt(2)
								+ "");
						dtcode4.setText(getCheckCode(dtpassdworld).charAt(3)
								+ "");
						dtcode5.setText(getCheckCode(dtpassdworld).charAt(4)
								+ "");
						dtcode6.setText(getCheckCode(dtpassdworld).charAt(5)
								+ "");
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
					} catch (DecodingException e) {
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getActivity(), "请添加或者选择账号",
							Toast.LENGTH_SHORT).show();
				}
				roundProgressBar.setProgress(time);
				roundProgressBar.setText(time + "");
				break;
			default:
				break;
			}
		};
	};

	public void setAnimation() {
		ObjectAnimator o1 = ObjectAnimator
				.ofFloat(dtcode1, "alpha", 1.0f, 0.5f);
		ObjectAnimator o2 = ObjectAnimator
				.ofFloat(dtcode1, "alpha", 0.5f, 1.0f);
		ObjectAnimator o3 = ObjectAnimator
				.ofFloat(dtcode2, "alpha", 1.0f, 0.5f);
		ObjectAnimator o4 = ObjectAnimator
				.ofFloat(dtcode2, "alpha", 0.5f, 1.0f);
		ObjectAnimator o5 = ObjectAnimator
				.ofFloat(dtcode3, "alpha", 1.0f, 0.5f);
		ObjectAnimator o6 = ObjectAnimator
				.ofFloat(dtcode3, "alpha", 0.5f, 1.0f);
		ObjectAnimator o7 = ObjectAnimator
				.ofFloat(dtcode4, "alpha", 1.0f, 0.5f);
		ObjectAnimator o8 = ObjectAnimator
				.ofFloat(dtcode4, "alpha", 0.5f, 1.0f);
		ObjectAnimator o9 = ObjectAnimator
				.ofFloat(dtcode5, "alpha", 1.0f, 0.5f);
		ObjectAnimator o10 = ObjectAnimator.ofFloat(dtcode5, "alpha", 0.5f,
				1.0f);
		ObjectAnimator o11 = ObjectAnimator.ofFloat(dtcode6, "alpha", 1.0f,
				0.5f);
		ObjectAnimator o12 = ObjectAnimator.ofFloat(dtcode6, "alpha", 0.5f,
				1.0f);
		AnimatorSet set = new AnimatorSet();
		set.playTogether(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12);
		set.setDuration(500);
		set.start();
	}

	static String getCheckCode(String secret) throws GeneralSecurityException,
	DecodingException {
		final byte[] keyBytes = Base32String.decode(secret);
		Mac mac = Mac.getInstance("HMACSHA1");
		mac.init(new SecretKeySpec(keyBytes, ""));
		PasscodeGenerator pcg = new PasscodeGenerator(mac);
		long T0 = 0;
		long X = 30;
		Calendar cal = Calendar.getInstance();
		long time = cal.getTimeInMillis() / 1000;
		long T = (time - T0) / X;
		return pcg.generateResponseCode(T);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (SlidingMenu.ignoredViews != null) {
			SlidingMenu.removeAllIgnoredView();
		}

		if (view == null) {
			view = LayoutInflater.from(getActivity()).inflate(
					R.layout.contentview, null);
		}
		layout = (RelativeLayout) view.findViewById(R.id.frameLayout1);
		tvtitle = (TextView) view.findViewById(R.id.tv_headadd);
		back_addu_btn = (ImageButton) view.findViewById(R.id.back_addu_btn);
		click_back_btn = (TextView) view.findViewById(R.id.click_back_btn);
		click_back_btn.setOnClickListener(this);
		more_btn = (Button) view.findViewById(R.id.save_btn);
		more_btn.setBackgroundDrawable(null);
		more_btn.setText("更多");
		more_btn.setTextColor(Color.WHITE);
		more_btn.setTextSize(18);
		more_btn.setOnClickListener(this);
		read_ear = (TextView) view.findViewById(R.id.read_ear);
		read_ear.setText(getResources().getString(R.string.aaa));
		read_ear.setTypeface(FontManager.getTypeface(getActivity(),
				FontManager.FONTAWESOME));
		ll_onclick_read = (LinearLayout) view
				.findViewById(R.id.ll_onclick_read);
		ll_onclick_read.setOnClickListener(this);
		dtcode1 = (TextView) view.findViewById(R.id.dtcode1);
		dtcode2 = (TextView) view.findViewById(R.id.dtcode2);
		dtcode3 = (TextView) view.findViewById(R.id.dtcode3);
		dtcode4 = (TextView) view.findViewById(R.id.dtcode4);
		dtcode5 = (TextView) view.findViewById(R.id.dtcode5);
		dtcode6 = (TextView) view.findViewById(R.id.dtcode6);
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/watertext.ttf");
		dtcode1.setTypeface(face);
		dtcode2.setTypeface(face);
		dtcode3.setTypeface(face);
		dtcode4.setTypeface(face);
		dtcode5.setTypeface(face);
		dtcode6.setTypeface(face);
		roundProgressBar = (RoundProgressBar) view
				.findViewById(R.id.roundProgressBar);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainFragment"); 
		handler.sendEmptyMessage(0x1002);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainFragment");
	}

	/**
	 * 加载数据
	 */

	/**
	 * 30s计时
	 */
	public void delay() {
		if (mTimer != null) {
			mTimer.cancel();
		}
		time = 30 - (int) (System.currentTimeMillis() / 1000) % 30;
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				time--;
				handler.sendEmptyMessage(0x1004);
			}

		}, 1000, 1000);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click_back_btn:
			MainActivity.mMenu.toggle();
			break;
		case R.id.save_btn:

			showWindow();

			break;
		case R.id.ll_onclick_read:
			if (isNetworkAvailable(getActivity())) {
				SynthesizerPlayer player = SynthesizerPlayer
						.createSynthesizerPlayer(getActivity(), APPID);
				// 设置语音朗读者，可以根据�?要设置男女朗读，具体请看api文档和官方论�?
				player.setVoiceName("vivixiaoqi");// 在此设置语音播报的人选例如：vivixiaoyan、vivixiaomei、vivixiaoqi
				player.playText(dtcode1.getText().toString() + " "
						+ dtcode2.getText().toString() + " "
						+ dtcode3.getText().toString() + " "
						+ dtcode4.getText().toString() + " "
						+ dtcode5.getText().toString() + " "
						+ dtcode6.getText().toString(), "ent=vivi21,bft=5",
						null);
			} else {
				Toast.makeText(getActivity(), "请检查网络！确认连接后使用此功能",
						Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}

	/**
	 * �?测当的网络（WLAN�?3G/2G）状�?
	 * 
	 * @param context
	 *            Context
	 * @return true 表示网络可用
	 */
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前�?连接的网络可�?
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 创建PopupWindow
	 */

	protected void showWindow() {

		LayoutInflater lay = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		View popview = lay.inflate(R.layout.user_popwind_lay, null);
		TextView tv_single = (TextView) popview.findViewById(R.id.tv_single);
		tv_single.setText(popview.getResources().getString(R.string.bbb));
		tv_single.setTypeface(FontManager.getTypeface(getActivity(),
				FontManager.FONTAWESOME));
		CornerListView listView = (CornerListView) popview
				.findViewById(R.id.userlist);
		Myadapter adapter = new Myadapter();
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				closePopupWindow();
				dtpassdworld = ((MyApplication) getActivity().getApplication()).userInfos
						.get(position).password;
				yy = ((MyApplication) getActivity().getApplication()).userInfos
						.get(position).yy;

				applyRotation(0, 180);
			}
		});

		window = new PopupWindow(popview, (int) getActivity().getResources()
				.getDimension(R.dimen.x170), LayoutParams.WRAP_CONTENT);
		window.setAnimationStyle(R.style.Animtop);
		window.setFocusable(true);
		window.setTouchable(true);
		window.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(-00000);
		window.setBackgroundDrawable(dw);
		backgroundAlpha(0.5f);
		window.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				backgroundAlpha(1.0f);
				closePopupWindow();
			}
		});
		window.showAsDropDown(more_btn, 10, 0);
		window.update();
	}

	private void applyRotation(float start, float end) {
		// 计算中心�?
		final float centerX = layout.getWidth() / 2.0f;
		final float centerY = layout.getHeight() / 2.0f;
		final Rotate3dAnimation rotation = new Rotate3dAnimation(getActivity(),
				start, end, centerX, centerY, 1.0f, true);
		rotation.setDuration(600);
		rotation.setFillAfter(true);
		rotation.setInterpolatedTimeListener(this);
		//	rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setInterpolator(new DecelerateInterpolator());
		// ObjectAnimator o1 = ObjectAnimator.ofFloat(layout, "alpha",
		// 1.0f,0.5f);
		// ObjectAnimator o2 = ObjectAnimator.ofFloat(layout, "alpha",
		// 0.5f,1.0f);
		ObjectAnimator o3 = ObjectAnimator
				.ofFloat(layout, "scaleX", 1.0f, 0.9f);
		ObjectAnimator o4 = ObjectAnimator
				.ofFloat(layout, "scaleX", 0.9f, 1.0f);
		ObjectAnimator o5 = ObjectAnimator
				.ofFloat(layout, "scaleY", 1.0f, 0.9f);
		ObjectAnimator o6 = ObjectAnimator
				.ofFloat(layout, "scaleY", 0.9f, 1.0f);
		o5.setInterpolator(new DecelerateInterpolator());
		// o6.setInterpolator(new AccelerateInterpolator());
		o6.setInterpolator(new DecelerateInterpolator());
		AnimatorSet set = new AnimatorSet();
		set.play(o3).before(o4);
		set.play(o5).before(o6);
		// set.play(o1).before(o2);
		set.setDuration(300);
		set.start();
		layout.startAnimation(rotation);
	}

	class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ((MyApplication) getActivity().getApplication()).userInfos
					.size();
		}

		@Override
		public Object getItem(int position) {
			return ((MyApplication) getActivity().getApplication()).userInfos
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(getActivity()).inflate(
					R.layout.pop_listview_item, null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.tv_list_item);

			textView.setText(((MyApplication) getActivity().getApplication()).userInfos
					.get(position).yy);

			return convertView;
		}
	}

	/**
	 * 设置添加屏幕的背景�?�明�?
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}

	/**
	 * 关闭窗口
	 */
	private void closePopupWindow() {
		if (window != null && window.isShowing()) {
			window.dismiss();
			window = null;

		}
	}

	@Override
	public void interpolatedTime(float interpolatedTime) {
		if (interpolatedTime > 0.5f) {
			handler.sendEmptyMessage(0x1001);
		}
	}

	@Override
	public void update(UserInfo user) {
		dtpassdworld = user.password;
		yy = user.yy;
		tvtitle.setText(yy);
		if (dtpassdworld != null && !dtpassdworld.equals("")) {
			try {
				if (mTimer != null) {
					mTimer.cancel();
					delay();
				} else {
					delay();
				}
				// setAnimation();
				dtcode1.setText(getCheckCode(dtpassdworld).charAt(0) + "");
				dtcode2.setText(getCheckCode(dtpassdworld).charAt(1) + "");
				dtcode3.setText(getCheckCode(dtpassdworld).charAt(2) + "");
				dtcode4.setText(getCheckCode(dtpassdworld).charAt(3) + "");
				dtcode5.setText(getCheckCode(dtpassdworld).charAt(4) + "");
				dtcode6.setText(getCheckCode(dtpassdworld).charAt(5) + "");
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (DecodingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete() {
		handler.sendEmptyMessage(0x1002);
	}

}
