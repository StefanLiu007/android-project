package com.example.leohuang.password_manager.events;

import com.example.leohuang.password_manager.bean.Item;

import java.util.List;

/**
 * 查询事件
 * Created by leo.huang on 16/4/18.
 */
public class QueryEvent {
    public List<Item> mItems;

    public QueryEvent(List<Item> mItems) {
        this.mItems = mItems;
    }
}
