package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.ListDao;

import java.util.List;

/**
 * Created by sun on 16/3/4.
 */
public class LeftListAdapter extends BaseAdapter {
    List<ListDao> list;
    Context context;
    LayoutInflater inflater;
    ViewHolder holder;

    public LeftListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<ListDao> list) {
        this.list = list;
        for (ListDao listdao : list) {

        }
    }

    public List<ListDao> getList() {
        return list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.left_list_item, parent,false);
            holder = new ViewHolder();
            holder.left_title= (TextView) convertView.findViewById(R.id.left_title);
            holder.left_icon= (ImageView) convertView.findViewById(R.id.left_icon);
            holder.left_icon.setBackground(context.getResources().getDrawable(list.get(position).icon));
            holder.left_title.setText(list.get(position).title);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView left_icon;
        TextView left_title;
    }

}
