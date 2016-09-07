package com.example.leohuang.password_manager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.activity.DisplayItemInfo;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Notification;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.interfaces.ItemTouchHelperAdapter;
import com.example.leohuang.password_manager.interfaces.ItemTouchHelperViewHolder;
import com.example.leohuang.password_manager.ui.SwipeMenuLayout;
import com.example.leohuang.password_manager.ui.SwipeMenuRecyclerView;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    Activity context;

    private MyApplication myApplication;
    SwipeMenuRecyclerView menuRecyclerView;
    List<Item> list = new ArrayList<>();
    private static final int VIEW_TYPE_ENABLE = 0;
    private static final int VIEW_TYPE_DISABLE = 1;

    public RecyclerViewAdapter( Activity context, SwipeMenuRecyclerView menuRecyclerView) {
        myApplication = (MyApplication) context.getApplication();
        this.context = context;
        LayoutInflater.from(context);
        this.menuRecyclerView = menuRecyclerView;
    }

    private View itemView1;
    public void setData(List<Item> items){
        this.list = items;
        notifyDataSetChanged();
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Notification notification = new Notification();
            if (msg.what == 0x1002){
                EventBus.getDefault().post(notification);
            }else if (msg.what == 0x1003){
                int position = msg.arg1;
                list.remove(position);
                notifyItemRemoved(position);
                EventBus.getDefault().post(notification);
            }

        }
    };

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public RecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView1 = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false);
        return new ItemViewHolder(itemView1);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ItemViewHolder holder, int position) {
        ItemViewHolder myViewHolder = (ItemViewHolder) holder;
       final Item item = list.get(holder.getAdapterPosition());
        final SwipeMenuLayout itemView = (SwipeMenuLayout) myViewHolder.itemView;
        if(myApplication.icons.get(item.icon) != null){
            myViewHolder.iv_account_icon.setBackgroundResource(myApplication.icons.get(item.icon));
        }else {
            myViewHolder.iv_account_icon.setBackgroundResource(R.mipmap.ic_launcher);
        }

        myViewHolder.tv_account_name.setText(myApplication.modelName.get(item.type_id));
        boolean swipeEnable = swipeEnableByViewType(getItemViewType(position));
        itemView.setSwipeEnable(swipeEnable);
        itemView.setOpenInterpolator(menuRecyclerView.getOpenInterpolator());
        itemView.setCloseInterpolator(menuRecyclerView.getCloseInterpolator());
        View viewiew = ((ItemViewHolder) holder).itemView;
                 holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseManager.deleteItem(item);
                        Message message = Message.obtain();
                        message.what = 0x1003;
                        message.arg1 = holder.getAdapterPosition();
                        mHandler.sendMessage(message);
                    }
                });

            }
        });
          holder.editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.fields != null) {
                    if (item.fields.size() > 0) {
                        Intent intent = new Intent(context, DataLoggingActivity.class);
                        intent.putExtra(Setting.FIELD_ITEM, item);
                        intent.putExtra(Setting.FIELD_IS, 2);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "没有详细信息了", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        if(item.is_favorited == 1){
            holder.collection.setImageDrawable(context.getResources().getDrawable(R.drawable.collection));
        }else {
            holder.collection.setImageDrawable(context.getResources().getDrawable(R.drawable.no_collection));
        }
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.is_favorited == 1){


                    holder.collection.setImageDrawable(context.getResources().getDrawable(R.drawable.no_collection));
                    MyApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.addOrDeleteFavroite(item.guid
                                    , 0);
                            Message message = Message.obtain();
                            message.what = 0x1002;
                            mHandler.sendMessage(message);

                        }
                    });
                }else {
                    holder.collection.setImageDrawable(context.getResources().getDrawable(R.drawable.collection));
                    MyApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.addOrDeleteFavroite(item.guid, 1);
                            Message message = Message.obtain();
                            message.what = 0x1002;
                            mHandler.sendMessage(message);
                        }
                    });
                }
            }
        });

        if (item.fields != null){
            if (item.fields.size()>0){
                viewiew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DisplayItemInfo.class);
                        intent.putExtra(Setting.FIELD_ITEM, item);
                        intent.putExtra(Setting.FIELD_IS, 0);
                        context.startActivity(intent);
                    }
                });
            }
        }



    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ENABLE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public final ImageView iv_account_icon, iv_account_drag;
        public final TextView tv_account_name;
        public ImageView editor,delete;
        public ImageView collection;
        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_account_icon = (ImageView) itemView.findViewById(R.id.item_image);
            iv_account_drag = (ImageView) itemView.findViewById(R.id.drag_item);
            tv_account_name = (TextView) itemView.findViewById(R.id.tvSwipeEnable);
            collection = (ImageView)itemView.findViewById(R.id.favorite);
            editor = (ImageView)itemView.findViewById(R.id.btOpen);
            delete = (ImageView)itemView.findViewById(R.id.btDelete);

        }

        @Override
        public void onItemSelect() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void itemClear() {
            itemView.setBackgroundColor(0);
        }
    }


    public boolean swipeEnableByViewType(int viewType) {
        if (viewType == VIEW_TYPE_ENABLE)
            return true;
        else if (viewType == VIEW_TYPE_DISABLE)
            return false;
        else
            return true; // default
    }


}
