package com.tdActivity.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdActivity.android.activity.R;
import com.tdActivity.android.MyApplication;
import com.tdActivity.android.activity.CaptureActivity;
import com.tdActivity.android.activity.HanldAddActivity;
import com.tdActivity.android.activity.MainActivity;
import com.umeng.analytics.MobclickAgent;

public class AddUserFragment extends Fragment implements OnClickListener {
	private LinearLayout hand_lay;
	private ImageButton  code_lay;
	//private Button back_btn;
	private ImageButton back_addu_btn;
	private TextView click_back_btn;
	private TextView tv_headadd;
    private SlideMenuFragment f;
	public  AddUserFragment(){
	}
    public  AddUserFragment(SlideMenuFragment fragment){
		this.f = fragment;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.adduser_frgment, null);
		intviews(view);
		return view;
	}

	

	private void intviews(View view) {
		//back_addu_btn = (ImageButton) view.findViewById(R.id.back_addu_btn);
//		back_btn=(Button) view.findViewById(R.id.back_btn);
//		back_btn.setText(getResources().getString(R.string.fa_icon_item));
//		back_btn.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
//		back_btn.setTextColor(Color.WHITE);
//		back_btn.setTextSize(18);
//		back_btn.setOnClickListener(this);
		back_addu_btn=(ImageButton) view.findViewById(R.id.back_addu_btn);
		click_back_btn=(TextView) view.findViewById(R.id.click_back_btn);
		click_back_btn.setOnClickListener(this);
		tv_headadd=(TextView) view.findViewById(R.id.tv_headadd);
		tv_headadd.setText("添加账户");
		code_lay = (ImageButton) view.findViewById(R.id.code_lay);
		hand_lay = (LinearLayout) view.findViewById(R.id.hand_lay);
		//back_addu_btn.setOnClickListener(this);
		code_lay.setOnClickListener(this);
		hand_lay.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click_back_btn:
//			if (!MainActivity.sm.isShown()) {
//				MainActivity.sm.showContent();
//			} else {
//				MainActivity.sm.showMenu();
//			}
			MainActivity.mMenu.toggle();
			break;
		case R.id.code_lay:
			Intent intent = new Intent(getActivity(),
					CaptureActivity.class);
            intent.putExtra("AddUserFragment", 1);
            startActivityForResult(intent, 0x12);
			break;
		case R.id.hand_lay:
			Intent intent1 = new Intent(getActivity(),
					HanldAddActivity.class);
            intent1.putExtra("AddUserFragment", 1);
			startActivityForResult(intent1, 0x12);

			break;

		default:
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("AddUserFragment"); 
//		if(((MyApplication)getActivity().getApplication()).userInfos.size()>0){
//			f.setSelect(0);
			MainActivity.mMenu.closeMenu();
//		}
		
	}
@Override
public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("AddUserFragment"); 
}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0x12) {
			if (resultCode == 0x112) {
				f.setSelect(0);
			}
		}
	}

}
