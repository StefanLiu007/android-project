package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;

import java.util.List;

/**
 * Created by leo.huang on 16/4/1.
 */
public class SyncItemAdapter extends BaseAdapter {

    private List<String> strName;
    private List<Integer> iconIds;
    private LayoutInflater mInflater;

    public SyncItemAdapter(Context context, List<String> strName, List<Integer> iconIds) {
        mInflater = LayoutInflater.from(context);
        this.strName = strName;
        this.iconIds = iconIds;
    }


    @Override
    public int getCount() {
        if (strName != null) {
            return strName.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (strName != null) {
            return strName.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (strName != null) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (strName != null && iconIds != null) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.sync_list_item, null, false);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvDes = (TextView) convertView.findViewById(R.id.tv_description);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvDes.setText(strName.get(position));
            holder.ivIcon.setImageResource(iconIds.get(position));
            return convertView;
        }
        return null;
    }

    private class ViewHolder {
        ImageView ivIcon;
        TextView tvDes;
    }
}
