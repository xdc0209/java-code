package com.xdc.basic.apidemo.java4android.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * 哈希映射测试类
 * 
 * @author xdc
 * 
 */
public class HashMapTest
{

	public static void main(String[] args)
	{

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xudacaho");
		map.put("age", "24");
		map.put("number", "20082890");
		map.put("gender", "male");

		System.out.println(map.size());
		System.out.println(map.get("name"));

		// 会覆盖原来键"name"对应的值
		map.put("name", "genxue");

		System.out.println(map.size());
		System.out.println(map.get("name"));
	}
}
