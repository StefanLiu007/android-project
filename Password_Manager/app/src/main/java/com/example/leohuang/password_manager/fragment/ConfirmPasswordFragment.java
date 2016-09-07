package com.example.leohuang.password_manager.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.leohuang.password_manager.bean.User;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;
import com.example.leohuang.password_manager.interfaces.OnFragmentButtonClickListener;
import com.example.leohuang.password_manager.utils.Setting;

/**
 * Created by leo.huang on 16/3/28.
 */
public class ConfirmPasswordFragment extends Fragment {
    private ImageView mIvIcon;
    private TextView mTvTitle, mTvDes;
    private Button mBtnConfirm;
    private EditText mEtInput;
    private String message;
    private FragmentButtonClickListener mListener;
    private boolean next;

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
        Bundle b = getArguments();
        next = b.getBoolean("next");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_same_layout, null, false);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText("输入您的新主密码");
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mTvDes.setText("您的主密码将保护您的所有数据");
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mEtInput.requestFocus();
        mEtInput.setHint("输入新的主密码");
        mEtInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEtInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if (!next) {
            mEtInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        } else {
            mEtInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        }
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        if (!next) {
            mBtnConfirm.setText("完成");
        }
        initEvents();
        return view;
    }

    private void initEvents() {
        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!next) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        confirmPassword();
                        return true;
                    }
                } else {
                    if (actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        confirmPassword();
                        return true;
                    }
                }
                return false;
            }
        });
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mBtnConfirm.setEnabled(false);
                    mBtnConfirm.setBackgroundResource(R.drawable.btn_gray_background);
                } else {
                    mBtnConfirm.setEnabled(true);
                    mBtnConfirm.setBackgroundResource(R.drawable.btn_blue_selector);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        if (message.equals(msg)) {
            // TODO: 16/3/28 数据库存储
            mListener.onBtnClick(msg, Setting.CONFIRM_PASSWORD);
        } else {
            mTvTitle.setText("两次输入的主密码不同");
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
