package com.example.stefan.rxjava.viewUtils;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by Stefan on 2016/7/18.
 */
public class ToastUtil {

    public static void showToast(Context context,String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
