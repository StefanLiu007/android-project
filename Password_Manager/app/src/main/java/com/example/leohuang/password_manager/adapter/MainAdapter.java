package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.LaberAccount;
import com.example.leohuang.password_manager.ui.DragSelectRecyclerViewAdapter;
import com.example.leohuang.password_manager.ui.RectangleView;

import java.util.List;

/**
 * @author Aidan Follestad (afollestad)
 */
public class MainAdapter extends DragSelectRecyclerViewAdapter<MainAdapter.MainViewHolder> {

    private List<LaberAccount> labels;
    public interface ClickListener {
        void onClick(int index);

        void onLongClick(int index);
    }

    private final ClickListener mCallback;

    public  MainAdapter(ClickListener callback,List<LaberAccount> labels) {
        super();
        mCallback = callback;
        this.labels = labels;
    }

    public LaberAccount getItem(int index) {
        if (labels != null){
            if (labels.size()>0){
                return labels.get(index);
            }
        }
        return null;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_item, parent, false);
        return new MainViewHolder(v, mCallback);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LaberAccount laberAccount = labels.get(holder.getAdapterPosition());
        final Drawable d;
        final Context c = holder.itemView.getContext();

        if (isIndexSelected(position)) {
            d = new ColorDrawable(ContextCompat.getColor(c, R.color.grid_foreground_selected));
            holder.labelName.setText(laberAccount.name);
            holder.number.setText(laberAccount.count+"个项目");
        } else {
            d = null;
            holder.labelName.setText(laberAccount.name);
            holder.number.setText(laberAccount.count+"各项目");
        }

        //noinspection RedundantCast
        ((FrameLayout) holder.colorSquare).setForeground(d);
    }

    @Override
    public int getItemCount() {
        if (labels != null){
            if (labels.size()>0){
                return labels.size();
            }
        }
        return 0;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        public final TextView labelName,number;
        private final ClickListener mCallback;
        RectangleView colorSquare;

        public MainViewHolder(View itemView, ClickListener callback) {
            super(itemView);
            mCallback = callback;
            colorSquare = (RectangleView) itemView.findViewById(R.id.colorSquare);
            this.labelName = (TextView) itemView.findViewById(R.id.laber_name);
            number = (TextView) itemView.findViewById(R.id.number);
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null)
                mCallback.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mCallback != null)
                mCallback.onLongClick(getAdapterPosition());
            return true;
        }
    }
}