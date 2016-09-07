package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.slide.LinearItemDecoration;
import com.example.leohuang.password_manager.slide.SlideItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 16/3/28.
 */
public class SearchResultFragment extends Fragment {
    View view;
    String text;
    SlideItemAdapter adapter;
    RecyclerView recyclerView;
    List<Item> items = new ArrayList<>();
    MyApplication myApplication;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            items = (List<Item>) msg.obj;
            adapter = new SlideItemAdapter(items, getActivity(),myApplication);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_result, null);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_slide);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new LinearItemDecoration(Color.BLACK));
        Bundle b = getArguments();
        if (b!=null) {
            text = b.getString("text");
        }
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Item> items;
                if (!DatabaseManager.queryAllItemOrderByLabel(text).equals("")) {
                    items = DatabaseManager.queryAllItemOrderByLabel(text);
                } else {
                    items = DatabaseManager.queryBlurredAllItem(text);
                }
                Message message = handler.obtainMessage();
                message.obj = items;
                handler.sendMessage(message);

            }
        });
        //recyclerView.setAdapter(new SlideItemAdapter);
        adapter = new SlideItemAdapter(items, getActivity(),myApplication);
        recyclerView.setAdapter(adapter);
    }
}
