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
public class ClassifyDialogAdapter extends BaseAdapter {
    List<ListDao> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    ViewHolder holder;
    private MyApplication myApplication;

    public ClassifyDialogAdapter(Context context) {
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
        if (convertView==null){
            convertView=inflater.inflate(R.layout.classify_dialog_item,parent,false);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.account_classify_icon);
            holder.textView= (TextView) convertView.findViewById(R.id.account_classify_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(MyApplication.icons.get(list.get(position).itemIcon) != null){
            holder.imageView.setBackgroundResource(MyApplication.icons.get(list.get(position).itemIcon));
        }else {
            holder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
        }

        holder.textView.setText(list.get(position).title);
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
