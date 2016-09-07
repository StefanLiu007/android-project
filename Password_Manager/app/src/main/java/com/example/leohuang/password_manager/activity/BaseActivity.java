package com.example.leohuang.password_manager.activity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.AllOrSingle;
import com.example.leohuang.password_manager.events.ClearEvent;
import com.example.leohuang.password_manager.events.LockEvent;
import com.example.leohuang.password_manager.receiver.ScreenBroadCastReceiver;
import com.example.leohuang.password_manager.service.ClipDataClearService;
import com.example.leohuang.password_manager.utils.IntentUtils;
import com.example.leohuang.password_manager.utils.PrefUtils;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * 基类
 * Created by leo.huang on 16/3/22.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";
    private ScreenBroadCastReceiver mReceiver;
    private boolean isActive = true;
    private AlarmManager mAlarmManager;
    private Intent freeIntent;
    private PendingIntent sender;
    protected MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        EventBus.getDefault().register(this);
        startService(new Intent(this, ClipDataClearService.class));//开启定时清除服务
        myApplication = (MyApplication) getApplication();
        myApplication.stop = false;
        //设置定时器
        mReceiver = new ScreenBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(2147483647);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Setting.MINTENT);
        this.registerReceiver(mReceiver, filter);

        freeIntent = new Intent();
        freeIntent.setAction(Setting.MINTENT);
        sender = PendingIntent.getBroadcast(this, 0, freeIntent, 0);

        //设置闹钟
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + PrefUtils.getFreeTime(this), sender);
    }


    @Override
    protected void onStop() {
        super.onStop();
        cancelAM();
        if (!isAppOnForeground()) {
            // app 进入后台
            isActive = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        resetAM();
        if (!isActive) {
            isActive = true;
            Log.i(TAG, "后台");
//            IntentUtils.intentToLockActivity(this, startTime, endTime, IntentUtils.BACKGROUND_TYPE);

            Log.i(TAG, "MyApplication.stop" + MyApplication.stop);
            if (myApplication.backgroundLock && !MyApplication.stop && !myApplication.isFirst) {
                MyApplication.stop = true;
                IntentUtils.intentToLockActivity(this);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        EventBus.getDefault().unregister(this);
    }


    /**
     * 绑定控件
     */
    protected abstract void assignViews();

    /**
     * 注册事件
     */
    protected abstract void initEvents();


    // TODO: 16/3/23 闲置监听
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        resetAM();
        return super.onTouchEvent(event);
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 重新设置闹钟
     *
     * @param time
     */
    protected void resetAM(long time) {
        mAlarmManager.cancel(sender);
        if (time == Setting.NO_LOCK) {
            return;
        }
        mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + time, sender);
    }

    protected void resetAM() {
        mAlarmManager.cancel(sender);
        if (PrefUtils.getFreeTime(this) == Setting.NO_LOCK) {
            return;
        }
        mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + PrefUtils.getFreeTime(this), sender);
    }

    protected void cancelAM() {
        mAlarmManager.cancel(sender);
    }


    /**
     * 进行剪贴板清除
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventClear(ClearEvent event) {
        ClipData clipData = ClipData.newPlainText(null, null);
        myApplication.mClipboardManager.setPrimaryClip(clipData);//清除
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLock(LockEvent event) {
        resetAM(event.time);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AllOrSingle allOrSingle){
    }
}
