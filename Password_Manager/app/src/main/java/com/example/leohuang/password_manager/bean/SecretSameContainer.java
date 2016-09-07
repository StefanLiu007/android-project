package com.example.leohuang.password_manager.bean;

import java.util.List;

/**
 * 相同密码的容器
 * Created by leo.huang on 16/4/22.
 */
public class SecretSameContainer {
    //密码
    public String secret;
    //相同密码的集合
    public List<Item> items;

    @Override
    public String toString() {
        return "SecretSameContainer{" +
                "secret='" + secret + '\'' +
                ", items=" + items +
                '}';
    }
}
