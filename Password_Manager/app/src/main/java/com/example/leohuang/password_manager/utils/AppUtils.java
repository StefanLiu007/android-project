package com.example.leohuang.password_manager.utils;

import android.os.Handler;

import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.db.DatabaseManager;



/**
 * application工具类
 * Created by 46697 on 2016/3/31.
 */
public class AppUtils {
    public static  void loadModels(final MyApplication myApplication,final Handler mHandler) {
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myApplication.models = DatabaseManager.getModels();
                myApplication.items = DatabaseManager.showItems(null);
                myApplication.labels = DatabaseManager.queryAllLabels();
                mHandler.sendEmptyMessage(Setting.GET_ALL_INFO);
            }
        });
    }
}
