package com.xdc.basic.api.thread.lockfree.beanmanager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ConcurrentHashMap并没有实现Lock-Free，只是使用了分离锁的办法使得能够支持多个Writer并发。ConcurrentHashMap需要使用更多的内存。
 */
public class BeanManagerConcurrentMap
{
    private ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    public Object getBean(String key)
    {
        Object bean = map.get(key);
        if (bean == null)
        {
            Object newBean = createBean(key);
            Object oldBean = map.putIfAbsent(key, newBean);
            if (oldBean != null)
            {
                bean = oldBean;

                // 销毁bean，以免导致资源泄露。例如缓存对象是MessageProducer时，销毁对象要调用MessageProducer.close()关闭连接。
                destroyBean(newBean);
            }
            else
            {
                bean = newBean;
            }
        }
        return bean;
    }

    private Object createBean(String key)
    {
        return key;
    }

    private void destroyBean(Object bean)
    {
    }
}
