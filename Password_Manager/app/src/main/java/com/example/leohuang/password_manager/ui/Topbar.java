package com.example.leohuang.password_manager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.interfaces.OnTopbarClickListener;

/**
 * Created by leo.huang on 16/3/24.
 */
public class Topbar extends RelativeLayout implements View.OnClickListener {

    private Button mBtnCancel, mBtnSave;
    private OnTopbarClickListener mListener;
    public Topbar(Context context) {
        this(context, null);
    }

    public Topbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Topbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        initEvents();
    }

    private void initEvents() {
        mBtnSave.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    private void initViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.subitem_topbar, this, true);
        mBtnCancel = (Button) findViewById(R.id.topbar_tv_save);
        mBtnSave = (Button) findViewById(R.id.topbar_tv_more);
    }

    public void setOnTopbarClickListener(OnTopbarClickListener listener){
        this.mListener=listener;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.topbar_tv_save:
                if(mListener!=null){
                    mListener.onCancel();
                }
                break;
            case R.id.topbar_tv_more:
                if(mListener!=null){
                    mListener.onSave();
                }
                break;
            default:
                break;
        }

    }
}
