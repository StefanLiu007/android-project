package com.example.leohuang.password_manager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.adapter.ChooseItemOneAdapter;
import com.example.leohuang.password_manager.adapter.ChooseItemTwoAdapter;
import com.example.leohuang.password_manager.adapter.TimerChooseAdapter;
import com.example.leohuang.password_manager.algorithm.PasswordGenerate;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Field;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Notification;
import com.example.leohuang.password_manager.bean.Remark;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.events.ChangeItemEvent;
import com.example.leohuang.password_manager.events.LockEvent;
import com.example.leohuang.password_manager.events.ResetTimeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo.huang on 16/3/9.
 */
public class DialogUtils {
    private final static String TAG = "DialogUtils";

    private static String date = "";


    private static boolean hasLargeChar = true;//使用大写字母
    private static boolean hasChar = true;//使用小写字母
    private static boolean hasNumber = true;//使用数字
    private static boolean hasSpecial = true;//使用特殊字符
    private static int secretLen;

    /**
     * 显示日期选择器
     *
     * @param context
     * @param editText
     */
    public static void showAndGetDataTime(Context context, final EditText editText) {
        date = "";
        View view = LayoutInflater.from(context).inflate(R.layout.data_time_picker_item, null, false);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                Log.i(TAG, "date change " + date);
            }
        });
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.i(TAG, "确定：" + date);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date = "";
                        dialog.dismiss();

                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        editText.setText(date);
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * 显示备注输入
     *
     * @param context
     */
    public static void showRemark(Context context, final Item item, final TextView tvShow) {
        View view = LayoutInflater.from(context).inflate(R.layout.remark_layout, null, false);
        Button btnCancel, btnConfirm;
        final EditText etRemark;
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        etRemark = (EditText) view.findViewById(R.id.et_remark);
        if (item.remark != null) {
            if (item.remark.value != null) {
                etRemark.setText(item.remark.value);
            }
        }
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etRemark.getText().toString().trim();
                if (item.remark == null) {
                    item.remark = new Remark();
                    item.remark.id = GuidBuilder.guidGenerator();
                    item.remark.item_id = item.guid;
                }
                item.remark.value = message;
                tvShow.setText(message);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 项目选择
     *
     * @param context
     * @param show
     * @param item
     * @param title
     */
    public static void showChoose(Context context, final EditText show, final String[] item, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_dialog_list_layout, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ListView lvChoose = (ListView) view.findViewById(R.id.lv_choose);
        tvTitle.setText(title);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        ChooseItemOneAdapter adapter = new ChooseItemOneAdapter(context, item);
        lvChoose.setAdapter(adapter);
        dialog.show();
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show.setText(item[position]);
                dialog.dismiss();
            }
        });

    }

    /**
     * 显示选择
     *
     * @param context
     * @param show
     * @param item
     * @param res
     * @param title
     */
    public static void showChoose(Context context, final EditText show, final String[] item, int[] res, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_dialog_list_layout, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ListView lvChoose = (ListView) view.findViewById(R.id.lv_choose);
        tvTitle.setText(title);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        ChooseItemTwoAdapter adapter = new ChooseItemTwoAdapter(context, item, res);
        lvChoose.setAdapter(adapter);
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show.setText(item[position]);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 显示锁屏时间选择
     *
     * @param context
     * @param freeIndex
     * @param show
     */
    public static void showLock(final Context context, int freeIndex, final TextView show, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_choose_layout, null, false);
        ListView lvChoose = (ListView) view.findViewById(R.id.lv_choose);
        final String[] times = context.getResources().getStringArray(R.array.lock);
        final long[] time = {Setting.ONE_MINUTES, Setting.TWO_MINUTES, Setting.THREE_MINUTES, Setting.FOUR_MINUTES, Setting.FIVE_MINUTES,
                Setting.TEN_MINUTES, Setting.FIVE_MINUTES, Setting.THREE_MINUTES, Setting.SIXTY_MINUTES, Setting.NO_LOCK};
        final Map<Integer, Boolean> chooses = new HashMap<>();
        chooses.put(freeIndex, true);
        final TimerChooseAdapter adapter = new TimerChooseAdapter(context, times, chooses);
        lvChoose.setAdapter(adapter);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        show.setText("处于开启及现状状态1分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 1:
                        show.setText("处于开启及现状状态2分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 2:
                        show.setText("处于开启及现状状态3分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 3:
                        show.setText("处于开启及现状状态4分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 4:
                        show.setText("处于开启及现状状态5分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 5:
                        show.setText("处于开启及现状状态10分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 6:
                        show.setText("处于开启及现状状态15分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 7:
                        show.setText("处于开启及现状状态30分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 8:
                        show.setText("处于开启及现状状态60分钟之后");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    case 9:
                        show.setText("从不");
//                        PrefUtils.setFreeTime(context, time[position]);
//                        EventBus.getDefault().post(new LockEvent(time[position]));
//                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
                PrefUtils.setFreeTime(context, time[position]);
                EventBus.getDefault().post(new LockEvent(time[position]));
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 密码生成
     *
     * @param context
     * @param editText
     */
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Notification notification = new Notification();
            EventBus.getDefault().post(notification);
        }
    };

    public static void secretGeneratorTwo(final Context context, final EditText editText, final boolean flag, final MyApplication myApplication) {
        String secret = "";
        hasLargeChar = true;//使用大写字母
        hasChar = true;//使用小写字母
        hasNumber = true;//使用数字
        hasSpecial = true;//使用特殊字符
        secretLen = 10;
        View view = LayoutInflater.from(context).inflate(R.layout.creat_secret_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view).create();

        final TextView tvShowSecret = (TextView) view.findViewById(R.id.show_secret);//显示密码
        final TextView tvSecretLength = (TextView) view.findViewById(R.id.strenth);//长度
        final CheckBox cbLargeCharacter = (CheckBox) view.findViewById(R.id.checkbox_large_character);//是否使用大写字母
        final CheckBox cbCharacter = (CheckBox) view.findViewById(R.id.checkbox_character);//是否使用小写字母
        final CheckBox cbNumber = (CheckBox) view.findViewById(R.id.checkbox_number);//是否使用数字
        final CheckBox cbSpecial = (CheckBox) view.findViewById(R.id.checkbox_symbol);//是否使用特殊字符
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar_secret_length);//长度
        final TextView tvSecretStrength = (TextView) view.findViewById(R.id.tv_secret_strength);
        TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
        final TextView tvSave = (TextView) view.findViewById(R.id.save);

        if (seekBar != null) {
            seekBar.setMax(26);
        }
        if (tvSecretLength != null)
            tvSecretLength.setText("长度" + secretLen);
        if (tvShowSecret != null)
            tvShowSecret.setSingleLine(true);

        seekBar.setProgress(secretLen - 6 - 1);
        secret = PasswordGenerate.generateSecret(hasLargeChar, hasChar, hasNumber, hasNumber, 10);
        showLevel(secret, tvSecretStrength);
        tvShowSecret.setText(secret);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSecretLength.setText("长度" + (progress + 6));
                secretLen = progress + 6;
                fixSecret(tvSecretStrength, tvShowSecret, hasLargeChar, hasChar, hasNumber, hasSpecial, secretLen);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cbLargeCharacter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasLargeChar = isChecked;
                fixSecret(tvSecretStrength, tvShowSecret, hasLargeChar, hasChar, hasNumber, hasSpecial, secretLen);
            }
        });

        cbCharacter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasChar = isChecked;
                fixSecret(tvSecretStrength, tvShowSecret, hasLargeChar, hasChar, hasNumber, hasSpecial, secretLen);
            }
        });

        cbNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasNumber = isChecked;
                fixSecret(tvSecretStrength, tvShowSecret, hasLargeChar, hasChar, hasNumber, hasSpecial, secretLen);
            }
        });

        cbSpecial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasSpecial = isChecked;
                fixSecret(tvSecretStrength, tvShowSecret, hasLargeChar, hasChar, hasNumber, hasSpecial, secretLen);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    if (tvSave != null && editText != null) {
                        String text = tvShowSecret.getText().toString().trim();
                        editText.setText(text);
                    } else {
                    }
                } else {
                    final Item item = new Item();
                    item.guid = GuidBuilder.guidGenerator();
                    item.name = "密码";
                    item.icon = "type_password";
                    String time = DateUtils.getFormatDate(System.currentTimeMillis());
                    item.create_time = time;
                    item.modify_time = time;
                    item.access_time = time;
                    item.access_count = 1;
                    item.type_id = 1;
                    Field field = new Field();
                    field.guid = GuidBuilder.guidGenerator();
                    field.name = "密码";
                    field.value = tvShowSecret.getText().toString().getBytes();
                    field.type = "password";
                    field.custom = Setting.NOT_CUSTOM;
                    field.index = 1;
                    field.item_id = item.guid;
                    item.fields = new ArrayList<Field>();
                    item.fields.add(field);//添加
                    myApplication.items.add(item);
                    EventBus.getDefault().post(new ChangeItemEvent(item));
                    myApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.insertItem(item);
                            Message message = Message.obtain();
                            mHandler.sendMessage(message);

                        }
                    });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 展示等级
     *
     * @param secret
     * @param tvSecretStrength
     */
    private static void showLevel(String secret, TextView tvSecretStrength) {
        switch (SecretUtils.getLevel(secret)) {
            case Setting.SECRET_LOW:
                tvSecretStrength.setText("低");
                break;
            case Setting.SECRET_MIDDLE:
                tvSecretStrength.setText("中");
                break;
            case Setting.SECRET_HIGH:
                tvSecretStrength.setText("高");
                break;
            default:
                break;
        }
    }

    /**
     * 进行判断
     *
     * @param tvShowSecret
     * @param hasLargeChar
     * @param hasChar
     * @param hasNumber
     * @param hasSpecial
     * @param secretLen
     */
    private static void fixSecret(TextView tvSecretStrength, TextView tvShowSecret, boolean hasLargeChar, boolean hasChar, boolean hasNumber, boolean hasSpecial, int secretLen) {
        if (!DialogUtils.hasLargeChar && !DialogUtils.hasChar && !DialogUtils.hasNumber && !DialogUtils.hasSpecial) {
            return;
        }
        String secret = PasswordGenerate.generateSecret(DialogUtils.hasLargeChar, DialogUtils.hasChar, DialogUtils.hasNumber, DialogUtils.hasSpecial, DialogUtils.secretLen);
        tvShowSecret.setText(secret);
        showLevel(secret, tvSecretStrength);
    }

    /**
     * 性别选择
     *
     * @param context
     * @param editText
     */
    public static void sexChoose(Context context, final EditText editText) {
        View view = LayoutInflater.from(context).inflate(R.layout.sex_layout, null);
        final RadioButton male = (RadioButton) view.findViewById(R.id.radio0);
        final RadioButton fale = (RadioButton) view.findViewById(R.id.radio1);
        Button commit = (Button) view.findViewById(R.id.button1);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.show();
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male.isChecked()) {
                    editText.setText(male.getText().toString());
                    dialog.dismiss();
                } else if (fale.isChecked()) {
                    editText.setText(fale.getText().toString());
                    dialog.dismiss();
                }
            }
        });


    }

    public static void showClear(final Context context, int clearIndex, final TextView show, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_choose_layout, null, false);
        ListView lvChoose = (ListView) view.findViewById(R.id.lv_choose);
        final String[] times = context.getResources().getStringArray(R.array.clear_clip);
        final long[] time = {Setting.THIRTY_SECONDS, Setting.ONE_MINUTES, Setting.NINTY_SECONDS, Setting.TWO_MINUTES, Setting.THREE_MINUTES, Setting.NO_LOCK};
        final Map<Integer, Boolean> chooses = new HashMap<>();
        chooses.put(clearIndex, true);
        final TimerChooseAdapter adapter = new TimerChooseAdapter(context, times, chooses);
        lvChoose.setAdapter(adapter);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        show.setText("将值拷贝到剪贴板30秒后");
                        break;
                    case 1:
                        show.setText("将值拷贝到剪贴板60秒后");
                        break;
                    case 2:
                        show.setText("将值拷贝到剪贴板90秒后");
                        break;
                    case 3:
                        show.setText("将值拷贝到剪贴板两分钟后");
                        break;
                    case 4:
                        show.setText("将值拷贝到剪贴板三分钟后");
                        break;
                    case 5:
                        show.setText("从不");

                        break;
                    default:
                        break;
                }
                PrefUtils.setClearClipTime(context, time[position]);
                EventBus.getDefault().post(new ResetTimeEvent(time[position]));
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
