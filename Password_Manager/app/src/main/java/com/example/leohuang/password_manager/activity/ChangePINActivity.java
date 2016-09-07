package com.example.leohuang.password_manager.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.FragmentVPAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.fragment.ChoosePINFragment;
import com.example.leohuang.password_manager.fragment.ConfirmPINFragment;
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;
import com.example.leohuang.password_manager.ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class ChangePINActivity extends BaseActivity implements FragmentButtonClickListener {
    private NoScrollViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<Fragment> fragments;
    private FragmentVPAdapter mAdapter;
    private boolean isFirst = true;
    private ChoosePINFragment chooseFragment;
    private ConfirmPINFragment confirmFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        fragments = new ArrayList<>();
        AppManager.getAppManager().addActivity(this);
        mInflater = LayoutInflater.from(this);
        assignViews();
        initEvents();

    }

    @Override
    protected void assignViews() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_show);
        //设置不能滚动
        mViewPager.setNoScroll(true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("PIN Code");
        setSupportActionBar(mToolbar);
        chooseFragment = new ChoosePINFragment();
        confirmFragment = new ConfirmPINFragment();
        fragments.add(chooseFragment);
        fragments.add(confirmFragment);
        mAdapter = new FragmentVPAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst) {
                    finish();
                } else {
                    mViewPager.setCurrentItem(0);
                    isFirst = true;
                }
            }
        });
    }

    @Override
    public void onBtnClick(String message, int where) {
        // TODO: 2016/3/27 进行Fragment界面跳转
        confirmFragment.setMsg(message);
        mViewPager.setCurrentItem(1);
        isFirst = false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode < KeyEvent.KEYCODE_0 || keyCode > KeyEvent.KEYCODE_9) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
