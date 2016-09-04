package com.xdc.basic.skills.singleton.singleton3;

// 单例模式：懒汉，静态内部类
// http://www.blogjava.net/kenzhh/archive/2013/03/15/357824.html
public class Singleton
{
    private static class SingletonHolder
    {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton()
    {
    }

    public static final Singleton getInstance()
    {
        return SingletonHolder.INSTANCE;
    }
}