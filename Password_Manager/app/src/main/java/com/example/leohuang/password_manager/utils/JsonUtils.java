package com.example.leohuang.password_manager.utils;

import com.example.leohuang.password_manager.bean.LogItem;
import com.example.leohuang.password_manager.bean.LogItemInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * json帮助类
 * Created by leo.huang on 16/4/1.
 */
public class JsonUtils {
    public static String generatePhoneInfo(String model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Setting.MODEL, model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    /**
     * 生成JSON数据
     *
     * @param logItems
     * @return
     */
    public static String generateLogItemString(List<LogItem> logItems) {

        JSONArray root = new JSONArray();
        try {
            int len = logItems.size();
            for (int i = 0; i < len; i++) {
                LogItem logItem = logItems.get(i);
                JSONObject item = new JSONObject();

                item.put("item_id", logItem.item_id);
                item.put("table_name", logItem.table_name);
                JSONArray itemArray = new JSONArray();
                int itemLen = logItem.logItemInfoLists.size();
                for (int j = 0; j < itemLen; j++) {
                    LogItemInfo logItemInfo = logItem.logItemInfoLists.get(j);
                    JSONObject itemObj = new JSONObject();
                    itemObj.put("action", logItemInfo.action);
                    itemObj.put("createTime", logItemInfo.createTime);
                    itemArray.put(itemObj);
                }
                item.put("list", itemArray);
                root.put(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }


    public static List<LogItem> StringToLogItemList(String string) {
        List<LogItem> logItems = new ArrayList<>();

        try {
            JSONArray root = new JSONArray(string);
            int len = root.length();
            for (int i = 0; i < len; i++) {
                JSONObject item = root.getJSONObject(i);
                LogItem logItem = new LogItem();
                logItem.item_id = item.getString("item_id");
                logItem.table_name = item.getString("table_name");
                List<LogItemInfo> logItemInfos = new ArrayList<>();
                logItem.logItemInfoLists = logItemInfos;
                JSONArray itemArray = item.getJSONArray("list");
                int itemLen = itemArray.length();
                for (int j = 0; j < itemLen; j++) {
                    JSONObject itemObj = itemArray.getJSONObject(j);

                    LogItemInfo logItemInfo = new LogItemInfo();
                    logItemInfo.action = itemObj.getInt("action");
                    logItemInfo.createTime = itemObj.getString("createTime");

                    logItemInfos.add(logItemInfo);
                }
                logItems.add(logItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return logItems;
    }
}
