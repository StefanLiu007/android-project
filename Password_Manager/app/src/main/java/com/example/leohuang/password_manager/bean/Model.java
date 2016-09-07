package com.example.leohuang.password_manager.bean;

import java.util.List;

/**
 * 类别
 * Created by Jack on 16/3/24.
 */
public class Model {
    public int id;//类别的id
    public String name;//类别的名字
    public String icon;//类别的图标
    public List<Template> templates;

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", templates=" + templates +
                '}';
    }

}
