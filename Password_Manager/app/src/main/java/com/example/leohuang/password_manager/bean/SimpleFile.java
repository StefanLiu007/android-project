package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Created by leo.huang on 16/4/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleFile {
    @JsonProperty("id")
    public String id;
    @JsonProperty("item_id")
    public String item_id;
    @JsonProperty("file")
    public byte[] file;

    @JsonCreator
    public SimpleFile() {

    }

    @JsonCreator
    public SimpleFile(@JsonProperty("id") String id, @JsonProperty("item_id") String item_id, @JsonProperty("file") byte[] file) {
        this.id = id;
        this.item_id = item_id;
        this.file = file;
    }

    @Override
    public String toString() {
        return "SimpleFile{" +
                "id='" + id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", file=" + Arrays.toString(file) +
                '}';
    }
}
