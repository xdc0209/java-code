package com.xdc.basic.apidemo;

import java.security.SecureRandom;
import java.util.Random;

public class RandomTest
{

	public static void main(String[] args)
	{
		// Math.random()方法
		for (int i = 0; i < 10; i++)
		{
			System.out.print((int) (Math.random() * 100) + " ");
		}
		System.out.println();

		// 不带种子，每次的随机数不同
		Random rand = new Random();
		for (int i = 0; i < 10; i++)
		{
			System.out.print(rand.nextInt(100) + " ");
		}
		System.out.println();

		// 带种子，每次的随机数相同
		Random rand2 = new Random(10);
		for (int i = 0; i < 10; i++)
		{
			System.out.print(rand2.nextInt(100) + " ");
		}
		System.out.println();

		// 生成完全不重复的随机数
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < 10; i++)
		{
			System.out.print(random.nextInt(100) + " ");
		}
		System.out.println();
	}
}
