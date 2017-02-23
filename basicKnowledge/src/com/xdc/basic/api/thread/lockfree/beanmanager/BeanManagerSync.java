package com.xdc.basic.api.thread.lockfree.beanmanager;

import java.util.HashMap;
import java.util.Map;

public class BeanManagerSync
{
    private Map<String, Object> map = new HashMap<String, Object>();

    public Object getBean(String key)
    {
        synchronized (map)
        {
            Object bean = map.get(key);
            if (bean == null)
            {
                map.put(key, createBean(key));
                bean = map.get(key);
            }
            return bean;
        }
    }

    private Object createBean(String key)
    {
        return key;
    }
}
