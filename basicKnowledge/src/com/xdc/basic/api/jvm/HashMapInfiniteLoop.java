package com.xdc.basic.api.jvm;

import java.util.HashMap;

// 摘自：http://blog.csdn.net/xuefeng0707/article/details/40797085
//
// HashMap多线程死循环问题
//
// HashMap不是线程安全的，在被多线程共享操作时，有概率出现死循环，导致单核cpu占用100%。这个概率比较低，多次执行代码不一定能复现，但可以通过单步调试构造出来。
// 问题原因是多线程同时put时，如果同时触发了rehash操作，会导致HashMap中的链表中出现循环节点，进而使得后面get的时候，会死循环。
// 多线程场景下一定要使用线程安全的ConcurrentHashMap，不能使用HashMap。
public class HashMapInfiniteLoop
{
    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(2, 0.75f);

    public static void main(String[] args)
    {
        map.put(5, 55);

        new Thread("Thread1")
        {
            @Override
            public void run()
            {
                map.put(7, 77);
                System.out.println(map);
            };
        }.start();

        new Thread("Thread2")
        {
            @Override
            public void run()
            {
                map.put(3, 33);
                System.out.println(map);
            };
        }.start();
    }
}