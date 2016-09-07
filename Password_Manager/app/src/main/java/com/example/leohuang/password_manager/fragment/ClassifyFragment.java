package com.example.leohuang.password_manager.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.activity.MainActivity;
import com.example.leohuang.password_manager.activity.SecondActivity;
import com.example.leohuang.password_manager.activity.SplashActivity;
import com.example.leohuang.password_manager.adapter.ClassifyAccountAdapter;
import com.example.leohuang.password_manager.adapter.ClassifyDialogAdapter;
import com.example.leohuang.password_manager.adapter.PopListAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.ListDao;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.bean.Notification;
import com.example.leohuang.password_manager.bean.PopDao;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.interfaces.Back;
import com.example.leohuang.password_manager.ui.PopMenu;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment implements View.OnClickListener, Back {
    View view, listHead;
    TextView title;
    TextView back, more;
    ImageView search;
    List<PopDao> list;
    PopListAdapter adapter;
    private PopMenu mPopmenu;
    FloatingActionButton button;
    ClassifyDialogAdapter adapter2;
    List<ListDao> list2;
    ListView lv_account_classify;
    ClassifyAccountAdapter classifyAccountAdapter;
    TextView count;
    public static Fragment classifyItemFragment;
    private MyApplication myApplication;
    public Back back_icon;

    public Fragment getClassifyItemFragment() {
        return classifyItemFragment;
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x10001){
                classifyAccountAdapter.setItems();
                count.setText(myApplication.items.size() + "个项");
            }
        }
    };

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
        //      initItem();
    }

    private void initItem() {
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myApplication.items = DatabaseManager.showItems(null);
                Message message = Message.obtain();
                message.what = 0x10001;
                mHandler.sendMessage(message);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.classify, null);
        listHead = inflater.inflate(R.layout.listhead, null);
        initView();
        return view;

    }

    private void initView() {
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.list_icon);
        toolbar.setTitle(getResources().getString(R.string.account));
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
//        more = (TextView) view.findViewById(R.id.openwindow);
//        more.setOnClickListener(this);
//        search = (ImageView) view.findViewById(R.id.search);
//        search.setOnClickListener(this);
//        title = (TextView) view.findViewById(R.id.title_name);
//        title.setText("分类");
//        back = (TextView) view.findViewById(R.id.back);
//        back.setOnClickListener(this);
        mPopmenu = new PopMenu(getActivity());
//        button = (FloatingActionButton) view.findViewById(R.id.fab2);
//        button.setOnClickListener(this);
        lv_account_classify = (ListView) view.findViewById(R.id.lv_account_classify);
        listHead.setTag("head");
        if (myApplication.items == null) {
            listHead.setVisibility(View.GONE);
        } else {
            int counts = myApplication.items.size();
            listHead.setVisibility(View.VISIBLE);
            lv_account_classify.addHeaderView(listHead);
            count = (TextView) listHead.findViewById(R.id.count);
            count.setText(counts + "个项");
            listHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(0);
                }
            });
        }
        classifyAccountAdapter = new ClassifyAccountAdapter(getActivity(), this);
        lv_account_classify.setAdapter(classifyAccountAdapter);
    }

    public void setSelect(int i) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (classifyItemFragment != null) {
            transaction.hide(classifyItemFragment);
        }
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:

                if (classifyItemFragment == null) {
                    classifyItemFragment = new ClassifyItemFragment(this);
                    transaction.add(R.id.content, classifyItemFragment);
                    transaction.show(classifyItemFragment);
                } else {
                    transaction.remove(classifyItemFragment);
                    classifyItemFragment = new ClassifyItemFragment(this);
                    transaction.add(R.id.content, classifyItemFragment);
                    transaction.show(classifyItemFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (classifyItemFragment != null) {
            transaction.hide(classifyItemFragment);
            transaction.commit();
        }

    }

    protected void showWindow() {
        list = new ArrayList<PopDao>();
        PopDao dao = new PopDao();
        dao.text = "同步";
        list.add(dao);
        PopDao dao1 = new PopDao();
        dao1.text = "锁定";
        list.add(dao1);
        PopDao dao2 = new PopDao();
        dao2.text = "帮助";
        list.add(dao2);
        PopDao dao3 = new PopDao();
        dao3.text = "退出";
        list.add(dao3);
        adapter = new PopListAdapter(getActivity());
        adapter.setList(list);
        mPopmenu.setAdapter(adapter);
        mPopmenu.setOnItemClickListener(popmenuItemClickListener);
        adapter.notifyDataSetChanged();
        mPopmenu.showAsDropDown(more);

    }

    AdapterView.OnItemClickListener popmenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list.get(position).text.equals("同步")) {
                Intent intent1 = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent1);
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("锁定")) {
//                Intent intent1 = new Intent(getActivity(), HanldAddActivity.class);
//                startActivity(intent1);
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("帮助")) {
//             Intent intent1=new Intent(getActivity(),)
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("退出")) {
                mPopmenu.dismiss();
            }
        }

    };


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.openwindow:
                showWindow();
                break;
            case R.id.search:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (SecondActivity.searchFragment != null) {
                    ft.add(R.id.content, SecondActivity.searchFragment);
                } else {
                    SecondActivity.searchFragment = new SearchFragment();
                    ft.add(R.id.content, SecondActivity.searchFragment);
                }

                ft.commit();
                break;
            case R.id.back:
                SecondActivity.mDrawerLayout.openDrawer(GravityCompat.START);
                break;
//            case R.id.fab2:
//                showDialog();
//                break;

        }
    }

    private void showDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.classify_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
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
        adapter2 = new ClassifyDialogAdapter(getActivity());
        adapter2.setList(list2);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DataLoggingActivity.class);
                intent.putExtra(Setting.MODEL, position);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        classifyAccountAdapter.setItems();
        count.setText(myApplication.items.size() + "个项");
        super.onResume();

    }

    @Override
    public void back() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (classifyItemFragment != null) {
            transaction.hide(classifyItemFragment);
        }
        if (MainActivity.Classify != null) {
            transaction.remove(MainActivity.Classify);
            MainActivity.Classify = new ClassifyFragment();
            transaction.add(R.id.content, MainActivity.Classify);
            transaction.show(MainActivity.Classify);
            transaction.commit();
        }

    }

    @Override
    public void back(int type) {
        if (classifyItemFragment != null) {
            classifyItemFragment = null;
        }
        classifyItemFragment = new ClassifyItemFragment(this);
        Bundle b = new Bundle();
//        b.putParcelableArrayList(Setting.FIELD_ITEM,items);
        b.putInt(Setting.TYPE, type);
        classifyItemFragment.setArguments(b);
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        transaction.add(R.id.content, classifyItemFragment);
        transaction.show(classifyItemFragment);
        transaction.commit();
    }

    @Override
    public void onEventMainThread(Notification event) {
        initItem();
        super.onEventMainThread(event);
    }
}

