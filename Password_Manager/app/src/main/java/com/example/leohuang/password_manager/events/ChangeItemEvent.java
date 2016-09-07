package com.example.leohuang.password_manager.events;

import com.example.leohuang.password_manager.bean.Item;

/**
 * 生成密码并且跳转
 * Created by leo.huang on 16/4/22.
 */
public class ChangeItemEvent {
    public Item mItem;

    public ChangeItemEvent(Item mItem) {
        this.mItem = mItem;
    }
}
