package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.MyFlowAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.bean.Label;
import com.example.leohuang.password_manager.utils.GuidBuilder;
import com.example.leohuang.password_manager.utils.Setting;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;


import java.util.ArrayList;

/**
 * 添加标签界面
 */
public class AddTagActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnAddTag;
    private AutoCompleteTextView mActvAddTag;
    private TagFlowLayout mFlowLayout;
    private MyFlowAdapter myFlowAdapter;
    private String guid;
    private ArrayList<Label> labels;//自带的标签
    private Intent mIntent;
    private ArrayAdapter<String> autoAdapter;//自动提示的适配器

    private ArrayList<String> tagString = new ArrayList<>();
    private int len;
    private int position;

    private boolean tagHas = false;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        AppManager.getAppManager().addActivity(this);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mIntent = getIntent();
        guid = mIntent.getStringExtra(Setting.GUID);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        labels = mIntent.getParcelableArrayListExtra(Setting.TAG);//已有的label
        len = myApplication.labels.size();
        for (int i = 0; i < len; i++) {
            tagString.add(myApplication.labels.get(i).name);
        }
        mBtnAddTag = (Button) findViewById(R.id.btn_add_tag);
        mActvAddTag = (AutoCompleteTextView) findViewById(R.id.actv_add_tag);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.flowLayout_tag);
        myFlowAdapter = new MyFlowAdapter(this, labels);
        mFlowLayout.setAdapter(myFlowAdapter);
        // TODO: 16/3/30 全局获取
        autoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagString);//设置适配器
        mActvAddTag.setAdapter(autoAdapter);
        //设置TextView左边图标
    }

    @Override
    protected void initEvents() {
        mBtnAddTag.setOnClickListener(this);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                labels.remove(position);
                myFlowAdapter.notifyDataChanged();
                return true;
            }
        });
        mActvAddTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                hasLabel(name);
                mActvAddTag.dismissDropDown();
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_cancel:
                        setResult(RESULT_CANCELED);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTagActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                mIntent.putParcelableArrayListExtra(Setting.TAG, labels);
                setResult(RESULT_OK, mIntent);
                finish();

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_tag:
                // TODO: 16/3/30 添加到标签云中
                String name = mActvAddTag.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddTagActivity.this, "标签内容不能为空", Toast.LENGTH_SHORT).show();
                } else {

                    hasLabel(name);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否与该标签了
     *
     * @param name
     */
    private void hasLabel(String name) {
        for (Label lable : labels) {
            if (lable.name.equals(name)) {
                mActvAddTag.setText("");
                Toast.makeText(AddTagActivity.this, "重复添加标签", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //没有重复的标签
        Label label = new Label();
        // TODO: 16/4/13 更改标签添加的过程
        //判断是否存在
        for (int i = 0; i < len; i++) {
            if (tagString.get(i).equals(name)) {
                tagHas = true;
                position = i;
                break;
            }
        }
        if (tagHas) {
            label.id = myApplication.labels.get(position).id;
        } else {
            label.id = GuidBuilder.guidGenerator();
        }
        label.name = name;
        label.type_id = guid;
        label.relation_id = GuidBuilder.guidGenerator();
        labels.add(label);
        myFlowAdapter.notifyDataChanged();
        mActvAddTag.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.save_cancel_memu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
