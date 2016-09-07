package com.example.leohuang.password_manager.service;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import com.example.leohuang.password_manager.utils.Setting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * udp客户端
 */
public class UDPClientService extends Service {

    private final String TAG = "UDPClientService";
    private List<String> addresses;
    private DatagramPacket sendPacket;
    private DatagramPacket receiverPacket;
    private DatagramSocket sendSocket;
    private MyThread myThread;
    private byte[] buf = new byte[1024];

    public UDPClientService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.i(TAG,"start client");
            sendSocket = new DatagramSocket();
            sendPacket = new DatagramPacket("sync".getBytes(), "sync".getBytes().length, InetAddress.getByName("255.255.255.255"), Setting.PORT);
            receiverPacket = new DatagramPacket(buf, 1024);
            myThread = new MyThread(sendSocket, sendPacket, receiverPacket);
            new Thread(myThread).start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MyThread implements Runnable {
        private DatagramPacket sendPacket;
        private DatagramPacket receiverPacket;
        private DatagramSocket sendSocket;

        public MyThread(DatagramSocket sendSocket, DatagramPacket sendPacket, DatagramPacket receiverPacket) {
            this.sendPacket = sendPacket;
            this.sendSocket = sendSocket;
            this.receiverPacket = receiverPacket;
        }

        @Override
        public void run() {
            try {
                sendSocket.send(sendPacket);
                sendSocket.send(sendPacket);
                sendSocket.send(sendPacket);

                sendSocket.receive(receiverPacket);
                String address = sendSocket.getInetAddress().getHostAddress();
                if (!addresses.contains(address)) {//判重
                    addresses.add(address);
                    //进行数据发送
                    Log.i(TAG, "address" + address);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
