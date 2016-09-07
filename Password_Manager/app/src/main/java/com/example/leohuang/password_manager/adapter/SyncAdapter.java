package com.example.leohuang.password_manager.adapter;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.SyncModel;
import com.example.leohuang.password_manager.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by leo.huang on 16/4/5.
 */
public class SyncAdapter extends RecyclerView.Adapter<SyncAdapter.ViewHolder> {

    private OnRecyclerItemClickListener mListener;
    private static final int IS_HEADER = 1;
    private static final int NORMAL = 2;

    private List<SyncModel> mModels;
    private Context context;

    public SyncAdapter(Context context, List<SyncModel> mModels) {
        this.context = context;
        this.mModels = mModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        switch (viewType) {
            case IS_HEADER:
                View headerView = LayoutInflater.from(context).inflate(R.layout.sync_conn_des_layout, parent, false);
                holder = new ViewHolder(headerView, IS_HEADER);
                break;
            case NORMAL:
                View nornal = LayoutInflater.from(context).inflate(R.layout.sync_conn_item_layout, parent, false);
                holder = new ViewHolder(nornal, NORMAL);
                break;
            default:
                break;
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0 && holder.viewType == IS_HEADER) {

        } else if (position != 0 && holder.viewType == NORMAL) {
            holder.tvModel.setText(mModels.get(position - 1).name);
            final View view = holder.itemView;
            if (mListener != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(position - 1);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mModels != null) {
            return mModels.size() + 1;
        } else
            return 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if (position != 0) {
            return NORMAL;
        }
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        LinearLayout llItem;
        TextView tvModel;
        int viewType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == IS_HEADER) {
                mImage = (ImageView) itemView.findViewById(R.id.iv_animation);
                mImage.setBackgroundResource(R.drawable.sync_anim_list);
                AnimationDrawable anim = (AnimationDrawable) mImage.getBackground();
                anim.start();
            } else if (viewType == NORMAL) {
                tvModel = (TextView) itemView.findViewById(R.id.tv_model);
                llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            }
        }
    }


    public void setOnRecyclerClickListener(OnRecyclerItemClickListener mListener) {
        this.mListener = mListener;
    }
}
