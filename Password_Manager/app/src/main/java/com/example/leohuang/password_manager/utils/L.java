package com.example.leohuang.password_manager.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Jack on 16/3/30.
 */
public class L {
    public static void test(String name){
        Log.i("MMM",name);
    }
    public static void toast(Context context, String name){
        Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
    }
}
