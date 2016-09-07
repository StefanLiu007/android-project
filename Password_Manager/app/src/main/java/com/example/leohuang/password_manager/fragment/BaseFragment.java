package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.example.leohuang.password_manager.bean.Notification;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Jack on 16/4/21.
 */
public class BaseFragment extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Notification event){
    }
}
