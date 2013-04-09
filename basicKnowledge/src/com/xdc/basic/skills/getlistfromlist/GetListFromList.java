package com.xdc.basic.skills.getlistfromlist;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class GetListFromList
{
	public static void main(String[] args)
	{
		List<Student> students = new ArrayList<>();
		students.add(new Student("20082890", "xudachao"));
		students.add(new Student("20082889", "wangziwei"));

		// Google 提供的类库
		List<String> numbers = Lists.transform(students, new Function<Student, String>()
		{
			public String apply(Student input)
			{
				return input.getNumber();
			}
		});

		System.out.println("仅输出学号：");
		for (String number : numbers)
		{
			System.out.println(number);
		}

	}
}
