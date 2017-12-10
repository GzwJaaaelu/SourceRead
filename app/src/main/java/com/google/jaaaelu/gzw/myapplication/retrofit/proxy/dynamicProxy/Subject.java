package com.google.jaaaelu.gzw.myapplication.retrofit.proxy.dynamicProxy;

import android.annotation.TargetApi;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/12/6.
 * 动态代理的接口
 */

public interface Subject {

    @GET("users/{user}/repos")
    void shopping(@Path("user") String user);
}
