package com.example.leohuang.password_manager.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.LeftListAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.ListDao;
import com.example.leohuang.password_manager.events.FinishEvent;
import com.example.leohuang.password_manager.fragment.BrowserFragment;
import com.example.leohuang.password_manager.fragment.ClassifyFragment;
import com.example.leohuang.password_manager.fragment.CollectionFragment;
import com.example.leohuang.password_manager.fragment.ConfigurationFragment;
import com.example.leohuang.password_manager.fragment.LabelFragment;
import com.example.leohuang.password_manager.fragment.SearchFragment;
import com.example.leohuang.password_manager.utils.DialogUtils;
import com.example.leohuang.password_manager.utils.PrefUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private long mExitTime;
    public static DrawerLayout mDrawerLayout;
    private ListView mLv;
    public static Fragment Classify, Collection, CreatSecret, Label, Browser, Configuration, searchFragment;
    List<ListDao> list;
    private LeftListAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_second);
        assignViews();
        if (MyApplication.isFirst == true) {
            MyApplication.isFirst = false;
            PrefUtils.setFirst(this);
        }
    }

    @Override
    public void assignViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout2);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
             */
            public void onDrawerStateChanged(int arg0) {
                Log.i("drawer", "drawer的状态：" + arg0);
            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * arg1 表示 滑动的幅度（0-1）
             */
            @Override
            public void onDrawerSlide(View arg0, float arg1) {
                Log.i("drawer", arg1 + "");
            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View arg0) {
                Log.i("drawer", "抽屉被完全打开了！");
            }

            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View arg0) {
                Log.i("drawer", "抽屉被完全关闭了！");
            }
        });

        /**
         * 也可以使用DrawerListener的子类SimpleDrawerListener,
         * 或者是ActionBarDrawerToggle这个子类(详见FirstDemoActivity)
         */
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        });


        list = new ArrayList<>();
        ListDao dao = new ListDao();
        dao.icon = R.drawable.menu_folders;
        dao.title = "收藏夹";
        list.add(dao);

        ListDao dao1 = new ListDao();
        dao1.icon = R.drawable.menu_categories;
        dao1.title = "所有账户";
        list.add(dao1);

        ListDao dao2 = new ListDao();
        dao2.icon = R.drawable.menu_folders;
        dao2.title = "密码生成";
        list.add(dao2);

        ListDao dao3 = new ListDao();
        dao3.icon = R.drawable.menu_settings;
        dao3.title = "标签";
        list.add(dao3);

        ListDao dao4 = new ListDao();
        dao4.icon = R.drawable.menu_settings;
        dao4.title = "浏览器";
        list.add(dao4);

        ListDao dao5 = new ListDao();
        dao5.icon = R.drawable.menu_settings;
        dao5.title = "设置";
        list.add(dao5);
        adapter = new LeftListAdapter(this);
        adapter.setList(list);
        mLv = (ListView) findViewById(R.id.id_lv);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        searchFragment = new SearchFragment();
        setSelect(0);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setSelect(1);
                break;
            case 1:
                setSelect(0);
                break;
            case 2:
                setSelect(2);
                break;
            case 3:
                setSelect(3);
                break;
            case 4:
                setSelect(4);
                break;
            case 5:
                setSelect(5);
                break;


        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (SecondActivity.Collection != null) {
            if (!SecondActivity.Collection.isHidden()) {
                setSelect(1);
            } else {
                setSelect(0);
            }
        } else {
            setSelect(0);
        }

        super.onResume();

    }

    // 监听手机上的BACK键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断两次点击的时间间隔（默认设置为2秒）
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                AppManager.getAppManager().AppExit(this);
                System.exit(0);
                super.onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void setSelect(int i) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (i != 2) {
            hideFragment(transaction);
        }
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (Classify == null) {
                    Classify = new ClassifyFragment();
                    if (ClassifyFragment.classifyItemFragment != null) {
                        transaction.hide(ClassifyFragment.classifyItemFragment);
                    }
                    transaction.add(R.id.id_framelayout2, Classify);
                } else {
                    transaction.remove(Classify);
                    Classify = new ClassifyFragment();
                    transaction.add(R.id.id_framelayout2, Classify);
                    if (ClassifyFragment.classifyItemFragment != null) {
                        transaction.hide(ClassifyFragment.classifyItemFragment);
                    }
                    transaction.show(Classify);
                }
                break;
            case 1:
                if (Collection == null) {
                    Collection = new CollectionFragment();
                    transaction.add(R.id.id_framelayout2, Collection);
                } else {
                    transaction.remove(Collection);
                    Collection = new CollectionFragment();
                    transaction.add(R.id.id_framelayout2, Collection);
                    transaction.show(Collection);
                }
                break;
            case 2:
                DialogUtils.secretGeneratorTwo(this, null, false, myApplication);
//                if (CreatSecret == null) {
//                    CreatSecret = new CreatSecretFragment();
//                    transaction.add(R.id.id_framelayout2, CreatSecret);
//                } else {
//                    transaction.show(CreatSecret);
//                }
                break;
            case 3:
                if (Label == null) {
                    Label = new LabelFragment();
                    transaction.add(R.id.id_framelayout2, Label);
                } else {
                    transaction.show(Label);
                }
                break;
            case 4:
                if (Browser == null) {
                    Browser = new BrowserFragment();
                    transaction.add(R.id.id_framelayout2, Browser);
                } else {
                    transaction.show(Browser);
                }
                break;
            case 5:
                if (Configuration == null) {
                    Configuration = new ConfigurationFragment();
                    transaction.add(R.id.id_framelayout2, Configuration);
                } else {
                    transaction.show(Configuration);
                }
                break;

            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (Classify != null) {
            transaction.hide(Classify);
        }
        if (Collection != null) {
            transaction.hide(Collection);
        }
        if (CreatSecret != null) {
            transaction.hide(CreatSecret);
        }
        if (Label != null) {
            transaction.hide(Label);
        }
        if (Browser != null) {
            transaction.hide(Browser);
        }
        if (Configuration != null) {
            transaction.hide(Configuration);
        }
//        if(classifyItem != null){
//            transaction.hide(classifyItem);
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBack(FinishEvent event) {
        setSelect(1);
    }
}
