package com.xdc.basic.skills.singleton.singleton2;

// 单例模式：懒汉，双重锁定
// http://www.blogjava.net/kenzhh/archive/2013/03/15/357824.html
public class Singleton
{
    // 注意必须添加volatile关键字， 原因见：http://freish.iteye.com/blog/1008304
    private static volatile Singleton instance = null;

    private Singleton()
    {
    }

    public static Singleton getInstance()
    {
        if (instance == null)
        {
            synchronized (Singleton.class)
            {
                if (instance == null)
                {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
