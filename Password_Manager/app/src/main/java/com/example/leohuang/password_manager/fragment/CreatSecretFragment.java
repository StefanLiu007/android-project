package com.example.leohuang.password_manager.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.SecondActivity;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.utils.DialogUtils;

public class CreatSecretFragment extends Fragment {
    View view;
    TextView title;
    TextView back;
    private MyApplication myApplication;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.creat_secret, null);
        initView();
        return null;
    }


    private void initView() {
        title = (TextView) view.findViewById(R.id.title_name);
        title.setText("密码设置");
        back = (TextView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        DialogUtils.secretGeneratorTwo(getActivity(), null, false, myApplication);
    }
}
