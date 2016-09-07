package com.example.leohuang.password_manager.events;

import com.example.leohuang.password_manager.bean.SecretSameContainer;

import java.util.List;

/**
 * ExpendableListView展示事件
 * Created by leo.huang on 16/4/22.
 */
public class ExpendableEvent {
    public List<SecretSameContainer> containers;

    public ExpendableEvent(List<SecretSameContainer> containers) {
        this.containers = containers;
    }
}
