package com.example.leohuang.password_manager.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;

import java.io.PipedInputStream;

/**
 * Created by 46697 on 2016/3/27.
 */
public class ChoosePINFragment extends Fragment {
    private final String TAG = "ChoosePINFragment";
    private ImageView mIvIcon;
    private TextView mTvTitle, mTvDes;
    private Button mBtnConfirm;
    private EditText mEtInput;
    private FragmentButtonClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FragmentButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_same_layout, null, false);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mEtInput.requestFocus();
        mEtInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mEtInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        initEvents();
        return view;
    }

    private void initEvents() {
        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    intentToNext();
                    return false;
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
                Log.i(TAG, "OnTextChanged");
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
                intentToNext();
            }
        });
    }

    private void intentToNext() {
        String msg = mEtInput.getText().toString().trim();
        if (msg.length() < 4) {
            mTvTitle.setText("PIN码长度不可小于4个数字");
        } else {
            mListener.onBtnClick(msg, 0);
        }
    }
}
