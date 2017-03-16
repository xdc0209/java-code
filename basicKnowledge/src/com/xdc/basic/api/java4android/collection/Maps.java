package com.xdc.basic.api.java4android.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 改自：http://blog.csdn.net/limin2928/article/details/8079446
 */
public class Maps
{
    // Java为数据结构中的映射定义了一个接口java.util.Map，它有四个实现类，分别是HashMap、HashTable、LinkedHashMap和TreeMap。本节实例主要介绍这4中实例的用法和区别。
    //
    // 关键技术剖析：
    // Map用于存储键值对，根据键得到值，因此不允许键重复，值可以重复。
    // （1）HashMap是一个最常用的Map，它根据键的hashCode值存储数据，根据键可以直接获取它的值，具有很快的访问速度。HashMap最多只允许一条记录的键为null，允许多条记录的值为null。HashMap不支持线程的同步，即任一时刻可以有多个线程同时写HashMap，可能会导致数据的不一致。如果需要同步，可以用Collections.synchronizedMap(HashMap map)方法使HashMap具有同步的能力。
    // （2）Hashtable与HashMap类似，不同的是：它不允许记录的键或者值为空；它支持线程的同步，即任一时刻只有一个线程能写Hashtable，然而，这也导致了Hashtable在写入时会比较慢。
    // （3）LinkedHashMap保存了记录的插入顺序，在用Iteraor遍历LinkedHashMap时，先得到的记录肯定是先插入的。在遍历的时候会比HashMap慢。有HashMap的全部特性。
    // （4）TreeMap能够把它保存的记录根据键排序，默认是按升序排序，也可以指定排序的比较器。当用Iteraor遍历TreeMap时，得到的记录是排过序的。TreeMap的键不能为空。
    //
    // 另外还有一个不常用的Map，这里简单介绍下： WeakHashMap是一种改进的HashMap，它对key实行“弱引用”，如果一个key不再被外部所引用，那么该key可以被GC回收。

    public static void init(Map<String, String> map)
    {
        if (map == null)
        {
            return;
        }

        for (int i = 5; i > 0; i--)
        {
            String key = String.valueOf(i);
            map.put(key, key);
            // Map中的键是不重复的，如果插入两个键值一样的记录，那么后插入的记录会覆盖先插入的记录
            map.put(key, key + key);
        }
    }

    public static void output(Map<String, String> map)
    {
        if (map == null)
        {
            return;
        }

        // 使用迭代器遍历Map的键，根据键取值
        Iterator<String> kit = map.keySet().iterator();
        while (kit.hasNext())
        {
            String key = kit.next();
            String value = map.get(key);
            System.out.println("key: " + key + "; value: " + value);
        }

        // // 或者使用迭代器遍历Map的记录Map.Entry
        // Iterator<Entry<String, String>> eit = map.entrySet().iterator();
        // while (eit.hasNext())
        // {
        // // 一个Map.Entry代表一条记录
        // Entry<String, String> entry = eit.next();
        // String key = entry.getKey();
        // String value = entry.getValue();
        // System.out.println("key: " + key + "; value: " + value);
        // }
    }

    public static void testHashMap()
    {
        Map<String, String> hashMap = new HashMap<String, String>();
        init(hashMap);

        // HashMap的键可以为null
        hashMap.put(null, "key is null");
        hashMap.put(null, "key is null 2");

        // HashMap的值可以为null
        hashMap.put("value is null", null);
        hashMap.put("value is null 2", null);

        output(hashMap);
    }

    public static void testHashtable()
    {
        Map<String, String> hashtable = new Hashtable<String, String>();
        init(hashtable);

        // // Hashtable的键不能为null
        // hashtable.put(null, "key is null");
        // hashtable.put(null, "key is null 2");

        // // Hashtable的值不能为null
        // hashtable.put("value is null", null);
        // hashtable.put("value is null 2", null);

        output(hashtable);
    }

    public static void testLinkedHashMap()
    {
        Map<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        init(linkedHashMap);

        // LinkedHashMap的键可以为null
        linkedHashMap.put(null, "key is null");
        linkedHashMap.put(null, "key is null 2");

        // LinkedHashMap的值可以为null
        linkedHashMap.put("value is null", null);
        linkedHashMap.put("value is null 2", null);

        output(linkedHashMap);
    }

    public static void testTreeMap()
    {
        Map<String, String> treeMap = new TreeMap<String, String>();
        init(treeMap);

        // // TreeMap的键不能为null
        // treeMap.put(null, "key is null");

        // TreeMap的值可以为null
        treeMap.put("value is null", null);
        treeMap.put("value is null 2", null);

        output(treeMap);
    }

    public static void main(String[] args)
    {
        System.out.println("采用HashMap --非线程安全，较快");
        Maps.testHashMap();
        System.out.println();

        System.out.println("采用Hashtable --线程安全，较慢");
        Maps.testHashtable();
        System.out.println();

        System.out.println("采用LinkedHashMap --保持放入顺序");
        Maps.testLinkedHashMap();
        System.out.println();

        System.out.println("采用TreeMap --按key排序");
        Maps.testTreeMap();
        System.out.println();
    }
}
