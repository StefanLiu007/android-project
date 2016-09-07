package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 备注
 * Created by Jack on 16/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleRemark {
    @JsonProperty("id")
    public String id;
    @JsonProperty("value")
    public String value;
    @JsonProperty("item_id")
    public String item_id;

    @JsonCreator
    public SimpleRemark() {

    }

    @JsonCreator
    public SimpleRemark(@JsonProperty("id") String id, @JsonProperty("value") String value, @JsonProperty("item_id") String item_id) {
        this.id = id;
        this.value = value;
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return "SimpleRemark{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", item_id='" + item_id + '\'' +
                '}';
    }
}
