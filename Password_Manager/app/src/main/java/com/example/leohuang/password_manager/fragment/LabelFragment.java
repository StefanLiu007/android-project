package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.tag.TagBaseAdapter;
import com.example.leohuang.password_manager.tag.TagCloudLayout;
import com.example.leohuang.password_manager.utils.GuidBuilder;


/**
 * 只是纯粹的添加标签
 * Created by sun on 16/3/23.
 */
public class LabelFragment extends Fragment {
    View view;
    private TagCloudLayout mContainer;
    private TagBaseAdapter mAdapter;
    private MyApplication myApplication;
    private EditText add_btn;
//    private ArrayList<Label> lists;
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.label, null);
        initView();
        return view;
    }

    private void initView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.list_icon);
        toolbar.setTitle(getResources().getString(R.string.label));
        mContainer = (TagCloudLayout) view.findViewById(R.id.cloud_label);
        mAdapter = new TagBaseAdapter(getActivity(), myApplication.labels);// TODO: 16/4/8 获取标签
        mContainer.setAdapter(mAdapter);
        add_btn = (EditText) view.findViewById(R.id.add_btn);
        add_btn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                            getActivity().INPUT_METHOD_SERVICE);
                    String text = add_btn.getText().toString().trim();
                    if (TextUtils.isEmpty(text)) {
                        Toast.makeText(getActivity(), "标签内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        addLabel(text);
                    }
                }
                return true;
            }
        });
    }

    private void addLabel(String text) {//添加标签
        for (SimpleLabel label : myApplication.labels) {
            if (label.name.equals(text)) {
                add_btn.setText("");
                Toast.makeText(getActivity(), "该标签已存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        SimpleLabel label = new SimpleLabel();
        label.name = text;
        label.id = GuidBuilder.guidGenerator();
        myApplication.labels.add(label);
        mAdapter.notifyDataSetChanged();
        add_btn.setText("");
        DatabaseManager.addLabel(label);

    }
}
