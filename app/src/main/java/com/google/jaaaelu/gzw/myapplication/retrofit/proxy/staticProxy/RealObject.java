package com.google.jaaaelu.gzw.myapplication.retrofit.proxy.staticProxy;

/**
 * Created by Administrator on 2017/12/6.
 * 目标的实现类，也就是目标对象角色
 */

public class RealObject extends AbstractObject {

    @Override
    public void operation() {
        System.out.println("do operation...");
    }
}
