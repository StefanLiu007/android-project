package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.ui.CustomListView;
import com.example.leohuang.password_manager.utils.Setting;

/**
 * 模板选择界面
 */
public class ModelChooseActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvBack, mTvName, mTvOpenWindow, mTvTitle, mTvEmptyChoose;
    private ImageView mIvBack, mIvSearch, mIvOpenWindow;
    private CustomListView mListView;
    private Intent mIntent;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_choose);
        AppManager.getAppManager().addActivity(this);
        mIntent = getIntent();
        position = mIntent.getIntExtra(Setting.MODEL, 0);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mTvBack = (TextView) findViewById(R.id.back);
        mIvBack = (ImageView) findViewById(R.id.img_back);
        mIvBack.setImageResource(R.drawable.icon_back);
        mTvName = (TextView) findViewById(R.id.title_name);
        mTvName.setText("模板选择");
        mIvSearch = (ImageView) findViewById(R.id.search);
        mIvSearch.setVisibility(View.GONE);
        mTvOpenWindow = (TextView) findViewById(R.id.openwindow);
        mTvOpenWindow.setVisibility(View.GONE);
        mIvOpenWindow = (ImageView) findViewById(R.id.img_openwindow);
        mIvOpenWindow.setVisibility(View.GONE);


        mTvTitle = (TextView) findViewById(R.id.tv_title);
        setTitle();
        mTvEmptyChoose = (TextView) findViewById(R.id.tv_empty_choose);
        mListView = (CustomListView) findViewById(R.id.lv_model);
    }

    @Override
    protected void initEvents() {
        mTvBack.setOnClickListener(this);
        mTvEmptyChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_empty_choose:
                Intent intent = new Intent(this, DataLoggingActivity.class);
                intent.putExtra(Setting.MODEL, position);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 设置空白模板名称
     */
    private void setTitle() {
        // TODO: 16/3/28 进行赋值 mTvTitle显示不同的类别
        switch (position) {
            case 0:
                mTvTitle.setText("密码");
                break;
            case 1:
                mTvTitle.setText("登录信息");
                break;
            case 2:
                mTvTitle.setText("银行账户");
                break;
            case 3:
                mTvTitle.setText("信用卡");
                break;
            case 4:
                mTvTitle.setText("安全备注");
                break;
            case 5:
                mTvTitle.setText("会籍");
                break;
            case 6:
                mTvTitle.setText("护照");
                break;
            case 7:
                mTvTitle.setText("身份识别");
                break;
            case 8:
                mTvTitle.setText("驾驶执照");
                break;
            case 9:
                mTvTitle.setText("户外许可证");
                break;
            case 10:
                mTvTitle.setText("数据库");
                break;
            case 11:
                mTvTitle.setText("服务器");
                break;
            case 12:
                mTvTitle.setText("服务器");
                break;
            case 13:
                mTvTitle.setText("无线路由器");
                break;
            case 14:
                mTvTitle.setText("软件许可");
                break;
            case 15:
                mTvTitle.setText("社会保险号码");
                break;
            case 16:
                mTvTitle.setText("电子邮件账户");
                break;
            case 17:
                break;
            case 18:
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
                break;
            case 22:
                break;
            default:
                break;
        }
    }

}
