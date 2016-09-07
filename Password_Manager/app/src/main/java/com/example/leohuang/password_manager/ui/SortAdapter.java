package com.example.leohuang.password_manager.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            int section = getSectionForPosition(position);
            if (position==getPositionForSection(section)){
                view = LayoutInflater.from(mContext).inflate(R.layout.list_head, null);//如果是第一次出现则加载首字母布局
                viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
                viewHolder.tvLetter.setText(mContent.getSortLetters());
            }
//            if (position == getPositionForSection(section)) {
//                view = LayoutInflater.from(mContext).inflate(R.layout.list_head, null);
//                viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
//                viewHolder.tvLetter.setVisibility(View.VISIBLE);
//                viewHolder.tvLetter.setText(mContent.getSortLetters());
//            } else {
//                view = LayoutInflater.from(mContext).inflate(R.layout.classifyitem_fragment_item, null);
//                viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_classifyaccount_name);
//                viewHolder.iv_accountitem_icon = (ImageView) view.findViewById(R.id.iv_accountitem_icon);
//                viewHolder.iv_accountitem_slide = (ImageView) view.findViewById(R.id.iv_accountitem_slide);
//                viewHolder.iv_edit = (ImageView) view.findViewById(R.id.iv_edit);
//                viewHolder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
//                viewHolder.iv_collection = (ImageView) view.findViewById(R.id.iv_collection);
//                viewHolder.iv_label = (ImageView) view.findViewById(R.id.iv_label);
//                viewHolder.iv_accountitem_slide.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                    }
//                });
//            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {

            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());

        return view;

    }


    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView iv_accountitem_icon, iv_accountitem_slide;
        ImageView iv_edit, iv_delete, iv_collection, iv_label;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
    @Override
    public Object[] getSections() {
        return null;
    }
}