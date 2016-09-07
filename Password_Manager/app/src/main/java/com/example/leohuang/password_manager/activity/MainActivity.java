package com.example.leohuang.password_manager.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.ClassifyDialogAdapter;
import com.example.leohuang.password_manager.adapter.SearchAdapter;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.AllOrSingle;
import com.example.leohuang.password_manager.bean.DownBack;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.ListDao;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.db.SharedPreference;
import com.example.leohuang.password_manager.events.ChangeItemEvent;
import com.example.leohuang.password_manager.events.FinishEvent;
import com.example.leohuang.password_manager.fragment.AllSearchFragment;
import com.example.leohuang.password_manager.fragment.BrowserFragment;
import com.example.leohuang.password_manager.fragment.ClassifyFragment;
import com.example.leohuang.password_manager.fragment.CollectionFragment;
import com.example.leohuang.password_manager.fragment.ConfigurationFragment;
import com.example.leohuang.password_manager.fragment.LabelFragment2;
import com.example.leohuang.password_manager.utils.DialogUtils;
import com.example.leohuang.password_manager.utils.IntentUtils;
import com.example.leohuang.password_manager.utils.PrefUtils;
import com.example.leohuang.password_manager.utils.Setting;
import com.example.leohuang.password_manager.utils.Uiutils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ClassifyDialogAdapter adapter2;
    List<ListDao> list2;
    private MyApplication myApplication;
    public static DrawerLayout drawer;
    NavigationView navigationView;
    FloatingActionButton fab;
    Toolbar toolbar;
    private long mExitTime;
    public static int position = -1;//判断fab的点击事件的响应
    public static Fragment Classify, Collection, CreatSecret, Label, Browser, Configuration, searchFragment, allsearchfragment;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_main);
        init();
        if (MyApplication.isFirst == true) {
            MyApplication.isFirst = false;
            PrefUtils.setFirst(this);
        }


    }

    private void init() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        myApplication = (MyApplication) getApplication();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSelect(0);
    }

    @Override
    protected void onResume() {
        if (MainActivity.Collection == null && MainActivity.Browser == null && Label == null && Configuration == null && ClassifyFragment.classifyItemFragment == null) {
            setSelect(0);
        } else if (Collection != null && !Collection.isHidden()) {
            setSelect(1);
        } else if (Label != null && !Label.isHidden()) {
            setSelect(3);
        } else if (Configuration != null && !Configuration.isHidden()) {
            setSelect(5);
        } else if (ClassifyFragment.classifyItemFragment != null && !ClassifyFragment.classifyItemFragment.isHidden()) {
        }
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.synchronization) {
//            return true;
//        } else if (id == R.id.lock) {
//            return true;
//        } else if (id == R.id.help) {
//            return true;
//        } else if (id == R.id.exit) {
//            return true;
//        } else if (id == R.id.action_search) {
////            setSelect(6);
////            invalidateOptionsMenu();//使原来的菜单无效
//            loadToolBarSearch();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.collection) {
            hideFab();
            setSelect(1);
        } else if (id == R.id.account) {
            showFab();
            setSelect(0);

        } else if (id == R.id.secret) {
            setSelect(2);

        } else if (id == R.id.label) {
            hideFab();
            setSelect(3);

        } else if (id == R.id.brower) {
            hideFab();
            setSelect(4);

        } else if (id == R.id.setting) {
            hideFab();
            setSelect(5);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.classify_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择类别")
                .setView(view)
                .create();
        ListView listView = (ListView) view.findViewById(R.id.account_classify_list);
        list2 = new ArrayList<>();
        for (int i = 0; i < myApplication.models.size(); i++) {
            Model model = myApplication.models.get(i);
            ListDao dao = new ListDao(model.icon, model.name);
            list2.add(dao);
        }
        adapter2 = new ClassifyDialogAdapter(this);
        adapter2.setList(list2);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DataLoggingActivity.class);
                intent.putExtra(Setting.MODEL, position);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void assignViews() {
//        setSelect(0);
    }

    @Override
    protected void initEvents() {

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
                position = -1;
                setToolBar(R.drawable.list_icon, R.string.account);
                if (Classify == null) {
                    Classify = new ClassifyFragment();
                    if (ClassifyFragment.classifyItemFragment != null) {
                        transaction.hide(ClassifyFragment.classifyItemFragment);
                    }
                    transaction.add(R.id.content, Classify);
                } else {
                    if (ClassifyFragment.classifyItemFragment != null) {
                        transaction.hide(ClassifyFragment.classifyItemFragment);
                    }
                    transaction.show(Classify);
                }

                break;
            case 1:
                setToolBar(R.drawable.list_icon, R.string.collection);
                if (Collection == null) {
                    Collection = new CollectionFragment();
                    transaction.add(R.id.content, Collection);
                } else {
                    transaction.show(Collection);
                }
                break;
            case 2:
                DialogUtils.secretGeneratorTwo(this, null, false, myApplication);
                break;
            case 3:
                setToolBar(R.drawable.list_icon, R.string.label);
                if (Label == null) {
                    Label = new LabelFragment2();
                    transaction.add(R.id.content, Label);
                } else {
                    transaction.show(Label);
                }
                break;
            case 4:
                if (Browser == null) {
                    Browser = new BrowserFragment();
                    transaction.add(R.id.content, Browser);
                } else {
                    transaction.show(Browser);
                }
                setToolBar(R.drawable.list_icon, R.string.brower);
                break;
            case 5:
                setToolBar(R.drawable.list_icon, R.string.setting);
                if (Configuration == null) {
                    Configuration = new ConfigurationFragment();
                    transaction.add(R.id.content, Configuration);
                } else {
                    transaction.show(Configuration);
                }

                break;
            case 6:
                if (allsearchfragment == null) {
                    allsearchfragment = new AllSearchFragment();
                    transaction.add(R.id.content, allsearchfragment);
                } else {
                    transaction.show(allsearchfragment);
                }

            default:
                break;
        }

        transaction.commit();
    }

    private void setToolBar(int id, int string) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(id);
        toolbar.setTitle(getResources().getString(string));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.drawer.isDrawerOpen(GravityCompat.START)) {
                    MainActivity.drawer.closeDrawer(GravityCompat.START);
                } else {
                    MainActivity.drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.lock:
                        if (myApplication.backgroundLock && !MyApplication.stop && !myApplication.isFirst) {
                            MyApplication.stop = true;
                            IntentUtils.intentToLockActivity(MainActivity.this);
                        }
                        break;
                    case R.id.exit://退出
                        MainActivity.this.finish();
                        AppManager.getAppManager().finishAllActivity();
                        System.exit(0);
                        break;
                    case R.id.action_search:
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
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
        if (ClassifyFragment.classifyItemFragment != null) {
            transaction.hide(ClassifyFragment.classifyItemFragment);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断两次点击的时间间隔（默认设置为2秒）
            DownBack downBack = new DownBack();
            EventBus.getDefault().post(downBack);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBack(FinishEvent event) {
        setSelect(1);
    }

    public static void setToolBar(String name) {

    }

    TextView all, item, label;
    ListView listSearch;

    //点击搜索 弹出的dialog
    public void loadToolBarSearch() {
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
        ArrayList<Item> countryStored = SharedPreference.loadList(MainActivity.this, Uiutils.PREFS_NAME, Uiutils.KEY_COUNTRIES);
        View view = MainActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        all = (TextView) view.findViewById(R.id.all_item);
        item = (TextView) view.findViewById(R.id.item_name);
        label = (TextView) view.findViewById(R.id.label_item);
        all.setOnClickListener(this);
        item.setOnClickListener(this);
        label.setOnClickListener(this);
        setStyle(all);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        ImageView imgToolMic = (ImageView) view.findViewById(R.id.img_tool_mic);
        listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        Uiutils.setListViewHeightBasedOnChildren(listSearch);
        edtToolSearch.setHint("Search ...");
        final Dialog toolbarSearchDialog = new Dialog(MainActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.setCanceledOnTouchOutside(true);
        toolbarSearchDialog.show();
        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        countryStored = (countryStored != null && countryStored.size() > 0) ? countryStored : new ArrayList<Item>();
        final SearchAdapter searchAdapter = new SearchAdapter(MainActivity.this, countryStored, false);
        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);
        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            }
        });
        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(myApplication.items, true);


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<String>();
                boolean isNodata = false;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtToolSearch.setText("");

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (position >= 0) {
                    Intent intent = new Intent(MainActivity.this, DataLoggingActivity.class);
                    intent.putExtra(Setting.MODEL, position);
                    startActivity(intent);
                } else {
                    showDialog();
                }
                break;
            case R.id.all_item:
                clearStyle();
                setStyle(all);
                break;
            case R.id.item_name:
                clearStyle();
                setStyle(item);
                break;
            case R.id.label_item:
                clearStyle();
                setStyle(label);
                break;
            default:
                break;
        }
    }

    public void setStyle(TextView button) {
        button.setBackgroundColor(getResources().getColor(R.color.accent));
        button.setTextColor(getResources().getColor(R.color.white));

    }

    private void clearStyle() {
        all.setBackgroundColor(getResources().getColor(R.color.white));
        all.setTextColor(getResources().getColor(R.color.accent));
        item.setBackgroundColor(getResources().getColor(R.color.white));
        item.setTextColor(getResources().getColor(R.color.accent));
        label.setBackgroundColor(getResources().getColor(R.color.white));
        label.setTextColor(getResources().getColor(R.color.accent));

    }

    private void hideFab() {
        fab.setVisibility(View.GONE);
    }

    private void showFab() {
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEventMainThread(AllOrSingle allOrSingle) {
        int type = allOrSingle.getType();
        List<Model> models = myApplication.models;
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).id == type) {
                position = i;
                continue;
            }
        }
        super.onEventMainThread(allOrSingle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeItemEvent event) {
        Intent intent = new Intent(this, DisplayItemInfo.class);
        intent.putExtra(Setting.FIELD_ITEM, event.mItem);
        startActivity(intent);
    }
}
