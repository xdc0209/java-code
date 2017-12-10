package com.xdc.basic.book.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

// 观察者PriceObserver主要用来对产品价格(price)进行观察的
public class PriceObserver implements Observer
{
    private float price = 0;

    public void update(Observable obj, Object arg)
    {
        if (arg instanceof Float)
        {
            price = ((Float) arg).floatValue();
            // 产品价格改变值在price中
            System.out.println("PriceObserver: price changet to " + price);
        }
    }
}
