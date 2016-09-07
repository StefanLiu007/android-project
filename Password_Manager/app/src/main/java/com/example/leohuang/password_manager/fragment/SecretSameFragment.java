package com.example.leohuang.password_manager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.adapter.SecretSameAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.SecretSameContainer;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.AnalyseEvent;
import com.example.leohuang.password_manager.events.ExpendableEvent;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 相同密码Fragmnet
 * Created by leo.huang on 16/4/21.
 */
public class SecretSameFragment extends Fragment {

    private final String TAG = "SecretSameFragment";

    private ExpandableListView mExpandableListView;//可扩展的ListView
    private SecretSameAdapter mAdapter;
    private SecretSameAdapter.OnParentImageClickListener mListener;
    private List<SecretSameContainer> containers = new ArrayList<SecretSameContainer>();
    private MyApplication myApplication;
    private TextView mTvDes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        //加载分析
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                containers = DatabaseManager.getSameSecret();//获取数据
                EventBus.getDefault().post(new ExpendableEvent(containers));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_secret_same_layout, null, false);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.elv_same);
        mExpandableListView.setGroupIndicator(null);
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mListener = new SecretSameAdapter.OnParentImageClickListener() {
            @Override
            public void onClick(ImageView ivicon, TextView tvPassword, boolean close) {
                if (close) {
                    ivicon.setImageResource(R.drawable.eye_open);
                    tvPassword.setTransformationMethod(null);
                } else {
                    ivicon.setImageResource(R.drawable.eye_close);
                    tvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }

            @Override
            public void onItemClick(int groupPosition, int childPosition) {
                Intent mIntent = new Intent(getActivity(), DataLoggingActivity.class);
                mIntent.putExtra(Setting.FIELD_ITEM, containers.get(groupPosition).items.get(childPosition));
                mIntent.putExtra("编辑", 1);
                startActivity(mIntent);
            }
        };
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ExpendableEvent event) {
        System.out.print(TAG + event.containers);
        mAdapter = new SecretSameAdapter(getActivity(), event.containers, myApplication.icons);
        mTvDes.setText("一共有" + event.containers.size() + "组相同的密码");
        mExpandableListView.setAdapter(mAdapter);
        int len = event.containers.size();
        for (int i = 0; i < len; i++) {
            mExpandableListView.expandGroup(i);
        }
        mAdapter.setOnImageClickListener(mListener);
    }
}
