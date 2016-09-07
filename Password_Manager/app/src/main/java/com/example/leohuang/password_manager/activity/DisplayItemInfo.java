package com.example.leohuang.password_manager.activity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.DisplayItemInfoAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.events.ResetEvent;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;


/**
 * 信息录入界面
 */
public class DisplayItemInfo extends BaseActivity {
    private final String TAG = "DataLoggingActivity";
    private RecyclerView mRecyclerView;
    private DisplayItemInfoAdapter mAdapter;
    private Intent mIntent;
    private Model mModel;
    private Item mItem;//数据库存储的对象

    private final int EDITOR = 0x0003;

    private Toolbar mToolbar;
    private DisplayItemInfoAdapter.OnItemClickListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_logging);
        AppManager.getAppManager().addActivity(this);
        mIntent = getIntent();
        assignViews();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setTitle("项目详情");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myApplication = (MyApplication) getApplication();
        Item item = (Item) getIntent().getParcelableExtra(Setting.FIELD_ITEM);
        if (item != null) {
            mItem = item;
            for (int i = 0; i < myApplication.models.size(); i++) {
                Model model = myApplication.models.get(i);
                if (model.id == mItem.type_id) {
                    mModel = model;
                    break;
                }
            }
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);//进行数据填写

        mAdapter = new DisplayItemInfoAdapter(this, mItem, myApplication.icons);
        mListener = new DisplayItemInfoAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView ivicon, TextView tvPassword, boolean close) {
                if (close) {
                    ivicon.setImageResource(R.drawable.eye_open);
                    tvPassword.setTransformationMethod(null);
                } else {
                    ivicon.setImageResource(R.drawable.eye_close);
                    tvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

            @Override
            public void onLongClick(String text) {
                // TODO: 16/4/14 进行剪贴板添加
                Toast.makeText(DisplayItemInfo.this, "以复制到剪贴板", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new ResetEvent());//重新设置
                ClipData clipData = ClipData.newPlainText("复制", text);
                myApplication.mClipboardManager.setPrimaryClip(clipData);
            }
        };
        mAdapter.setOnItemClickListener(mListener);
    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.action_edit:
                        Intent intent = new Intent(DisplayItemInfo.this, DataLoggingActivity.class);
                        intent.putExtra(Setting.FIELD_ITEM, mItem);
                        intent.putExtra(Setting.ISADD, Setting.Editor);
                        startActivityForResult(intent, EDITOR);
                        break;
                    case R.id.action_delete:
                        // TODO: 16/4/14 删除该项
                        int index = -1;
                        for (int i = 0, n = myApplication.items.size(); i < n; i++) {
                            if (mItem.guid.equals(myApplication.items.get(i).guid)) {
                                index = i;
                            }
                        }
                        if (index != -1) {
                            myApplication.items.remove(index);
                        }
                        Toast.makeText(DisplayItemInfo.this, "删除成功", Toast.LENGTH_SHORT).show();
                        finish();

                        break;
                    default:
                        break;
                }
                return true;
            }
        });//设置menu点击事件

        //设置布局（全部的）
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITOR) {
            if (resultCode == RESULT_OK && data != null) {
                mItem = data.getParcelableExtra("mItem");
                mAdapter = new DisplayItemInfoAdapter(this, mItem, myApplication.icons);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(mListener);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.detail_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
