package com.google.jaaaelu.gzw.myapplication.retrofit.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/12/6.
 *
 * 每个动态代理都必须要实现 InvocationHandler
 * InvocationHandler 可以理解成是代理对象调用程序时所一定要实现的接口，当我们通过代理对象调用方法的时
 * 候，那么这个方法就会把它指派到它调用处的 InvocationHandler 的 invoke(...) 上
 */

public class Proxy implements InvocationHandler {
    private Object target;

    public Proxy(Subject target) {
        this.target = target;
    }


    @Override
    //  proxy 指代我们所代理的真实对象，可以理解为真实对象的代理，method 表示真实对象的某个方法，
    //  args 表示真实对象的某个方法的所有参数
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy: " + proxy.getClass().getName());
        System.out.println("before...");
        System.out.println("method.getAnnotations()..." + Arrays.toString(method.getAnnotations()));
        System.out.println("method.getGenericParameterTypes()..." + Arrays.toString(method.getGenericParameterTypes()));
        System.out.println("method.getParameterAnnotations()..." + Arrays.toString(method.getParameterAnnotations()));

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            for (int j = 0; j < method.getParameterAnnotations()[i].length; j++) {
                System.out.println("ParameterAnnotations..." + method.getParameterAnnotations()[i][j].toString());
                System.out.println("ParameterAnnotations..." + method.getParameterAnnotations()[i][j].annotationType());
            }
        }

        System.out.println("method - args..." + Arrays.toString(args));
        method.invoke(target, args);
        System.out.println("after...");
        return null;
    }
}
