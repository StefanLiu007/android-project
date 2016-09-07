package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;

/**
 * Created by leo.huang on 16/3/23.
 */
public class ChooseItemTwoAdapter extends BaseAdapter {
    private String[] item;
    private int[] res;
    private LayoutInflater mInflater;

    public ChooseItemTwoAdapter(Context context, String[] item, int[] res) {
        mInflater = LayoutInflater.from(context);
        this.item = item;
        this.res = res;
    }

    @Override
    public int getCount() {
        if(item!=null){
            return item.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(item!=null){
            return item[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(item!=null){
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(item!=null){
            ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder();
                convertView=mInflater.inflate(R.layout.dialog_list_item2,null,false);
            }
        }
        return null;
    }

    public class ViewHolder{
        ImageView ivIcon;
        TextView  tvContent;
    }
}
