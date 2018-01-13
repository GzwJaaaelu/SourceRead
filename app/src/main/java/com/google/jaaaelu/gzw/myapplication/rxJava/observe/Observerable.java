package com.google.jaaaelu.gzw.myapplication.rxJava.observe;

/**
 * Created by Administrator on 2018/1/13.
 */

public interface Observerable {

    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
