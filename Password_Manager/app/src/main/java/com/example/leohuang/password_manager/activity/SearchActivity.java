package com.example.leohuang.password_manager.activity;

import android.app.usage.UsageEvents;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.events.FindEvent;
import com.example.leohuang.password_manager.fragment.SearchAllFragment;
import com.example.leohuang.password_manager.fragment.SearchTagFragment;
import com.example.leohuang.password_manager.fragment.SearchTitleFragment;
import com.example.leohuang.password_manager.fragment.SecretStrengthFragment;
import com.example.leohuang.password_manager.ui.NoScrollViewPager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity {
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;

    private String[] titles = new String[]{"全部字段", "名称", "标签"};//标签名称
    private Fragment mSearchAllFragment, mSearchTagFragment, mSearchTitleFragment;
    private List<Fragment> fragments;
    private MyViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        assignViews();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setSupportActionBar(mToolbar);
        mSearchView = (SearchView) findViewById(R.id.search_view);
//        mSearchView.setIconifiedByDefault(false);
        mViewPager = (NoScrollViewPager) findViewById(R.id.nsvp);
        mViewPager.setNoScroll(true);//不允许滚动
        fragments = new ArrayList<>();
        mSearchAllFragment = new SearchAllFragment();
        mSearchTagFragment = new SearchTagFragment();
        mSearchTitleFragment = new SearchTitleFragment();
        fragments.add(mSearchAllFragment);
        fragments.add(mSearchTitleFragment);
        fragments.add(mSearchTagFragment);
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initEvents() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {//返回按键
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EventBus.getDefault().post(new FindEvent(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EventBus.getDefault().post(new FindEvent(newText));
                return false;
            }
        });

    }

    /**
     * 适配器
     */
    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            if (fragments != null) {
                return fragments.size();
            }
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments != null) {
                return fragments.get(position);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
