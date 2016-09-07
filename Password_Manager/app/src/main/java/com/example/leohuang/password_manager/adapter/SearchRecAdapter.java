package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.events.CheckItemClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by leo.huang on 16/4/25.
 */
public class SearchRecAdapter extends RecyclerView.Adapter<SearchRecAdapter.MyViewHolder> {
    private Map<String, Integer> icons;//图标
    private List<Item> mItems;
    private Context context;

    public SearchRecAdapter(Context context, List<Item> mItems, Map<String, Integer> icons) {
        this.icons = icons;
        this.mItems = mItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItems != null) {
            MyViewHolder holder = null;
            View view = LayoutInflater.from(context).inflate(R.layout.check_secret_strength_nornal, parent, false);
            holder = new MyViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (mItems != null) {
            if (icons.get(mItems.get(position).icon) == null) {
                holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
            } else {
                holder.ivIcon.setImageResource(icons.get(mItems.get(position).icon));
            }

            holder.tvItemName.setText(mItems.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CheckItemClickEvent(mItems.get(position)));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        ImageView ivIcon;
        LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
