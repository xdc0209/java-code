package com.xdc.basic.skills;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断类的父子关系
 * 
 * @author xdc
 * 
 */
public class Instanceof
{
    public static void main(String[] args)
    {
        // instanceof 和 class.isInstance() 功能完全相同：判断对象是否是一个接口的实例，或者是这个类及其子类的实例。
        // 判断list是否是List的实例
        ArrayList<String> list = new ArrayList<String>();
        if (list instanceof List)
        {
            System.out.println("list instanceof List");
        }
        // 判断list是否是List的实例
        if (List.class.isInstance(list))
        {
            System.out.println("List.class.isInstance(list)");
        }

        // class.isAssignableFrom()是针对Class对象，判断Class对象之间是否有联系。
        // 如果入参的Class对象来自这个接口，或者就是这个类本身，或者是其子类，返回true, 否则返回false
        // 入参ArrayList.class是一个Class对象，判断该Class代表的类，是否实现，继承，等同 List.
        if (List.class.isAssignableFrom(ArrayList.class))
        {
            System.out.println("List.class.isAssignableFrom(list.getClass())");
        }
    }
}
