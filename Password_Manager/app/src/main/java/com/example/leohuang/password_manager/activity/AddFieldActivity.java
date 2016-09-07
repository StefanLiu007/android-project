package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.Arrays;
import java.util.List;

/**
 * 添加字段
 */
public class AddFieldActivity extends BaseActivity {
    private final String TAG = "AddFieldActivity";

    private Intent mIntent;
    //    private TextView mTvSave, mTvCancel;
//    private Drawable mLeftDrawable;
    private EditText mEtFieldName;
    private Spinner mSpinnerFiledType;
    private ListView mLvExampleFields;
    private List<String> examples;

    private String type;
    private String simpleType;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field);
        AppManager.getAppManager().addActivity(this);
        mIntent = getIntent();
        assignViews();
        initEvents();
    }

    @Override
    protected void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mLvExampleFields = (ListView) findViewById(R.id.lv_example_fields);
        View view = LayoutInflater.from(this).inflate(R.layout.lv_example_header_layout, null, false);
        mEtFieldName = (EditText) view.findViewById(R.id.et_field_name);
        mSpinnerFiledType = (Spinner) view.findViewById(R.id.spinner_field);
        mLvExampleFields.addHeaderView(view);
        examples = Arrays.asList(getResources().getStringArray(R.array.example_field));
        mLvExampleFields.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
    }

    @Override
    protected void initEvents() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_cancel:
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtFieldName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddFieldActivity.this, "字段名称为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mIntent.putExtra(Setting.FIELD_NAME, name);
                mIntent.putExtra(Setting.FIELD_TYPE, type);
                Log.i(TAG, "name: " + name + "   type:" + type);
                setResult(RESULT_OK, mIntent);
                finish();

            }
        });
        mLvExampleFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = examples.get(position - 1);
                switch (position) {
                    case 1:
                        simpleType = Setting.IS_TEXT;
                        break;
                    case 2:
                        simpleType = Setting.IS_PASSWORD;
                        break;
                    case 3:
                        simpleType = Setting.IS_URL;
                        break;
                    case 4:
                        simpleType = Setting.IS_TEXT;
                        break;
                    case 5:
                        simpleType = Setting.IS_NUMBER;
                        break;
                    case 6:
                        simpleType = Setting.IS_NUMBER;
                        break;
                    case 7:
                        simpleType = Setting.IS_EMAIL;
                        break;
                    default:
                        break;
                }
                mIntent.putExtra(Setting.FIELD_NAME, name);
                mIntent.putExtra(Setting.FIELD_TYPE, simpleType);
                Log.i(TAG, "name: " + name + "   type:" + simpleType);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
        mSpinnerFiledType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        type = Setting.IS_PASSWORD;
                        break;
                    case 1:
                        type = Setting.IS_TEXT;
                        break;
                    case 2:
                        type = Setting.IS_FILE;
                        break;
                    case 3:
                        type = Setting.IS_URL;
                        break;
                    case 4:
                        type = Setting.IS_TEXTAREA;
                        break;
                    case 5:
                        type = Setting.IS_EMAIL;
                    case 6:
                        type = Setting.IS_NUMBER;
                        break;
                    case 7:
                        type = Setting.IS_DATE;
                        break;
                    case 8:
                        type = Setting.IS_COLOR;
                        break;
                    case 9:
                        type = Setting.IS_POSTCODE;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.save_cancel_memu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
