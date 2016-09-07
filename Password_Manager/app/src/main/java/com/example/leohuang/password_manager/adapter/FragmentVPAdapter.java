package com.example.leohuang.password_manager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * viewpager的Adapter（PIN和Password）
 * Created by 46697 on 2016/3/27.
 */
public class FragmentVPAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public FragmentVPAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }

    public FragmentVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments!=null){
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments!=null){
            return  fragments.size();
        }
        return 0;
    }
}
