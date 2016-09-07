package com.example.leohuang.password_manager.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.SyncAdapter;
import com.example.leohuang.password_manager.bean.SyncModel;
import com.example.leohuang.password_manager.events.BooleanEvent;
import com.example.leohuang.password_manager.events.ConfirmEvent;
import com.example.leohuang.password_manager.events.DialogEvent;
import com.example.leohuang.password_manager.events.FinishEvent;
import com.example.leohuang.password_manager.interfaces.OnRecyclerItemClickListener;
import com.example.leohuang.password_manager.service.TCPServerService;
import com.example.leohuang.password_manager.ui.DividerItemDecoration;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * wifi同步客户端界面
 */
public class WifiSyncClientActivity extends BaseActivity {
    private Toolbar mToolbar;
//    private ImageView mIcon;
    private final String TAG = "WifiSyncClientActivity";

    private List<SyncModel> mModels;
    private RecyclerView mRecyclerView;
    private SyncAdapter mAdapter;
    private String myAddress;

    private DatagramSocket client;
    private DatagramSocket server;
    private DatagramPacket sendPacket;
    private DatagramPacket receiverPacket;
    private byte[] buf = new byte[1024];
    private volatile List<String> addresses;
    private boolean stop = false;

    private Timer mTimer;
    private TimerTask mTimerTask;

    //使用定时器去重新发送一次数据

    private Handler mHandler;
    private final int GET_MODEL = 0x001;
    private final String ADDRESS = "address";
    private final String MODEL = "model";

    private WifiManager mWifiManger;
    private WifiInfo mWifiInfo;
    private String msg;

    private ServiceConnection conn;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_sync);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_MODEL:
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        super.handleMessage(msg);
                }

            }
        };
        assignViews();
        initEvents();

    }


//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        if (hasFocus) {
//            AnimationDrawable anim = (AnimationDrawable) mIcon.getBackground();
//            anim.start();
//        }
//
//
//        super.onWindowFocusChanged(hasFocus);
//    }

    @Override
    protected void assignViews() {
        msg = Build.MANUFACTURER + "  " + Build.MODEL;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        mIcon = (ImageView) findViewById(R.id.iv_animation);
//        mIcon.setBackgroundResource(R.drawable.sync_anim_list);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在传输数据，请等待");
        mWifiManger = (WifiManager) getSystemService(WIFI_SERVICE);
        mWifiInfo = mWifiManger.getConnectionInfo();
        myAddress = Formatter.formatIpAddress(mWifiInfo.getIpAddress());
        addresses = new ArrayList<>();
        mModels = new ArrayList<>();


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new SyncAdapter(this, mModels);
        mAdapter.setOnRecyclerClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onClick(int positon) {
                //进行跳转
                Intent intent = new Intent(WifiSyncClientActivity.this, SyncConfirmActivity.class);
                intent.putExtra(Setting.ADDRESS, mModels.get(positon));
                startActivity(intent);
                finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        try {
            client = new DatagramSocket();
            server = new DatagramSocket(Setting.PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("255.255.255.255"), Setting.PORT);
                    client.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mTimer.schedule(mTimerTask, 10, 1000);

        //开启服务
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, TCPServerService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //进行通知
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                receiverPacket = new DatagramPacket(buf, 1024);
                while (!stop) {
                    try {
                        server.receive(receiverPacket);
                        Log.i(TAG, "myAddress" + myAddress);
                        synchronized (WifiSyncClientActivity.class) {
                            String receiveAddress = receiverPacket.getAddress().getHostAddress();
                            Log.i(TAG, "(!addresses.contains(receiveAddress)" + !addresses.contains(receiveAddress));
                            if (!receiveAddress.equals(myAddress) && (!addresses.contains(receiveAddress))) {
                                Log.i(TAG, "收到的地址" + receiveAddress);
                                addresses.add(receiveAddress);
                                String mobel = new String(receiverPacket.getData(), 0, receiverPacket.getLength());
                                SyncModel syncModel = new SyncModel(receiveAddress, mobel);
                                mModels.add(syncModel);
                                mHandler.sendEmptyMessage(GET_MODEL);
                            }
                        }
                        client.setReceiveBufferSize(1024);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        mTimer.cancel();
        stop = true;
        client.close();
        server.close();
        unbindService(conn);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(ConfirmEvent event) {
        //显示dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 16/4/6 取消操作
                        EventBus.getDefault().post(new BooleanEvent(false));
                        Toast.makeText(WifiSyncClientActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确认操作
                        EventBus.getDefault().post(new BooleanEvent(true));
                        Toast.makeText(WifiSyncClientActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        dialog.setTitle("确认同步");
        dialog.setMessage("确认和" + event.info + "进行同步吗？");
        dialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventDailog(DialogEvent event) {
        if (event.flag) {
            Toast.makeText(WifiSyncClientActivity.this, "开始同步", Toast.LENGTH_SHORT).show();
            mProgressDialog.show();
        } else {
            Toast.makeText(WifiSyncClientActivity.this, "同步完成", Toast.LENGTH_SHORT).show();
            SystemClock.sleep(1500);
            mProgressDialog.dismiss();
            finish();
        }
    }
}
