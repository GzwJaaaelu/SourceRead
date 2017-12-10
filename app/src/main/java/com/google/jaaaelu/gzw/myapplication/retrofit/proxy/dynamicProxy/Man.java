package com.google.jaaaelu.gzw.myapplication.retrofit.proxy.dynamicProxy;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Man implements Subject {

    @Override
    public void shopping(String user) {
        System.out.println(user + " 要去买东西...");
    }
}
