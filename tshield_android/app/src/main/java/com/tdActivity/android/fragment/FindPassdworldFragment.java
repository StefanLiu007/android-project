package com.tdActivity.android.fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.activity.FindPassWord;
import com.tdActivity.android.activity.LoginActivity;
import com.tdActivity.android.activity.MainActivity;
import com.tdActivity.android.activity.RegistActivity;
import com.tdActivity.android.entity.JsonDTO;
import com.tdActivity.android.util.Constant;
import com.tdActivity.android.util.HttpUtil;
import com.tdActivity.android.view.CustomProgressDialog;
import com.tdActivity.android.view.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FindPassdworldFragment extends Fragment implements OnClickListener {
	private EditText ed_user_phone, ed_iput_code;
	private TextView tv_get_code;
	private int time;
	private Timer mTimer;
	private String log;
	private ImageButton back_fpw_btn;
	private ImageButton back_addu_btn;
	private TextView click_back_btn,tv_headadd;
	private Button savebtn, creatbtn, canclebtn;
	private SharedPreferences saveMemberInfo;
	private Editor editor;

	private CustomProgressDialog progressDialog = null;
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0x1001:
				delay();
				break;
			case 0x1002:
				tv_get_code.setBackgroundColor(getActivity().getResources()
						.getColor(R.color.back));
				tv_get_code.setText("获取验证码");
				tv_get_code.setClickable(true);
				if (mTimer != null) {
					mTimer.cancel();
				}
				Toast.makeText(getActivity(), log, Toast.LENGTH_SHORT).show();
				break;
			case 0x1003:
				stopProgressDialog();
				Toast.makeText(getActivity(), "请检查网络连接!", Toast.LENGTH_SHORT)
						.show();
				break;

			case 0x1004:
				tv_get_code.setBackgroundColor(getActivity().getResources()
						.getColor(R.color.backno));
				tv_get_code.setText(time + "秒后重新获取");
				if (time <= 0) {
					tv_get_code.setBackgroundColor(getActivity().getResources()
							.getColor(R.color.back));
					tv_get_code.setText("获取验证码");
					tv_get_code.setClickable(true);
					mTimer.cancel();
				}
				break;
			case 0x1005:
				stopProgressDialog();
				Toast.makeText(getActivity(), log, Toast.LENGTH_SHORT).show();
				break;
			case 0x1006:
				stopProgressDialog();
				Toast.makeText(getActivity(), "绑定成功", Toast.LENGTH_SHORT)
						.show();
				startActivity(new Intent(getActivity(), RegistActivity.class));
				mTimer.cancel();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(SlidingMenu.ignoredViews != null){
			SlidingMenu.removeAllIgnoredView();
		}
		View view = inflater.inflate(R.layout.settt_fragment, null);
		ed_user_phone = (EditText) view.findViewById(R.id.ed_user_phone);
		ed_iput_code = (EditText) view.findViewById(R.id.ed_iput_code);
		tv_get_code = (TextView) view.findViewById(R.id.txt_get_code);
		tv_get_code.setOnClickListener(this);
//		back_fpw_btn = (ImageButton) view.findViewById(R.id.back_fpw_btn);
//		back_fpw_btn.setOnClickListener(this);
		back_addu_btn=(ImageButton) view.findViewById(R.id.back_addu_btn);
		click_back_btn=(TextView) view.findViewById(R.id.click_back_btn);
		tv_headadd=(TextView) view.findViewById(R.id.tv_headadd);
		tv_headadd.setText("手势密码管理");
		click_back_btn.setOnClickListener(this);
		savebtn = (Button) view.findViewById(R.id.savebtn);
		savebtn.setOnClickListener(this);
		creatbtn = (Button) view.findViewById(R.id.creatbtn);
		creatbtn.setOnClickListener(this);
		canclebtn = (Button) view.findViewById(R.id.canclebtn);
		canclebtn.setOnClickListener(this);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		saveMemberInfo = getActivity().getSharedPreferences("member", 0);
		editor = saveMemberInfo.edit();
		if (saveMemberInfo != null
				&& saveMemberInfo.getInt("partnerID", -1) > 0) {
			creatbtn.setVisibility(View.GONE);
			savebtn.setVisibility(View.VISIBLE);
			canclebtn.setVisibility(View.VISIBLE);

		} else {
			creatbtn.setVisibility(View.VISIBLE);
			savebtn.setVisibility(View.GONE);
			canclebtn.setVisibility(View.GONE);
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
	 * 获取验证码
	 */
	public void getCodeThread() {
		final String phone = ed_user_phone.getText().toString().trim();
		if (phone == null || phone.equals("")) {
			Toast.makeText(getActivity(), "请输入手机号！", Toast.LENGTH_SHORT).show();
		} else if (!isMobilePhoneNumber(phone)) {
			Toast.makeText(getActivity(), "请输入正确的手机号！", Toast.LENGTH_SHORT)
					.show();
		} else {

			new Thread(new Runnable() {
				public void run() {
					try {
						sendSms(phone);
					} catch (Exception e) {
						handler.sendEmptyMessage(0x1003);
					}
				}
			}).start();

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

		}, 1000, 1000);
	}

	private void Findpasworld() throws Exception {
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

	/**
	 * 
	 */
	public void FindPDThread() {

		final String phone = ed_user_phone.getText().toString().trim();

		String code = ed_iput_code.getText().toString().trim();

		if (phone == null || phone.equals("")) {
			Toast.makeText(getActivity(), "请输入手机号！", Toast.LENGTH_SHORT).show();
		} else if (!isMobilePhoneNumber(phone)) {
			Toast.makeText(getActivity(), "请输入正确的手机号！", Toast.LENGTH_SHORT)
					.show();
		} else if (code == null || code.equals("")) {
			Toast.makeText(getActivity(), "请输入正确的验证码！", Toast.LENGTH_SHORT)
					.show();
		} else {
			startProgressDialog(getActivity());
			new Thread(new Runnable() {
				public void run() {
					try {
						Findpasworld();
					} catch (Exception e) {
						handler.sendEmptyMessage(0x1003);
					}
				}
			}).start();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click_back_btn:
			// if (!MainActivity.sm.isShown()) {
			// MainActivity.sm.showContent();
			//
			// } else {
			// MainActivity.sm.showMenu();
			// }
			MainActivity.mMenu.toggle();
			break;
		case R.id.txt_get_code:

			getCodeThread();

			break;
		case R.id.savebtn:
			startActivity(new Intent(getActivity(), FindPassWord.class));
			break;
		case R.id.creatbtn:
			startActivity(new Intent(getActivity(), RegistActivity.class));
			break;
		case R.id.canclebtn:

			editor.clear();
			editor.commit();
			new Handler().postAtTime(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getActivity(), "取消成功", Toast.LENGTH_SHORT)
							.show();
				}
			}, 2000);

			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// MainActivity.sm.setSlidingEnabled(true);
	}

	/**
	 * 取消加载对话框
	 */
	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	/**
	 * 显示加载对话框
	 */
	public void startProgressDialog(Context context) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
			progressDialog.setMessage("正在玩命加载...");
		}

		progressDialog.show();
	}
}
