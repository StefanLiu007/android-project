package com.example.leohuang.password_manager.tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.SimpleLabel;

import java.util.List;

/**
 * @author fyales
 * @since 8/26/15.
 */
//public class TagBaseAdapter extends TagAdapter<String> {
//    private LayoutInflater mInflater;
//
//    public TagBaseAdapter(Context context, List<String> datas) {
//        super(datas);
//        mInflater = LayoutInflater.from(context);
//    }
//    @Override
//    public View getView(FlowLayout parent, int position, String label) {
//        TextView tv = (TextView) mInflater.inflate(R.layout.flow_layout_item, parent, false);
//        tv.setText(label);
//        return tv;
//    }
//}
public class TagBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<SimpleLabel> mList;

    public TagBaseAdapter(Context mContext, List<SimpleLabel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position).name;
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
        final String text=getItem(position);
        holder.tagBtn.setText(text);
        return convertView;
    }
    static class ViewHolder {
        Button tagBtn;
    }

}
