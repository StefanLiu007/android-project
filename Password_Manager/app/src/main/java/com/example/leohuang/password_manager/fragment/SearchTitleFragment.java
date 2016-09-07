package com.example.leohuang.password_manager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.adapter.SearchRecAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.CheckItemClickEvent;
import com.example.leohuang.password_manager.events.FindEvent;
import com.example.leohuang.password_manager.events.ShowResultEvent;
import com.example.leohuang.password_manager.ui.DividerItemDecoration;
import com.example.leohuang.password_manager.utils.MyListUtils;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询标题
 * Created by leo.huang on 16/4/25.
 */
public class SearchTitleFragment extends Fragment {

    private final String TAG = "SearchTitleFragment";

    private MyApplication myApplication;
    private RecyclerView mRecyclerView;
    private SearchRecAdapter mAdapter;
    private List<Item> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        myApplication= (MyApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result, null, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_slide);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    /**
     * 去数据库查询
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(final FindEvent event) {
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(event.value)) {
                    items.clear();
                } else {
                    MyListUtils.copyList(DatabaseManager.queryItemsByName(event.value), items);
                }
                EventBus.getDefault().post(new ShowResultEvent());
            }
        });
    }

    /**
     * 展示
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowResultEvent event) {
        if (mAdapter == null) {
            mAdapter = new SearchRecAdapter(getActivity(), items, myApplication.icons);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 跳转事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckItemClickEvent event) {
        Intent mIntent = new Intent(getActivity(), DataLoggingActivity.class);
        mIntent.putExtra(Setting.FIELD_ITEM, event.mItem);
        mIntent.putExtra("编辑", 1);
        startActivity(mIntent);
    }
}