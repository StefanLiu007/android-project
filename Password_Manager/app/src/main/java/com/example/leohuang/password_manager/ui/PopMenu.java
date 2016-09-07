package com.example.leohuang.password_manager.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.leohuang.password_manager.R;


/**
 * Created by sun on 16/3/9.
 */
public class PopMenu {
    private Activity context;
    private PopupWindow popupWindow;
    private ListView listView;
    // private OnItemClickListener listener;

    private BaseAdapter adapter;

    public PopMenu(Activity context) {
        this.context = context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.popwindow_listview, null);
        listView = (ListView) view.findViewById(R.id.pop_list);
        //设置适配�??
        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);
        popupWindow = new PopupWindow(view, context.getResources()
                .getDimensionPixelSize(R.dimen.popmenu_width),
                ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.Animtop);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    //对外提供设置适配器方�??
    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        listView.setAdapter(adapter);
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        // this.listener = listener;
        listView.setOnItemClickListener(listener);
    }

    public void showAsDropDown(View parent) {

        backgroundAlpha(1.0f);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(parent,
                10,
                context.getResources().getDimensionPixelSize(
                        R.dimen.popmenu_yoff));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);

            }
        });
        popupWindow.update();


    }

    /**
     * 设置添加屏幕的背景�?�明�?
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    public void dismiss() {
        backgroundAlpha(1.0f);
        popupWindow.dismiss();

    }
}
