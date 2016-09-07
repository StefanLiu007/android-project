package com.example.leohuang.password_manager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leohuang.password_manager.R;

/**
 * 云同步
 */
public class DropBoxSyncActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnUpload;
    private Button mBtnDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_box_sync);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mBtnUpload = (Button) findViewById(R.id.btn_cloud_upload);
        mBtnDownload = (Button) findViewById(R.id.btn_cloud_download);
    }

    @Override
    protected void initEvents() {
        mBtnDownload.setOnClickListener(this);
        mBtnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_cloud_upload:
                break;
            case R.id.btn_cloud_download:
                break;
            default:
                break;
        }
    }
}
