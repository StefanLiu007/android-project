package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 一个对象数据
 * Created by leo.huang on 16/4/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogItem {
    @JsonProperty("item_id")
    public String item_id;
    @JsonProperty("table_name")
    public String table_name;
    @JsonProperty("list")
    public List<LogItemInfo> logItemInfoLists;

    @JsonCreator
    public LogItem(@JsonProperty("item_id") String item_id, @JsonProperty("table_name") String table_name, @JsonProperty("list") List<LogItemInfo> logItemInfoLists) {
        this.item_id = item_id;
        this.table_name = table_name;
        this.logItemInfoLists = logItemInfoLists;
    }

    @JsonCreator
    public LogItem() {

    }

    @Override
    public String toString() {
        return "LogItem{" +
                "item_id='" + item_id + '\'' +
                ", table_name='" + table_name + '\'' +
                ", logItemInfoLists=" + logItemInfoLists +
                '}';
    }
}
