package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.utils.DialogUtils;
import com.example.leohuang.password_manager.utils.IntentUtils;
import com.example.leohuang.password_manager.utils.PrefUtils;
import com.example.leohuang.password_manager.utils.Setting;

/**
 * 安全设置界面
 */
public class SafeSettingActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "SafeSettingActivity";
    private RelativeLayout mRlPin, mRlBackground, mRlLock, mRlClipBoard;
    private TextView mTvChangePIN, mTvChangePassword, mTvDes, mTvClipBoardDes;
    private Switch mSwitchPin, mSwitchBackground;
    private long freeTime;
    private int freeIndex = -1;
    private long clearTime;//清除时间
    private int clearIndex = -1;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        AppManager.getAppManager().addActivity(this);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRlPin = (RelativeLayout) findViewById(R.id.rl_pin);
        mSwitchPin = (Switch) findViewById(R.id.switch_pin);
        if (myApplication.usePin) {
            mSwitchPin.setSelected(true);
        } else {
            mSwitchPin.setSelected(false);
        }
        mTvChangePIN = (TextView) findViewById(R.id.tv_change_pin);
        mTvChangePassword = (TextView) findViewById(R.id.tv_change_password);
        mRlBackground = (RelativeLayout) findViewById(R.id.rl_background);
        mSwitchBackground = (Switch) findViewById(R.id.switch_background);
        if (myApplication.backgroundLock) {
            mSwitchBackground.setSelected(true);
        } else {
            mSwitchBackground.setSelected(false);
        }

        mRlLock = (RelativeLayout) findViewById(R.id.rl_lock);
        mTvDes = (TextView) findViewById(R.id.tv_description);
        mRlClipBoard = (RelativeLayout) findViewById(R.id.rl_clip_board);
        mTvClipBoardDes = (TextView) findViewById(R.id.tv_clip_board_description);
        // TODO: 16/3/25 初始化清除剪贴板描述
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIndexAndShowText(true);
        getIndexAndClearText(true);
        if (myApplication.usePin) {
            mTvChangePIN.setTextColor(getResources().getColor(R.color.black));
        } else {
            mTvChangePIN.setTextColor(getResources().getColor(R.color.light_gray));
        }

        if (myApplication.usePin == false) {
            mSwitchPin.setChecked(false);
        } else {
            mSwitchPin.setChecked(true);
        }

    }

    @Override
    protected void initEvents() {
//        mTvBack.setOnClickListener(this);
        mRlPin.setOnClickListener(this);
        mTvChangePIN.setOnClickListener(this);
        mTvChangePassword.setOnClickListener(this);
        mRlBackground.setOnClickListener(this);
        mRlLock.setOnClickListener(this);
        mRlClipBoard.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.lock:
                        if (myApplication.backgroundLock && !MyApplication.stop && !myApplication.isFirst) {
                            MyApplication.stop = true;
                            IntentUtils.intentToLockActivity(SafeSettingActivity.this);
                        }
                        break;
                    case R.id.exit://退出
                        SafeSettingActivity.this.finish();
                        AppManager.getAppManager().finishAllActivity();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                return true;
            }

        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_pin:
                // TODO: 16/3/25 开启PIN码
                if (myApplication.usePin) {
                    mSwitchPin.setChecked(false);
                    PrefUtils.setUsePIN(this, false);
                    myApplication.usePin = false;
                    mTvChangePIN.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    // TODO: 16/3/25 进行跳转
                    mSwitchBackground.setChecked(true);
                    mTvChangePIN.setTextColor(getResources().getColor(R.color.black));
                    Intent intent = new Intent(this, ChangePINActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tv_change_pin:
                // TODO: 16/3/25 修改PIN码
                if (!myApplication.usePin) {
                    return;
                } else {
                    Intent intent = new Intent(this, ChangePINActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_change_password:
                // TODO: 16/3/25 修改主密码
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_background:
                if (myApplication.backgroundLock) {
                    mSwitchBackground.setChecked(false);
                    myApplication.backgroundLock = false;
                    PrefUtils.setBackgroundTime(this, false);
                } else {
                    mSwitchBackground.setChecked(true);
                    myApplication.backgroundLock = true;
                    PrefUtils.setBackgroundTime(this, true);
                }
                break;
            case R.id.rl_lock:
                getIndexAndShowText(false);
                DialogUtils.showLock(this, freeIndex, mTvDes, "自动锁定");
                break;
            case R.id.rl_clip_board:
                // TODO: 16/3/25 清除剪贴板

                getIndexAndClearText(false);
                DialogUtils.showClear(this, clearIndex, mTvClipBoardDes, "自动清除剪贴板");
                break;
        }
    }

    /**
     * 获取选择的时间，并且设置位置
     */

    private void getIndexAndShowText(boolean show) {
        freeTime = PrefUtils.getFreeTime(this);
        if (freeTime == Setting.ONE_MINUTES) {
            freeIndex = 0;
            resetAM(Setting.ONE_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态1分钟之后");
            }
        } else if (freeTime == Setting.TWO_MINUTES) {
            freeIndex = 1;
            resetAM(Setting.TWO_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态2分钟之后");
            }
        } else if (freeTime == Setting.THREE_MINUTES) {
            freeIndex = 2;
            resetAM(Setting.THREE_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态3分钟之后");
            }
        } else if (freeIndex == Setting.FOUR_MINUTES) {
            freeIndex = 3;
            resetAM(Setting.FOUR_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态4分钟之后");
            }
        } else if (freeTime == Setting.FIVE_MINUTES) {
            freeIndex = 4;
            resetAM(Setting.FIVE_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态5分钟之后");
            }
        } else if (freeTime == Setting.TEN_MINUTES) {
            freeIndex = 5;
            resetAM(Setting.TEN_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态10分钟之后");
            }
        } else if (freeTime == Setting.FIFTEEN_MINUTES) {
            freeIndex = 6;
            resetAM(Setting.FIFTEEN_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态15分钟之后");
            }
        } else if (freeTime == Setting.THIRTY_MINUTES) {
            freeIndex = 7;
            resetAM(Setting.THIRTY_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态30分钟之后");
            }
        } else if (freeTime == Setting.SIXTY_MINUTES) {
            freeIndex = 8;
            resetAM(Setting.SIXTY_MINUTES);
            if (show) {
                mTvDes.setText("处于开启及闲置状态60分钟之后");
            }
        } else if (freeTime == Setting.NO_LOCK) {
            cancelAM();
            freeIndex = 9;
            if (show) {
                mTvDes.setText("从不");
            }
        }
    }


    private void getIndexAndClearText(boolean show) {
        clearTime = PrefUtils.getClearClipTime(this);
        if (clearTime == Setting.THIRTY_SECONDS) {//30秒
            clearIndex = 0;
            if (show) {
                mTvClipBoardDes.setText("将值拷贝到剪贴板30秒后");
            }
        } else if (clearTime == Setting.ONE_MINUTES) {//60秒
            clearIndex = 1;
            if (show) {
                mTvClipBoardDes.setText("将值拷贝到剪贴板60秒后");
            }
        } else if (clearTime == Setting.NINTY_SECONDS) {//90秒
            clearIndex = 2;
            if (show) {
                mTvClipBoardDes.setText("将值拷贝到剪贴板90秒后");
            }
        } else if (clearTime == Setting.TWO_MINUTES) {//两分钟
            clearIndex = 3;
            if (show) {
                mTvClipBoardDes.setText("将值拷贝到剪贴板两分钟后");
            }
        } else if (clearTime == Setting.THREE_MINUTES) {//三分钟
            clearIndex = 4;
            if (show) {
                mTvClipBoardDes.setText("将值拷贝到剪贴板三分钟后");
            }
        } else if (clearTime == Setting.NO_LOCK) {//不清除
            clearIndex = 5;
            if (show) {
                mTvClipBoardDes.setText("从不");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
