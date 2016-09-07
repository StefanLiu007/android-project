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
import com.example.leohuang.password_manager.interfaces.FragmentButtonClickListener;
import com.example.leohuang.password_manager.interfaces.OnFragmentButtonClickListener;
import com.example.leohuang.password_manager.utils.Setting;

import org.w3c.dom.Text;

/**
 * Created by leo.huang on 16/3/28.
 */
public class ChoosePasswordFragment extends Fragment {
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
        mIvIcon.setImageResource(R.drawable.confirm_password_paddy_icon);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText("输入您的新主密码");
        mTvDes = (TextView) view.findViewById(R.id.tv_description);
        mTvDes.setText("您的主密码将保护您的所有数据");
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mEtInput.requestFocus();
        mEtInput.setHint("输入新的主密码");
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
                    inputPassword();
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
                inputPassword();
            }
        });
    }

    private void inputPassword() {
        String message = mEtInput.getText().toString().trim();
        // TODO: 2016/3/28 密码长度？？？
        if (!TextUtils.isEmpty(message)) {
            mListener.onBtnClick(message, Setting.CHOOSE_PASSWORD);
        }
    }

}
