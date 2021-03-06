package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.example.leohuang.password_manager.R;
import java.util.List;
/**
 * Created by sun on 16/4/6.
 */
public class AddLabelAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    public AddLabelAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);
            holder = new ViewHolder();
            holder.tagBtn = (Button) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Label label = new Label();
//        holder.tagBtn.setText(label.name);
        return convertView;
    }

    static class ViewHolder {
        Button tagBtn;
    }
}
