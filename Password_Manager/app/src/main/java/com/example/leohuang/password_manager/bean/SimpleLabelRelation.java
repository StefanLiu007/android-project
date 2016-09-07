package com.example.leohuang.password_manager.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by leo.huang on 16/4/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleLabelRelation {
    @JsonProperty("id")
    public String id;
    @JsonProperty("label_id")
    public String label_id;
    @JsonProperty("item_id")
    public String item_id;

    @JsonCreator
    public SimpleLabelRelation() {

    }

    @JsonCreator
    public SimpleLabelRelation(@JsonProperty("id") String id, @JsonProperty("label_id") String label_id, @JsonProperty("item_id") String item_id) {
        this.id = id;
        this.label_id = label_id;
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return "SimpleLabelRelation{" +
                "id='" + id + '\'' +
                ", label_id='" + label_id + '\'' +
                ", item_id='" + item_id + '\'' +
                '}';
    }
}
