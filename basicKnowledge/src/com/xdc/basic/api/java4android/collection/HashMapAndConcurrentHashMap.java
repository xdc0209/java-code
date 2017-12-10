package com.xdc.basic.api.java4android.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HashMapAndConcurrentHashMap
{
    public static void main(String[] args)
    {
        // String ConcurrentHashMap.putIfAbsent(String key, String value)
        // 并发安全性：并发安全
        // 处理逻辑：存在旧值则不会覆盖，不存在则添加
        // 返回值：存在旧值则返回旧值，不存在则返回空
        // 用法：com.xdc.basic.api.thread.lockfree.beanmanager.BeanManagerConcurrentMap
        testConcurrentMap1();

        // String ConcurrentHashMap.put(String key, String value)
        // 并发安全性：并发安全
        // 处理逻辑：存在旧值则会覆盖，不存在则添加
        // 返回值：存在旧值则返回旧值，不存在则返回空
        testConcurrentMap2();

        // String HashMap.put(String key, String value)
        // 并发安全性：非并发安全，多线程并发修改可能导致HashMap死循环或者导致ConcurrentModificationException异常
        // 处理逻辑：存在旧值则会覆盖，不存在则添加
        // 返回值：存在旧值则返回旧值，不存在则返回空
        // 参考1：http://blog.sina.com.cn/s/blog_465bcfba01000ds7.html
        // 参考2：com.xdc.basic.api.jvm.test.stub.HashMapInfiniteLoopTestStub
        testMap();
    }

    private static void testConcurrentMap1()
    {
        ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
        String s1 = concurrentMap.putIfAbsent("key", "value111");
        System.out.println("111-" + s1);
        System.out.println("111-" + concurrentMap.get("key"));

        String s2 = concurrentMap.putIfAbsent("key", "value222");
        System.out.println("222-" + s2);
        System.out.println("222-" + concurrentMap.get("key"));

        String s3 = concurrentMap.putIfAbsent("key", "value333");
        System.out.println("333-" + s3);
        System.out.println("333-" + concurrentMap.get("key"));

        System.out.println();
    }

    private static void testConcurrentMap2()
    {
        ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
        String s1 = concurrentMap.put("key", "value111");
        System.out.println("111-" + s1);
        System.out.println("111-" + concurrentMap.get("key"));

        String s2 = concurrentMap.put("key", "value222");
        System.out.println("222-" + s2);
        System.out.println("222-" + concurrentMap.get("key"));

        String s3 = concurrentMap.put("key", "value333");
        System.out.println("333-" + s3);
        System.out.println("333-" + concurrentMap.get("key"));

        System.out.println();
    }

    private static void testMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        String s1 = map.put("key", "value111");
        System.out.println("111-" + s1);
        System.out.println("111-" + map.get("key"));

        String s2 = map.put("key", "value222");
        System.out.println("222-" + s2);
        System.out.println("222-" + map.get("key"));

        String s3 = map.put("key", "value333");
        System.out.println("333-" + s3);
        System.out.println("333-" + map.get("key"));

        System.out.println();
    }
}
