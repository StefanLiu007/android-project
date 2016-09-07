package com.example.leohuang.password_manager.events;

import android.widget.ImageView;

import com.example.leohuang.password_manager.bean.Item;

import java.util.List;

/**
 * Created by leo.huang on 16/4/19.
 */
public class ShowEvent {
    public List<Item> items;

    public ShowEvent(List<Item> items) {
        this.items = items;
    }
}
