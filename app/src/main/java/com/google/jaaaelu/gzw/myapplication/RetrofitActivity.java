package com.google.jaaaelu.gzw.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RetrofitActivity extends AppCompatActivity {
    //  Retrofit 并不是网络请求框架，只是对网络请求框架的封装

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }
}
