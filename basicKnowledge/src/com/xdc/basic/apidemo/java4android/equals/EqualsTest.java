package com.xdc.basic.apidemo.java4android.equals;

/**
 * equals方法测试类
 * 
 * @author xdc
 * 
 */
public class EqualsTest
{
	public static void main(String[] args)
	{
		User u1 = new User();
		User u2 = new User();
		User u3 = u1;

		u1.name = "gengxue";
		u1.age = 23;
		u2.name = "xudachao";
		u2.age = 24;

		System.out.println("u1 == u2 : " + (u1 == u2));
		System.out.println("u1 == u3 : " + (u1 == u3));
		System.out.println("u2 == u3 : " + (u2 == u3));

		System.out.println("u1.equals(u2) : " + u1.equals(u2));
		System.out.println("u1.equals(u3) : " + u1.equals(u3));
		System.out.println("u2.equals(u3) : " + u2.equals(u3));
	}
}
