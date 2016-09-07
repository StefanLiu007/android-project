package com.example.leohuang.password_manager.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.FragmentVPAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.bean.User;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.fragment.CheckPasswordFragment;
import com.example.leohuang.password_manager.fragment.ChoosePasswordFragment;
import com.example.leohuang.password_manager.fragment.ConfirmPasswordFragment;
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;
import com.example.leohuang.password_manager.interfaces.OnFragmentButtonClickListener;
import com.example.leohuang.password_manager.ui.NoScrollViewPager;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * 更改主密码
 */
public class ChangePasswordActivity extends BaseActivity implements OnFragmentButtonClickListener, FragmentButtonClickListener {
    private NoScrollViewPager mViewPager;
    private int position = 0;
    private List<Fragment> fragments;
    private FragmentVPAdapter mAdapter;
    private CheckPasswordFragment mCheckPasswordFragment;
    private ChoosePasswordFragment mChoosePasswordFragment;
    private ConfirmPasswordFragment mConfirmPasswordFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        AppManager.getAppManager().addActivity(this);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_show);
        mViewPager.setNoScroll(true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Master Password");
        setSupportActionBar(mToolbar);
        fragments = new ArrayList<Fragment>();
        mCheckPasswordFragment = new CheckPasswordFragment();
        fragments.add(mCheckPasswordFragment);
        mChoosePasswordFragment = new ChoosePasswordFragment();
        fragments.add(mChoosePasswordFragment);
        mConfirmPasswordFragment = new ConfirmPasswordFragment();
        Bundle b = new Bundle();
        b.putBoolean("next", false);
        mConfirmPasswordFragment.setArguments(b);
        fragments.add(mConfirmPasswordFragment);
        mAdapter = new FragmentVPAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);


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
    public void onBtnClick() {
        mViewPager.setCurrentItem(1);
        position = 1;
    }

    @Override
    public void onBtnClick(final String message, int where) {
        if (where == Setting.CHOOSE_PASSWORD) {
            mViewPager.setCurrentItem(2);
            position = 2;
            mConfirmPasswordFragment.setMessage(message);
        } else if (where == Setting.CONFIRM_PASSWORD) {

            myApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    User user = new User();
                    user.password = message;
                    DatabaseManager.updatePinOrPassword(user);
                }
            });
        }
    }
}
