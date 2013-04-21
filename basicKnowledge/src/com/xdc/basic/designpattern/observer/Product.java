package com.xdc.basic.designpattern.observer;

import java.util.Observable;

// 产品类 可供Jsp直接使用UseBean调用 该类主要执行产品数据库插入 更新
public class Product extends Observable
{
    private String name;
    private float  price;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        // 设置变化点 
        setChanged();
        // 变更会通过循环通知给所有观察者，观察者通过判断类型过滤掉他不关心的变更
        notifyObservers(name);
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
        // 设置变化点
        setChanged();
        notifyObservers(new Float(price));
    }
}