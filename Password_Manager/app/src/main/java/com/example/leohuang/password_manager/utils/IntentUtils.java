package com.example.leohuang.password_manager.utils;

import android.content.Context;
import android.content.Intent;

import com.example.leohuang.password_manager.activity.InputMainKeyActivity;

/**
 * 跳转帮助类
 * Created by leo.huang on 16/3/24.
 */
public class IntentUtils {

    public final static int SCREEN_TYPE = 0;
    public final static int BACKGROUND_TYPE = 1;
    public final static int FREE_TYPE = 2;

    /**
     * 跳转到输入密码界面
     * @param context
     */
    public static void intentToLockActivity(Context context){
        intentToLock(context);
    }

    private static void intentToLock(Context context) {
        Intent mIntent = new Intent(context, InputMainKeyActivity.class);
        mIntent.putExtra(Setting.TO_LOCK, true);
        context.startActivity(mIntent);
    }
}
