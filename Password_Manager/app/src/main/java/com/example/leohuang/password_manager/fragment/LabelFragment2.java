package com.example.leohuang.password_manager.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.MainActivity;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.LaberAccount;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.ui.TagGroup;
import com.example.leohuang.password_manager.utils.GuidBuilder;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jack on 16/4/18.
 */
public class LabelFragment2 extends BaseFragment implements View.OnClickListener{
    View view;
    private TextView mPromptText;
    Toolbar toolbar;
    Button submit;
    private TagGroup mDefaultTagGroup;
    private List<LaberAccount> laberAccounts;
    private String[] nameCopy;
    private List<String> label = new ArrayList<>();
    private List<LaberAccount> delet = new ArrayList<>();//要删除的标签
    private List<LaberAccount> add = new ArrayList<>();//要新增的标签
    private List<LaberAccount> edit = new ArrayList<>();//要编辑的标签
    private List<String> label_1 = new ArrayList<>();//如果账户没标签，防止重复添加标签
    private  Timer timer;
    private MyApplication myApplication;
    private TagGroup.OnTagClickListener mTagClickListener = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(final String tag) {
            MyApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                  List<Item> items =  DatabaseManager.queryAllItemOrderByLabel(tag);
                    Message message = Message.obtain();
                    message.what = 0x10002;
                    message.obj = items;
                    mHandler.sendMessage(message);
                }
            });



        }
    };
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x10003:
                    laberAccounts = (List<LaberAccount>)msg.obj;
                    label.clear();
                    for (int i = 0;i<laberAccounts.size();i++){
                        label.add(laberAccounts.get(i).name);
                    }
                    if (laberAccounts != null && laberAccounts.size() > 0) {
                        mDefaultTagGroup.setTags(label);
                    }
                    break;
                case 0x10002:
                    FragmentManager fm = getFragmentManager() ;
                    FragmentTransaction transaction = fm.beginTransaction();
                    if (ClassifyFragment.classifyItemFragment != null){
                        transaction.remove(ClassifyFragment.classifyItemFragment);
                    }
                    ClassifyFragment.classifyItemFragment = null;
                    ClassifyFragment.classifyItemFragment = new ClassifyItemFragment();
                    Bundle b = new Bundle();
                    ArrayList<Item> items = (ArrayList<Item>) msg.obj;
                    b.putParcelableArrayList(Setting.Label_ITEM,items);
                    ClassifyFragment.classifyItemFragment.setArguments(b);
                    transaction.add(R.id.content,ClassifyFragment.classifyItemFragment);
                    transaction.commit();
                    break;
                case 0x10001:
//                    L.toast(getActivity(),(String)msg.obj);
                    break;
            }



        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myApplication = (MyApplication) getActivity().getApplication();

        MyApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<LaberAccount> laberAccounts = DatabaseManager.getLaberNumber();
                Message message = Message.obtain();
                message.obj = laberAccounts;
                message.what = 0x10003;
                mHandler.sendMessage(message);
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.label2,null);
        }
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.list_icon);
        toolbar.setTitle(getResources().getString(R.string.label));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.drawer.isDrawerOpen(GravityCompat.START)) {
                    MainActivity.drawer.closeDrawer(GravityCompat.START);
                }else {
                    MainActivity.drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        mDefaultTagGroup = (TagGroup) view.findViewById(R.id.tag_group);
        mDefaultTagGroup.setOnTagClickListener(mTagClickListener);
        nameCopy = mDefaultTagGroup.getTags();
        MyApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<LaberAccount> laberAccounts = DatabaseManager.getLaberNumber();
                Message message = Message.obtain();
                message.obj = laberAccounts;
                message.what = 0x10003;
                mHandler.sendMessage(message);
            }
        });
       return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            if (submit()) {
                mDefaultTagGroup.submitTag();
                //setTimer();
            }
        }
    }
    private void setTimer(){
        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                dealLabel();
            }
        };
        timer.schedule(task,3000);

    }




    private void dealLabel(){
        String[] name = mDefaultTagGroup.getTags();
        boolean is,same = false;
//        if (name.length == nameCopy.length){
//            for (int i = 0;i<name.length;i++){
//                if (name[i] != nameCopy[i]){
//                    is = true;
//                    continue;
//                }
//            }
//        }
        if (nameCopy != null){
            if (name.length != nameCopy.length){
                devide(name);
            }else {
                for (int i =0;i<name.length;i++){
                    if (!name[i].equals(nameCopy[i])){
                        same = true;
                        break;
                    }
                }
                if (same){
                    devide(name);
                }
            }
        }else {
            devide(name);
        }



        MyApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String info =  DatabaseManager.insertLabel(add);
                DatabaseManager.deleteLabel(delet);
                Message message = Message.obtain();
                myApplication.labels = DatabaseManager.queryAllLabels();
                message.obj = info;
                message.what = 0x10001;
                mHandler.sendMessage(message);
            }
        });
    }

    private void devide(String[] name) {
        final List<SimpleLabel> simpleLabels = new ArrayList<>();
//        for (int i = 0;i<name.length;i++){
//            SimpleLabel simpleLabel = new SimpleLabel();
//            simpleLabel.id = GuidBuilder.guidGenerator();
//            simpleLabel.name = name[i];
//            simpleLabels.add(simpleLabel);
//        }
        if (laberAccounts != null&&laberAccounts.size()>0){
                //筛选要删除的标签
                for(int i = 0;i<laberAccounts.size();i++){
                    LaberAccount lab = laberAccounts.get(i);
                    boolean isExit = false;
                    for (int j =0;j<name.length;j++){
                        if (lab.name.equals(name[j])){
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit){
                        delet.add(lab);
                    }
                }
                //筛选要新增的
                for (int i = 0;i<name.length;i++){
                    boolean isExit = false;
                    for (int j = 0;j<laberAccounts.size();j++){
                        LaberAccount l = laberAccounts.get(j);
                        if (name[i].equals(l.name)){
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit){
                        LaberAccount l = new LaberAccount();
                        l.name = name[i];
                        l.id = GuidBuilder.guidGenerator();
                        add.add(l);
                    }
                }

        }else {
            for (int i = 0;i<name.length;i++){
                LaberAccount l = new LaberAccount();
                l.name = name[i];
                l.id = GuidBuilder.guidGenerator();
                add.add(l);
            }
        }
    }

    private boolean submit(){
        String  name = mDefaultTagGroup.getInputTagText();
        boolean exit = true;
        if (laberAccounts != null){
            for(int i = 0;i<laberAccounts.size();i++){
                if (name != null){
                    if (name.equals(laberAccounts.get(i).name)){
                        exit = false;
                        continue;
                    }
                }
            }
        }
        if (exit){
            if (label_1 != null){
                for (int j = 0;j<label_1.size();j++){
                    if (name != null){
                        if (name.equals(label_1.get(j))){
                            exit = false;
                            continue;
                        }
                    }
                }
                if (exit){
                    label_1.add(name);
                }
            }
        }
        return exit;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden){
            dealLabel();
        }else {
            nameCopy = mDefaultTagGroup.getTags();
            MyApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    List<LaberAccount> laberAccounts = DatabaseManager.getLaberNumber();
                    Message message = Message.obtain();
                    message.obj = laberAccounts;
                    message.what = 0x10003;
                    mHandler.sendMessage(message);
                }
            });
        }

        super.onHiddenChanged(hidden);
    }
    }

