package com.xdc.basic.java4android.collection;

import java.util.ArrayList;

public class ArrayListTest
{

	public static void main(String[] args)
	{

		// 继承关系： Iterator(接口：迭代器) <-- Collection(接口：类集) <-- List(接口：列表) <--
		// ArrayList(类：数组列表)
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("a");
		arrayList.add("b");
		arrayList.add("c");
		arrayList.add("c");
		arrayList.remove(1);

		// 因为也继承了Iterator，所以也有hasNext方法和next方法，但不如下标访问直接。
		for (int i = 0; i < arrayList.size(); i++)
		{
			String s = arrayList.get(i);
			System.out.println(s);
		}
	}
}
