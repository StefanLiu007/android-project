package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.MainActivity;
import com.example.leohuang.password_manager.adapter.ContactAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.bean.Notification;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.expendRecyclerview.StickyRecyclerHeadersDecoration;
import com.example.leohuang.password_manager.interfaces.Back;
import com.example.leohuang.password_manager.ui.ClearEditText;
import com.example.leohuang.password_manager.ui.DividerDecoration;
import com.example.leohuang.password_manager.ui.SideBar;
import com.example.leohuang.password_manager.ui.SwipeMenuRecyclerView;
import com.example.leohuang.password_manager.utils.CharacterParser;
import com.example.leohuang.password_manager.utils.MyListUtils;
import com.example.leohuang.password_manager.utils.PinyinComparator;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jack on 16/4/13.
 */
public class ClassifyItemFragment extends BaseFragment {
    View view;
    private SwipeMenuRecyclerView mRecyclerView;
    private SideBar sideBar;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    //    private ClearEditText mClearEditText;
    private int type;//传过来的对象类型

//    private String label;//标签名

    private List<Item> items1 = new ArrayList<>();//需要展示的Items
    private List<Item> typeItems = new ArrayList<>();//分类的ITEM

    //    ItemTouchHelper itemTouchHelper;
    private MyApplication myApplication;
    //    ImageView imageback;
    Back backicon;
    private TextView mUserDialog;
    private ContactAdapter mAdapter;
    Toolbar toolbar;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x1002){
                if (mAdapter != null) {
                    getTypeItem(typeItems, myApplication.items, type);
                    seperateLists(typeItems);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    public ClassifyItemFragment() {

    }

    public ClassifyItemFragment(Back backicon) {
        this.backicon = backicon;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            getTypeItem(typeItems, myApplication.items, type);
            seperateLists(typeItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.classifyitem_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Bundle b = getArguments();
        if (b != null) {
            type = b.getInt(Setting.TYPE);//必须传bundle过来
        }
        getTypeItem(typeItems, myApplication.items, type);//得打想要的类型数据
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        if (type == 0){
            toolbar.setTitle(getResources().getString(R.string.classify_account));
        }else {
            for (int i =0 ;i<myApplication.models.size();i++){
                Model model = myApplication.models.get(i);
                if (model.id == type){
                    toolbar.setTitle(model.name);
                    continue;
                }
            }
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backicon.back();
                MainActivity.position = -1;
            }
        });
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        mUserDialog = (TextView) view.findViewById(R.id.dialog);
        mRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView_item);
        sideBar.setTextView(mUserDialog);
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
        seperateLists(typeItems);

        if (mAdapter == null) {
            mAdapter = new ContactAdapter(getActivity(), items1, myApplication.icons,mHandler);
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
            mAdapter.notifyDataSetChanged();
        }
    }

    private void seperateLists(List<Item> items) {
        items1 = MyListUtils.copyList(items, items1);
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
     * 获取类别的item
     *
     * @param typesItems
     * @param allItems
     * @param type
     */
    public void getTypeItem(List<Item> typesItems, List<Item> allItems, int type) {
        typesItems.clear();
        if (type == 0) {
            typesItems = MyListUtils.copyList(allItems, typesItems);
        } else {
            int len = allItems.size();
            for (int i = 0; i < len; i++) {
                if (allItems.get(i).type_id == type) {
                    typesItems.add(allItems.get(i));//添加进入
                }
            }
        }
    }

    @Override
    public void onEventMainThread(Notification event) {
        MyApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
             myApplication.items = DatabaseManager.showItems(null);
                Message message = Message.obtain();
                message.what = 0x1002;
                mHandler.sendMessage(message);
            }
        });
        super.onEventMainThread(event);
    }
}
