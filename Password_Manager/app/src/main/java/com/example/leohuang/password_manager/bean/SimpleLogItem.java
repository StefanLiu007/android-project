package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by leo.huang on 16/4/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleLogItem {
    @JsonProperty("item_id")
    public String item_id;
    @JsonProperty("table_name")
    public String table_name;

    @JsonCreator
    public SimpleLogItem() {

    }

    @JsonCreator
    public SimpleLogItem(@JsonProperty("item_id") String item_id, @JsonProperty("table_name") String table_name) {
        this.item_id = item_id;
        this.table_name = table_name;

    }

    @Override
    public String toString() {
        return "SimpleLogItem{" +
                "item_id='" + item_id + '\'' +
                ", table_name='" + table_name + '\'' +
                '}';
    }


}
