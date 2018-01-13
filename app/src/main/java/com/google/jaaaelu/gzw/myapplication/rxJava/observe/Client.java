package com.google.jaaaelu.gzw.myapplication.rxJava.observe;

/**
 * Created by Administrator on 2018/1/13.
 */

public class Client {

    public static void main(String[] ages) {
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
