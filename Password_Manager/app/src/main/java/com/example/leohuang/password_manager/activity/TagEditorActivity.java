package com.example.leohuang.password_manager.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.LaberAccount;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.ui.TagGroup;
import com.example.leohuang.password_manager.utils.GuidBuilder;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;


public class TagEditorActivity extends AppCompatActivity {
    private TagGroup mTagGroup;
    private List<LaberAccount> laberAccounts;//获取所有数据库标签
    private List<String> label = new ArrayList<>();
    Toolbar toolbar;
    private String[] nameCopy;
    private List<LaberAccount> delet = new ArrayList<>();//要删除的标签
    private List<LaberAccount> add = new ArrayList<>();//要新增的标签
    private List<LaberAccount> edit = new ArrayList<>();//要编辑的标签
    private List<String> label_1 = new ArrayList<>();//如果账户没标签，防止重复添加标签
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x10000:
                    laberAccounts = (List<LaberAccount>)msg.obj;
                    for (int i = 0;i<laberAccounts.size();i++){
                        label.add(laberAccounts.get(i).name);
                    }
                    if (laberAccounts != null && laberAccounts.size() > 0) {
                        mTagGroup.setTags(label);
                    }
                    break;
                case 0x10001:
                    Toast.makeText(TagEditorActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_editor);
        toolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        toolbar.setTitle(getResources().getString(R.string.edit));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        nameCopy = mTagGroup.getTags();
        MyApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<LaberAccount> laberAccounts = DatabaseManager.getLaberNumber();
                Message message = Message.obtain();
                message.obj = laberAccounts;
                message.what = 0x10000;
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag_editor_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            dealLabel();
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_submit) {
            if (submit()){
                mTagGroup.submitTag();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        dealLabel();
        super.onBackPressed();
    }
    private void dealLabel(){
        String[] name = mTagGroup.getTags();
        boolean is = false;
//        if (name.length == nameCopy.length){
//            for (int i = 0;i<name.length;i++){
//                if (name[i] != nameCopy[i]){
//                    is = true;
//                    continue;
//                }
//            }
//        }

            if (nameCopy != name){
                final List<SimpleLabel> simpleLabels = new ArrayList<>();
                for (int i = 0;i<name.length;i++){
                    SimpleLabel simpleLabel = new SimpleLabel();
                    simpleLabel.id = GuidBuilder.guidGenerator();
                    simpleLabel.name = name[i];
                    simpleLabels.add(simpleLabel);
                }
                if (laberAccounts != null){
                    if (laberAccounts.size()>0){
                        //筛选要删除的标签
                        for(int i = 0;i<laberAccounts.size();i++){
                            LaberAccount lab = laberAccounts.get(i);
                            boolean isExit = false;
                            for (int j =0;j<name.length;j++){
                                if (lab.name.equals(name[j])){
                                    isExit = true;
                                    continue;
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
                                    continue;
                                }
                            }
                            if (!isExit){
                                LaberAccount l = new LaberAccount();
                                l.name = name[i];
                                l.id = GuidBuilder.guidGenerator();
                                add.add(l);
                            }
                        }
                    }

                }
            }
            MyApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String info =  DatabaseManager.insertLabel(add);
                    DatabaseManager.deleteLabel(delet);
                    Message message = Message.obtain();
                    message.obj = info;
                    message.what = 0x10001;
                    mHandler.sendMessage(message);
                }
            });
    }
    private boolean submit(){
        String  name = mTagGroup.getInputTagText();
        boolean exit = true;
        if (laberAccounts != null){
            for(int i = 0;i<laberAccounts.size();i++){
                if (name.equals(laberAccounts.get(i).name)){
                    Toast.makeText(this,Setting.EXIT_LABEL,Toast.LENGTH_SHORT).show();
                    exit = false;
                    continue;
                }
            }
        }
        if (exit){
            if (label_1 != null){
                for (int j = 0;j<label_1.size();j++){
                    if (name.equals(label_1.get(j))){
                        Toast.makeText(this,Setting.EXIT_LABEL,Toast.LENGTH_SHORT).show();
                        exit = false;
                        continue;
                    }
                }
                if (exit){
                    label_1.add(name);
                }
            }
        }
        return exit;
    }
}