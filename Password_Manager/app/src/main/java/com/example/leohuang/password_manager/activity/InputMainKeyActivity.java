package com.example.leohuang.password_manager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.events.ClearEvent;
import com.example.leohuang.password_manager.events.ExitEvent;
import com.example.leohuang.password_manager.events.FinishEvent;
import com.example.leohuang.password_manager.events.IntentEvent;
import com.example.leohuang.password_manager.events.LastInputEvent;
import com.example.leohuang.password_manager.events.TryEvent;
import com.example.leohuang.password_manager.events.UnLockedEvent;
import com.example.leohuang.password_manager.events.UnLockingEvent;
import com.example.leohuang.password_manager.utils.AppUtils;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class InputMainKeyActivity extends AppCompatActivity {
    private final String TAG = "InputMainKeyActivity";
    private EditText input_mainkey;
    private int count = 5;
    private String prompt = "提示:";
    private MyApplication myApplication;
    private Intent mIntent;
    private boolean lock;
    private Handler mHandler;
    private boolean couldIntent = false;
    private final int INTENT = 0x0001;
    private InputMethodManager inputMethodManager;
    private String password;
    private String toast;
    private boolean hander = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_main_key);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        EventBus.getDefault().register(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Setting.GET_ALL_INFO:
                        couldIntent = true;
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
        mIntent = getIntent();
        lock = mIntent.getBooleanExtra(Setting.TO_LOCK, false);
        AppManager.getAppManager().addActivity(this);
        initView();
        initEvents();
        AppUtils.loadModels(myApplication, mHandler);
    }

    private void initView() {
        myApplication = (MyApplication) getApplication();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        input_mainkey = (EditText) findViewById(R.id.input_mainkey);
        input_mainkey.setCursorVisible(false);   //隐藏光标
        prompt = "提示：" + myApplication.user.tip;

        if (myApplication.usePin) {
            input_mainkey.setHint("输入PIN码");
            input_mainkey.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            input_mainkey.setHint("输入主密码");
            input_mainkey.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        input_mainkey.setTransformationMethod(PasswordTransformationMethod.getInstance());
        input_mainkey.setImeOptions(EditorInfo.IME_ACTION_GO);
    }

    private void initEvents() {
        input_mainkey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                password = input_mainkey.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (hander) {//如果能够进行判断回车
                        Log.i(TAG, "aaa");
                        String password = input_mainkey.getText().toString();
                        while (!couldIntent) ;//数据库读取完成
                        EventBus.getDefault().post(new UnLockingEvent());//发送解锁事件
                        if (!myApplication.usePin) {//如果不使用pin码
                            if (password.equals(myApplication.user.password)) {//如果密码相同
                                input_mainkey.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new UnLockedEvent());//发送解锁完成事件
                                    }
                                }, 1000);
                            } else {//密码错误
                                count--;
                                if (count == 1) {
                                    input_mainkey.postDelayed(new Runnable() {//发送最后一次尝试
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new LastInputEvent());
                                        }
                                    }, 1000);
                                } else if (count == 0) {
                                    input_mainkey.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new FinishEvent());//发送结束事件
                                        }
                                    }, 1000);
                                } else {
                                    input_mainkey.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new TryEvent());//发送再次尝试事件
                                        }
                                    }, 1000);
                                }
                            }
                        } else {//使用PIN码
                            if (password.equals(myApplication.user.pin)) {//如果PIN码相同
                                input_mainkey.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new UnLockedEvent());//发送解锁完成事件
                                    }
                                }, 1000);
                            } else {//密码错误
                                count--;
                                if (count == 1) {
                                    input_mainkey.postDelayed(new Runnable() {//发送最后一次尝试
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new LastInputEvent());
                                        }
                                    }, 1000);
                                } else if (count == 0) {
                                    input_mainkey.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new FinishEvent());//发送结束事件
                                        }
                                    }, 1000);
                                } else {
                                    input_mainkey.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new TryEvent());//发送再次尝试事件
                                        }
                                    }, 1000);
                                }
                            }
                        }
                    }
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    // TODO: 16/4/11 判读键盘，并且结束
                    if (inputMethodManager.isActive()) {
                        Log.i(TAG, "isActive" + true);
                        //隐藏软键盘
                        inputMethodManager.hideSoftInputFromWindow(input_mainkey.getWindowToken(), 0);
                    } else {
                        moveTaskToBack(true);
                    }
                }
                return false;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUnlockingEvent(UnLockingEvent event) {
        Log.i(TAG, "UnLocking");
        hander = false;
        input_mainkey.setTransformationMethod(null);
        input_mainkey.setText("正在解锁");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUnlockedEvent(UnLockedEvent event) {
        Log.i(TAG, "UnLocked");
        input_mainkey.setTransformationMethod(null);
        input_mainkey.setText("已解锁");

        input_mainkey.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new IntentEvent());
            }
        }, 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLastInputEvent(LastInputEvent event) {
        Log.i(TAG, "LastInput");
        input_mainkey.setTransformationMethod(null);
        input_mainkey.setText("退出前最后一次测试");
        if (myApplication.user.tip != null) {
            Toast.makeText(InputMainKeyActivity.this, prompt, Toast.LENGTH_SHORT).show();
        }
        input_mainkey.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ClearEvent());
            }
        }, 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventFinishEvent(FinishEvent event) {
        Log.i(TAG, "Finish");
        input_mainkey.setTransformationMethod(null);
        input_mainkey.setText("错误退出");
        input_mainkey.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ExitEvent());
            }
        }, 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventTryEvent(TryEvent enent) {
        Log.i(TAG, "Try");
        input_mainkey.setTransformationMethod(null);
        input_mainkey.setText("请在试一次");
        input_mainkey.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ClearEvent());
            }
        }, 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventClearEvent(ClearEvent event) {
        input_mainkey.setText("");
        input_mainkey.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置成密码模式
        inputMethodManager.showSoftInput(input_mainkey, 0);
        hander = true;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventIntentEvent(IntentEvent event) {
        inputMethodManager.hideSoftInputFromWindow(input_mainkey.getWindowToken(), 0);
        if (!lock) {//不是锁定
            Intent intent = new Intent(InputMainKeyActivity.this, MainActivity.class);
            startActivity(intent);
        }
//        MyApplication.stop = false;
        hander = true;
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventExitEvent(ExitEvent event) {
        hander = true;
        AppManager.getAppManager().finishAllActivity();
    }
}
