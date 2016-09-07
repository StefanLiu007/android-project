package com.example.stefan.rxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stefan.rxjava.R;
import com.example.stefan.rxjava.entity.Subjects;
import com.example.stefan.rxjava.http.HttpMethord;
import com.example.stefan.rxjava.subscribers.ProgressSubcribers;
import com.example.stefan.rxjava.subscribers.ProgressSubcribersListen;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    TextView content;
    ProgressSubcribersListen<List<Subjects>> progressSubcribersListen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View view) {
        getMovie();
    }

    private void getMovie() {
        HttpMethord httpMethord = HttpMethord.getInstence();
//        MethordInMian<String> methordInMian = new MethordInMian<String>() {
//            @Override
//            public void upDateUi(String text) {
//                content.setText(text);
//            }
//        };
//        httpMethord.setMethordInMian(methordInMian);
//        httpMethord.getMovie();

        httpMethord.getMovie(new ProgressSubcribers<List<Subjects>>(progressSubcribersListen,MainActivity.this),0,10);
    }

    private void initView() {
        btn = (Button) findViewById(R.id.get);
        content = (TextView) findViewById(R.id.content);
        btn.setOnClickListener(this);
         progressSubcribersListen = new ProgressSubcribersListen<List<Subjects>>() {
            @Override
            public void onNext(List<Subjects> subjectses) {
                content.setText(subjectses.toString());
            }
        };
    }


}
