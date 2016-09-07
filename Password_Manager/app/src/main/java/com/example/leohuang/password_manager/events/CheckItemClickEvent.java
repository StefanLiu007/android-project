package com.example.leohuang.password_manager.events;

import com.example.leohuang.password_manager.bean.Item;

/**
 * Item对应的View的点击事件
 * Created by leo.huang on 16/4/20.
 */
public class CheckItemClickEvent {
    public Item mItem;

    public CheckItemClickEvent(Item mItem) {
        this.mItem = mItem;
    }
}
