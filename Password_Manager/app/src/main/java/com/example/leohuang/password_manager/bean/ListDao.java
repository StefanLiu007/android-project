package com.example.leohuang.password_manager.bean;


/**
 * Created by sun on 16/3/4.
 */
/*侧滑菜单*/
public class ListDao {
    public int icon;
    public String title;
    public String itemIcon;
    public ListDao() {
    }

    public ListDao(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public ListDao(String itemIcon, String title) {
        this.itemIcon = itemIcon;
        this.title = title;
    }
}
