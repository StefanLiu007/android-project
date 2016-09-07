package com.example.leohuang.password_manager.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.FragmentVPAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.fragment.SecretSameFragment;
import com.example.leohuang.password_manager.fragment.SecretStrengthFragment;
import com.example.leohuang.password_manager.ui.NoScrollViewPager;

import java.util.ArrayList;


/**
 * 密码强度检测Activity
 * l
 */
public class CheckSecretStrengthActivity extends BaseActivity {

    private final String TAG = "CheckSecretStrength";

    private MyApplication myApplication;
    private Toolbar mToolbar;

    private NoScrollViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] title = new String[]{"密码强度", "相同密码"};

    private Fragment mSecretSameFragment, mSecretStrengthFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyViewPagerAdapter mFragmentAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_secret_strength);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("强度检测");
        setSupportActionBar(mToolbar);
        myApplication = (MyApplication) getApplication();
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.nsvp);
        mViewPager.setNoScroll(false);
        mSecretStrengthFragment = new SecretStrengthFragment();
        mSecretSameFragment = new SecretSameFragment();
        fragments.add(mSecretStrengthFragment);
        fragments.add(mSecretSameFragment);
        mFragmentAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return title[position];
        }
    }
}
