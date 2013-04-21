package com.xdc.basic.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

// 观察者NameObserver主要用来对产品名称(name)进行观察的
public class NameObserver implements Observer
{
    private String name = null;

    @Override
    public void update(Observable obj, Object arg)
    {
        if (arg instanceof String)
        {
            name = (String) arg;
            // 产品名称改变值在name中
            System.out.println("NameObserver: name changet to " + name);
        }
    }
}