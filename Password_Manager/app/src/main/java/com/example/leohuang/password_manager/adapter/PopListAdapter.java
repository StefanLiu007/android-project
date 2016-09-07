package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.PopDao;

import java.util.List;

/**
 * Created by sun on 16/3/8.
 */
public class PopListAdapter extends BaseAdapter {
    List<PopDao> list;
    Context context;
    LayoutInflater inflater;
    ViewHolder holder;

    public PopListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<PopDao> getList() {
        return list;
    }

    public void setList(List<PopDao> list) {
        this.list = list;
        for (PopDao dao : list) {

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
            convertView = inflater.inflate(R.layout.pop_list_item, parent,false);
            holder = new ViewHolder();
            holder.pop_text= (TextView) convertView.findViewById(R.id.pop_list_text);
            holder.pop_text.setText(list.get(position).text);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    static class ViewHolder {
        TextView pop_text;
    }
}
