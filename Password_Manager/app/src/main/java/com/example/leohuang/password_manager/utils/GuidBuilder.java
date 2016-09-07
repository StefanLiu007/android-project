package com.example.leohuang.password_manager.utils;

import java.util.UUID;

/**
 * Guid生成器
 * Created by leo.huang on 16/3/29.
 */
public class GuidBuilder {
    public static String guidGenerator(){
        return UUID.randomUUID().toString();
    }
}
