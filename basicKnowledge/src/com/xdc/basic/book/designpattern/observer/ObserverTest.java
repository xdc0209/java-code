package com.xdc.basic.book.designpattern.observer;

public class ObserverTest
{
    public static void main(String[] args)
    {
        Product product = new Product();

        NameObserver nameobs = new NameObserver();
        PriceObserver priceobs = new PriceObserver();

        // 加入观察者
        product.addObserver(nameobs);
        product.addObserver(priceobs);

        product.setName("橘子红了");
        product.setPrice(9.22f);
    }
}
