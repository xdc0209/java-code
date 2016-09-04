package com.xdc.basic.skills.singleton.singleton1;

// 单例模式：饿汉
// http://www.blogjava.net/kenzhh/archive/2013/03/15/357824.html
public class Singleton
{
    private static Singleton instance = new Singleton();

    private Singleton()
    {
    }

    public static Singleton getInstance()
    {
        return instance;
    }
}