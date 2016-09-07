package com.tdActivity.android.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.AppManager;
import com.tdActivity.android.entity.JsonDTO;
import com.tdActivity.android.receiver.ScreenBroadCastReceiver;
import com.tdActivity.android.util.Constant;
import com.tdActivity.android.util.FontManager;
import com.tdActivity.android.util.HttpUtil;
import com.tdActivity.android.util.ProgressShowUtils;
import com.tdActivity.android.util.RegisterBroadcast;
import com.tdActivity.android.util.Setting;
import com.tdActivity.android.view.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发生短信
 * 
 * @author leo.huang
 *
 */
public class FindPassWord extends BaseActivity implements OnClickListener {
	private EditText ed_user_phone, ed_iput_code;
	private TextView tv_get_code;
	private int time;
	private Timer mTimer;
	private String log = "";
	// private Button back_fpw_btn;
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private Button savebtn;
	private CustomProgressDialog progressDialog = null;
	private Intent mIntent;
	private boolean bind;
	private TextView tv_headadd;
	private int fromActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.findpassword_fragment);
		AppManager.getAppManager().addActivity(this);
		intviews();

	}

	private void intviews() {
		tv_headadd = (TextView) findViewById(R.id.tv_headadd);
		tv_headadd.setText("验证手机");
		ed_user_phone = (EditText) findViewById(R.id.ed_user_phone);
		ed_iput_code = (EditText) findViewById(R.id.ed_iput_code);
		tv_get_code = (TextView) findViewById(R.id.txt_get_code);
		tv_get_code.setOnClickListener(this);
		back_addu_btn = (ImageButton) findViewById(R.id.back_addu_btn);
		back_addu_btn.setBackgroundResource(R.drawable.backnew);
		click_back_btn = (TextView) findViewById(R.id.click_back_btn);
		click_back_btn.setOnClickListener(this);
		savebtn = (Button) findViewById(R.id.savebtn);
		savebtn.setOnClickListener(this);
		mIntent = getIntent();
		bind = mIntent.getBooleanExtra("bind", false);
		fromActivity = mIntent.getIntExtra(Setting.FROM_ACTIVITY, -1);
	}

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0x1001:
				delay();
				break;
			case 0x1002:
				tv_get_code.setBackgroundColor(getResources().getColor(R.color.back));
				tv_get_code.setText("获取验证码");
				tv_get_code.setClickable(true);
				if (mTimer != null) {
					mTimer.cancel();
				}
				Toast.makeText(FindPassWord.this, log, Toast.LENGTH_SHORT).show();
				break;
			case 0x1003:
				ProgressShowUtils.stopProgressDialog(progressDialog);
				Toast.makeText(FindPassWord.this, "请检查网络连接!", Toast.LENGTH_SHORT).show();
				break;

			case 0x1004:
				tv_get_code.setBackgroundColor(getResources().getColor(R.color.backno));
				tv_get_code.setText(time + "秒后重新获取");
				tv_get_code.setBackgroundResource(R.drawable.btn_obtain_auth_code_wait);
				if (time <= 0) {
					tv_get_code.setBackgroundResource(R.drawable.btn_obtain_auth_code);
					tv_get_code.setText("获取验证码");
					tv_get_code.setClickable(true);
					mTimer.cancel();
				}
				break;
			case 0x1005:
				ProgressShowUtils.stopProgressDialog(progressDialog);
				Toast.makeText(FindPassWord.this, log, Toast.LENGTH_SHORT).show();
				break;
			case 0x1006:
				ProgressShowUtils.stopProgressDialog(progressDialog);
				if (bind) {
					Toast.makeText(FindPassWord.this, "绑定成功", Toast.LENGTH_SHORT).show();
					Intent mIntent = new Intent(FindPassWord.this, RegistActivity.class);
					startActivity(mIntent);
				} else {
					Toast.makeText(FindPassWord.this, "找回成功", Toast.LENGTH_SHORT).show();
					Intent mIntent = new Intent(FindPassWord.this, RegistActivity.class);
					mIntent.putExtra(Setting.FROM_ACTIVITY, fromActivity);
					startActivity(mIntent);
				}
				finish();// 关闭界面
				break;
			}

		}
	};

	/**
	 * 获取验证码
	 * 
	 * @param phoneNum
	 * @throws Exception
	 */
	private void sendSms(String phoneNum) throws Exception {
		String url = "getSmsVerify?mobile=" + phoneNum;

		JSONObject json = HttpUtil.getRequest(url);

		if (json.optInt("code") == 0) {
			handler.sendEmptyMessage(0x1001);
		} else {
			log = json.optString("error");

			handler.sendEmptyMessage(0x1002);
		}

	}

	/**
	 * 验证用户输入手机号的合法性
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public boolean isMobilePhoneNumber(String phoneNumber) {
		String MOBILE = "^0?1\\d{10}$";
		return phoneNumber.matches(MOBILE);
	}

	/**
	 * 获取验证码
	 */
	public void getCodeThread() {
		final String phone = ed_user_phone.getText().toString().trim();
		if (phone == null || phone.equals("")) {
			Toast.makeText(FindPassWord.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
		} else if (!isMobilePhoneNumber(phone)) {
			Toast.makeText(FindPassWord.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
		} else {

			fixedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						sendSms(phone);
					} catch (Exception e) {
						handler.sendEmptyMessage(0x1003);
					}
				}
			});

		}
	}

	/**
	 * 限时获取验证码
	 */
	public void delay() {
		time = 60;
		mTimer = new Timer();
		tv_get_code.setClickable(false);
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(0x1004);
				time--;
			}

		}, 10, 1000);
	}

	/**
	 * 重新设置手势密码
	 * 
	 * @throws Exception
	 */
	private void Findpaswold() throws Exception {
		String url = "resetPassword";
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("mobile", ed_user_phone.getText().toString().trim());
		rawParams.put("smsCode", ed_iput_code.getText().toString().trim());
		rawParams.put("uuid", Constant.UUID);
		JSONObject json = HttpUtil.postRequest(url, rawParams);

		if (json.optInt("code") == 0) {
			handler.sendEmptyMessage(0x1006);
		} else {
			log = json.optString("error");
			handler.sendEmptyMessage(0x1005);
		}

	}

	/**
	 * 绑定手机
	 * 
	 * @throws Exception
	 */
	private void bindWord() throws Exception {
		String url = "bindMobile";
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("mobile", ed_user_phone.getText().toString().trim());
		rawParams.put("smsCode", ed_iput_code.getText().toString().trim());
		rawParams.put("uuid", Constant.UUID);
		JSONObject json = HttpUtil.postRequest(url, rawParams);

		if (json.optInt("code") == 0) {
			handler.sendEmptyMessage(0x1006);
		} else {
			log = json.optString("error");
			handler.sendEmptyMessage(0x1005);
		}

	}

	public void FindPDThread() {

		final String phone = ed_user_phone.getText().toString().trim();

		String code = ed_iput_code.getText().toString().trim();

		if (phone == null || phone.equals("")) {
			Toast.makeText(FindPassWord.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
		} else if (!isMobilePhoneNumber(phone)) {
			Toast.makeText(FindPassWord.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
		} else if (code == null || code.equals("")) {
			Toast.makeText(FindPassWord.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
		} else {
			progressDialog = ProgressShowUtils.startProgressDialog(progressDialog, FindPassWord.this,
					ProgressShowUtils.MESSAGE);

			fixedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						if (bind) {
							bindWord();// 进行绑定
						} else {
							Findpaswold();// 进行重设
						}

					} catch (Exception e) {
						handler.sendEmptyMessage(0x1003);
					}
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click_back_btn:// 返回按钮
			// 隐藏软件盘
			View view = getWindow().peekDecorView();
			if (view != null) {
				InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
			setResult(0x01);
			finish();

			break;
		case R.id.txt_get_code:

			getCodeThread();

			break;
		case R.id.savebtn:// 进行选择判断

			// TODO 调试完成 删除下面代码。取消注释
			handler.sendEmptyMessage(0x1006);
//			 FindPDThread();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("FindPassWord"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
	    MobclickAgent.onPause(this);
	}
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onPageStart("FindPassWord"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
	MobclickAgent.onResume(this);
}
}
