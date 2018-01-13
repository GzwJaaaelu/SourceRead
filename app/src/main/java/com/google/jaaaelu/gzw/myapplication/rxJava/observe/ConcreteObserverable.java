package com.google.jaaaelu.gzw.myapplication.rxJava.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/13.
 */

public class ConcreteObserverable implements Observerable {
    private List<Observer> mObservers;
    private int edition;
    private float cost;

    public ConcreteObserverable() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        mObservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int index = mObservers.indexOf(o);
        if (index >= 0) {
            mObservers.remove(index);
        }
    }

    @Override
    public void notifyObserver() {
        for (int i = 0; i < mObservers.size(); i++) {
            Observer observer = mObservers.get(i);
            observer.update(edition, cost);
        }
    }

    public void setInfo(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        notifyObserver();
    }
}
