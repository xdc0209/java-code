package com.xdc.basic.java4android.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetTest
{

	public static void main(String[] args)
	{

		// 继承关系： Iterator(接口：迭代器) <-- Collection(接口：类集) <-- Set(接口：集合)
		// <--HashSet(类：哈希集合)
		Set<String> set = new HashSet<String>();

		set.add("a");
		set.add("b");
		set.add("c");
		set.add("d");
		set.add("c");

		// 调用Set对象的Iterator方法，会生成一个迭代器对象，该对象用于遍历整个Set
		// 因为set是无序，所以无法像arraylist使用下标访问元素
		Iterator<String> it = set.iterator();

		while (it.hasNext())
		{
			String s = it.next();
			System.out.println(s);
		}
	}
}
