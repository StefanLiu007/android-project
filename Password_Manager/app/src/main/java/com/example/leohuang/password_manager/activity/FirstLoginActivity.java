package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.FragmentVPAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.fragment.QuickBaseFragment;
import com.example.leohuang.password_manager.fragment.QuickTeamFragment;
import com.example.leohuang.password_manager.fragment.QuickWelcomeFragment;
import com.example.leohuang.password_manager.interfaces.QuickOnClickListener;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class FirstLoginActivity extends BaseActivity implements View.OnClickListener, QuickOnClickListener {
    private FloatingActionButton mFab;
    private ViewPager mViewPager;
    private Fragment mQuickBaseFragment, mQuickTeamFragment, mQuickWelcomeFragment;
    private List<Fragment> fragments;
    private FragmentVPAdapter mAdapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        AppManager.getAppManager().addActivity(this);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        fragments = new ArrayList<>();
        mQuickBaseFragment = new QuickBaseFragment();
        fragments.add(mQuickBaseFragment);
        mQuickTeamFragment = new QuickTeamFragment();
        fragments.add(mQuickTeamFragment);
        mQuickWelcomeFragment = new QuickWelcomeFragment();
        fragments.add(mQuickWelcomeFragment);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mViewPager = (ViewPager) findViewById(R.id.vp_introduce);
        mViewPager.setOffscreenPageLimit(2);
        mAdapter = new FragmentVPAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                if (position == 2) {
                    mFab.setVisibility(View.GONE);
                } else {
                    mFab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initEvents() {
        mFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                index++;
                mViewPager.setCurrentItem(index);
                break;
        }
    }

    @Override
    public void onBtnClick() {
        // TODO: 16/3/31 跳转到创建密码界面
        Intent intent = new Intent(this, MasterPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onTvClick() {
        // TODO: 16/3/31 跳转到同步界面
//        Intent intent = new Intent(this, SyncMethodsActivity.class);
//        intent.putExtra(Setting.FIRST, true);
//        startActivity(intent);
    }
}
