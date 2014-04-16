package com.xdc.basic.api.thread.lockfree.beanmanager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ConcurrentHashMap并没有实现Lock-Free，只是使用了分离锁的办法使得能够支持多个Writer并发。
 * ConcurrentHashMap需要使用更多的内存。
 * 
 * @author xdc
 * 
 */
public class BeanManagerConcurrentMap
{
    private ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    public Object getBean(String key)
    {
        Object bean = map.get(key);
        if (bean == null)
        {
            map.putIfAbsent(key, createBean(key));
            bean = map.get(key);
        }
        return bean;
    }

    private Object createBean(String key)
    {
        return key;
    }
}
