package com.example.leohuang.password_manager.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.utils.Setting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * udp 服务端
 */
public class UDPServerService extends Service {

    private final String TAG = "UDPServerService";
    private DatagramSocket service = null;
    private DatagramPacket receiverPacket;
    private byte[] buf = new byte[1024];

    private boolean stop = false;

    public UDPServerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.i(TAG, "start service");
            service = new DatagramSocket(Setting.PORT);
            receiverPacket = new DatagramPacket(buf, 1024);
            MyThread myThread = new MyThread(service, receiverPacket);
            new Thread(myThread).start();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
    }

    private class MyThread implements Runnable {
        private DatagramSocket service;
        private DatagramPacket receiverPacket;

        public MyThread(DatagramSocket service, DatagramPacket receiverPacket) {
            this.service = service;
            this.receiverPacket = receiverPacket;
        }

        @Override
        public void run() {
            while (!MyApplication.stop && !stop) {
                try {
                    service.receive(receiverPacket);
                    String str = new String(receiverPacket.getData(), receiverPacket.getLength());
                    Log.i(TAG, "收到的信息" + str);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}



