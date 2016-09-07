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
 * 密码强度检测的适配器
 * Created by leo.huang on 16/4/20.
 */
public class CheckSecretStrengthAdapter extends RecyclerView.Adapter<CheckSecretStrengthAdapter.ViewHolder> {

    private final int HEADER = 1;//头部
    private final int NORNAL = 2;//展示item

    private Map<String, Integer> icons;//图标
    private List<Item> mItems;
    private Context context;

    public CheckSecretStrengthAdapter(Context context, List<Item> mItems, Map<String, Integer> icons) {
        this.context = context;
        this.mItems = mItems;
        this.icons = icons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItems != null) {
            ViewHolder holder = null;
            switch (viewType) {
                case HEADER:
                    View itemView = LayoutInflater.from(context).inflate(R.layout.check_secret_strength_header, parent, false);
                    holder = new ViewHolder(itemView, HEADER);
                    break;
                case NORNAL:
                    View nornalView = LayoutInflater.from(context).inflate(R.layout.check_secret_strength_nornal, parent, false);
                    holder = new ViewHolder(nornalView, NORNAL);
                    break;
                default:
                    break;
            }
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mItems != null) {
            if (position == 0 && holder.viewType == HEADER) {
                holder.tvHeader.setText("共有" + mItems.size() + "密码等级过低");
            } else {
                if (icons.get(mItems.get(position - 1).icon) == null) {
                    holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
                } else {
                    holder.ivIcon.setImageResource(icons.get(mItems.get(position - 1).icon));
                }

                holder.tvItemName.setText(mItems.get(position - 1).name);
                holder.llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new CheckItemClickEvent(mItems.get(position - 1)));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size() + 1;
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (mItems != null) {
            if (position == 0) {
                return HEADER;
            } else {
                return NORNAL;
            }
        }
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader, tvItemName;
        ImageView ivIcon;
        LinearLayout llItem;

        int viewType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            switch (viewType) {
                case HEADER:
                    tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
                    break;
                case NORNAL:
                    llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
                    ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                    tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
                    break;
                default:
                    break;
            }
        }
    }
}
