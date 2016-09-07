package com.example.leohuang.password_manager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.LogItem;
import com.example.leohuang.password_manager.bean.SimpleFieldData;
import com.example.leohuang.password_manager.bean.SimpleFile;
import com.example.leohuang.password_manager.bean.SimpleItem;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.bean.SimpleLabelRelation;
import com.example.leohuang.password_manager.bean.SimpleLogItem;

import com.example.leohuang.password_manager.bean.SimpleRemark;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.BooleanEvent;
import com.example.leohuang.password_manager.events.ConfirmEvent;
import com.example.leohuang.password_manager.events.DialogEvent;

import com.example.leohuang.password_manager.events.MessageEvent;
import com.example.leohuang.password_manager.utils.CloseUtils;
import com.example.leohuang.password_manager.utils.DateUtils;
import com.example.leohuang.password_manager.utils.Setting;
import com.example.leohuang.password_manager.utils.StreamUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * tcp服务端
 */
public class TCPServerService extends Service {
    private final String TAG = "TCPServerService";
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean flag = false;
    private ServerThread mServerThread;

    private boolean conformConnect = false;


    private boolean firstSend = true;
    private boolean receiveSync = false;
    private boolean sendSync = false;
    private boolean canFinish = false;

    private ObjectMapper objectMapper;

    private List<SimpleLogItem> simpleLogItems;

    private Map<String, List<Object>> map;

    private List<LogItem> mLocalItems;

    private List<LogItem> mServerItems;
    private boolean same = false;
    private MyApplication myApplication;

    public TCPServerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = (MyApplication) getApplication();
        objectMapper = new ObjectMapper(new MessagePackFactory());
        EventBus.getDefault().register(this);
        simpleLogItems = new ArrayList<>();
        try {
            //打开端口
            serverSocket = new ServerSocket(Setting.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mServerThread = new ServerThread();
        new Thread(mServerThread).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);

        CloseUtils.close(socket);
        CloseUtils.close(serverSocket);
    }


    private class ServerThread implements Runnable {
        private PrintStream out;
        private InputStream in;
        private String str;

        @Override
        public void run() {
            // TODO: 16/4/6 进行末尾-1判读
            try {
                Log.i(TAG, "打开服务");
                socket = serverSocket.accept();
                Log.i(TAG, "连接成功");

                new Thread(new ReceiverThread(socket)).start();
                new Thread(new SendThread(socket)).start();

//                CloseUtils.close(in);
//                CloseUtils.close(out);
//                Log.i(TAG, "stop socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private class SendThread implements Runnable {
        private BufferedOutputStream out;
        private Socket socket;

        public SendThread(Socket socket) {
            this.socket = socket;
            try {
                out = new BufferedOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (firstSend) ;
            try {
                if (conformConnect) {
                    byte[] header = StreamUtils.intToByteArray1("ok".getBytes().length);
                    out.write(header);
                    out.flush();
                    out.write("ok".getBytes());
                    out.flush();

                    EventBus.getDefault().post(new DialogEvent(false));
                    // TODO: 16/4/11 MessagePack 发送数据包 发送的是日志数据
//                    byte[] buf = objectMapper.writeValueAsBytes(DatabaseManager.getLogs());
//                    header = StreamUtils.intToByteArray1(buf.length);
//                    out.write(header);
//                    out.flush();
//                    out.write(buf);
//                    out.flush();
                    receiveSync = true;//允许接收

                    //发送需要同步的数据
                    while (!sendSync) ;
                    byte[] buf = null;
                    // TODO: 16/4/12 不直接发送Map集合 把map集合变量发送
                    map = DatabaseManager.getUpdateLog(simpleLogItems);

                    Iterator iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, List<Object>> set = (Map.Entry<String, List<Object>>) iterator.next();
                        String key = set.getKey();
                        Log.i(TAG, "key==>" + key);
                        if (Setting.T_FIELD_DATA.equals(key)) {
                            List<Object> value = set.getValue();
                            int len = value.size();
                            List<SimpleFieldData> simpleFieldDatas = new ArrayList<>();
                            for (int i = 0; i < len; i++) {
                                simpleFieldDatas.add((SimpleFieldData) value.get(i));
                            }
                            //先传表名
                            header = StreamUtils.intToByteArray1(Setting.T_FIELD_DATA.getBytes().length);
                            out.write(header);
                            out.flush();
                            out.write(Setting.T_FIELD_DATA.getBytes());
                            out.flush();

                            //在传数据


                            buf = objectMapper.writeValueAsBytes(simpleFieldDatas);
                            header = StreamUtils.intToByteArray1(buf.length);
                            out.write(header);
                            out.flush();
                            out.write(buf);
                            out.flush();

                        } else if (Setting.T_FILE.equals(key)) {
                            List<Object> value = set.getValue();
                            int len = value.size();
                            List<SimpleFile> simpleFiles = new ArrayList<>();
                            for (int i = 0; i < len; i++) {
                                simpleFiles.add((SimpleFile) value.get(i));
                            }
                            header = StreamUtils.intToByteArray1(Setting.T_FILE.getBytes().length);
                            out.write(header);
                            out.flush();
                            out.write(Setting.T_FILE.getBytes());
                            out.flush();

                            buf = objectMapper.writeValueAsBytes(simpleFiles);
                            header = StreamUtils.intToByteArray1(buf.length);
                            out.write(header);
                            out.flush();
                            out.write(buf);
                            out.flush();
                        } else if (Setting.T_ITEM.equals(key)) {
                            List<Object> value = set.getValue();
                            int len = value.size();
                            List<SimpleItem> simpleItems = new ArrayList<>();
                            for (int i = 0; i < len; i++) {
                                simpleItems.add((SimpleItem) value.get(i));
                            }
                            header = StreamUtils.intToByteArray1(Setting.T_ITEM.getBytes().length);
                            out.write(header);
                            out.flush();
                            out.write(Setting.T_ITEM.getBytes());
                            out.flush();

                            buf = objectMapper.writeValueAsBytes(simpleItems);
                            header = StreamUtils.intToByteArray1(buf.length);
                            out.write(header);
                            out.flush();
                            out.write(buf);
                            out.flush();
                        } else if (Setting.T_LABEL.equals(key)) {
                            List<Object> value = set.getValue();
                            int len = value.size();
                            List<SimpleLabel> simpleLabels = new ArrayList<>();
                            for (int i = 0; i < len; i++) {
                                simpleLabels.add((SimpleLabel) value.get(i));
                            }

                            header = StreamUtils.intToByteArray1(Setting.T_LABEL.getBytes().length);
                            out.write(header);
                            out.flush();
                            out.write(Setting.T_LABEL.getBytes());
                            out.flush();

                            buf = objectMapper.writeValueAsBytes(simpleLabels);
                            header = StreamUtils.intToByteArray1(buf.length);
                            out.write(header);
                            out.flush();
                            out.write(buf);
                            out.flush();
                        } else if (Setting.T_LABEL_RELATION.equals(key)) {
                            List<Object> value = set.getValue();
                            int len = value.size();
                            List<SimpleLabelRelation> simpleLabelRelations = new ArrayList<>();
                            for (int i = 0; i < len; i++) {
                                simpleLabelRelations.add((SimpleLabelRelation) value.get(i));
                            }

                            header = StreamUtils.intToByteArray1(Setting.T_LABEL_RELATION.getBytes().length);
                            out.write(header);
                            out.flush();
                            out.write(Setting.T_LABEL_RELATION.getBytes());
                            out.flush();

                            buf = objectMapper.writeValueAsBytes(simpleLabelRelations);
                            header = StreamUtils.intToByteArray1(buf.length);
                            out.write(header);
                            out.flush();
                            out.write(buf);
                            out.flush();
                        } else if (Setting.T_REMARK.equals(key)) {
                            List<Object> value = set.getValue();
                            int len = value.size();
                            List<SimpleRemark> simpleRemarks = new ArrayList<>();
                            for (int i = 0; i < len; i++) {
                                simpleRemarks.add((SimpleRemark) value.get(i));
                            }

                            header = StreamUtils.intToByteArray1(Setting.T_REMARK.getBytes().length);
                            out.write(header);
                            out.flush();
                            out.write(Setting.T_REMARK.getBytes());
                            out.flush();

                            buf = objectMapper.writeValueAsBytes(simpleRemarks);
                            header = StreamUtils.intToByteArray1(buf.length);
                            out.write(header);
                            out.flush();
                            out.write(buf);
                            out.flush();
                        }
                    }
                    header = StreamUtils.intToByteArray1("finish".getBytes().length);
                    out.write(header);
                    out.flush();
                    out.write("finish".getBytes());
                    out.flush();
//                    buf = objectMapper.writeValueAsBytes(DatabaseManager.getUpdateLog(simpleLogItems));
//                    header = StreamUtils.intToByteArray1(buf.length);
                    out.write(header);
                    out.flush();
                    out.write(buf);
                    out.flush();
                    canFinish = true;

                } else {
                    byte[] header = StreamUtils.intToByteArray1("no".getBytes().length);
                    out.write(header);
                    out.flush();
                    out.write("no".getBytes());
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收线程
     */

    private class ReceiverThread implements Runnable {
        private BufferedInputStream bis;
        private Socket socket;

        public ReceiverThread(Socket socket) {
            this.socket = socket;
            try {
                bis = new BufferedInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] header = new byte[4];
            int len = -1;
            try {
                bis.read(header);
                len = StreamUtils.byteArrayToInt(header);
                Log.i(TAG, "len===>" + len);
                byte[] buf = new byte[len];
                bis.read(buf);
                String firstStr = new String(buf);
                EventBus.getDefault().post(new ConfirmEvent(firstStr));
                while (!receiveSync) ;
                //得到日志数据
                bis.read(header);
                len = StreamUtils.byteArrayToInt(header);
                buf = new byte[len];
                bis.read(buf);
                TypeReference<List<LogItem>> typeReference = new TypeReference<List<LogItem>>() {
                };
                //解析出结果 得到服务器端的数据结果
                mServerItems = objectMapper.readValue(buf, typeReference);

                mLocalItems = DatabaseManager.getLogs();//得到本地的Log文件
//                TypeReference<List<SimpleLogItem>> typeReference = new TypeReference<List<SimpleLogItem>>() {
//                };
//                simpleLogItems = objectMapper.readValue(buf, typeReference);
//                Log.i(TAG, "simpleLogItem====>" + simpleLogItems.toString());
                EventBus.getDefault().post(new MessageEvent(null));//开线程去分析数据


                while (!canFinish) ;

                //同步完成
                bis.read(header);//确认回复
                len = StreamUtils.byteArrayToInt(header);
                buf = new byte[len];
                bis.read(buf);
                String confirm = new String(buf);
                if ("ok".equals(confirm)) {
                    EventBus.getDefault().post(new DialogEvent(true));
                }

                // TODO: 16/4/11 结束
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventConfirm(BooleanEvent event) {
        flag = true;
        firstSend = false;
        this.conformConnect = event.flag;
        Log.i(TAG, "conformConnect" + conformConnect);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(final MessageEvent event) {
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int myLocalLen = 0;
                int myServerLen = 0;
                //进行数据库更新
                if (mLocalItems != null) {
                    myLocalLen = mLocalItems.size();//本地的日志数据
                }

                if (mServerItems != null) {
                    myServerLen = mServerItems.size();
                }//远端的日志数据

                for (int i = 0; i < myLocalLen; i++) {//进行数据比对
                    same = false;
                    LogItem mServerItem = null;
                    LogItem mLocalItem = mLocalItems.get(i);
                    for (int j = 0; j < myServerLen; j++) {//判断远端和本地的数据是否有相同的ID的
                        mServerItem = mServerItems.get(j);
                        if (mLocalItem.item_id.equals(mServerItem.item_id) && mLocalItem.table_name.equals(mServerItem.table_name)) {
                            same = true;//表示有相同ID
                            break;
                        }
                    }

                    if (same) { //有相同的选项
                        int localLen = mLocalItem.logItemInfoLists.size();
                        int serverLen = mServerItem.logItemInfoLists.size();

                        if (mLocalItem.logItemInfoLists.get(localLen - 1).action == Setting.LOG_DELETE) {//本地已经删除
                            same = false;
                        } else if (mServerItem.logItemInfoLists.get(serverLen - 1).action == Setting.LOG_DELETE) {//远端已经删除
                            same = false;
                        } else {
                            if (DateUtils.timeIsAfter(mLocalItem.logItemInfoLists.get(localLen - 1).createTime, mServerItem.logItemInfoLists.get(serverLen - 1).createTime)) {
                                simpleLogItems.add(new SimpleLogItem(mLocalItem.item_id, mLocalItem.table_name));//本地的时间在远端的后面，把远端的数据发送
                            }
                        }
                    } else { //没有相同的选项，即代表本地没有该条数据
                        int len = mLocalItem.logItemInfoLists.size();//本地数据
                        if (mLocalItem.logItemInfoLists.get(len - 1).action != Setting.LOG_DELETE) {//远端没有这条数据，并且本地没有删除
                            simpleLogItems.add(new SimpleLogItem(mLocalItem.item_id, mLocalItem.table_name));
                        }
                    }
                }

                Log.i(TAG, "simpleLogItems:===>" + simpleLogItems.toString());
                // TODO: 16/4/11 开启发送

                sendSync = true;
            }
        });
    }


}

