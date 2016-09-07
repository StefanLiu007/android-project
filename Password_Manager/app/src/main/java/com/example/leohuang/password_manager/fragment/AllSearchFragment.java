package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.ContactAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.QueryEvent;
import com.example.leohuang.password_manager.expendRecyclerview.StickyRecyclerHeadersDecoration;
import com.example.leohuang.password_manager.interfaces.Back;
import com.example.leohuang.password_manager.ui.DividerDecoration;
import com.example.leohuang.password_manager.ui.SideBar;
import com.example.leohuang.password_manager.ui.SwipeMenuRecyclerView;
import com.example.leohuang.password_manager.utils.CharacterParser;
import com.example.leohuang.password_manager.utils.MyListUtils;
import com.example.leohuang.password_manager.utils.PinyinComparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

/**
 * 搜索界面
 * Created by leo.huang on 16/4/18.
 */
public class AllSearchFragment extends Fragment {

    private final String TAG = "AllSearchFragment";

    private SwipeMenuRecyclerView mRecyclerView;
    private SideBar sideBar;
    private TextView mUserDialog;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    Back backicon;
    Toolbar toolbar;
    private List<Item> items;
    List<Item> items1;
    private ContactAdapter mAdapter;
    private MyApplication myApplication;
    private SearchView mSearchView;

    public AllSearchFragment() {

    }

    public AllSearchFragment(Back back) {
        this.backicon = back;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
        myApplication = (MyApplication) getActivity().getApplication();
        items = myApplication.items;//获取所有的项目
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classifyitem_fragment, null, false);
        assignViews(view);
        getItemsByName(null);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void assignViews(View view) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mSearchView = (SearchView) getActivity().findViewById(R.id.search);
        mSearchView.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backicon.back();
            }
        });
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        mUserDialog = (TextView) view.findViewById(R.id.dialog);
        mRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView_item);
        sideBar.setTextView(mUserDialog);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "query==>" + query);
                getItemsByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "newText==>" + newText);
                getItemsByName(newText);
                return false;
            }
        });
        setUI();
    }

    private void setUI() {

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mRecyclerView.scrollToPosition(position);
                }

            }
        });
        setAdapter(items);
    }

    private void setAdapter(List<Item> items) {
        seperateLists(items);

        if (mAdapter == null) {
            mAdapter = new ContactAdapter(getActivity(), items1, myApplication.icons,null);
            int orientation = LinearLayoutManager.VERTICAL;
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), orientation, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
            mRecyclerView.addItemDecoration(headersDecor);
            mRecyclerView.addItemDecoration(new DividerDecoration(getActivity()));

            //   setTouchHelper();
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    headersDecor.invalidateHeaders();
                }
            });
        } else {
            mAdapter = new ContactAdapter(getActivity(), items1, myApplication.icons,null);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void seperateLists(List<Item> items) {
        items1 = MyListUtils.changList(items, items1);
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                String pinyin = characterParser.getSelling(item.name);
                String sortString = pinyin.substring(0, 1).toUpperCase();

                if (sortString.matches("[A-Z]")) {
                    items1.get(i).sortLetters = sortString.toUpperCase();
                } else {
                    items1.get(i).sortLetters = "#";
                }
            }
            Collections.sort(items1, pinyinComparator);
        }

    }

    /**
     * 获取根据名称得到对象
     *
     * @param name
     */
    private void getItemsByName(final String name) {
        if (TextUtils.isEmpty(name)) {
            myApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // TODO: 16/4/18 查询所有
                    items = myApplication.items;//显示全部
                    Log.i(TAG, items.toString());
                    EventBus.getDefault().post(new QueryEvent(items));
                }
            });
        } else {
            myApplication.mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // TODO: 16/4/18 查询特定
                    String trim = name.trim();
                    List<Item> items = DatabaseManager.queryItemsByName(name);
                    Log.i(TAG, "itmes==》" + items);
                    EventBus.getDefault().post(new QueryEvent(items));
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(QueryEvent event) {
        List<Item> items = event.mItems;
        setAdapter(items);
    }

}
