package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Created by leo.huang on 16/4/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleFieldData {
    @JsonProperty("id")
    public String id;
    @JsonProperty("type")
    public String type;
    @JsonProperty("name")
    public String name;
    @JsonProperty("value")
    public byte[] value;
    @JsonProperty("sort")
    public int sort;
    @JsonProperty("custom")
    public int custom;
    @JsonProperty("item_id")
    public String item_id;

    @JsonCreator
    public SimpleFieldData() {

    }

    @JsonCreator
    public SimpleFieldData(@JsonProperty("id") String id, @JsonProperty("type") String type
            , @JsonProperty("name") String name, @JsonProperty("value") byte[] value, @JsonProperty("sort") int sort
            , @JsonProperty("custom") int custom, @JsonProperty("item_id") String item_id
    ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.sort = sort;
        this.custom = custom;
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return "SimpleFieldData{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", value=" + Arrays.toString(value) +
                ", sort=" + sort +
                ", custom=" + custom +
                ", item_id='" + item_id + '\'' +
                '}';
    }

}
