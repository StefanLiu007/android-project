package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by leo.huang on 16/4/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleItem {
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("icon")
    public String icon;
    @JsonProperty("create_time")
    public String create_time;
    @JsonProperty("type_id")
    public int type_id;
    @JsonProperty("modify_time")
    public String modify_time;
    @JsonProperty("access_date")
    public String access_date;
    @JsonProperty("access_count")
    public int access_count;

    @JsonCreator
    public SimpleItem() {

    }

    @JsonCreator
    public SimpleItem(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("icon") String icon,
                      @JsonProperty("create_time") String create_time, @JsonProperty("type_id") int type_id, @JsonProperty("modify_time") String modify_time,
                      @JsonProperty("access_date") String access_date, @JsonProperty("access_count") int access_count
    ) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.create_time = create_time;
        this.type_id = type_id;
        this.modify_time = modify_time;
        this.access_date = access_date;
        this.access_count = access_count;
    }
}
