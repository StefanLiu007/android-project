package com.example.leohuang.password_manager.utils;

/**
 * Created by leo.huang on 16/3/23.
 */
public interface Setting {

    //pref相关设置
    String SETTING_PREF = "setting";
    String SCREEN_LOCK = "screen_lock";
    String BACKGROUNT_LOCK = "backgrount_lock";
    String FREE_LOCK = "free_lock";
    String PIN_USE = "pin_use";
    String FIRST = "first";
    String AUTO_SYNC = "auto_sync";
    String USE_3G = "use_3g";
    String AUTO_TOAST = "auto_toast";
    String SYNC_TYPE = "sync_type";
    String SYNC = "sync";
    String WIFI_SYNC_TIME = "wifi_sync_time";
    String CLEAR_CLIP = "clear_clip";

    int NO_SYNC = 0;
    int CLOUD_SYNC = 1;
    int WIFI_SYNC = 2;
    /**
     * 不锁定
     */
    long NO_LOCK = -1;
    /**
     * 立即
     */
    long NOW_LOCK = 0;

    long THIRTY_SECONDS = 30 * 1000;
    /**
     * 1分钟
     */
    long ONE_MINUTES = 60 * 1000;
    long NINTY_SECONDS = 90 * 1000;
    /**
     * 2分钟
     */
    long TWO_MINUTES = 2 * 60 * 1000;
    /**
     * 3分钟
     */
    long THREE_MINUTES = 3 * 60 * 1000;
    /**
     * 4分钟
     */
    long FOUR_MINUTES = 4 * 60 * 1000;
    /**
     * 5分钟
     */
    long FIVE_MINUTES = 5 * 60 * 1000;
    /**
     * 10分钟
     */
    long TEN_MINUTES = 10 * 60 * 1000;
    /**
     * 15分钟
     */
    long FIFTEEN_MINUTES = 15 * 60 * 1000;
    /**
     * 30分钟
     */
    long THIRTY_MINUTES = 30 * 60 * 1000;
    /**
     * 60分钟
     */
    long SIXTY_MINUTES = 60 * 60 * 1000;

    /**
     * 跳转界面
     */
    String TO_LOCK = "to_lock";


    /**
     * 自定义意图
     */
    String MINTENT = "com.example.leohuang.password_manager.FREE";

    //跳转
    String FROM_WHERE = "from_where";
    int FROM_PIN = 1;
    String MODEL = "model";
    String TYPE = "type";

    //类型

    String IS_PASSWORD = "password";
    String IS_TEXT = "text";
    String IS_FILE = "file";
    String IS_URL = "url";
    String IS_RADIO = "radio";
    String IS_CHECKBOX = "checkbox";
    String IS_SELECT = "select";
    String IS_TEXTAREA = "textarea";
    String IS_EMAIL = "email";
    String IS_NUMBER = "number";
    String IS_DATE = "date";
    String IS_COLOR = "color";
    String IS_POSTCODE = "postcode";

    int NOT_CUSTOM = 0;
    int CUSTOM = 1;


    String FIELD_NAME = "field_name";
    String FIELD_TYPE = "field_type";
    String GUID = "guid";
    String TAG = "tag";

    String ADDRESS = "address";
    String SYNC_NAME = "sync_name";

    int CHOOSE_PASSWORD = 0;
    int CONFIRM_PASSWORD = 1;
    int TIP = 2;
    int GET_ALL_INFO = 0x1111;


    int PORT = 9000;
    String FIELD_ITEM = "item";
    String Label_ITEM = "label";
    String FIELD_IS = "item_is";


    /**
     * 日志表中的数据操作类型
     */

    int LOG_INSERT = 1;
    int LOG_UPDATE = 2;
    int LOG_DELETE = 3;
    /**
     * 表名
     */

    String T_FIELD_DATA = "t_field_data";
    String T_FILE = "t_file";
    String T_ITEM = "t_item";
    String T_LABEL = "t_label";
    String T_LABEL_RELATION = "t_label_relation";
    String T_REMARK = "t_remark";
    String ERROR_ITEM = "添加项目失败";
    String ERROR_FIELD = "添加字段失败";
    String ERROR_LABEL = "添加标签失败";
    String ERROR_LABEL_RELATION = "添加标签关系表失败";
    String SUCCESS = "添加成功";
    String ERROR_REMARK = "添加备注失败";

    String ERROR_UPDATE_ITEM = "更新项目失败";
    String ERROR_UPDATE_FIELD = "更新字段失败";
    String ERROR_UPDATE_LABEL = "更新标签失败";
    String ERROR_UPDATE_LABEL_RELATION = "更新标签关系表失败";
    String ERROR_UPDATE_REMARK = "更新备注失败";
    String UPDATE_SUCCESS = "更新成功";

    String DELETE_FAIL = "删除失败";
    String DELETE_SUCCESS = "删除成功";

    String INSERT_LABER_SUCCESS = "编辑标签成功";
    String INSERT_LABER_FAILD = "编辑标签失败";
    String EXIT_LABEL = "标签已存在";


    /**
     * 密码强度
     */

    int SECRET_LOW = 1;//低等级
    int SECRET_MIDDLE = 2;//中等级
    int SECRET_HIGH = 3;//高等级

    /**
     * 判断是编辑还是添加
     *
     */
    int Editor = 1;
    String ISADD = "编辑";
}
