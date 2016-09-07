package com.example.leohuang.password_manager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by Jack on 16/4/8.
 */
public class Uiutils {
    public static final String PREFS_NAME = "TAKEOFFANDROID";

    public static final String KEY_COUNTRIES = "Countries";

    public static final int REQUEST_CODE = 1234;
    private void onFocusChange(boolean hasFocus, final EditText commentEdit) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) commentEdit.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isFocus) {
                    commentEdit.setFocusable(true);
                    commentEdit.setFocusableInTouchMode(true);
                    commentEdit.requestFocus();
                    commentEdit.requestFocusFromTouch();
                    // 显示输入
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    // 隐藏输入
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
            }
        }, 100);
    }
    public static void toast(Context context,String name){
        Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static boolean isConnectedNetwork (Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo () != null && cm.getActiveNetworkInfo ().isConnectedOrConnecting ();

    }
}
