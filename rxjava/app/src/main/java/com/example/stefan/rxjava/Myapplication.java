package com.example.stefan.rxjava;

import android.app.Application;
import android.content.Context;

/**
 * Created by Stefan on 2016/7/20.
 */
public class Myapplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return  context;
    }
}
