package com.xdc.basic.apidemo.apache.commons.lang3;

import org.apache.commons.lang3.ObjectUtils;

public class ObjectUtilsTest
{

	public static void main(String[] args)
	{
		System.out.println("Returns a default value if the object passed is null.");
		Object obj = null;
		System.out.println(ObjectUtils.defaultIfNull(obj, "空"));
		System.out.println();

		/*
		ObjectUtils.equals(null, null)                  = true
		ObjectUtils.equals(null, "")                    = false
		ObjectUtils.equals("", null)                    = false
		ObjectUtils.equals("", "")                      = true
		ObjectUtils.equals(Boolean.TRUE, null)          = false
		ObjectUtils.equals(Boolean.TRUE, "true")        = false
		ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
		ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
		 */
		System.out.println("Compares two objects for equality, where either one or both objects may be null.");
		Object a = new Object();
		Object b = a;
		Object c = new Object();
		System.out.println(ObjectUtils.equals(a, b));
		System.out.println(ObjectUtils.equals(a, c));
		System.out.println();
	}
}
