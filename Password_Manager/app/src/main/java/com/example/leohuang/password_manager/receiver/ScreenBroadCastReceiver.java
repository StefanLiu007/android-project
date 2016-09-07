package com.example.leohuang.password_manager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.utils.IntentUtils;
import com.example.leohuang.password_manager.utils.PrefUtils;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;

import java.util.Properties;

/**
 * 监听锁屏事件
 * Created by leo.huang on 16/3/23.
 */
public class ScreenBroadCastReceiver extends BroadcastReceiver {
    private final String TAG = "ScreenBroadCast";
    private boolean pass = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            pass = true;
            Log.i(TAG, "锁屏" + "pass" + pass);
        } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            // TODO: 16/4/12 测试
            if (!MyApplication.stop && !MyApplication.isFirst) {
                if (pass) {
                    Log.i(TAG, "解锁" + "pass" + pass);
                    MyApplication.stop = true;
                    IntentUtils.intentToLockActivity(context);
                    pass = false;
                }
            }
            Log.i(TAG, "解锁");
        } else if (Setting.MINTENT.equals(intent.getAction())) {
            Log.i(TAG, "定时器");
            if (!MyApplication.stop && !MyApplication.isFirst) {
                MyApplication.stop = true;
                IntentUtils.intentToLockActivity(context);
            }
        }
    }
}


