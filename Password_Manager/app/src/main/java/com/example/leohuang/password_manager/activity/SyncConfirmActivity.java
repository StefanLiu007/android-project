package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.LogItem;
import com.example.leohuang.password_manager.bean.SimpleFieldData;
import com.example.leohuang.password_manager.bean.SimpleFile;
import com.example.leohuang.password_manager.bean.SimpleItem;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.bean.SimpleLabelRelation;
import com.example.leohuang.password_manager.bean.SimpleLogItem;
import com.example.leohuang.password_manager.bean.SimpleRemark;
import com.example.leohuang.password_manager.bean.SyncModel;
import com.example.leohuang.password_manager.bean.Type;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.CancelEvent;
import com.example.leohuang.password_manager.events.FinishEvent;
import com.example.leohuang.password_manager.events.MessageEvent;
import com.example.leohuang.password_manager.events.SyncEvent;
import com.example.leohuang.password_manager.utils.CloseUtils;
import com.example.leohuang.password_manager.utils.DateUtils;
import com.example.leohuang.password_manager.utils.PrefUtils;
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
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 同步确认
 */
public class SyncConfirmActivity extends BaseActivity {
    private final String TAG = "SyncConfirmActivity";
    private Intent mIntent;
    private SyncModel mSyncModel;
    private TextView mTvDes;
    //    private TextView mTvBack, mTvName, mTvOpenWindow, mTvDes;
//    private ImageView mIvBack, mIvSearch, mIvOpenWindow;
    private Socket client;
    private boolean flag = true;

    private String me = Build.MANUFACTURER + "  " + Build.MODEL;

    private List<LogItem> mLocalItems;

    private List<LogItem> mServerItems;

    private boolean confirm = true;


    private String lastWIFISyncTime;

    private boolean same = false;

    private static List<Item> items;
    //    private List<String> labels;
    private List<SimpleLabel> simpleTags;

    private ObjectMapper objectMapper;
    private List<SimpleLogItem> simpleLogItems;

    private boolean isAnalysis = false;//是否分析结束
    private boolean receiveDate = false;

    private boolean finish = false;

    private List<SimpleFieldData> simpleFieldDatas;
    private List<SimpleFile> simpleFiles;
    private List<SimpleItem> simpleItems;
    private List<SimpleLabel> simpleLabels;
    private List<SimpleLabelRelation> simpleLabelRelations;
    private List<SimpleRemark> simpleRemarks;

    private Toolbar mToolbar;


    private TypeReference<List<Map<String, Map<String, String>>>> examples = new TypeReference<List<Map<String, Map<String, String>>>>() {
    };

    private TypeReference<List<SimpleFieldData>> fieldDataReference = new TypeReference<List<SimpleFieldData>>() {
    };
    private TypeReference<List<SimpleFile>> fileDataReference = new TypeReference<List<SimpleFile>>() {
    };
    private TypeReference<List<SimpleItem>> itemReference = new TypeReference<List<SimpleItem>>() {
    };
    private TypeReference<List<SimpleLabel>> labelReference = new TypeReference<List<SimpleLabel>>() {
    };
    private TypeReference<List<SimpleLabelRelation>> labelRelationReference = new TypeReference<List<SimpleLabelRelation>>() {
    };
    private TypeReference<List<SimpleRemark>> remarkReference = new TypeReference<List<SimpleRemark>>() {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_confirm);
        assignViews();
        initEvents();

    }

    @Override
    protected void assignViews() {
        mIntent = getIntent();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSyncModel = mIntent.getParcelableExtra(Setting.ADDRESS);//获取IP地址
        Log.i(TAG, "tcp地址" + mSyncModel.name);
        objectMapper = new ObjectMapper(new MessagePackFactory());
        simpleLogItems = new ArrayList<>();
        mTvDes = (TextView) findViewById(R.id.tv_description);
        lastWIFISyncTime = PrefUtils.getWifiSyncTime(this);//得到上次同步的时间

        // TODO: 16/4/6 进行末尾-1判断
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Socket(mSyncModel.ipAddress, Setting.PORT);
                    new Thread(new SendThread(client)).start();
                    new Thread(new ReceiverThread(client)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloseUtils.close(client);
    }


    /**
     * 发送线程
     */
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
            byte[] header = StreamUtils.intToByteArray1(me.getBytes().length);//信息长度
            try {
                out.write(header);
                out.flush();
                out.write(me.getBytes());
                out.flush();
                while (!isAnalysis) ;

                //发送日志
                byte[] buf = objectMapper.writeValueAsBytes(DatabaseManager.getLogs());
                header = StreamUtils.intToByteArray1(buf.length);
                out.write(header);
                out.flush();
                out.write(buf);
                out.flush();

                //发送需要的日志
//                byte[] buf = objectMapper.writeValueAsBytes(simpleLogItems);
//                header = StreamUtils.intToByteArray1(buf.length);
//                out.write(header);
//                out.flush();
//                out.write(buf);
//                out.flush();
                receiveDate = true;
                while (confirm) ;
                header = StreamUtils.intToByteArray1("ok".getBytes().length);
                out.write(header);
                out.flush();
                out.write("ok".getBytes());
                out.flush();
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
                byte[] firstResult = new byte[len];
                bis.read(firstResult);
                String firtStr = new String(firstResult);
                Log.i(TAG, firtStr);
                if ("ok".equals(firtStr)) {
                    EventBus.getDefault().post(new SyncEvent());//进行界面改变
//                    bis.read(header);
//                    len = StreamUtils.byteArrayToInt(header);
                    isAnalysis = true;//开始发送日志
//                    byte[] buf = new byte[len];
//                    bis.read(buf);
//                    TypeReference<List<LogItem>> typeReference = new TypeReference<List<LogItem>>() {
//                    };
//                    //解析出结果 得到服务器端的数据结果
//                    mServerItems = objectMapper.readValue(buf, typeReference);
//
//                    mLocalItems = DatabaseManager.getLogs();//得到本地的Log文件

//                    Log.i(TAG, "MserverItems====>" + mServerItems.toString());
//                    EventBus.getDefault().post(new MessageEvent(null));
                    while (!receiveDate) ;//接收数据
                    while (!finish) {
                        bis.read(header);
                        len = StreamUtils.byteArrayToInt(header);

                        byte[] buf = new byte[len];
                        bis.read(buf);
                        String tName = new String(buf);
                        if (Setting.T_FIELD_DATA.equals(tName)) {
                            bis.read(header);
                            len = StreamUtils.byteArrayToInt(header);
                            buf = new byte[len];
                            bis.read(buf);
                            simpleFieldDatas = objectMapper.readValue(buf, fieldDataReference);

                        } else if (Setting.T_FILE.equals(tName)) {
                            bis.read(header);
                            len = StreamUtils.byteArrayToInt(header);
                            buf = new byte[len];
                            bis.read(buf);
                            simpleFiles = objectMapper.readValue(buf, fileDataReference);
                        } else if (Setting.T_ITEM.equals(tName)) {
                            bis.read(header);
                            len = StreamUtils.byteArrayToInt(header);
                            buf = new byte[len];
                            bis.read(buf);
                            simpleItems = objectMapper.readValue(buf, itemReference);
                        } else if (Setting.T_LABEL.equals(tName)) {
                            bis.read(header);
                            len = StreamUtils.byteArrayToInt(header);
                            buf = new byte[len];
                            bis.read(buf);
                            simpleLabels = objectMapper.readValue(buf, labelReference);
                        } else if (Setting.T_LABEL_RELATION.equals(tName)) {
                            bis.read(header);
                            len = StreamUtils.byteArrayToInt(header);
                            buf = new byte[len];
                            bis.read(buf);
                            simpleLabelRelations = objectMapper.readValue(buf, labelRelationReference);
                        } else if (Setting.T_REMARK.equals(tName)) {
                            bis.read(header);
                            len = StreamUtils.byteArrayToInt(header);
                            buf = new byte[len];
                            bis.read(buf);
                            simpleRemarks = objectMapper.readValue(buf, remarkReference);

                        } else if ("finish".equals(tName)) {
                            finish = true;
                        } else {
                            continue;
                        }
                    }
                    DatabaseManager.syncUpdate(simpleFieldDatas, simpleFiles, simpleItems, simpleLabels, simpleLabelRelations, simpleRemarks);
                    //更新数据
                    items = DatabaseManager.showItems(null);
                    myApplication.items.clear();
                    int itemsLen = items.size();
                    for (int i = 0; i < itemsLen; i++) {
                        myApplication.items.add(items.get(i));
                    }

                    simpleTags = DatabaseManager.queryAllLabels();
                    int labelsLen = simpleTags.size();//标签的长度
                    for (int i = 0; i < labelsLen; i++) {
                        myApplication.labels.add(simpleTags.get(i));
                    }
                    EventBus.getDefault().post(new FinishEvent());
                } else if ("no".equals(firtStr)) {
                    EventBus.getDefault().post(new CancelEvent());
                }
                while (true) ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭界面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventClose(CancelEvent event) {
        mTvDes.setText("同步被取消");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSync(SyncEvent event) {
        mTvDes.setText("正在准备同步");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(final MessageEvent event) {
        mTvDes.setText("正在同步");
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int myLocalLen = 0;
                //进行数据库更新
                if (mLocalItems != null) {
                    myLocalLen = mLocalItems.size();
                }

                int myServerLen = mServerItems.size();
                for (int i = 0; i < myServerLen; i++) {//进行数据比对
                    LogItem mServerItem = mServerItems.get(i);
                    LogItem mLocalItem = null;
                    for (int j = 0; j < myLocalLen; j++) {
                        mLocalItem = mLocalItems.get(j);
                        if (mLocalItem.item_id.equals(mServerItem.item_id) && mLocalItem.table_name.equals(mServerItem.table_name)) {
                            same = true;
                            break;
                        }
                    }

                    if (same) { //有相同的选项
                        int localLen = mLocalItem.logItemInfoLists.size();
                        int serverLen = mServerItem.logItemInfoLists.size();

                        if (mLocalItem.logItemInfoLists.get(localLen - 1).action == Setting.LOG_DELETE) {//本地已经删除
                            same = false;
                        } else if (mServerItem.logItemInfoLists.get(serverLen - 1).action == Setting.LOG_DELETE) {
                            same = false;
                        } else {
                            if (!DateUtils.timeIsAfter(mLocalItem.logItemInfoLists.get(localLen - 1).createTime, mServerItem.logItemInfoLists.get(serverLen - 1).createTime)) {
                                simpleLogItems.add(new SimpleLogItem(mServerItem.item_id, mServerItem.table_name));
                            }
                        }
                    } else { //没有相同的选项，即代表本地没有该条数据
                        int len = mServerItem.logItemInfoLists.size();
                        if (mServerItem.logItemInfoLists.get(len - 1).action != Setting.LOG_DELETE) {//本地没有这条数据并且远端已经删除
                            simpleLogItems.add(new SimpleLogItem(mServerItem.item_id, mServerItem.table_name));
                        }
                    }
                }

                Log.i(TAG, "simpleLogItems:===>" + simpleLogItems.toString());
                // TODO: 16/4/11 开启发送
                isAnalysis = true;
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventFinish(FinishEvent finishEvent) {
        mTvDes.setText("同步完成");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (MyApplication.isFirst) {
            startActivity(new Intent(SyncConfirmActivity.this, MainActivity.class));
        }
        finish();
    }
}
