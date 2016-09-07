package com.example.leohuang.password_manager.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.leohuang.password_manager.activity.CheckSecretStrengthActivity;
import com.example.leohuang.password_manager.activity.SafeSettingActivity;
import com.example.leohuang.password_manager.activity.SecondActivity;
import com.example.leohuang.password_manager.activity.SyncSettingActivity;
import com.example.leohuang.password_manager.adapter.ConfigurationAdapter;
import com.example.leohuang.password_manager.adapter.PopListAdapter;
import com.example.leohuang.password_manager.bean.ListDao;
import com.example.leohuang.password_manager.bean.PopDao;
import com.example.leohuang.password_manager.ui.PopMenu;

import java.util.ArrayList;
import java.util.List;


public class ConfigurationFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView title;
    TextView back, more;
    ImageView search;
    List<PopDao> list;
    PopListAdapter adapter;
    private PopMenu mPopmenu;
    ListView listView;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.configuration, null);
        initView();
        return view;
    }

    private void initView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.list_icon);
        toolbar.setTitle(getResources().getString(R.string.setting));
//        title = (TextView) view.findViewById(R.id.title_name);
//        title.setText("设置");
//        back = (TextView) view.findViewById(R.id.back);
//        back.setOnClickListener(this);
//        more = (TextView) view.findViewById(R.id.openwindow);
//        more.setOnClickListener(this);
//        search = (ImageView) view.findViewById(R.id.search);
//        search.setVisibility(View.GONE);
        mPopmenu = new PopMenu(getActivity());
        listView = (ListView) view.findViewById(R.id.config_list);
        List<ListDao> list2 = new ArrayList<>();
        ListDao dao1 = new ListDao(R.drawable.settings_security, "安全");
        list2.add(dao1);
        ListDao dao2 = new ListDao(R.drawable.settings_sync, "同步");
        list2.add(dao2);
        ListDao dao3 = new ListDao(R.drawable.ic_secret, "强度检测");
        list2.add(dao3);
//        ListDao dao3 = new ListDao(R.drawable.settings_display, "显示");
//        list2.add(dao3);
//        ListDao dao4 = new ListDao(R.drawable.settings_filling, "填写");
//        list2.add(dao4);
//        ListDao dao5 = new ListDao(R.drawable.settings_premium, "高级功能");
//        list2.add(dao5);
//        ListDao dao6 = new ListDao(R.drawable.settings_teams, "1Password for Teams");
//        list2.add(dao6);
//        ListDao dao7 = new ListDao(R.drawable.settings_advanced, "高级");
//        list2.add(dao7);
//        ListDao dao8 = new ListDao(R.drawable.settings_about, "关于");
//        list2.add(dao8);
//        ListDao dao9 = new ListDao(R.drawable.settings_about, "允许wifi同步");
//        list2.add(dao9);
        ConfigurationAdapter configurationAdapter = new ConfigurationAdapter(getActivity());
        configurationAdapter.setList(list2);
        listView.setAdapter(configurationAdapter);
        initData();
    }

    private void initData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity(), SafeSettingActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), SyncSettingActivity.class));
                        break;
                    case 2:
                        // TODO: 16/4/18 密码强度检测
                        startActivity(new Intent(getActivity(), CheckSecretStrengthActivity.class));
                        break;
//                    case 8:
//                        startActivity(new Intent(getActivity(), WifiSyncServerActivity.class));
//                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                SecondActivity.mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.openwindow:
                showWindow();
                break;
        }
    }

    protected void showWindow() {
        list = new ArrayList<PopDao>();
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
//                Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
//                startActivity(intent1);
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
}
