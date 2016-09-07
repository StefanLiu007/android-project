package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;

/**
 * 没有图片的选择适配器
 * Created by leo.huang on 16/3/23.
 */
public class ChooseItemOneAdapter extends BaseAdapter {
    private String[] item;
    private LayoutInflater mInflater;

    public ChooseItemOneAdapter(Context context, String[] item) {
        mInflater = LayoutInflater.from(context);
        this.item = item;
    }

    @Override
    public int getCount() {
        if (item != null) {
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
                convertView=mInflater.inflate(R.layout.dialog_list_item1,null,false);
                holder.tvContent= (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(item[position]);
            return  convertView;
        }
        return null;
    }
    public class ViewHolder{
        TextView tvContent;
    }
}
