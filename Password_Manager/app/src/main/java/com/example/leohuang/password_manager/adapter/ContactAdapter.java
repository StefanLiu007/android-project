/**
 * created by jiang, 12/3/15
 * Copyright (c) 2015, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.activity.DisplayItemInfo;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Notification;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.expendRecyclerview.StickyRecyclerHeadersAdapter;
import com.example.leohuang.password_manager.utils.L;
import com.example.leohuang.password_manager.utils.Setting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by jiang on 12/3/15.
 * 根据当前权限进行判断相关的滑动逻辑
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    /**
     * 当前处于打开状态的item
     */
    //   private List<SwipeItemLayout> mOpenedSil = new ArrayList<>();

    private List<Item> mLists;

    private Context mContext;
    private boolean isFirstCheck = false;
    private Map<String, Integer> icons;
    Handler mHandler;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Notification notification = new Notification();
            if (msg.what == 0x10001) {
                mLists.remove(msg.arg1);
                notifyItemRemoved(msg.arg1);
            }if (msg.what == 0x1002){
                notifyItemChanged(msg.arg1);
            }
            EventBus.getDefault().post(notification);
        }
    };

    public ContactAdapter(Context ct, List<Item> mLists, Map<String, Integer> icons,Handler mHandler) {
        this.mLists = mLists;
        mContext = ct;
        this.icons = icons;
        this.mHandler = mHandler;
    }

    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactAdapter.ContactViewHolder holder, final int position) {
        final Item item = mLists.get(holder.getAdapterPosition());
        if(icons.get(item.icon) == null){
            holder.mImageView.setBackgroundResource(R.mipmap.ic_launcher);
        }else {
            holder.mImageView.setImageResource(icons.get(item.icon));
        }

        final View view = holder.itemView;
        holder.mTextView.setText(item.name);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.obj = DatabaseManager.deleteItem(item);
                        MyApplication.items = DatabaseManager.showItems(null);
                        message.what =0x10001;
                        message.arg1 = holder.getAdapterPosition();
                        L.test(""+position);
                        handler.sendMessage(message);
                    }
                });

            }
        });
         holder.editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(mContext, DataLoggingActivity.class);
                        intent.putExtra(Setting.FIELD_ITEM, item);
                        intent.putExtra(Setting.ISADD, Setting.Editor);
                        mContext.startActivity(intent);
                    }
        });
        if(item.is_favorited == 1){
            holder.toggleButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collection));
        }else {
            holder.toggleButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_collection));
        }
        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.is_favorited == 1){
                    holder.toggleButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_collection));
                    MyApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.addOrDeleteFavroite(item.guid
                                    , 0);
                            Message message = Message.obtain();
                            message.what = 0x1002;
                            message.arg1 = holder.getAdapterPosition();
                            handler.sendMessage(message);
                        }
                    });
                }else {
                    holder.toggleButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collection));
                    MyApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.addOrDeleteFavroite(item.guid, 1);
                            Message message = Message.obtain();
                            message.what = 0x1002;
                            message.arg1 = holder.getAdapterPosition();
                            handler.sendMessage(message);
                        }
                    });
                }

            }
        });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DisplayItemInfo.class);
                        intent.putExtra(Setting.FIELD_ITEM, item);
                        mContext.startActivity(intent);
                    }
                });



    }

    @Override
    public long getHeaderId(int position) {

        return mLists.get(position).sortLetters.charAt(0);

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String showValue = String.valueOf(mLists.get(position).sortLetters.charAt(0));
        if ("$".equals(showValue)) {
            textView.setText("群主");
        } else if ("%".equals(showValue)) {
            textView.setText("系统管理员");

        } else {
            textView.setText(showValue);
        }

    }


    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mLists.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }

//    public void closeOpenedSwipeItemLayoutWithAnim() {
//        for (SwipeItemLayout sil : mOpenedSil) {
//            sil.closeWithAnim();
//        }
//        mOpenedSil.clear();
//    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView editor, delete;
        ImageView mImageView;
        ImageView toggleButton;
        TextView mTextView;

        ContactViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tvSwipeEnable);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            toggleButton = (ImageView) view.findViewById(R.id.favorite);
            editor = (ImageView) view.findViewById(R.id.btOpen);
            delete = (ImageView) view.findViewById(R.id.btDelete);


        }
    }
}
