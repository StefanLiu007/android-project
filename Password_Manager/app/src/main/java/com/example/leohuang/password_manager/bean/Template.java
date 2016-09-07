package com.example.leohuang.password_manager.bean;

import com.example.leohuang.password_manager.utils.Setting;

/**
 * 字段模版
 * Created by Jack on 16/3/24.
 */
public class Template {
    public int id ;//字段模版id
    public String name;//字段模版的名字
    public String type;//字段模版的类型
    public int index;//字段模版的索引
    public int type_id;//字段模版所属类型
    public int custom= Setting.NOT_CUSTOM ;//默认是零

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", index=" + index +
                ", type_id=" + type_id +
                ", custom=" + custom +
                '}';
    }
}
