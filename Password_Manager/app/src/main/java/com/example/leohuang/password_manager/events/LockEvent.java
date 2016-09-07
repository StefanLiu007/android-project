package com.example.leohuang.password_manager.events;

/**
 * 重新设置锁屏事件
 * Created by leo.huang on 16/4/15.
 */
public class LockEvent {
    public long time;

    public LockEvent(long time) {
        this.time = time;
    }
}
