package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.Label;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by 46697 on 2016/3/30.
 */
public class MyFlowAdapter extends TagAdapter<Label> {
    private LayoutInflater mInflater;

    public MyFlowAdapter(Context context, List<Label> datas) {
        super(datas);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, Label label) {
        TextView tv = (TextView) mInflater.inflate(R.layout.flow_layout_item, parent, false);
        tv.setText(label.name);
        return tv;
    }
}
