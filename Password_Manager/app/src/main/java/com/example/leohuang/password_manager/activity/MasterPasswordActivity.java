package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.FragmentVPAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.User;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.fragment.ChoosePasswordFragment;
import com.example.leohuang.password_manager.fragment.ConfirmPasswordFragment;
import com.example.leohuang.password_manager.fragment.PasswordPromptFragment;
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;
import com.example.leohuang.password_manager.ui.NoScrollViewPager;
import com.example.leohuang.password_manager.utils.AppUtils;
import com.example.leohuang.password_manager.utils.GuidBuilder;
import com.example.leohuang.password_manager.utils.PrefUtils;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;

public class MasterPasswordActivity extends BaseActivity implements FragmentButtonClickListener {
    private final String TAG = "MasterPasswordActivity";
    private NoScrollViewPager mViewPager;
    //    private TextView mTvBack, mTvTitle, mTvOpenWindow;
//    private ImageView mIvBack, mIvSearch, mIvOpenWindow;
    private int position = 0;
    private List<Fragment> fragments;
    private FragmentVPAdapter mAdapter;
    private ChoosePasswordFragment mChoosePasswordFragment;
    private ConfirmPasswordFragment mConfirmPasswordFragment;
    private PasswordPromptFragment mPasswordPromptFragment;
    private MyApplication myApplication;
    private String password;
    private Handler mHandler;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        AppManager.getAppManager().addActivity(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Setting.GET_ALL_INFO:
                        Intent intent = new Intent(MasterPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        myApplication = (MyApplication) getApplication();
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_show);
        mViewPager.setNoScroll(true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("输入主密码");
        setSupportActionBar(mToolbar);
        fragments = new ArrayList<Fragment>();
        mChoosePasswordFragment = new ChoosePasswordFragment();
        fragments.add(mChoosePasswordFragment);
        mConfirmPasswordFragment = new ConfirmPasswordFragment();
        Bundle b = new Bundle();
        b.putBoolean("next", true);
        mConfirmPasswordFragment.setArguments(b);
        fragments.add(mConfirmPasswordFragment);
        mPasswordPromptFragment = new PasswordPromptFragment();
        fragments.add(mPasswordPromptFragment);
        mAdapter = new FragmentVPAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);

    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        finish();
                        break;
                    case 1:
                        // TODO: 16/3/28 进行Fragment改变
                        position = 0;
                        mViewPager.setCurrentItem(0);
                        break;
                    case 2:
                        // TODO: 16/3/28 进行Fragment改变
                        position = 1;
                        mViewPager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onBtnClick(final String message, int where) {
        if (where == Setting.CHOOSE_PASSWORD) {

            mViewPager.setCurrentItem(1);
            position = 1;
            mConfirmPasswordFragment.setMessage(message);
        } else if (where == Setting.CONFIRM_PASSWORD) {
            Log.i(TAG, "password=" + message);
            password = message;
            position = 2;
            mViewPager.setCurrentItem(2);
        } else if (where == Setting.TIP) {
            // TODO: 2016/3/31 进行数据库存储并且进行跳转
            Log.i(TAG, "tip=" + message);
            myApplication.user.password = password;
            myApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "password:" + password + "tip" + message);
                    User user = new User();
                    user.id = GuidBuilder.guidGenerator();
                    user.password = password;
                    user.tip = message;
                    DatabaseManager.setPassword(user);
                }
            });
            AppUtils.loadModels(myApplication, mHandler);
            PrefUtils.setFirst(this);
            MyApplication.isFirst = false;
        }
    }
}
