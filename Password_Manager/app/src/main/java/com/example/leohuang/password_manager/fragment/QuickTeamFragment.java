package com.example.leohuang.password_manager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;

/**
 * Created by leo.huang on 16/3/31.
 */
public class QuickTeamFragment extends Fragment {
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
        mTvIntroduce.setText("Teams");
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mIvIcon.setImageResource(R.drawable.quick_tour_teams_banner);

        mIvItem1 = (ImageView) view.findViewById(R.id.iv_item1);
        mIvItem1.setImageResource(R.drawable.quick_tour_teams_icon);
        mTvTitle1 = (TextView) view.findViewById(R.id.tv_item1);
        mTvTitle1.setText("加入团队");
        mIvItem2 = (ImageView) view.findViewById(R.id.iv_item2);
        mIvItem2.setImageResource(R.drawable.quick_tour_paddy_icon);
        mTvTitle2 = (TextView) view.findViewById(R.id.tv_item2);
        mTvTitle2.setText("享受你的团队工作");
        mIvItem3 = (ImageView) view.findViewById(R.id.iv_item3);
        mIvItem3.setVisibility(View.GONE);
        mTvTitle3 = (TextView) view.findViewById(R.id.tv_item3);
        mTvTitle3.setVisibility(View.GONE);
        return view;
    }
}
