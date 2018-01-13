package com.google.jaaaelu.gzw.myapplication.rxJava;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.jaaaelu.gzw.myapplication.R;
import com.google.jaaaelu.gzw.myapplication.rxJava.observe.ConcreteObserver;
import com.google.jaaaelu.gzw.myapplication.rxJava.observe.ConcreteObserverable;

public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observer();
            }
        });
    }

    private void observer() {
        //  创建被观察者
        ConcreteObserverable co = new ConcreteObserverable();

        //  创建三个不同的观察者
        ConcreteObserver ca = new ConcreteObserver("A");
        ConcreteObserver cb = new ConcreteObserver("B");
        ConcreteObserver cc = new ConcreteObserver("C");

        //  将观察者注册到被观察者中
        co.registerObserver(ca);
        co.registerObserver(cb);
        co.registerObserver(cc);

        //  更新被观察者的中的数据，当数据更新后，会自动通知所有已注册的观察者
        co.setInfo(1, 36.0f);
    }
}
