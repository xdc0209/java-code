package com.xdc.basic.api.thread.threadlocal.demo2;

/**
 * 本例展示了ThreadLocal的基本用法： 一般在静态公共方法中使用，各个线程中访问的是不同的对象，达到了线程隔离的作用。 当然在现在定义局部变量的方式同样可以达到同样的效果，但是没有使用ThreadLocal灵活。
 * 比如：不同线程访问数据库要使用不同的数据库连接，Hibernate官方文档中HibernateUtil类就是使用的ThreadLocal实现的。
 * 可以想象，在数据库这种场景下，如果使用线程的局部变量，而不是使用静态公共方法+ThreadLocal实现，会是多么的麻烦。
 */
public class ThreadLocalTest
{
    public static void main(String[] args)
    {
        Judge.prepare();
        for (int i = 1; i <= 3; i++)
        {
            Player player = new Player(i);
            player.start();
        }
    }
}
