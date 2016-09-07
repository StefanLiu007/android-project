package com.example.leohuang.password_manager.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.leohuang.password_manager.interfaces.OnFragmentButtonClickListener;

/**
 * Created by leo.huang on 16/3/28.
 */
public class CheckPasswordFragment extends Fragment {
    private ImageView mIvIcon;
    private TextView mTvTitle, mTvDes;
    private Button mBtnConfirm;
    private EditText mEtInput;
    private MyApplication myApplication;
    private Handler mHandler;
    private final int CLEAR_TEXT = 0x0001;
    private OnFragmentButtonClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement OnFragmentButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CLEAR_TEXT:
                        mEtInput.setTextColor(Color.BLACK);
                        mEtInput.setText("");
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.password_same_layout, null, false);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText("输入您当前的主密码");
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mTvDes.setText("在你改变你的主密码前需要确认当前的密码");
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mEtInput.requestFocus();
        mEtInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEtInput.setHint("输入当前的主密码");
        mEtInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEtInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        initEvents();
        return view;
    }

    private void initEvents() {
        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkPassword();
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
                checkPassword();
            }
        });
    }

    private void checkPassword() {
        String msg = mEtInput.getText().toString().trim();
        if (msg.equals(myApplication.user.password)) {
            mListener.onBtnClick();
        } else {
            mEtInput.setTextColor(Color.RED);
            mEtInput.setText("主密码不正确");
            mHandler.sendEmptyMessageDelayed(CLEAR_TEXT, 1500);
        }
    }

}
