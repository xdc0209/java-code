package com.xdc.basic.skills;

/**
 * 输出前导0
 * 
 * @author xdc
 * 
 */
public class Leading0
{
	public static void main(String[] args)
	{
		for (int i = 1; i < 1001; i++)
		{
			String str = String.format("%04d", i);
			System.out.println(str);
		}
	}
}
