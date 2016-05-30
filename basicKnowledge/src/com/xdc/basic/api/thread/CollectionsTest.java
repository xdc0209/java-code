package com.xdc.basic.api.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsTest
{
    // 在并发场景下，为了保证线程安全，需要进行同步Collections提供了方法返回一个线程安全的代理，进而保证现场安全。
    // 推荐使用专门的并发类CopyOnWriteArrayList、ConcurrentHashMap，性能更高。
    //
    // CopyOnWriteArrayList 如果需要频繁对CopyOnWriteArrayList进行修改，而很少读取的话，那么会严重降低系统性能。在少量修改，频繁读取的场景下，有很好的并发性能。
    // ConcurrentHashMap 不允许null key和null value。

    private final static List<String>        list = Collections.synchronizedList(new ArrayList<String>());
    private final static Map<String, String> map  = Collections.synchronizedMap(new HashMap<String, String>());

    public static void main(String[] args)
    {
        list.add("a");
        list.add("b");
        list.add("c");

        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
    }
}
