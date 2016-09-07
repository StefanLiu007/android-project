package com.example.leohuang.password_manager.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Field;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Label;
import com.example.leohuang.password_manager.bean.LaberAccount;
import com.example.leohuang.password_manager.bean.LogItem;
import com.example.leohuang.password_manager.bean.LogItemInfo;
import com.example.leohuang.password_manager.bean.LoginTemplate;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.bean.Remark;
import com.example.leohuang.password_manager.bean.SecretSameContainer;
import com.example.leohuang.password_manager.bean.SimpleFieldData;
import com.example.leohuang.password_manager.bean.SimpleFile;
import com.example.leohuang.password_manager.bean.SimpleItem;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.bean.SimpleLabelRelation;
import com.example.leohuang.password_manager.bean.SimpleLogItem;
import com.example.leohuang.password_manager.bean.SimpleRemark;
import com.example.leohuang.password_manager.bean.Template;
import com.example.leohuang.password_manager.bean.User;
import com.example.leohuang.password_manager.utils.CloseUtils;
import com.example.leohuang.password_manager.utils.SecretUtils;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private static SQLiteDatabase mDatabase;
    private static final String T_USER = "t_user";
    private static final String T_TYPE = "t_type";
    private static final String T_TEMPLATE = "t_template";
    private static final String T_LABEL = "t_label";
    private static final String T_ITEM = "t_item";
    private static final String T_FIELD_DATA = "t_field_data";
    private static final String T_LOGIN_TEMPLATE = "t_login_template";
    private static final String T_VERSION = "t_version";
    private static final String T_LOG = "t_log";
    private static final String T_LABEL_RELATION = "t_label_relation";
    private static final String T_REMARK = "t_remark";

    private static final String TAG = "DatabaseManager";

    public synchronized static SQLiteDatabase openDatabase() {
        SQLiteDatabase mDatabase = SQLiteDatabase.openOrCreateDatabase(MyApplication.databaseFilename, null);
        return mDatabase;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static User queryUser() {
        // TODO: 16/4/12 获取用户信息
        User user = new User();
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor cursor = sqLiteDatabase.query(T_USER, null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            user.password = cursor.getString(cursor.getColumnIndex("password"));
            user.answer = cursor.getString(cursor.getColumnIndex("answer"));
            user.pin = cursor.getString(cursor.getColumnIndex("pin"));
            user.tip = cursor.getString(cursor.getColumnIndex("tip"));
            user.question = cursor.getString(cursor.getColumnIndex("question"));
        }
        return user;
    }

    /**
     * 第一次设置进入密码
     *
     * @param use
     */
    public static void setPassword(User use) {
        // TODO: 16/3/28 设置密码信息

        SQLiteDatabase sqLiteDatabase = openDatabase();
        ContentValues values = new ContentValues();
        values.put("password", use.password);
        values.put("question", use.question);
        values.put("answer", use.answer);
        values.put("id", use.id);
        values.put("pin", use.pin);
        values.put("tip", use.tip);
        sqLiteDatabase.insert(T_USER, null, values);
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }

    }

    /**
     * 用密码登录，如果忘记密码，输入预设定问题的答案
     *
     * @param user
     * @return
     */
    public static boolean login(User user) {
        // TODO: 16/3/28 登录
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = null;
        if (user.pin != null) {
            c = sqLiteDatabase.query(T_USER, new String[]{"pin"}, "pin=?", new String[]{user.password}, null, null, null, null);
            if (c.moveToNext()) {
                close(sqLiteDatabase, c);
                return true;
            }
        } else if (user.password != null) {
            c = sqLiteDatabase.query(T_USER, new String[]{"password"}, "password=?", new String[]{user.password}, null, null, null, null);
            if (c.moveToNext()) {
                close(sqLiteDatabase, c);
                return true;
            }
        } else {
            c = sqLiteDatabase.query(T_USER, new String[]{"answer"}, "answer=?", new String[]{user.answer}, null, null, null, null);

            if (c.moveToNext()) {
                close(sqLiteDatabase, c);
                return true;
            }
        }
        c.close();

        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return false;
    }

    /**
     * 是否设置过pin
     *
     * @return
     */
    public static boolean hasPin() {
        // TODO: 16/3/28 是否设置过pin
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(T_USER, new String[]{"pin"}, null, null, null, null, null);
        if (c.moveToNext()) {
            return true;
        }
        c.close();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return false;
    }

    /**
     * 是否设置过问题
     *
     * @return
     */
    public static boolean hasQuestion() {
        // TODO: 16/3/28 是否设置过问题
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(T_USER, new String[]{"question"}, null, null, null, null, null);
        if (c.moveToNext()) {
            return true;
        }
        c.close();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return false;
    }

    /**
     * 重置pin或password
     *
     * @param user
     * @return
     */
    public static boolean updatePinOrPassword(User user) {
        // TODO: 16/3/28 重置pin或password
        SQLiteDatabase sqLiteDatabase = openDatabase();
        if (user.pin != null) {
            ContentValues values = new ContentValues();
            values.put("pin", user.pin);
            int a = sqLiteDatabase.update(T_USER, values, null, null);
            sqLiteDatabase.close();
            if (a > 0) {
                return true;
            }
        } else if (user.password != null) {
            ContentValues values = new ContentValues();
            values.put("password", user.password);
            int b = sqLiteDatabase.update(T_USER, values, null, null);
            sqLiteDatabase.close();
            if (b > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取信息的类别
     *
     * @return
     */
    public static List<Model> getType() {
        SQLiteDatabase sqLiteDatabase = openDatabase();
        List<Model> types = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(T_TYPE, new String[]{"id", "name", "icon"}, null, null, "icon", null, "id");
        while (c.moveToNext()) {
            Model type = new Model();
            type.id = c.getInt(c.getColumnIndex("id"));
            type.icon = c.getString(c.getColumnIndex("icon"));
            type.name = c.getString(c.getColumnIndex("name"));
            types.add(type);
        }
        c.close();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return types;
    }

    /**
     * 获取模版字段
     *
     * @param id
     * @return
     */
    public static List<Template> getTemplate(int id) {
        SQLiteDatabase sqLiteDatabase = openDatabase();
        List<Template> templates = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(T_TEMPLATE, new String[]{"name", "type", "sort"}, "type_id=?", new String[]{id + ""}, null, null, "sort");
        while (c.moveToNext()) {
            Template template = new Template();
            template.name = c.getString(c.getColumnIndex("name"));
            template.type = c.getString(c.getColumnIndex("type"));
            template.index = c.getInt(c.getColumnIndex("sort"));
            templates.add(template);

        }
        c.close();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return templates;
    }

    /**
     * 添加项目信息时，数据插入项目表和字段表
     *
     * @param item
     * @return
     */

    public static String insertItem(Item item) {
        //TODO 插入项目信息（项目信息，字段，标签）Stefan
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(T_ITEM, new String[]{"id"}, "id=?", new String[]{item.guid}, null, null, null);
        // TODO: 16/4/7 日志添加 
        if (c.moveToNext()) {
            return update(item, sqLiteDatabase);
        } else {
            return insert(item, sqLiteDatabase);
        }
        //插入项目

    }

    private static String update(Item item, SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();
        Cursor cursor = null;
        values.put("id", item.guid);//新建项目的id
        values.put("name", item.name);
        values.put("icon", item.icon);
        values.put("create_date", item.create_time);
        values.put("modify_date", item.modify_time);
        values.put("access_date", item.access_time);
        values.put("access_count", item.access_count);
        values.put("is_favorited", item.is_favorited);
        values.put("favorite_index", item.favorite_index);
        values.put("type_id", item.type_id);
        long l = sqLiteDatabase.update(T_ITEM, values, "id=?", new String[]{item.guid});
        if (l <= 0) {
            cursor.close();
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
            return Setting.ERROR_UPDATE_ITEM;
        } else {
            /**
             * 更新字段
             *
             */
            if (item.fields != null) {
                values.clear();
                long l2 = 0, l3 = 0;
                for (int i = 0; i < item.fields.size(); i++) {
                    Field a = item.fields.get(i);
                    cursor = sqLiteDatabase.query(T_FIELD_DATA, new String[]{"id"}, "id=?", new String[]{a.guid}, null, null, null);
                    if (cursor.moveToNext()) {
                        if (a.value != null) {
                            values.put("id", a.guid);
                            values.put("name", a.name);
                            values.put("value", a.value);
                            values.put("type", a.type);
                            values.put("sort", a.index);
                            values.put("custom", a.custom);
                            values.put("item_id", a.item_id);//所参考项目的id
                            l2 = sqLiteDatabase.update(T_FIELD_DATA, values, "id=?", new String[]{a.guid});
                            values.clear();
                            if (l2 <= 0) {
                                return Setting.ERROR_UPDATE_FIELD;
                            }
                        } else {
                            int c = sqLiteDatabase.delete(T_FIELD_DATA, "id=?", new String[]{a.guid});
                            if (l2 <= 0) {
                                return Setting.ERROR_UPDATE_FIELD;
                            }
                        }
                    } else {
                        if (a.value != null) {
                            values.put("id", a.guid);
                            values.put("name", a.name);
                            values.put("value", a.value);
                            values.put("type", a.type);
                            values.put("sort", a.index);
                            values.put("custom", a.custom);
                            values.put("item_id", a.item_id);//所参考项目的id
                            l2 = sqLiteDatabase.insert(T_FIELD_DATA, null, values);
                            values.clear();
                            if (l2 <= 0) {
                                return Setting.ERROR_UPDATE_FIELD;
                            }
                        }
                    }
                }
            }
            values.clear();
            if (item.remark != null) {
                if (item.remark.value != null) {
                    Cursor c = sqLiteDatabase.query(T_REMARK, new String[]{"id"}, "id=?", new String[]{item.remark.id}, null, null, null);
                    if (c.moveToNext()) {
                        values.put("id", item.remark.id);
                        values.put("value", item.remark.value);
                        values.put("item_id", item.remark.item_id);
                        l = sqLiteDatabase.update(T_REMARK, values, "item_id=?", new String[]{item.guid});
                        c.close();
                        if (l <= 0) {
                            return Setting.ERROR_UPDATE_REMARK;
                        }

                    } else {
                        values.put("id", item.remark.id);
                        values.put("value", item.remark.value);
                        values.put("item_id", item.remark.item_id);
                        l = sqLiteDatabase.insert(T_REMARK, null, values);
                        if (l < 0) {
                            return Setting.ERROR_UPDATE_REMARK;
                        }
                    }


                }
            }

            values.clear();
            //更新标签
            if (item.labels != null) {
                for (int i = 0; i < item.labels.size(); i++) {
                    Label label = item.labels.get(i);
                    Cursor c = sqLiteDatabase.query(T_LABEL, new String[]{"name"}, "name=?", new String[]{label.name}, null, null, null);
                    if (!c.moveToNext()) {
                        values.put("id", label.id);
                        values.put("name", label.name);
                        long l3 = sqLiteDatabase.insert(T_LABEL, null, values);
                        if (l3 <= 0) {
                            return Setting.ERROR_UPDATE_LABEL;
                        }
                        values.clear();
                        values.put("id", label.relation_id);
                        values.put("label_id", label.id);
                        values.put("item_id", label.type_id);
                        l3 = sqLiteDatabase.insert(T_LABEL_RELATION, null, values);
                        if (l3 <= 0) {
                            return Setting.ERROR_UPDATE_LABEL_RELATION;
                        }
                    }
                    c.close();
                    values.clear();
                }
            }
            if (cursor != null) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
            return Setting.UPDATE_SUCCESS;
        }
    }

    private static String insert(Item item, SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();
        values.put("id", item.guid);//新建项目的id
        values.put("name", item.name);
        values.put("icon", item.icon);
        values.put("create_date", item.create_time);
        values.put("modify_date", item.modify_time);
        values.put("access_date", item.access_time);
        values.put("access_count", item.access_count);
        values.put("is_favorited", item.is_favorited);
        values.put("favorite_index", item.favorite_index);
        values.put("type_id", item.type_id);
        long l = sqLiteDatabase.insert(T_ITEM, null, values);
        if (l <= 0) {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
            return Setting.ERROR_ITEM;
        } else {
            //插入字段
            if (item.fields != null) {
                values.clear();
                long l2 = 0;
                for (int i = 0; i < item.fields.size(); i++) {
                    Field a = item.fields.get(i);
                    if (a.value != null) {
                        values.put("id", a.guid);
                        values.put("name", a.name);
                        values.put("value", a.value);
                        values.put("type", a.type);
                        values.put("sort", a.index);
                        values.put("custom", a.custom);
                        values.put("item_id", a.item_id);//所参考项目的id
                        l2 = sqLiteDatabase.insert(T_FIELD_DATA, null, values);
                        values.clear();
                        if (l2 <= 0) {
                            return Setting.ERROR_FIELD;
                        }
                    }
                }
            }
            //插入备注
            if (item.remark != null) {
                values.put("id", item.remark.id);
                values.put("value", item.remark.value);
                values.put("item_id", item.remark.item_id);
                l = sqLiteDatabase.insert(T_REMARK, null, values);
                values.clear();
                if (l <= 0) {
                    return Setting.ERROR_REMARK;
                }


            }

            //插入标签
            long l3 = 0, l4 = 0;
            if (item.labels != null) {
                for (int i = 0; i < item.labels.size(); i++) {
                    Label label = item.labels.get(i);
                    Cursor c = sqLiteDatabase.query(T_LABEL, new String[]{"id"}, "name=?", new String[]{label.name}, null, null, null);
                    if (c.moveToNext()) {
                        label.id = c.getString(c.getColumnIndex("id"));
                    } else {
                        values.put("id", label.id);
                        values.put("name", label.name);
                        l3 = sqLiteDatabase.insert(T_LABEL, null, values);
                        if (l3 <= 0) {
                            return Setting.ERROR_LABEL;
                        }
                    }
                    c.close();
                    values.clear();
                    values.put("id", label.relation_id);
                    values.put("label_id", label.id);
                    values.put("item_id", label.type_id);
                    l4 = sqLiteDatabase.insert(T_LABEL_RELATION, null, values);
                    if (l4 <= 0) {
                        return Setting.ERROR_LABEL_RELATION;
                    }
                    values.clear();
                }
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
            return Setting.SUCCESS;
        }
    }

    /**
     * 获取登录模版
     *
     * @return
     */
    public static List<LoginTemplate> getLoginTemplate() {
        // TODO: 16/3/29 获取登陆模版
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(T_LOGIN_TEMPLATE, null, null, null, null, null, null);
        List<LoginTemplate> loginTemplates = new ArrayList<>();
        while (c.moveToNext()) {
            LoginTemplate loginTemplate = new LoginTemplate();
            loginTemplate.name = c.getString(c.getColumnIndex("name"));
            loginTemplate.icon = c.getString(c.getColumnIndex("icon"));
            loginTemplate.url = c.getString(c.getColumnIndex("url"));
            loginTemplates.add(loginTemplate);
        }
        close(sqLiteDatabase, c);
        return loginTemplates;
    }

    /**
     * 展示项目的信息，不包括项目里字段信息
     *
     * @return
     */
    public static List<Item> showItems(String orderby) {
        // TODO: 16/3/29 展示项目信息
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = null;
        if (orderby == null) {
            c = sqLiteDatabase.query(T_ITEM, null, null, null, null, null, "access_date", null);
        } else {
            c = sqLiteDatabase.query(T_ITEM, null, null, null, null, null, orderby, null);
        }
        List<Item> items = new ArrayList<>();
        while (c.moveToNext()) {
            Item item = new Item();
            includeItem(c, items, item, sqLiteDatabase);
        }
        close(sqLiteDatabase, c);

        return items;

    }


    /**
     * 获取密码等级为低的项目的Id
     *
     * @return
     */
    public static List<String> getSecretLevelLowItems() {
        List<String> items = new ArrayList<>();

        SQLiteDatabase sqliteDatabase = openDatabase();

        Cursor cursor = sqliteDatabase.query(Setting.T_ITEM, new String[]{"id"}, null, null, null, null, null);//查询所有对象的Id


        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            Cursor secretCursor = sqliteDatabase.query(Setting.T_FIELD_DATA, new String[]{"value"}, "item_id=? and type=?", new String[]{id, "password"}, null, null, null);
            while (secretCursor.moveToNext()) {
                byte[] value = secretCursor.getBlob(secretCursor.getColumnIndex("value"));
                String secret = new String(value);
                int level = SecretUtils.getLevel(secret);
                if (level == Setting.SECRET_LOW) {
                    items.add(id);
                    break;
                }
            }
        }

        return items;
    }


    private static void includeItem(Cursor c, List<Item> items, Item item, SQLiteDatabase sqLiteDatabase) {
        item.name = c.getString(c.getColumnIndex("name"));
        item.icon = c.getString(c.getColumnIndex("icon"));
        item.create_time = c.getString(c.getColumnIndex("create_date"));
        item.modify_time = c.getString(c.getColumnIndex("modify_date"));
        item.access_count = c.getInt(c.getColumnIndex("access_count"));
        item.guid = c.getString(c.getColumnIndex("id"));
        item.is_favorited = c.getInt(c.getColumnIndex("is_favorited"));
        item.favorite_index = c.getInt(c.getColumnIndex("favorite_index"));
        item.type_id = c.getInt(c.getColumnIndex("type_id"));
        item.access_time = c.getString(c.getColumnIndex("access_date"));
        Item item1 = getItemInfo(sqLiteDatabase, item.guid);
        item.fields = item1.fields;
        item.labels = item1.labels;
        item.remark = item1.remark;
        items.add(item);

    }

    /**
     * 删除项目及对应里面的条目
     *
     * @param item
     * @return
     */
    public static String deleteItem(Item item) {
        // TODO: 16/3/29 删除项目及对应里面的条目
        SQLiteDatabase sqLiteDatabase = openDatabase();
        // TODO: 16/4/7 添加日志
        /**
         * 删除标签
         */
        if (item.labels != null) {
            if (item.labels.size() > 0) {
                int l1 = sqLiteDatabase.delete(T_LABEL_RELATION, "item_id=?", new String[]{item.guid});
                if (l1 <= 0) {
                    return Setting.DELETE_FAIL + "label";
                }
            }
        }
        if (item.remark != null) {
            int a = sqLiteDatabase.delete(T_REMARK, "item_id=?", new String[]{item.guid});
            if (a <= 0) {
                return Setting.DELETE_FAIL + "remark";
            }
        }
        if (item.fields != null && item.fields.size() > 0) {
            int l = sqLiteDatabase.delete(T_FIELD_DATA, "item_id=?", new String[]{item.guid});
            if (l <= 0) {
                return Setting.DELETE_FAIL + "field";
            }
        }
        if (item != null) {
            int l2 = sqLiteDatabase.delete(T_ITEM, "id=?", new String[]{item.guid});
            sqLiteDatabase.close();
            if (l2 <= 0) {
                return Setting.DELETE_FAIL + "item";
            }
        }
        return Setting.DELETE_SUCCESS;


    }

    /**
     * 点击每个条目展示的具体项目信息
     *
     * @param item_id
     * @return
     */
    public static Item getItemInfo(SQLiteDatabase sqLiteDatabase, String item_id) {
        // TODO: 16/3/29 点击每个条目的具体项目信息展示字段信息
        Cursor c = sqLiteDatabase.query(T_FIELD_DATA, null, "item_id=?", new String[]{item_id}, null, null, "sort");
        /**
         * 获取字段
         */
        List<Field> fields = new ArrayList<>();
        while (c.moveToNext()) {
            Field field = new Field();
            field.guid = c.getString(c.getColumnIndex("id"));
            field.name = c.getString(c.getColumnIndex("name"));
            field.value = c.getBlob(c.getColumnIndex("value"));
            field.custom = c.getInt(c.getColumnIndex("custom"));
            field.type = c.getString(c.getColumnIndex("type"));
            field.item_id = c.getString(c.getColumnIndex("item_id"));
            field.index = c.getInt(c.getColumnIndex("sort"));
            fields.add(field);
        }
        /**
         * 获取备注
         */
        c = sqLiteDatabase.query(T_REMARK, null, "item_id=?", new String[]{item_id}, null, null, null, null);
        Remark remark = null;
        if (c.moveToNext()) {
            remark = new Remark();
            remark.id = c.getString(c.getColumnIndex("id"));
            remark.value = c.getString(c.getColumnIndex("value"));
            remark.item_id = c.getString(c.getColumnIndex("item_id"));
        }
        /**
         * 获取标签
         */
        c = sqLiteDatabase.query(T_LABEL_RELATION, new String[]{"label_id"}, "item_id=?", new String[]{item_id}, null, null, null, null);
        ArrayList<Label> labels = new ArrayList<>();
        while (c.moveToNext()) {
            Label label = new Label();
            label.id = c.getString(c.getColumnIndex("label_id"));
            label.type_id = item_id;
            labels.add(label);
        }
        if (labels.size() > 0) {
            for (int i = 0; i < labels.size(); i++) {
                Label label = labels.get(i);
                c = sqLiteDatabase.query(T_LABEL, new String[]{"name"}, "id=?", new String[]{label.id}, null, null, null);
                if (c.moveToNext()) {
                    label.name = c.getString(c.getColumnIndex("name"));
                }
            }
        }
        Item item = new Item();
        if (labels.size() > 0) {
            item.labels = labels;
        } else {
            item.labels = null;
        }
        item.fields = fields;
        item.remark = remark;
        c.close();
        return item;
    }

    /**
     * 删除自定义字段同事更新需改时间
     *
     * @param field
     * @return
     */
    public static boolean deleteCustomField(Field field, String time) {
        // TODO: 16/3/29 删除自定义字段，同时更改修改时间
        SQLiteDatabase sqLiteDatabase = openDatabase();
        // TODO: 16/4/7 添加日志 
        String whereClause = "id=?";
        String[] whereArgs = new String[]{field.guid};
        int a = sqLiteDatabase.delete(T_FIELD_DATA, whereClause, whereArgs);
        ContentValues values = new ContentValues();
        values.put("modify_date", time);
        String whereClause1 = "type_id=?";
        String[] whereArgs1 = new String[]{field.item_id};
        int a1 = sqLiteDatabase.update(T_ITEM, values, whereClause1, whereArgs1);
        sqLiteDatabase.close();
        if (a > 0 && a1 > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取所有项目模版及对应的字段模版
     *
     * @return
     */
    public static List<Model> getModels() {
        // TODO: 16/3/29 获取模版及对应字段
        SQLiteDatabase sqLiteDatabase = openDatabase();
        List<Model> models = new ArrayList<>();
        Cursor c1 = null;
        Cursor c = sqLiteDatabase.query(T_TYPE, new String[]{"id", "name", "icon"}, null, null, "icon", null, "id");
        while (c.moveToNext()) {
            Model model = new Model();
            List<Template> templates = new ArrayList<>();
            model.id = c.getInt(c.getColumnIndex("id"));
            model.icon = c.getString(c.getColumnIndex("icon"));
            model.name = c.getString(c.getColumnIndex("name"));
            c1 = sqLiteDatabase.query(T_TEMPLATE, null, "type_id=?", new String[]{model.id + ""}, null, null, "sort");
            while (c1.moveToNext()) {
                Template template = new Template();
                template.name = c1.getString(c1.getColumnIndex("name"));
                template.type = c1.getString(c1.getColumnIndex("type"));
                template.index = c1.getInt(c1.getColumnIndex("sort"));
                template.id = c1.getInt(c1.getColumnIndex("id"));
                template.type_id = model.id;
                templates.add(template);

            }
            model.templates = templates;
            models.add(model);
        }
        c1.close();
        c.close();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        if (models.size() > 0) {
            return models;
        }
        return null;
    }

    /**
     * 加载登录模版
     *
     * @return
     */
    public static List<LoginTemplate> loadLoginTemplate() {
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(T_LOGIN_TEMPLATE, null, null, null, null, null, null);
        List<LoginTemplate> loginTemplates = new ArrayList<>();
        while (c.moveToNext()) {
            LoginTemplate loginTemplate = new LoginTemplate();
            loginTemplate.id = c.getInt(c.getColumnIndex("id"));
            loginTemplate.name = c.getString(c.getColumnIndex("name"));
            loginTemplate.url = c.getString(c.getColumnIndex("icon"));
            loginTemplate.icon = c.getString(c.getColumnIndex("icon"));
            loginTemplates.add(loginTemplate);
        }
        close(sqLiteDatabase, c);
        if (loginTemplates.size() > 0) {
            return loginTemplates;
        }
        return null;

    }

    /**
     * 删除收藏和添加收藏
     *
     * @param guid
     * @param type
     * @return
     */
    public static boolean addOrDeleteFavroite(String guid, int type) {
        // TODO: 16/3/29 删除收藏或添加收藏，guid代表项目名，type为1表示添加收藏，0表示取消搜藏 
        SQLiteDatabase sqLiteDatabase = openDatabase();

        if (type == 1) {
            int a = 0;
            Cursor c = sqLiteDatabase.rawQuery("SELECT MAX(favorite_index) FROM t_item", null);
            if (c.moveToNext()) {
                a = c.getCount();
            }
            ContentValues values = new ContentValues();
            values.put("is_favorited", type);
            values.put("favorite_index", a + 1);
            int a1 = sqLiteDatabase.update(T_ITEM, values, "id=?", new String[]{guid});
            close(sqLiteDatabase, c);
            if (a1 > 0) {
                return true;
            }
        } else {
            ContentValues values = new ContentValues();
            values.put("is_favorited", type);
            values.putNull("favorite_index");
            int a1 = sqLiteDatabase.update(T_ITEM, values, "id=?", new String[]{guid});
            sqLiteDatabase.close();
            if (a1 > 0) {
                return true;
            }
        }

        return false;

    }

    /**
     * 判断是否收藏
     *
     * @param id
     * @return
     */
    public static boolean isFavorite(String id) {
        // TODO: 16/3/29 判断是否收藏
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = null;
        c = sqLiteDatabase.query(T_ITEM, new String[]{"is_favorited"}, "id=?", new String[]{id}, null, null, null);
        close(sqLiteDatabase, c);
        if (c.moveToNext()) {
            return true;
        }
        return false;
    }

    /**
     * 查找所有收藏的项
     *
     * @param orderby
     * @return
     */
    public static List<Item> getFavorite(String orderby) {
        // TODO: 16/3/29 查找所有收藏的项目
        SQLiteDatabase sqLiteDatabase = openDatabase();
        List<Item> items = new ArrayList<>();
        Cursor c = null;
        if (orderby == null) {
            c = sqLiteDatabase.query(T_ITEM, null, "is_favorited=?", new String[]{1 + ""}, null, null, "favorite_index");
        } else {
            c = sqLiteDatabase.query(T_ITEM, null, "is_favorited=?", new String[]{1 + ""}, null, null, orderby + " DESC");
        }
        while (c.moveToNext()) {
            Item item = new Item();
            includeItem(c, items, item, sqLiteDatabase);
        }
        sqLiteDatabase.close();
        c.close();
        if (items.size() > 0) {
            return items;
        }
        return null;

    }

    /**
     * 手动排序收藏过的项目
     *
     * @param items
     * @return
     */
    public static boolean sortFavorite(List<Item> items) {
        // TODO: 16/3/29 手动排序收藏过的项目
        SQLiteDatabase sqLiteDatabase = openDatabase();
        ContentValues values = new ContentValues();
        int b = 0;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            values.put("favorite_index", item.favorite_index);
            b = sqLiteDatabase.update(T_ITEM, values, "id=?", new String[]{item.guid});
            values.clear();
        }
        sqLiteDatabase.close();
        if (b > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除对应项目的标签及修改项目的修改时间
     *
     * @param label
     * @param typeId
     * @param modifyTime
     * @return
     */
    public static boolean deleteLabel(Label label, String typeId, String modifyTime) {
        // TODO: 16/3/29 删除对应项目的标签及修改项目的修改时间
        // TODO: 16/4/7 添加日志
        SQLiteDatabase sqLiteDatabase = openDatabase();
        String whereClause = "item_id=?";
        String[] whereArgs = new String[]{typeId};
        int a = sqLiteDatabase.delete(T_LABEL, whereClause, whereArgs);
        ContentValues values = new ContentValues();
        values.put("modify_date", modifyTime);
        String whereClause1 = "type_id=?";
        String[] whereArgs1 = new String[]{typeId};
        int a1 = sqLiteDatabase.update(T_ITEM, values, whereClause1, whereArgs1);
        sqLiteDatabase.close();
        if (a > 0 && a1 > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查找所有项目的标签
     *
     * @return
     */
    public static List<Label> queryLabels() {
        // TODO: 16/3/29 查找所有项目的标签
        SQLiteDatabase sqLiteDatabase = openDatabase();
        List<Label> labels = new ArrayList<>();
        Cursor c = null;
        c = sqLiteDatabase.query(T_LABEL, null, null, null, null, null, null);
        while (c.moveToNext()) {
            Label label = new Label();
            label.id = c.getString(c.getColumnIndex("id"));
            label.name = c.getString(c.getColumnIndex("name"));
            label.type_id = c.getString(c.getColumnIndex("type_id"));
            labels.add(label);
        }
        close(sqLiteDatabase, c);
        if (labels.size() > 0) {
            return labels;
        }
        return null;
    }

    /**
     * 每次访问密码时访问次数增加
     *
     * @param itemId
     */
    public static void addCount(String itemId) {
        // TODO: 16/3/29 每次访问密码时访问次数增加
        SQLiteDatabase sqLiteDatabase = openDatabase();
        int a = 0;
        Cursor c = sqLiteDatabase.query(T_ITEM, new String[]{"access_count"}, "id=?", new String[]{itemId}, null, null, null, null);
        if (c.moveToNext()) {
            a = c.getInt(c.getColumnIndex("access_count"));
        }
        ContentValues values = new ContentValues();
        values.put("access_count", a + 1);
        int b = sqLiteDatabase.update(T_ITEM, values, "id=?", new String[]{itemId});
        close(sqLiteDatabase, c);
    }

    /**
     * 点击项目展示具体条目时更新最近访问时间
     *
     * @param itemId
     * @param nowTime
     */
    public static void updateAccessTime(String itemId, String nowTime) {
        // TODO: 16/3/29 点击项目展示具体条目时更新最近访问时间
        SQLiteDatabase sqLiteDatabase = openDatabase();
        ContentValues values = new ContentValues();
        values.put("access_date", nowTime);
        int b = sqLiteDatabase.update(T_ITEM, values, "id=?", new String[]{itemId});
        sqLiteDatabase.close();
    }

    /**
     * 动态添加标签
     *
     * @param label
     * @return
     */
    public static boolean addLabel(SimpleLabel label) {
        // TODO: 16/3/29 动态添加标签
        // TODO: 16/4/7 日志添加 
        SQLiteDatabase sqLiteDatabase = openDatabase();
        ContentValues values = new ContentValues();
        values.put("id", label.id);
        values.put("name", label.name);
//        values.put("item_id", label.type_id);
        long a = sqLiteDatabase.insert(T_LABEL, null, values);
        sqLiteDatabase.close();
        if (a > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据名字删除标签
     *
     * @param name
     * @return
     */
    public static boolean deleteAllItemLabel(String name) {
        // TODO: 16/3/29 根据名字删除标签
        // TODO: 16/4/7 添加日志
        SQLiteDatabase sqLiteDatabase = openDatabase();
        int a = sqLiteDatabase.delete(T_LABEL, "name=?", new String[]{name});
        sqLiteDatabase.close();
        if (a > 0) {
            return true;
        }
        return false;

    }

    /**
     * 获取所有label的值
     *
     * @return
     */
    public static List<SimpleLabel> queryAllLabels() {
        // TODO: 16/3/30 获取所有label的值
        List<SimpleLabel> simpleLabels = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(true, T_LABEL, null, null, null, null, null, null, null);
        while (c.moveToNext()) {
            SimpleLabel simpleLabel = new SimpleLabel();
            simpleLabel.name = c.getString(c.getColumnIndex("name"));
            simpleLabel.id = c.getString(c.getColumnIndex("id"));
            simpleLabels.add(simpleLabel);
        }
        close(sqLiteDatabase, c);
        return simpleLabels;

    }

    public static List<Item> queryAllItemOrderByLabel(String name) {
        // TODO: 16/4/1 根据标签名查找所有的项目名

        List<Item> items = new ArrayList<>();
        String id = null;
        List<String> itemId = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor c = sqLiteDatabase.query(T_LABEL, new String[]{"id"}, "name=?", new String[]{name}, null, null, null);
        if (c.moveToNext()) {
            id = c.getString(c.getColumnIndex("id"));
        }
        c = sqLiteDatabase.query(T_LABEL_RELATION, new String[]{"item_id"}, "label_id=?", new String[]{id}, null, null, null);
        while (c.moveToNext()) {
            itemId.add(c.getString(c.getColumnIndex("item_id")));
        }
        c.close();
        for (int i = 0; i < itemId.size(); i++) {
            Item item = new Item();
            c = sqLiteDatabase.query(T_ITEM, null, "id=?", new String[]{itemId.get(i)}, null, null, null);
            while (c.moveToNext()) {
                includeItem(c, items, item, sqLiteDatabase);
            }

        }
        sqLiteDatabase.close();
        return items;
    }

    public static List<Item> queryBlurredAllItem(String text) {
        // TODO: 16/4/7 根据账户名查询项目
        List<Item> items = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = openDatabase();
        for (int i = 0; i < items.size(); i++) {
            Item item = new Item();
            // Cursor c=sqLiteDatabase.query(T_ITEM,null,"name=?",new String[]{items.get(i).name},null,null,null);
            Cursor c = sqLiteDatabase.rawQuery("select * from T_ITEM where name=?", new String[]{text});
            while (c.moveToNext()) {
                includeItem(c, items, item, sqLiteDatabase);
            }
        }
        sqLiteDatabase.close();
        return items;
    }

    private static void close(SQLiteDatabase sqLiteDatabase, Cursor c) {
        if (c != null) {
            c.close();
        }
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }

    /**
     * 获取所有的日志
     */
    public static List<LogItem> getLogs() {
        boolean flag = true;
        List<LogItem> logItems = null;//全部的集合
        LogItem logItem = null;
        List<LogItemInfo> logItemInfos = null;//全部的信息集合
        String firstId = null;
        String firstTable = null;

        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor cursor = sqLiteDatabase.query(T_LOG, null, null, null, null, null, "item_id,table_name,create_time");

        if (!cursor.moveToNext()) {
            return null;
        } else {
            firstId = cursor.getString(cursor.getColumnIndex("item_id"));
            firstTable = cursor.getString(cursor.getColumnIndex("table_name"));
            logItems = new ArrayList<>();
            logItem = createLogItem(cursor, firstId, firstTable, logItem, logItemInfos);
            logItems.add(logItem);
        }

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("item_id"));
            String table = cursor.getString(cursor.getColumnIndex("table_name"));
            if (id.equals(firstId) && table.equals(firstTable)) {//如id和表名一样，就直接添加到数据上
                LogItemInfo logItemInfo = createLogItemInfo(cursor);
                logItem.logItemInfoLists.add(logItemInfo);//操作添加到同一个对象上
            } else {
                firstId = id;
                firstTable = table;
                logItem = createLogItem(cursor, firstId, firstTable, logItem, logItemInfos);
                logItems.add(logItem);
            }
        }

        CloseUtils.close(cursor);
        CloseUtils.close(sqLiteDatabase);
        return logItems;
    }


    /**
     * 创建LogItemInfo对象
     *
     * @param cursor
     * @return
     */
    private static LogItemInfo createLogItemInfo(Cursor cursor) {
        LogItemInfo logItemInfo = new LogItemInfo();
        logItemInfo.createTime = cursor.getString(cursor.getColumnIndex("create_time"));//操作时间
        logItemInfo.action = cursor.getInt(cursor.getColumnIndex("sql_action"));
        return logItemInfo;
    }


    /**
     * 创建LogItem对象
     *
     * @param cursor
     * @param id
     * @param table
     * @param logItem
     * @param logItemInfos
     * @return
     */
    private static LogItem createLogItem(Cursor cursor, String id, String table, LogItem logItem, List<LogItemInfo> logItemInfos) {
        logItem = new LogItem();//实例化出LogItem对象
        logItem.item_id = id;//得到id
        logItem.table_name = table;//得打表名
        logItemInfos = new ArrayList<>();
        logItem.logItemInfoLists = logItemInfos;
        LogItemInfo logItemInfo = createLogItemInfo(cursor);
        logItemInfos.add(logItemInfo);
        return logItem;
    }


    /**
     * 事物更新
     *
     * @param simpleFieldDatas
     * @param simpleFiles
     * @param simpleItems
     * @param simpleLabels
     * @param simpleLabelRelations
     * @param simpleRemarks
     */
    public static void syncUpdate(List<SimpleFieldData> simpleFieldDatas, List<SimpleFile> simpleFiles, List<SimpleItem> simpleItems, List<SimpleLabel> simpleLabels, List<SimpleLabelRelation> simpleLabelRelations, List<SimpleRemark> simpleRemarks) {
        SQLiteDatabase sqLiteDatabase = openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            int fieldDataLen = simpleFieldDatas.size();
            int fileLen = simpleFiles.size();
            int itemLen = simpleItems.size();
            int labelLen = simpleLabels.size();
            int relationLen = simpleLabelRelations.size();
            int remarkLen = simpleRemarks.size();
            for (int i = 0; i < fieldDataLen; i++) {
                SimpleFieldData simpleFieldData = simpleFieldDatas.get(i);
                Cursor cursor = sqLiteDatabase.query(Setting.T_FIELD_DATA, new String[]{"id"}, "id=?", new String[]{simpleFieldData.id}, null, null, null);
                ContentValues values = new ContentValues();
                values.put("id", simpleFieldData.id);
                values.put("type", simpleFieldData.type);
                values.put("name", simpleFieldData.name);
                values.put("value", simpleFieldData.value);
                values.put("sort", simpleFieldData.sort);
                values.put("custom", simpleFieldData.custom);
                values.put("item_id", simpleFieldData.item_id);
                if (cursor.moveToNext()) {
                    sqLiteDatabase.update(Setting.T_FIELD_DATA, values, "id=?", new String[]{simpleFieldData.id});
                } else {
                    sqLiteDatabase.insert(Setting.T_FIELD_DATA, null, values);
                }
                CloseUtils.close(cursor);
            }
            for (int i = 0; i < fileLen; i++) {
                SimpleFile simpleFile = simpleFiles.get(i);
                Cursor cursor = sqLiteDatabase.query(Setting.T_FILE, new String[]{"id"}, "id=?", new String[]{simpleFile.id}, null, null, null);
                ContentValues values = new ContentValues();
                values.put("id", simpleFile.id);
                values.put("item_id", simpleFile.item_id);
                values.put("file", simpleFile.file);
                if (cursor.moveToNext()) {
                    sqLiteDatabase.update(Setting.T_FILE, values, "id=?", new String[]{simpleFile.id});
                } else {
                    sqLiteDatabase.insert(Setting.T_FILE, null, values);
                }
                CloseUtils.close(cursor);

            }
            for (int i = 0; i < itemLen; i++) {
                SimpleItem simpleItem = simpleItems.get(i);
                Cursor cursor = sqLiteDatabase.query(Setting.T_ITEM, new String[]{"id"}, "id=?", new String[]{simpleItem.id}, null, null, null);
                ContentValues values = new ContentValues();
                values.put("id", simpleItem.id);
                values.put("name", simpleItem.name);
                values.put("icon", simpleItem.icon);
                values.put("create_date", simpleItem.create_time);
                values.put("modify_date", simpleItem.modify_time);
                values.put("access_date", simpleItem.access_date);
                values.put("type_id", simpleItem.type_id);
                values.put("access_count", simpleItem.access_count);
                if (cursor.moveToNext()) {
                    sqLiteDatabase.update(Setting.T_ITEM, values, "id=?", new String[]{simpleItem.id});
                } else {
                    sqLiteDatabase.insert(Setting.T_ITEM, null, values);
                }
                CloseUtils.close(cursor);
            }
            for (int i = 0; i < labelLen; i++) {
                SimpleLabel simpleLabel = simpleLabels.get(i);
                Cursor cursor = sqLiteDatabase.query(Setting.T_LABEL, new String[]{"id"}, "id=?", new String[]{simpleLabel.id}, null, null, null);
                ContentValues values = new ContentValues();
                values.put("id", simpleLabel.id);
                values.put("name", simpleLabel.name);
                if (cursor.moveToNext()) {
                    sqLiteDatabase.update(Setting.T_LABEL, values, "id=?", new String[]{simpleLabel.id});
                } else {
                    sqLiteDatabase.insert(Setting.T_LABEL, null, values);
                }
                CloseUtils.close(cursor);
            }
            for (int i = 0; i < relationLen; i++) {
                SimpleLabelRelation relation = simpleLabelRelations.get(i);
                Cursor cursor = sqLiteDatabase.query(Setting.T_LABEL_RELATION, new String[]{"id"}, "id=?", new String[]{relation.id}, null, null, null);
                ContentValues values = new ContentValues();
                values.put("id", relation.id);
                values.put("label_id", relation.label_id);
                values.put("item_id", relation.item_id);
                if (cursor.moveToNext()) {
                    sqLiteDatabase.update(Setting.T_LABEL_RELATION, values, "id=?", new String[]{relation.id});
                } else {
                    sqLiteDatabase.insert(Setting.T_LABEL_RELATION, null, values);
                }
                CloseUtils.close(cursor);
            }
            for (int i = 0; i < remarkLen; i++) {
                SimpleRemark remark = simpleRemarks.get(i);
                Cursor cursor = sqLiteDatabase.query(Setting.T_REMARK, new String[]{"id"}, "id=?", new String[]{remark.id}, null, null, null);
                ContentValues values = new ContentValues();
                values.put("id", remark.id);
                values.put("value", remark.value);
                values.put("item_id", remark.item_id);
                if (cursor.moveToNext()) {
                    sqLiteDatabase.update(Setting.T_REMARK, values, "id=?", new String[]{remark.id});
                } else {
                    sqLiteDatabase.insert(Setting.T_REMARK, null, values);
                }
                CloseUtils.close(cursor);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sqLiteDatabase.endTransaction();
            CloseUtils.close(sqLiteDatabase);
        }

    }

    /**
     * 获取所有要更新的数据集合
     *
     * @param simpleLogItems
     * @return
     */
    public static Map<String, List<Object>> getUpdateLog(List<SimpleLogItem> simpleLogItems) {
        SQLiteDatabase sqliteDatebase = openDatabase();
        Map<String, List<Object>> map = new HashMap();
        List<Object> simpleFieldDatas = new ArrayList<>();
        map.put("t_field_data", simpleFieldDatas);
        List<Object> simpleFiles = new ArrayList<>();
        map.put("t_file", simpleFiles);
        List<Object> simpleItems = new ArrayList<>();
        map.put("t_item", simpleItems);
        List<Object> simpleLabels = new ArrayList<>();
        map.put("t_label", simpleLabels);
        List<Object> simpleLableRelations = new ArrayList<>();
        map.put("t_label_relation", simpleLableRelations);
        List<Object> simpleRemarks = new ArrayList<>();
        map.put("t_remark", simpleRemarks);

        int len = simpleLogItems.size();
        for (int i = 0; i < len; i++) {
            SimpleLogItem simpleLogItem = simpleLogItems.get(i);
            if (Setting.T_FIELD_DATA.equals(simpleLogItem.table_name)) {
                Cursor cursor = sqliteDatebase.query(Setting.T_FIELD_DATA, null, "id=?", new String[]{simpleLogItem.item_id}, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleFieldData simpleFieldData = new SimpleFieldData();
                    simpleFieldData.id = cursor.getString(cursor.getColumnIndex("id"));
                    simpleFieldData.type = cursor.getString(cursor.getColumnIndex("type"));
                    simpleFieldData.name = cursor.getString(cursor.getColumnIndex("name"));
                    simpleFieldData.value = cursor.getBlob(cursor.getColumnIndex("value"));
                    simpleFieldData.sort = cursor.getInt(cursor.getColumnIndex("sort"));
                    simpleFieldData.custom = cursor.getInt(cursor.getColumnIndex("custom"));
                    simpleFieldData.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    simpleFieldDatas.add(simpleFieldData);
                }
                CloseUtils.close(cursor);
            } else if (Setting.T_FILE.equals(simpleLogItem.table_name)) {
                Cursor cursor = sqliteDatebase.query(Setting.T_FILE, null, "id=?", new String[]{simpleLogItem.item_id}, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleFile simpleFile = new SimpleFile();
                    simpleFile.id = cursor.getString(cursor.getColumnIndex("id"));
                    simpleFile.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    simpleFile.file = cursor.getBlob(cursor.getColumnIndex("file"));
                    simpleFiles.add(simpleFile);
                }
                CloseUtils.close(cursor);
            } else if (Setting.T_ITEM.equals(simpleLogItem.table_name)) {
                Cursor cursor = sqliteDatebase.query(Setting.T_ITEM, new String[]{"id", "name", "icon", "create_date", "type_id", "modify_date", "access_date", "access_count"}, "id=?", new String[]{simpleLogItem.item_id}, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleItem simpleItem = new SimpleItem();
                    simpleItem.id = cursor.getString(cursor.getColumnIndex("id"));
                    simpleItem.name = cursor.getString(cursor.getColumnIndex("name"));
                    simpleItem.icon = cursor.getString(cursor.getColumnIndex("icon"));
                    simpleItem.create_time = cursor.getString(cursor.getColumnIndex("create_date"));
                    simpleItem.type_id = cursor.getInt(cursor.getColumnIndex("type_id"));
                    simpleItem.modify_time = cursor.getString(cursor.getColumnIndex("modify_date"));
                    simpleItem.access_date = cursor.getString(cursor.getColumnIndex("access_date"));
                    simpleItem.access_count = cursor.getInt(cursor.getColumnIndex("access_count"));
                    Log.i(TAG, simpleItem.toString());
                    simpleItems.add(simpleItem);
                }
                CloseUtils.close(cursor);

            } else if (Setting.T_LABEL.equals(simpleLogItem.table_name)) {
                Cursor cursor = sqliteDatebase.query(Setting.T_LABEL, null, "id=?", new String[]{simpleLogItem.item_id}, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleLabel simpleLabel = new SimpleLabel();
                    simpleLabel.id = cursor.getString(cursor.getColumnIndex("id"));
                    simpleLabel.name = cursor.getString(cursor.getColumnIndex("name"));
                    simpleLabels.add(simpleLabel);
                }
                CloseUtils.close(cursor);

            } else if (Setting.T_LABEL_RELATION.equals(simpleLogItem.table_name)) {
                Cursor cursor = sqliteDatebase.query(Setting.T_LABEL_RELATION, null, "id=?", new String[]{simpleLogItem.item_id}, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleLabelRelation simpleLabelRelation = new SimpleLabelRelation();
                    simpleLabelRelation.id = cursor.getString(cursor.getColumnIndex("id"));
                    simpleLabelRelation.label_id = cursor.getString(cursor.getColumnIndex("label_id"));
                    simpleLabelRelation.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    simpleLableRelations.add(simpleLabelRelation);
                }
                CloseUtils.close(cursor);
            } else if (Setting.T_REMARK.equals(simpleLogItem.table_name)) {
                Cursor cursor = sqliteDatebase.query(Setting.T_REMARK, null, "id=?", new String[]{simpleLogItem.item_id}, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleRemark simpleRemark = new SimpleRemark();
                    simpleRemark.id = cursor.getString(cursor.getColumnIndex("id"));
                    simpleRemark.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    simpleRemark.value = cursor.getString(cursor.getColumnIndex("value"));
                    simpleRemarks.add(simpleRemark);
                }
                CloseUtils.close(cursor);
            }
        }
        return map;
    }

    /**
     * 标签页面所需要的count，和项目id
     *
     * @return
     */
    public static List<LaberAccount> getLaberNumber() {
        SQLiteDatabase sqliteDatebase = openDatabase();
        List<LaberAccount> laberAccounts = new ArrayList<>();
        Cursor c = sqliteDatebase.query(T_LABEL, null, null, null, null, null, null);
        while (c.moveToNext()) {
            LaberAccount laberAccount = new LaberAccount();
            laberAccount.id = c.getString(c.getColumnIndex("id"));
            laberAccount.name = c.getString(c.getColumnIndex("name"));
            laberAccounts.add(laberAccount);
        }
        for (int i = 0; i < laberAccounts.size(); i++) {
            LaberAccount account = laberAccounts.get(i);
            List<String> itemId = new ArrayList<>();
            c = sqliteDatebase.query(T_LABEL_RELATION, new String[]{"item_id"}, "label_id=?", new String[]{account.id}, null, null, null);
            while (c.moveToNext()) {
                itemId.add(c.getString(c.getColumnIndex("item_id")));
            }
            account.count = itemId.size();
            account.itemId = itemId;
        }
        return laberAccounts;
    }

    public static String insertLabel(List<LaberAccount> simpleLabel) {
        // TODO: 16/4/18 插入标签名字
        SQLiteDatabase sqliteDatebase = openDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < simpleLabel.size(); i++) {
            SimpleLabel label = simpleLabel.get(i);
            values.put("id", label.id);
            values.put("name", label.name);
            long a = sqliteDatebase.insert(T_LABEL, null, values);
        }
        sqliteDatebase.close();
        return Setting.INSERT_LABER_SUCCESS;
    }

    public static String deleteLabel(List<LaberAccount> laberAccount) {
        // TODO: 16/4/18 删除标签 
        SQLiteDatabase sqliteDatebase = openDatabase();
        for (int i = 0; i < laberAccount.size(); i++) {
            long a = sqliteDatebase.delete(T_LABEL_RELATION, "label_id=?", new String[]{laberAccount.get(i).id});
            long b = sqliteDatebase.delete(T_LABEL, "id=?", new String[]{laberAccount.get(i).id});
        }
        return Setting.INSERT_LABER_SUCCESS;
    }

    //根据id获取项目信息
    private static Item getItem(SQLiteDatabase sqLiteDatabase, String item_id) {
        Cursor c = sqLiteDatabase.query(T_ITEM, null, "id=?", new String[]{item_id}, null, null, null);
        Item item = new Item();
        if (c.moveToNext()) {
            item.name = c.getString(c.getColumnIndex("name"));
            item.icon = c.getString(c.getColumnIndex("icon"));
            item.create_time = c.getString(c.getColumnIndex("create_date"));
            item.modify_time = c.getString(c.getColumnIndex("modify_date"));
            item.access_count = c.getInt(c.getColumnIndex("access_count"));
            item.guid = c.getString(c.getColumnIndex("id"));
            item.is_favorited = c.getInt(c.getColumnIndex("is_favorited"));
            item.favorite_index = c.getInt(c.getColumnIndex("favorite_index"));
            item.type_id = c.getInt(c.getColumnIndex("type_id"));
            item.access_time = c.getString(c.getColumnIndex("access_date"));
            Item item1 = getItemInfo(sqLiteDatabase, item.guid);
            item.fields = item1.fields;
            item.labels = item1.labels;
            item.remark = item1.remark;
        }
        return item;
    }


    public static List<SecretSameContainer> getSameSecret() {

        String secret = "";
        String item_id = "";
        int count = 0;
        Item firstItem = null;
        List<SecretSameContainer> containers = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = openDatabase();
        Cursor cursor = sqliteDatabase.query(T_FIELD_DATA, new String[]{"item_id", "value"}, "type=?", new String[]{"password"}, null, null, "value");//通过order by获取共有多少个value


        SecretSameContainer container = null;
        if (cursor.moveToNext()) {
            secret = new String(cursor.getBlob(cursor.getColumnIndex("value")));// 拿到第一项的item_id和secret；
            item_id = cursor.getString(cursor.getColumnIndex("item_id"));
            count++;
            container = new SecretSameContainer();
            container.secret = secret;
            container.items = new ArrayList<>();
            // TODO: 16/4/22 通过id获取item
            firstItem = getItem(sqliteDatabase, item_id);
            container.items.add(firstItem);

        }

        while (cursor.moveToNext()) {
            String nextSecret = new String(cursor.getBlob(cursor.getColumnIndex("value")));
            item_id = cursor.getString(cursor.getColumnIndex("item_id"));
            Item item = getItem(sqliteDatabase, item_id);
            if (secret.equals(nextSecret)) {//如果两个密码相同
                count++;
                // TODO: 16/4/22  通过id获取item
                if (!item.guid.equals(firstItem.guid)) {//如果Id相同
                    container.items.add(item);
                    firstItem = item;//重新指向
                }
            } else {
                if (count > 1) {
                    containers.add(container);//如果超过两个，将这个container加入集合中
                }
                count = 1;//重新计数
                secret = nextSecret;//重新设置密码
                container = new SecretSameContainer();
                container.secret = secret;
                container.items = new ArrayList<>();
                firstItem = item;
                container.items.add(item);
            }
        }
        if (count > 1) {//最后
            containers.add(container);
        }

        CloseUtils.close(cursor);
        CloseUtils.close(sqliteDatabase);
        Log.i(TAG, "containers===>" + containers.toString());
        return containers;
    }

    /**
     * 查询所有字段
     *
     * @param chars
     * @return
     */
    public static List<Item> queryAllByChars(String chars) {
        Cursor tagIdCursor = null;
        String id = null;
        List<Item> items = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = openDatabase();//查询tag
        Cursor tagCursor = sqliteDatabase.query(Setting.T_LABEL, new String[]{"id"}, "name like ?", new String[]{"%" + chars + "%"}, null, null, null);
        while (tagCursor.moveToNext()) {
            String lableId = tagCursor.getString(tagCursor.getColumnIndex("id"));
            tagIdCursor = sqliteDatabase.query(Setting.T_LABEL_RELATION, new String[]{"item_id"}, "label_id=?", new String[]{lableId}, null, null, null);
            while (tagIdCursor.moveToNext()) {
                id = tagIdCursor.getString(tagIdCursor.getColumnIndex("item_id"));
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }

        }

        Cursor fieldCursor = sqliteDatabase.query(Setting.T_FIELD_DATA, new String[]{"item_id", "value"}, null, null, null, null, null);//查询出所有的value
        while (fieldCursor.moveToNext()) {//查询所有的内容
            id = fieldCursor.getString(fieldCursor.getColumnIndex("item_id"));
            byte[] value = fieldCursor.getBlob(fieldCursor.getColumnIndex("value"));
            String val = new String(value);
            if (val.contains(chars)) {
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }
        }

        Cursor itemCursor = sqliteDatabase.query(Setting.T_ITEM, new String[]{"id"}, "name like ?", new String[]{"%" + chars + "%"}, null, null, null);
        while (itemCursor.moveToNext()) {
            id = itemCursor.getString(itemCursor.getColumnIndex("id"));
            if (!ids.contains(id)) {
                ids.add(id);
            }
        }

        for (int i = 0, n = ids.size(); i < n; i++) {
            Item item = getItem(sqliteDatabase, ids.get(i));
            items.add(item);
        }

        CloseUtils.close(tagCursor);
        CloseUtils.close(tagIdCursor);
        CloseUtils.close(fieldCursor);
        CloseUtils.close(itemCursor);
        CloseUtils.close(sqliteDatabase);
        return items;
    }

    /**
     * 通过标签查询
     *
     * @param tag
     * @return
     */
    public static List<Item> queryByTag(String tag) {
        String id = null;
        Cursor itemIdCursor = null;
        List<Item> items = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = openDatabase();
        Cursor idCursor = sqliteDatabase.query(Setting.T_LABEL, new String[]{"id"}, "name like ?", new String[]{"%" + tag + "%"}, null, null, null);
        while (idCursor.moveToNext()) {
            String lableId = idCursor.getString(idCursor.getColumnIndex("id"));
            itemIdCursor = sqliteDatabase.query(Setting.T_LABEL_RELATION, new String[]{"item_id"}, "label_id=?", new String[]{lableId}, null, null, null);
            while (itemIdCursor.moveToNext()) {
                id = itemIdCursor.getString(itemIdCursor.getColumnIndex("item_id"));
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }

        }
        for (int i = 0, n = ids.size(); i < n; i++) {
            Item item = getItem(sqliteDatabase, ids.get(i));
            items.add(item);
        }

        CloseUtils.close(itemIdCursor);
        CloseUtils.close(idCursor);
        CloseUtils.close(sqliteDatabase);
        return items;
    }

    /**
     * 通过名字 模糊查找
     *
     * @param name
     * @return
     */
    public static List<Item> queryItemsByName(String name) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = openDatabase();
        Cursor cursor = sqLiteDatabase.query(T_ITEM, null, "name like ?", new String[]{"%" + name + "%"}, null, null, null);
        while (cursor.moveToNext()) {
            Item item = new Item();
            includeItem(cursor, items, item, sqLiteDatabase);
        }
        CloseUtils.close(cursor);
        CloseUtils.close(sqLiteDatabase);
        return items;
    }
}
