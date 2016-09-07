package com.example.leohuang.password_manager.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.interfaces.QuickOnClickListener;

/**
 * Created by leo.huang on 16/3/31.
 */
public class QuickWelcomeFragment extends Fragment implements View.OnClickListener {
    private Button mBtnCreate;
    private QuickOnClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QuickOnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implements QuickOnClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quick_welcome_layout, null, false);
        mBtnCreate = (Button) view.findViewById(R.id.btn_create);
        mBtnCreate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_create:
                mListener.onBtnClick();
                break;
            default:
                break;
        }
    }
}
