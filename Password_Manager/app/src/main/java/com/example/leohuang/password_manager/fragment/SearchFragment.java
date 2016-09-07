package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.SecondActivity;
import com.example.leohuang.password_manager.events.ConfirmEvent;
import com.example.leohuang.password_manager.events.FinishEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 搜索fragmnet
 * Created by sun on 16/3/9.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    View view;
    EditText editText;
    TextView back, clear;
    ImageView search_click_clear, search_start;
    private FragmentManager fragmentManager;
    private Fragment LabelShow;
    FragmentTransaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchfragment, null);
        initView();
        return view;
    }

    private TextWatcher textwatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = editText.getText().toString().trim();
            if (text != "") {
                search_click_clear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void initView() {
        editText = (EditText) view.findViewById(R.id.search_et);
        editText.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        editText.addTextChangedListener(textwatch);
//
//        Bundle bundle = new Bundle();
//        String texts = bundle.getString("text1");
//        if (texts != null) {
//            editText.setText(texts);
//        }

        back = (TextView) view.findViewById(R.id.search_back);
        back.setOnClickListener(this);
        clear = (TextView) view.findViewById(R.id.search_clear);
        clear.setOnClickListener(this);
        search_click_clear = (ImageView) view.findViewById(R.id.search_click_clear);
        fragmentManager = getFragmentManager();
        LabelShow = new LabelShowFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.search_result_layout, LabelShow).commit();
        search_start = (ImageView) view.findViewById(R.id.search_start);
        search_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                EventBus.getDefault().post(new FinishEvent());
                break;
            case R.id.search_clear:
                editText.setText("");
                search_click_clear.setVisibility(View.GONE);
                break;
            case R.id.search_start:
                String text = editText.getText().toString().trim();
                if (!text.equals("")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("text", text);
                    SearchResultFragment fragment = new SearchResultFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.search_result_layout, fragment);
                    ft.commit();
                } else {
                    Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSearch(ConfirmEvent event) {
        editText.setText(event.info);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
