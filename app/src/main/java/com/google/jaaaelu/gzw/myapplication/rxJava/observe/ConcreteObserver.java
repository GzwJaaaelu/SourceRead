package com.google.jaaaelu.gzw.myapplication.rxJava.observe;

/**
 * Created by Administrator on 2018/1/13.
 */

public class ConcreteObserver implements Observer {
    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(int edition, float cost) {
        buy(edition, cost);
    }

    private void buy(int edition, float cost) {
        System.out.println(name + " 购买了第 " + edition + " 期的杂志，花费了 " + cost + " 元");
    }
}
