package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.SyncItemAdapter;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步方法设置
 */
public class SyncMethodsActivity extends BaseActivity {
    private ListView mLvSyncMethods;
    //    private String[] strName = new String[]{"云盘同步", "wifi同步"};
//    private Integer[] iconIds = new Integer[]{R.drawable.setup_icon_dropbox, R.drawable.setup_icon_wifi};
    private List<String> strName = new ArrayList<>();
    private List<Integer> iconIds = new ArrayList<>();
    private SyncItemAdapter mAdapter;
    private TextView mTvTitle, mTvEmptyChoose;
//    private ImageView mIvBack, mIvSearch, mIvOpenWindow;

    private WifiInfo mWifiInfo;
    private WifiManager mWifiManager;

    private Intent mIntent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_methods);
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        strName.add("云盘同步");
        strName.add("wifi同步");
        iconIds.add(R.drawable.setup_icon_dropbox);
        iconIds.add(R.drawable.setup_icon_wifi);
        mLvSyncMethods = (ListView) findViewById(R.id.lv_sync_methods);

        mAdapter = new SyncItemAdapter(this, strName, iconIds);
        mLvSyncMethods.setAdapter(mAdapter);
        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    @Override
    protected void initEvents() {
        mLvSyncMethods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(SyncMethodsActivity.this, DropBoxSyncActivity.class));
                        break;
                    case 1:
                        if (mWifiManager.isWifiEnabled()) {
                            startActivity(new Intent(SyncMethodsActivity.this, WifiSyncClientActivity.class));
                        } else {
                            Toast.makeText(SyncMethodsActivity.this, "请处在wifi状态下", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
