package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.utils.PrefUtils;

import java.util.Properties;

/**
 * 同步设置界面
 */
public class SyncSettingActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "SyncSettingActivity";
    private TextView mTvTitle, mTvEmptyChoose, mTvSyncSetting;


    private RelativeLayout mRlSyncSetting;
    // , mRlAutoSync, mRlUse3G, mRlToast;
    private Switch mSwitchSyncSetting;
//    private CheckBox mCbAutoSync, mCbUse3G, mCbToast;

    private boolean sync;
    //            autoSync, use3g, autoToast;
    private int type;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_setting);
        AppManager.getAppManager().addActivity(this);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRlSyncSetting = (RelativeLayout) findViewById(R.id.rl_sync_setting);
//        mRlAutoSync = (RelativeLayout) findViewById(R.id.rl_auto_sync);
//        mRlUse3G = (RelativeLayout) findViewById(R.id.rl_use_3g);
//        mRlToast = (RelativeLayout) findViewById(R.id.rl_toast);

        mSwitchSyncSetting = (Switch) findViewById(R.id.switch_sync);
//
//        mCbAutoSync = (CheckBox) findViewById(R.id.checkbox_auto_sync);
//        mCbUse3G = (CheckBox) findViewById(R.id.checkbox_use_3g);
//        mCbToast = (CheckBox) findViewById(R.id.checkbox_toast);

    }


    @Override
    protected void onResume() {
        super.onResume();
        sync = PrefUtils.getSync(this);
//        autoSync = PrefUtils.getAutoSync(this);
//        use3g = PrefUtils.getUse3g(this);
//        autoToast = PrefUtils.getAutoToast(this);
        mSwitchSyncSetting.setChecked(sync);
//        mCbAutoSync.setChecked(autoSync);
//        mCbUse3G.setChecked(use3g);
//        mCbToast.setChecked(autoToast);

    }

    @Override
    protected void initEvents() {
        mRlSyncSetting.setOnClickListener(this);
//        mRlAutoSync.setOnClickListener(this);
//        mRlUse3G.setOnClickListener(this);
//        mRlToast.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_sync_setting:
                if (!sync) {
                    sync = true;
                    mSwitchSyncSetting.setChecked(true);
                    startActivity(new Intent(this, SyncMethodsActivity.class));
                } else {
                    sync = false;
                    PrefUtils.setSync(this, sync);
                }
                break;
//            case R.id.rl_auto_sync:
//                if (autoSync) {
//                    autoSync = false;
//                } else {
//                    autoSync = true;
//                }
//                mCbAutoSync.setChecked(autoSync);
//                PrefUtils.setAutoSync(this, autoSync);
//                break;
//            case R.id.rl_use_3g:
//                if (use3g) {
//                    use3g = false;
//                } else {
//                    use3g = true;
//                }
//                mCbUse3G.setChecked(use3g);
//                PrefUtils.setUse3g(this, use3g);
//                break;
//            case R.id.rl_toast:
//                if (autoToast) {
//                    autoToast = false;
//                } else {
//                    autoToast = true;
//                }
//                mCbToast.setChecked(autoToast);
//                PrefUtils.setAutoSync(this, autoSync);
//                break;
            default:
                break;
        }
    }
}
