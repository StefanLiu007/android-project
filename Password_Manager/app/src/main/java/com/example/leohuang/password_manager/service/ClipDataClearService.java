package com.example.leohuang.password_manager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.leohuang.password_manager.events.ClearEvent;
import com.example.leohuang.password_manager.events.ResetEvent;
import com.example.leohuang.password_manager.events.ResetTimeEvent;
import com.example.leohuang.password_manager.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 清除剪贴板中的内容
 */
public class ClipDataClearService extends Service {
    private final String TAG = "ClipDataClearService";
    private Timer mTimer;
    private TimerTask mTimerTask;
    private long clearTime;

    public ClipDataClearService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        clearTime = PrefUtils.getClearClipTime(this);
        initTimerTask();
        if (clearTime != -1) {
            mTimer.schedule(mTimerTask, clearTime, clearTime);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 重新设置时间
     *
     * @param timeEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventResetTime(ResetTimeEvent timeEvent) {
        Log.i(TAG, "重新设置时间====time:" + timeEvent.time);
        mTimer.cancel();
        if (timeEvent.time != -1) {
            initTimerTask();
            mTimer.schedule(mTimerTask, timeEvent.time, timeEvent.time);
        }
    }

    /**
     * 重新开始设置
     *
     * @param resetEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventReset(ResetEvent resetEvent) {
        Log.i(TAG, "重新计时");
        mTimer.cancel();
        if (clearTime != -1) {

            initTimerTask();
            mTimer.schedule(mTimerTask, clearTime, clearTime);
        }
    }

    /**
     * 新建任务
     */
    private void initTimerTask() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "开始清除");
                EventBus.getDefault().post(new ClearEvent());
            }
        };
    }
}
