package com.google.jaaaelu.gzw.myapplication.retrofit.proxy.staticProxy;

/**
 * Created by Administrator on 2017/12/6.
 * 代理类,代理对象角色
 */

public class ProxyObject extends AbstractObject {
    private RealObject realObject;

    public ProxyObject(RealObject realObject) {
        this.realObject = realObject;
    }

    @Override
    public void operation() {
        //  这里面可以加更多逻辑
        //  代理对象将客户端的调用委派给目标对象
        //  而在调用目标对象之前或者之后都可以执行某些自己的操作
        System.out.println("do something before real operation...");
        if (realObject == null) {
            realObject = new RealObject();
        }
        realObject.operation();
        System.out.println("do something after real operation...");
    }
}
