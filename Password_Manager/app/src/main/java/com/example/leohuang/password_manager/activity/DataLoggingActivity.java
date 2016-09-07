package com.example.leohuang.password_manager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.DataLoggingAdapter;
import com.example.leohuang.password_manager.adapter.MyFlowAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Field;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Label;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.bean.Template;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.interfaces.OnTagBtnClickListener;
import com.example.leohuang.password_manager.utils.DateUtils;
import com.example.leohuang.password_manager.utils.GuidBuilder;
import com.example.leohuang.password_manager.utils.Setting;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息录入界面
 */
public class DataLoggingActivity extends BaseActivity implements OnTagBtnClickListener {
    private final String TAG = "DataLoggingActivity";
    private RecyclerView mRecyclerView;
    private DataLoggingAdapter mAdapter;
    private Intent mIntent;
    private int position;
    private Model mModel;
    private Item mItem;//数据库存储的对象
    private List<Template> customTemplates;


    private Toolbar mToolbar;
    private final int ADD_FIELD = 0x0001;
    private final int ADD_TAG = 0x0002;

    private boolean tagHas = false;//判断标签是否存在

    int editor = 0;


    private MyFlowAdapter myFlowAdapter;
    private TagFlowLayout mTagFlowLayout;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_logging);
        AppManager.getAppManager().addActivity(this);
        mIntent = getIntent();
        position = mIntent.getIntExtra(Setting.MODEL, 0);//通过position来获取位置
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {

        mModel = new Model();
        myApplication = (MyApplication) getApplication();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        mToolbar.setNavigationIcon(R.drawable.topbar_ok);
        mToolbar.setTitle("编辑");
        setSupportActionBar(mToolbar);
        Item item = getIntent().getParcelableExtra(Setting.FIELD_ITEM);//传过来的对象，

        //获取缓存中的该对象
        editor = getIntent().getIntExtra(Setting.ISADD, 0);//是否是编辑
        Log.i(TAG, "editor==>" + editor);
        if (item != null) {
            int len = myApplication.items.size();
            for (int i = 0; i < len; i++) {
                if (item.guid.equals(myApplication.items.get(i).guid)) {//从缓存中获取对应的Item对象
                    mItem = myApplication.items.get(i);
                    break;
                }
            }
//            mItem = item;
            for (int i = 0; i < myApplication.models.size(); i++) {
                Model model = myApplication.models.get(i);
                if (model.id == mItem.type_id) {//得到模板对象用于动态生成界面
                    mModel.name = model.name;
                    mModel.icon = model.icon;
                    mModel.id = model.id;
                    mModel.templates = new ArrayList<>();
                    int tempLen = model.templates.size();
                    for (int j = 0; j < tempLen; j++) {
                        mModel.templates.add(model.templates.get(j));
                    }
                    mModel = model;
                    break;
                }
            }
        } else {
            mItem = new Item();//实例化存储对象
            Model model = myApplication.models.get(position);//得到模板对象，并且生成数据对象
            mModel.name = model.name;
            mModel.icon = model.icon;
            mModel.id = model.id;
            mModel.templates = new ArrayList<>();
            int len = model.templates.size();
            for (int i = 0; i < len; i++) {
                mModel.templates.add(model.templates.get(i));
            }
            mItem.guid = GuidBuilder.guidGenerator();
            mItem.icon = mModel.icon;
            mItem.type_id = mModel.id;

        }
        customTemplates = mItem.getCustoms();//获取自定义字段
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new DataLoggingAdapter(this, mModel, mItem, myApplication.icons, customTemplates);
        mAdapter.setListener(this);
    }

    @Override
    protected void initEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//保存事件
                Log.i(TAG, mItem.toString());
                long time = System.currentTimeMillis();
                if (editor == 0) {//如果是新建的，进行时间添加
                    mItem.create_time = DateUtils.getFormatDate(time);
                    mItem.modify_time = DateUtils.getFormatDate(time);
                    mItem.access_count = 1;
                }
                mItem.access_time = DateUtils.getFormatDate(time);
                mItem.access_count += 1;
                int tagSize = 0;
                List<Field> field = new ArrayList<>();
                for (int i = 0; i < mItem.fields.size(); i++) {
                    Field field1 = mItem.fields.get(i);
                    if (field1.value != null) {
                        field.add(field1);
                    }
                }
                mItem.fields = field;
                int len = myApplication.labels.size();
                if (mItem.labels != null) {
                    tagSize = mItem.labels.size();//mItem.labels中的长度
                }
                for (int i = 0; i < tagSize; i++) {
                    for (int j = 0; j < len; j++) {
                        if (myApplication.labels.get(j).name.equals(mItem.labels.get(i).name)) {
                            tagHas = true;
                            break;
                        }
                    }
                    if (!tagHas) {
                        // TODO: 16/4/13 添加新的标签
                        myApplication.labels.add(new SimpleLabel(mItem.labels.get(i).id, mItem.labels.get(i).name));//更新缓存
                    }
                }
                myApplication.mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2016/3/29 数据库操作
                        Message message = Message.obtain();
                        message.obj = DatabaseManager.insertItem(mItem);
                        // TODO: 16/4/20 否需要重新查找
                        mHandler.sendMessage(message);//只是展示没有任何
//                        myApplication.items = DatabaseManager.showItems(null);
                        Log.i(TAG, "items===>" + myApplication.items);
                    }
                });
                if (editor == 0) {
                    MyApplication.items.add(mItem);
                }
// else {
//                    for (int i = 0; i < myApplication.items.size(); i++) {
//                        Item item = myApplication.items.get(i);
//                        if (item.guid == mItem.guid) {//重新添加到该缓存中
//                            myApplication.items.remove(item);
//                            myApplication.items.add(i, mItem);
//                            continue;
//                        }
//
//                    }
//                }
                if (editor == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("mItem", mItem);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {//菜单点击事件
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_add:
                        Intent intent = new Intent(DataLoggingActivity.this, AddFieldActivity.class);
                        intent.putExtra(Setting.MODEL, position);
                        startActivityForResult(intent, ADD_FIELD);
                        break;
                    case R.id.action_cancel:
                        DataLoggingActivity.this.finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FIELD) {
            if (resultCode == RESULT_OK && data != null) {
                String type = data.getStringExtra(Setting.FIELD_TYPE);
                String name = data.getStringExtra(Setting.FIELD_NAME);
                Log.i(TAG, "type" + type);
                Template template = new Template();
                template.type = type;
                template.custom = Setting.CUSTOM;
                template.name = name;
                template.index = mItem.fields.size();
//                mModel.templates.add(template);
                Field field = new Field();//新建出添加的field字段
                field.index = template.index;
                field.guid = GuidBuilder.guidGenerator();
                mItem.fields.add(field);//把新添加的Field添加到集合中
                customTemplates.add(template);
                // TODO: 16/3/30 进行备注和添加项的替换以及通知更新
                mAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == ADD_TAG) {
            if (resultCode == RESULT_OK && data != null) {
                mItem.labels = data.getParcelableArrayListExtra(Setting.TAG);
                if (myFlowAdapter != null) {
                    myFlowAdapter = null;
                }
                myFlowAdapter = new MyFlowAdapter(this, mItem.labels);
                mTagFlowLayout.setAdapter(myFlowAdapter);
            }

        }
    }

    @Override
    public void onTagBtnClicl(Context context, MyFlowAdapter adapter, TagFlowLayout tag) {
        mTagFlowLayout = tag;
        Intent intent = new Intent(this, AddTagActivity.class);
        intent.putExtra(Setting.GUID, mItem.guid);
        if (mItem.labels == null) {
            mItem.labels = new ArrayList<Label>();
        }
        intent.putParcelableArrayListExtra(Setting.TAG, mItem.labels);
        startActivityForResult(intent, ADD_TAG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.data_logging_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
