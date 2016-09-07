package com.example.leohuang.password_manager.fragment;

import android.app.AlertDialog;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.CheckSecretStrengthActivity;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.adapter.CheckSecretStrengthAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.CheckItemClickEvent;
import com.example.leohuang.password_manager.events.ShowEvent;
import com.example.leohuang.password_manager.ui.DividerItemDecoration;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 密码强度检测
 * Created by leo.huang on 16/4/21.
 */
public class SecretStrengthFragment extends Fragment {
    private List<String> itemsId;
    private RecyclerView mRecyclerView;
    private AlertDialog mDialog;//提示框
    private CheckSecretStrengthAdapter mAdapter;
    private MyApplication myApplication;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Override
    public void onResume() {
        showDialog();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_secret_strength_layout, null, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_show);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_secret_strength_layout, null);
        mDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        mDialog.show();
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // TODO: 16/4/19 进行数据库查询?不需要直接使用myApplication中的数据
                List<Item> items = new ArrayList<Item>();
                itemsId = DatabaseManager.getSecretLevelLowItems();
                int len = myApplication.items.size();
                int idLen = itemsId.size();
                for (int i = 0; i < idLen; i++) {
                    String id = itemsId.get(i);
                    for (int j = 0; j < len; j++) {
                        Item item = myApplication.items.get(j);
                        if (item.guid.equals(id)) {
                            items.add(item);
                        }

                    }
                }
                EventBus.getDefault().post(new ShowEvent(items));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowEvent event) {
        mDialog.dismiss();
        // TODO: 16/4/19 设置适配器
        mAdapter = new CheckSecretStrengthAdapter(getActivity(), event.items, MyApplication.icons);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckItemClickEvent event) {
        Intent mIntent = new Intent(getActivity(), DataLoggingActivity.class);
        mIntent.putExtra(Setting.FIELD_ITEM, event.mItem);
        mIntent.putExtra("编辑", 1);
        startActivity(mIntent);
    }
}
