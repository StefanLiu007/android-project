package com.example.leohuang.password_manager.ui;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.interfaces.OnRemarkTagClickListener;

/**
 * Created by leo.huang on 16/3/24.
 */
public class RemarkAndTagView extends LinearLayout implements View.OnClickListener {
    private TextView mTvRemark, mTvTag;

    private OnRemarkTagClickListener mListener;

    public RemarkAndTagView(Context context) {
        this(context, null);
    }

    public RemarkAndTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemarkAndTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.remark_tag_layout, this, true);
        mTvRemark = (TextView) findViewById(R.id.tv_remark);
        mTvTag = (TextView) findViewById(R.id.tv_tag);
    }

    private void initEvents() {
        mTvRemark.setOnClickListener(this);
        mTvTag.setOnClickListener(this);
    }

    public void setOnRemarkTagClickListener(OnRemarkTagClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        int id = getId();
        switch (id) {
            case R.id.tv_remark:
                if(mListener!=null){
                    mListener.onRemark();
                }
                break;
            case R.id.tv_tag:
                if(mListener!=null){
                    mListener.onTag();
                }
                break;
            default:
                break;
        }
    }
}
