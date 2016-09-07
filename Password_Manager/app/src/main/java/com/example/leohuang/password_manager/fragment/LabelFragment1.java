package com.example.leohuang.password_manager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.MainAdapter;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.LaberAccount;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.ui.DragSelectRecyclerView;
import com.example.leohuang.password_manager.ui.DragSelectRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Jack on 16/4/15.
 */
public class LabelFragment1 extends Fragment implements MainAdapter.ClickListener, DragSelectRecyclerViewAdapter.SelectionListener{
    private MyApplication myApplication;
    View view;
    private DragSelectRecyclerView mList;
    private MainAdapter mAdapter;
    List<LaberAccount> laberAccounts;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            laberAccounts = (List<LaberAccount>) msg.obj;
            mAdapter = new MainAdapter(LabelFragment1.this,laberAccounts);
            // Receives selection updates, recommended to set before restoreInstanceState() so initial reselection is received
            mAdapter.setSelectionListener(LabelFragment1.this);
            mList = (DragSelectRecyclerView) view.findViewById(R.id.list);
            mList.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_width)));
            mList.setAdapter(mAdapter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
        myApplication.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
               List<LaberAccount> laberAccounts = DatabaseManager.getLaberNumber();
                Message message = Message.obtain();
                message.obj = laberAccounts;
                mHandler.sendMessage(message);

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_label,null);
        }
        mAdapter = new MainAdapter(LabelFragment1.this,laberAccounts);
        // Receives selection updates, recommended to set before restoreInstanceState() so initial reselection is received
        mAdapter.setSelectionListener(LabelFragment1.this);
        // Restore selected indices after Activity recreation
        mAdapter.restoreInstanceState(savedInstanceState);

        // Setup the RecyclerView
        mList = (DragSelectRecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_width)));
        mList.setAdapter(mAdapter);

        return view;
    }
    @Override
    public void onClick(int index) {
        mAdapter.toggleSelected(index);
    }

    @Override
    public void onLongClick(int index) {
        mList.setDragSelectActive(true, index);
    }

    @Override
    public void onDragSelectionChanged(int count) {
//        if (count > 0) {
//            if (mCab == null) {
//                mCab = new MaterialCab((AppCompatActivity)getActivity(), R.id.cab_stub)
//                        .setMenu(R.menu.cab)
//                        .setCloseDrawableRes(R.drawable.ic_close)
//                        .start(LabelFragment1.this);
//            }
//            mCab.setTitleRes(R.string.cab_title_x, count);
//        } else if (mCab != null && mCab.isActive()) {
//            mCab.reset().finish();
//            mCab = null;
//        }
    }

    // Material CAB Callbacks


//    @Override
//    public boolean onCabItemClicked(MenuItem item) {
//        if (item.getItemId() == R.id.done) {
//            StringBuilder sb = new StringBuilder();
//            int traverse = 0;
//            for (Integer index : mAdapter.getSelectedIndices()) {
//                if (traverse > 0) sb.append(", ");
//                sb.append(mAdapter.getItem(index));
//                traverse++;
//            }
////            Toast.makeText(this,
////                    String.format("Selected letters (%d): %s", mAdapter.getSelectedCount(), sb.toString()),
////                    Toast.LENGTH_LONG).show();
//            mAdapter.clearSelected();
//        }
//        return true;
//    }
}
