package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 每条记录的数据
 * Created by leo.huang on 16/4/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogItemInfo {
    @JsonProperty("action")
    public int action;
    @JsonProperty("createTime")
    public String createTime;

    @JsonCreator
    public LogItemInfo(@JsonProperty("action") int action, @JsonProperty("createTiem") String createTime) {
        this.action = action;
        this.createTime = createTime;
    }

    @JsonCreator
    public LogItemInfo(){

    }

    @Override
    public String toString() {
        return "LogItemInfo{" +
                "action=" + action +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
