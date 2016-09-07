package com.example.stefan.hellondk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.mylibrary.NDKString;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void hello(View view){
        Button button = (Button) view;
        button.setText(NDKString.NDKFromC());
        SurfaceView
    }
}
