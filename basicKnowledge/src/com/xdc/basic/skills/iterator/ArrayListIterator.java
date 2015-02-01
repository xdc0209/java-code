package com.xdc.basic.skills.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ArrayListIterator
{
    // 对容器ArrayList一边遍历，一边操作(增删)的时候，会碰到java.util.ConcurrentModificationException异常
    //
    // 官方文档中的说法是这样的：
    // This exception may be thrown by methods that have detected concurrent modification of an object when such modification is not permissible.
    // (翻译：concurrent是同时发生，大概意思是当一个object被同时修改的时候，而且该修改是不允许的，就会报这个异常)
    public static void main(String[] args)
    {
        // 摘自：http://www.cnblogs.com/alipayhutu/archive/2012/08/11/2634073.html
        // 总结：
        // 1. 一边遍历，一边删除: 
        //      ArrayList            foreach  报异常ConcurrentModificationException
        //      ArrayList            iterator 不会报错
        //      CopyOnWriteArrayList foreach  不会报错
        //      CopyOnWriteArrayList iterator 报异常UnsupportedOperationException
        // 2. 一边遍历，一边增加
        //      ArrayList            foreach  报异常ConcurrentModificationException
        //      ArrayList            iterator 报异常ConcurrentModificationException
        //      CopyOnWriteArrayList foreach  不会报错
        //      CopyOnWriteArrayList iterator 不会报错

        List<String> oss = new ArrayList<String>();
        oss.add("Windows 98");
        oss.add("Windows XP");
        oss.add("Windows 7");
        oss.add("Windows 10");
        oss.add("Linux");

        System.out.println(oss);

        System.out.println("foreach start.");
        for (String os : oss)
        {
            if (StringUtils.startsWithIgnoreCase(os, "Windows"))
            {
                System.out.println("Delete: " + os);
                oss.remove(os);
                oss.add(os);
            }
        }
        System.out.println("foreach end.");

        System.out.println("iterator start.");
        Iterator<String> it = oss.iterator();
        while (it.hasNext())
        {
            String os = it.next();
            if (StringUtils.startsWithIgnoreCase(os, "Windows"))
            {
                System.out.println("Delete: " + os);
                it.remove();
                oss.add(os);
            }
        }
        System.out.println("iterator end.");

        System.out.println(oss);
    }
}
