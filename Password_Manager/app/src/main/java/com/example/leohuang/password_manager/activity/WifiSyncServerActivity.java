package com.example.leohuang.password_manager.activity;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.utils.Setting;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * wifi同步界面服务端
 */
public class WifiSyncServerActivity extends BaseActivity {

    private final String TAG = "WifiSyncServerActivity";
    private Handler mHandler;
    private final int START_TCP = 0x0001;

    private DatagramSocket server;
    private DatagramPacket receiverPacket;
    private DatagramPacket sendPacket;
    private ServerSocket tcpServer;
    byte[] buf = new byte[1024];


    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_sync_server);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case START_TCP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (!MyApplication.isFirst) {
                                    try {
                                        Socket socket = tcpServer.accept();
                                        InputStream is = socket.getInputStream();
                                        //进行tcp链接
                                        PrintWriter pw = new PrintWriter(socket.getOutputStream());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        try {
            server = new DatagramSocket(Setting.PORT);
            receiverPacket = new DatagramPacket(buf, 1024);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    server.receive(receiverPacket);
                    String str = new String(receiverPacket.getData(), 0, receiverPacket.getLength());
                    Log.i(TAG, str);
                    String model = Build.MODEL;
                    sendPacket = new DatagramPacket(model.getBytes(), model.getBytes().length, receiverPacket.getAddress(), Setting.PORT);
                    server.send(sendPacket);
                    if (tcpServer == null) {
                        mHandler.sendEmptyMessage(START_TCP);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mTimer.schedule(mTimerTask, 10, 5000);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimerTask.cancel();
        server.close();
    }
}
