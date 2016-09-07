package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.activity.DataLoggingActivity;
import com.example.leohuang.password_manager.activity.DisplayItemInfo;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.interfaces.ItemTouchHelperAdapter;
import com.example.leohuang.password_manager.ui.SortModel;
import com.example.leohuang.password_manager.utils.L;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.List;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      2015/2/6  14:34.
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/2/6        ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter,CompoundButton.OnCheckedChangeListener {
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
    public enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }
    public void updateListView(List<SortModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<SortModel> list = null;
    private MyApplication myApplication;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            L.toast(mContext,(String)msg.obj);
        }
    };
    public MultipleItemAdapter(Context context,List<SortModel> sortModels,MyApplication myApplication) {
        mContext = context;
        this.list = sortModels;
        mLayoutInflater = LayoutInflater.from(context);
        this.myApplication=myApplication;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal()) {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_simple, parent, false));
        } else {
            return new TextViewHolder(mLayoutInflater.inflate(R.layout.multipleitem_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            int a = Integer.parseInt(list.get(position).getSortLetters());
            ((TextViewHolder) holder).mTextView.setText(String.valueOf((char)(a)));
        } else if (holder instanceof ImageViewHolder) {
            View view = holder.itemView;
            String name = list.get(holder.getAdapterPosition()).getName();
            final Item item = list.get(holder.getAdapterPosition()).getItem();
                    ((ImageViewHolder) holder).mTextView.setText(name);
            if(MyApplication.icons.get(list.get(position).icon) != null){
                ((ImageViewHolder) holder).mImageView.setBackgroundResource(MyApplication.icons.get(list.get(position).icon));
            }else {
                ((ImageViewHolder) holder).mImageView.setBackgroundResource(R.mipmap.ic_launcher);
            }
            ((ImageViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {

                            Message message = Message.obtain();
                            message.obj = DatabaseManager.deleteItem(item);
                            handler.sendMessage(message);
                            MyApplication.items = DatabaseManager.showItems(null);
                        }
                    });
                    list.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
            ((ImageViewHolder) holder).editor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.fields != null) {
                        if (item.fields.size() > 0) {
                            Intent intent = new Intent(mContext, DataLoggingActivity.class);
                            intent.putExtra(Setting.FIELD_ITEM, item);
                            intent.putExtra(Setting.FIELD_IS, 0);
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "没有详细信息了", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
            if(item.is_favorited == 1){
                ((ImageViewHolder) holder).toggleButton.setChecked(true);
            }else {
                ((ImageViewHolder) holder).toggleButton.setChecked(false);
            }
            ((ImageViewHolder) holder).toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        myApplication.mExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                DatabaseManager.addOrDeleteFavroite(item.guid, 1);
                                MyApplication.items = DatabaseManager.showItems(null);
                            }
                        });

                    } else {
                        myApplication.mExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                DatabaseManager.addOrDeleteFavroite(item.guid
                                        , 0);
                                MyApplication.items = DatabaseManager.showItems(null);
                            }
                        });
                    }
                }
            });

            if (item.fields != null){
                if (item.fields.size()>0){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, DisplayItemInfo.class);
                            intent.putExtra(Setting.FIELD_ITEM, item);
                            intent.putExtra(Setting.FIELD_IS, 0);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getName() != null ? ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal() : ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        TextViewHolder(View view) {
            super(view);
            mTextView = (TextView)view.findViewById(R.id.text_view);
        }

    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView editor,delete;
        ImageView mImageView;
        ToggleButton toggleButton;
        TextView mTextView;
        ImageViewHolder(View view) {
            super(view);
            mTextView = (TextView)view.findViewById(R.id.tvSwipeEnable);
            mImageView = (ImageView)view.findViewById(R.id.item_image);
            toggleButton = (ToggleButton)view.findViewById(R.id.favorite);
            editor = (ImageView)view.findViewById(R.id.btOpen);
            delete = (ImageView)view.findViewById(R.id.btDelete);
        }
    }
}
