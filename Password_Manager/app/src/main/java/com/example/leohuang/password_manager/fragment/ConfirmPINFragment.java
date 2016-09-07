package com.example.leohuang.password_manager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.leohuang.password_manager.utils.PrefUtils;
import com.example.leohuang.password_manager.utils.Setting;

/**
 * Created by 46697 on 2016/3/27.
 */
public class ConfirmPINFragment extends Fragment {
    private ImageView mIvIcon;
    private TextView mTvTitle, mTvDes;
    private Button mBtnConfirm;
    private EditText mEtInput;
    private String msg;
    private MyApplication myApplication;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_same_layout, null, false);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        //设置图标
        mIvIcon.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.confirm_password_paddy_icon));
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText("确认您的PIN");
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mEtInput.requestFocus();
        mEtInput.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        mEtInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        initEvents();
        return view;
    }

    private void initEvents() {
        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    saveChanged();
                    return true;
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
                saveChanged();
            }
        });
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private void saveChanged() {
        final String confirmMsg = mEtInput.getText().toString().trim();
        if (confirmMsg.equals(msg)) {
            myApplication.user.pin = confirmMsg;
            myApplication.usePin = true;
            myApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // TODO: 2016/3/28 把PIN密码放入数据库
                    User user = new User();
                    user.pin = confirmMsg;
                    DatabaseManager.updatePinOrPassword(user);

                }
            });
            PrefUtils.setUsePIN(getActivity(), true);
            getActivity().finish();
        } else {
            mTvTitle.setText("PIN码不一致");
            mEtInput.selectAll();
        }
    }
}
