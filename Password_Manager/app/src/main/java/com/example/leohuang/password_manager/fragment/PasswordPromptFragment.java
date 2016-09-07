package com.example.leohuang.password_manager.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;
import com.example.leohuang.password_manager.utils.Setting;

/**
 * 密码提示
 * Created by leo.huang on 16/3/31.
 */
public class PasswordPromptFragment extends Fragment {
    private ImageView mIvIcon;
    private TextView mTvTitle, mTvDes;
    private Button mBtnConfirm;
    private EditText mEtInput;
    private MyApplication myApplication;

    private FragmentButtonClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implements FragmentButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_same_layout, null, false);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText("密码提示");
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mTvDes.setText("请输入一个可以帮您想起主密码的提示");
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mEtInput.requestFocus();
        mEtInput.setHint("输入密码提示");
        mEtInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEtInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        mBtnConfirm.setEnabled(true);
        mBtnConfirm.setBackgroundResource(R.drawable.btn_blue_selector);
        mBtnConfirm.setText("完成");
        initEvents();
        return view;
    }

    private void initEvents() {
        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    confirmPassword();
                    return false;
                }
                return false;
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPassword();
            }
        });
    }

    private void confirmPassword() {
        final String msg = mEtInput.getText().toString().trim();
        mListener.onBtnClick(msg, Setting.TIP);
    }
}
