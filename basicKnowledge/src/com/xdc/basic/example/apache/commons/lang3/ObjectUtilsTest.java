package com.xdc.basic.example.apache.commons.lang3;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;

public class ObjectUtilsTest
{

	public static void main(String[] args)
	{
		System.out.println("**ObjectUtilsDemo**");
		System.out.println("Object为null时，默认打印某字符.");
		Object obj = null;
		System.out.println(ObjectUtils.defaultIfNull(obj, "空"));
		System.out.println("验证两个引用是否指向的Object是否相等,取决于Object的equals()方法.");
		Object a = new Object();
		Object b = a;
		Object c = new Object();
		System.out.println(ObjectUtils.equals(a, b));
		System.out.println(ObjectUtils.equals(a, c));
		System.out.println("用父类Object的toString()方法返回对象信息.");
		Date date = new Date();
		System.out.println(ObjectUtils.identityToString(date));
		System.out.println(date);
		System.out.println("返回类本身的toString()方法结果,对象为null时，返回0长度字符串.");
		System.out.println(ObjectUtils.toString(date));
		System.out.println(ObjectUtils.toString(null));
		System.out.println(date);

	}

}
