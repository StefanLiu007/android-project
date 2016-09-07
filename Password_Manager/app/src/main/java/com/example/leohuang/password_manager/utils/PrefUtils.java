package com.example.leohuang.password_manager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.leohuang.password_manager.activity.BaseActivity;

/**
 * Sharedpreferences帮助类
 * Created by leo.huang on 16/3/24.
 */
public class PrefUtils {
    public static void setBackgroundTime(Context context, boolean need) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.BACKGROUNT_LOCK, need).commit();
    }

    /**
     * 获取进入后台时间
     *
     * @param context
     * @return
     */
    public static boolean getBackgroundTime(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.BACKGROUNT_LOCK, true);
    }

    /**
     * 获取进入闲置时间
     *
     * @param context
     * @return
     */
    public static long getFreeTime(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getLong(Setting.FREE_LOCK, Setting.TEN_MINUTES);
    }

    /**
     * 设置进入闲置时间
     *
     * @param context
     * @param time
     */
    public static void setFreeTime(Context context, long time) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putLong(Setting.FREE_LOCK, time).commit();
    }

    /**
     * 设置PIN码使用
     *
     * @param context
     * @param use
     */
    public static void setUsePIN(Context context, boolean use) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.PIN_USE, use).commit();
    }

    /**
     * 获取PIN码是否使用
     *
     * @param context
     * @return
     */
    public static boolean getUsePIN(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.PIN_USE, false);
    }

    /**
     * 获取是否是第一次启动
     *
     * @return
     */
    public static boolean getFirst(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.FIRST, true);
    }

    public static void setFirst(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.FIRST, false).commit();
    }


    /**
     * 设置是否自动同步
     *
     * @param context
     * @param use
     */
    public static void setAutoSync(Context context, boolean use) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.AUTO_SYNC, use).commit();
    }

    /**
     * 获取是否自动同步
     *
     * @param context
     * @return
     */
    public static boolean getAutoSync(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.AUTO_SYNC, true);
    }

    /**
     * 设置是否使用3g同步
     *
     * @param context
     * @param use
     */
    public static void setUse3g(Context context, boolean use) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.USE_3G, use).commit();
    }

    /**
     * 获取是否使用3g
     *
     * @param context
     * @return
     */
    public static boolean getUse3g(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.USE_3G, true);
    }

    /**
     * 设置是否自动提示
     *
     * @param context
     * @param use
     */
    public static void setAutoToast(Context context, boolean use) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.AUTO_TOAST, use).commit();
    }

    public static boolean getAutoToast(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.AUTO_TOAST, true);
    }

    /**
     * 设置类别
     *
     * @param context
     * @param type
     */

    public static void setSyncType(Context context, int type) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putInt(Setting.SYNC_TYPE, type).commit();
    }

    /**
     * 获取类别
     *
     * @param context
     * @return
     */
    public static int getSyncType(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(Setting.SYNC_TYPE, Setting.NO_SYNC);
    }


    /**
     * 设置是否开启同步
     *
     * @param context
     * @param use
     */
    public static void setSync(Context context, boolean use) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(Setting.SYNC, use).commit();
    }

    /**
     * 获取是否开启同步
     *
     * @param context
     * @return
     */
    public static boolean getSync(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Setting.SYNC, false);
    }


    /**
     * 获取上次同步的时间
     *
     * @param context
     * @return
     */
    public static String getWifiSyncTime(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(Setting.WIFI_SYNC_TIME, "");
    }

    /**
     * 设置上次同步的时间
     *
     * @param context
     * @param time
     */
    public static void setWifiSyncTime(Context context, String time) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putString(Setting.WIFI_SYNC_TIME, time).commit();
    }

    /**
     * 设置清除剪贴板时间
     *
     * @param context
     * @param time
     */
    public static void setClearClipTime(Context context, long time) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putLong(Setting.CLEAR_CLIP, time).commit();
    }

    /**
     * 获取剪贴板清除时间
     *
     * @param context
     * @return
     */
    public static long getClearClipTime(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Setting.SETTING_PREF, Context.MODE_PRIVATE);
        return mSharedPreferences.getLong(Setting.CLEAR_CLIP, Setting.NO_LOCK);
    }
}
