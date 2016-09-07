package com.example.leohuang.password_manager.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.SecondActivity;
import com.example.leohuang.password_manager.adapter.PopListAdapter;
import com.example.leohuang.password_manager.adapter.RecyclerViewAdapter;
import com.example.leohuang.password_manager.adapter.SuquenceAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Notification;
import com.example.leohuang.password_manager.bean.PopDao;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.interfaces.StartDragListener;
import com.example.leohuang.password_manager.ui.ItemDecoration;
import com.example.leohuang.password_manager.ui.PopMenu;
import com.example.leohuang.password_manager.ui.SimpleItemTouchHelperCallback;
import com.example.leohuang.password_manager.ui.SlidingMenu;
import com.example.leohuang.password_manager.ui.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CollectionFragment extends BaseFragment implements View.OnClickListener, StartDragListener {
    View view, itemView, v;
    TextView title, more;
    TextView back;
    ImageView search;
    List<PopDao> list;
    PopListAdapter adapter;
    private PopMenu mPopmenu;
    RecyclerViewAdapter recyclerViewAdapter;
    public static int type = 1;
    ItemTouchHelper itemTouchHelper;
    ImageView iv_account_drag;
    private SwipeMenuRecyclerView mRecyclerView;
    SuquenceAdapter suquenceAdapter;
    List<Item> items;
    Toolbar toolbar;
    private List<Item> mItems;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                items = (List<Item>) msg.obj;
                recyclerViewAdapter.setData(items);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading();
    }
    private void loading(){
        MyApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Item> items = DatabaseManager.getFavorite(null);
                Message message = Message.obtain();
                message.obj = items;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.collection, null);
            itemView = LayoutInflater.from(getActivity()).inflate(R.layout.collection_list_item, null);
            initView();
            initData();
        }
        if (SlidingMenu.ignoredViews != null) {
            SlidingMenu.removeAllIgnoredView();
        }
        return view;
    }

    private void initData() {

    }

    private void initView() {
//        title = (TextView) view.findViewById(R.id.title_name);
//        title.setText("收藏夹");
//        back = (TextView) view.findViewById(R.id.back);
//        back.setOnClickListener(this);
//        more = (TextView) view.findViewById(R.id.openwindow);
//        more.setOnClickListener(this);
//        search = (ImageView) view.findViewById(R.id.search);
//        search.setOnClickListener(this);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.list_icon);
        toolbar.setTitle(getResources().getString(R.string.collection));
        mPopmenu = new PopMenu(getActivity());
        iv_account_drag = (ImageView) view.findViewById(R.id.iv_account_drag);
        v = view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView);
        ItemDecoration dividerLine = new ItemDecoration(ItemDecoration.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFDDDDDD);
        mRecyclerView.addItemDecoration(dividerLine);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOpenInterpolator(new BounceInterpolator());
        mRecyclerView.setCloseInterpolator(new BounceInterpolator());
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mRecyclerView);
        itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(recyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    protected void showWindow() {
        list = new ArrayList<PopDao>();
        PopDao dao = new PopDao();
        dao.text = "同步";
        list.add(dao);
        PopDao dao1 = new PopDao();
        dao1.text = "锁定";
        list.add(dao1);
        PopDao dao2 = new PopDao();
        dao2.text = "帮助";
        list.add(dao2);
        PopDao dao3 = new PopDao();
        dao3.text = "退出";
        list.add(dao3);
        PopDao dao4 = new PopDao();
        dao4.text = "排序";
        list.add(dao4);
        PopDao dao5 = new PopDao();
        dao5.text = "添加";
        list.add(dao5);
        adapter = new PopListAdapter(getActivity());
        adapter.setList(list);
        mPopmenu.setAdapter(adapter);
        mPopmenu.setOnItemClickListener(popmenuItemClickListener);
        adapter.notifyDataSetChanged();
        mPopmenu.showAsDropDown(more);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                SecondActivity.mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.openwindow:
                showWindow();
                break;
            case R.id.search:
                SearchFragment fragment = new SearchFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.id_framelayout2, fragment);
                ft.commit();
                break;
        }
    }

    AdapterView.OnItemClickListener popmenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list.get(position).text.equals("同步")) {
//                Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
//                startActivity(intent1);
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("锁定")) {
//                Intent intent1 = new Intent(getActivity(), HanldAddActivity.class);
//                startActivity(intent1);
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("帮助")) {
//             Intent intent1=new Intent(getActivity(),)
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("退出")) {
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("排序")) {
                ShowSuquence();
                mPopmenu.dismiss();
            }
            if (list.get(position).text.equals("添加")) {
                mPopmenu.dismiss();
            }
        }

        private void ShowSuquence() {
            List<PopDao> pd;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.suquence_list, null);
            ListView listView = (ListView) view.findViewById(R.id.suquen_list);
            pd = new ArrayList<>();
            PopDao dao1 = new PopDao("访问时间");
            pd.add(dao1);
            PopDao dao2 = new PopDao("访问次数");
            pd.add(dao2);
            PopDao dao3 = new PopDao("添加时间");
            pd.add(dao3);
            suquenceAdapter = new SuquenceAdapter(getActivity());
            suquenceAdapter.setList(pd);
            listView.setAdapter(suquenceAdapter);
            final AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view).create();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int len = 0;
                    switch (position) {
                        case 0:
                            mItems = DatabaseManager.getFavorite("access_date");
                            changeItems(dialog);

                            break;
                        case 1:
                            mItems = DatabaseManager.getFavorite("access_count");
                            changeItems(dialog);
                            break;
                        case 2:
                            mItems = DatabaseManager.getFavorite("create_date");
                            changeItems(dialog);
                            break;
                        default:
                            break;
                    }
                }
            });
            dialog.show();
        }

    };

    private void changeItems(Dialog dialog) {
        int len;
        items.clear();
        len = mItems.size();
        for (int i = 0; i < len; i++) {
            items.add(mItems.get(i));
        }
        recyclerViewAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    public void setVisiable() {
        View itemView1 = LayoutInflater.from(getActivity()).inflate(R.layout.collection_list_item, null);
        ImageView iv_account_drag = (ImageView) itemView1.findViewById(R.id.iv_account_drag);
        TextView tv_account_name = (TextView) itemView1.findViewById(R.id.tv_account_name);
        if (type == 0) {
            setDisEnable(iv_account_drag, tv_account_name);
        } else {
            setEnable(iv_account_drag, tv_account_name);
        }
    }

    public void setDisEnable(ImageView image, TextView text) {
        ObjectAnimator o4 = ObjectAnimator
                .ofFloat(image, "scaleX", 1.0f, 0.0f);
        ObjectAnimator o5 = ObjectAnimator
                .ofFloat(image, "scaleY", 1.0f, 0.0f);
        o5.setInterpolator(new DecelerateInterpolator());
        // o6.setInterpolator(new AccelerateInterpolator());
        o4.setInterpolator(new DecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(o4, o5);
        set.setDuration(500);
        set.start();
        text.setVisibility(View.GONE);
        SimpleItemTouchHelperCallback.LONG = false;
        recyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 设置不可排序
     *
     * @param image
     * @param text
     */
    public void setEnable(ImageView image, TextView text) {
        image.setVisibility(View.VISIBLE);
        ObjectAnimator o4 = ObjectAnimator
                .ofFloat(image, "scaleX", 0.0f, 1.0f);
        ObjectAnimator o5 = ObjectAnimator
                .ofFloat(image, "scaleY", 0.0f, 1.0f);
        o5.setInterpolator(new DecelerateInterpolator());
        o4.setInterpolator(new AccelerateInterpolator());
        //o4.setInterpolator(new DecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(o4, o5);
        set.setDuration(500);
        set.start();
        text.setVisibility(View.VISIBLE);
        SimpleItemTouchHelperCallback.LONG = true;
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onEventMainThread(Notification event) {
        loading();
        super.onEventMainThread(event);
    }
}
