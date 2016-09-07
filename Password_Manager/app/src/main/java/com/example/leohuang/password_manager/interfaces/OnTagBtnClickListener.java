package com.example.leohuang.password_manager.interfaces;

import android.content.Context;

import com.example.leohuang.password_manager.adapter.MyFlowAdapter;
import com.example.leohuang.password_manager.tag.TagCloudLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * Created by leo.huang on 16/3/30.
 */
public interface OnTagBtnClickListener {
    void onTagBtnClicl(Context context, MyFlowAdapter adapter, TagFlowLayout tag);
}
