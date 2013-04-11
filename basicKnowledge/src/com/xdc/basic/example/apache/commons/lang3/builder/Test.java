package com.xdc.basic.example.apache.commons.lang3.builder;

public class Test
{
	public static void main(String[] args)
	{
		Person person = new Person("xdc", 25, false);
		Person person2 = new Person("xdc", 25, false);
		Person person3 = new Person("gx", 25, false);

		System.out.println(person.equals(person2));
		System.out.println(person.equals(person3));

		System.out.println(person.hashCode());
		System.out.println(person2.hashCode());

		System.out.println(person.toString());
		System.out.println(person2.toString());
	}
}
