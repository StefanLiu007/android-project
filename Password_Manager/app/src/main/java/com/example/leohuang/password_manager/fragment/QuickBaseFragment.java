package com.example.leohuang.password_manager.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.interfaces.QuickOnClickListener;

/**
 * 启动介绍：基础知识
 * Created by leo.huang on 16/3/31.
 */
public class QuickBaseFragment extends Fragment {
    private ImageView mIvIcon, mIvItem1, mIvItem2, mIvItem3;
    private TextView mTvIntroduce, mTvTitle1, mTvTitle2, mTvTitle3;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quick_same_fragment, null, false);
        mTvIntroduce = (TextView) view.findViewById(R.id.tv_introduce);
        mTvIntroduce.setText("基础知识");
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mIvIcon.setImageResource(R.drawable.quick_tour_basics_banner);

        mIvItem1 = (ImageView) view.findViewById(R.id.iv_item1);
        mIvItem1.setImageResource(R.drawable.quick_tour_login_icon);
        mTvTitle1 = (TextView) view.findViewById(R.id.tv_item1);
        mTvTitle1.setText(Html.fromHtml("<b>登录</b>网站，查看安全详情，一按存储密码"));
        mIvItem2 = (ImageView) view.findViewById(R.id.iv_item2);
        mIvItem2.setImageResource(R.drawable.quick_tour_organize_icon);
        mTvTitle2 = (TextView) view.findViewById(R.id.tv_item2);
        mTvTitle2.setText(Html.fromHtml("<b>整理</b>您的项，可将它们分类，亦可将常用项标为最爱"));
        mIvItem3 = (ImageView) view.findViewById(R.id.iv_item3);
        mIvItem3.setImageResource(R.drawable.quick_tour_generate_icon);
        mTvTitle3 = (TextView) view.findViewById(R.id.tv_item3);
        mTvTitle3.setText(Html.fromHtml("<b>生成</b>强且不重复的网站密码"));
        return view;
    }
}
