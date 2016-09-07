package com.eden.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eden.R;
import com.eden.activity.AboutEdenActivity;
import com.eden.activity.LoginActivity;
import com.eden.activity.PersonalInfoActivity;
import com.eden.activity.RobotActivity;
import com.eden.util.MyAsyncTask;
import com.eden.util.MyGetUrl;

public class SettingFragment extends Fragment implements android.view.View.OnClickListener ,OnClickListener{
	Button button ;
	private View view;
	private Button btn5;	
	private TextView tv3;
	private LinearLayout about;
	private LinearLayout bianji;
	private LinearLayout tiantian;
	private ImageView imageButton1;
	private SharedPreferences sp ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		sp = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
		view = inflater.inflate(R.layout.activity_personalcenter, container, false);
		bianji = (LinearLayout) view.findViewById(R.id.bianji);
		bianji.setOnClickListener(this);
		tiantian = (LinearLayout) view.findViewById(R.id.tiantian);
		tiantian.setOnClickListener(this);
		about = (LinearLayout) view.findViewById(R.id.about);
		about.setOnClickListener(this);
		btn5=(Button) view.findViewById(R.id.btn5);
		btn5.setOnClickListener(this);
		imageButton1 = (ImageView) view.findViewById(R.id.imageButton1);
		MyAsyncTask task = new MyAsyncTask(imageButton1, getActivity());
		task.execute(MyGetUrl.getUrl()+sp.getString("picture", null));
		tv3 = (TextView) view.findViewById(R.id.tv3);
		tv3.setText(sp.getString("account", null));
		return view;
	}
//	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn5:
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("您确定要退出登录吗？");
			builder.setPositiveButton("确定",new OnClickListener() {		
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					getActivity().finish();
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					startActivity(intent);			
				}
			});
			builder.setNegativeButton("取消",null);
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		case R.id.bianji:
			Intent intent = new Intent(getActivity(),PersonalInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.about:	
			startActivity(new Intent(getActivity(),AboutEdenActivity.class));
			break;
		case R.id.tiantian:	
			startActivity(new Intent(getActivity(),RobotActivity.class));
			break;
		default:
			break;
		}
			
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode);
		
	}
}
