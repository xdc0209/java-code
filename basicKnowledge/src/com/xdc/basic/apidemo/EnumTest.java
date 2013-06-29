package com.xdc.basic.apidemo;

public class EnumTest
{
	public enum Color
	{
		RED, GREEN, BLANK, YELLOW
	}

	public static void main(String[] args)
	{
		// 枚举可以用==比较
		Color color = Color.GREEN;
		if (color == Color.GREEN)
		{
			System.out.println("颜色为GREEN");
		}
	}
}
