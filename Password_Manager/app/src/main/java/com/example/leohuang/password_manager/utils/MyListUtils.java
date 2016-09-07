package com.example.leohuang.password_manager.utils;

import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.ui.SortModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 16/3/31.
 */
public class MyListUtils {
    public static List<SortModel> filledData(List<Item> items) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            SortModel sortModel = new SortModel();
            sortModel.setName(item.name);
            //汉字转换成拼音
            sortModel.setIcon(item.icon);
            sortModel.setItem(item);
            String pinyin = CharacterParser.getInstance().getSelling(item.name);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    public static List<SortModel> sortData(List<SortModel> sortModels) {
        List<Integer> b = new ArrayList<>();
        Map<Integer, Integer> map1 = new HashMap<>();
        for (int i = 0; i < sortModels.size(); i++) {
            int a = sortModels.get(i).getSortLetters().charAt(0);
            for (int j = 0; j < sortModels.size(); j++) {
                String sortStr = sortModels.get(j).getSortLetters();
                int firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == a) {
                    if (!b.contains(a)) {
                        b.add(a);
                        map1.put(a, j);

                    }
                    continue;
                }
            }
        }

        for (int i = 0; i < b.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setSortLetters(b.get(i).toString());
            sortModels.add(map1.get(b.get(i)) + i, sortModel);
        }
        return sortModels;
    }

    public static List<Item> copyList(List<Item> from, List<Item> to) {
        to.clear();
        int len = from.size();
        for (int i = 0; i < len; i++) {
            to.add(from.get(i));
        }
        return to;
    }

    /**
     * 更改内容
     *
     * @param from
     * @param to
     */
    public static List<Item> changList(List<Item> from, List<Item> to) {
        if (to == null) {
            to = new ArrayList<>();
        }
        int fromLen = from.size();
        to.clear();
        for (int i = 0; i < fromLen; i++) {
            to.add(from.get(i));
        }

        return to;
    }
}
