package com.example.leohuang.password_manager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.AppManager;
import com.example.leohuang.password_manager.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppManager.getAppManager().addActivity(this);
        if (PrefUtils.getFirst(this)) {
            Intent intent = new Intent(this, FirstLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, InputMainKeyActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
