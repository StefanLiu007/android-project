package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.events.ConfirmEvent;
import com.example.leohuang.password_manager.tag.TagBaseAdapter;
import com.example.leohuang.password_manager.tag.TagCloudLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by sun on 16/3/28.
 */
public class LabelShowFragment extends Fragment {
    MyApplication myApplication;
    View view;
    private TagCloudLayout mContainer;
    private TagBaseAdapter mAdapter;
    private List<String> mList;
    SearchFragment searchFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tag_show, null);
        mContainer = (TagCloudLayout) view.findViewById(R.id.container);
        mAdapter = new TagBaseAdapter(getActivity(), myApplication.labels);// TODO: 16/4/8 获取标签 
        mContainer.setAdapter(mAdapter);
        mContainer.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                String text = myApplication.labels.get(position).name;
                EventBus.getDefault().post(new ConfirmEvent(text));

            }
        });
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSearch(ConfirmEvent event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

