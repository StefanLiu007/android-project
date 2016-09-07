package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.ListDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sun on 16/3/16.
 */
public class ConfigurationAdapter extends BaseAdapter {
    List<ListDao> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    ViewHolder holder;
    //private MyApplication myApplication;
    public ConfigurationAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public List<ListDao> getList() {
        return list;
    }
    public void setList(List<ListDao> list) {
        this.list = list;
        for (ListDao dao : list) {

        }
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
            convertView = inflater.inflate(R.layout.configuration_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.configuration_icon);
            holder.textView = (TextView) convertView.findViewById(R.id.configuration_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).title);
        holder.imageView.setBackgroundResource(list.get(position).icon);
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
