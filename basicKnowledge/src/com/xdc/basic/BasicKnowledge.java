package com.xdc.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class BasicKnowledge
{
	public static void main(String[] args)
	{
		// 1. 获取环境变量
		System.getenv("PATH");
		System.getenv("JAVA_HOME");

		// 2. 获取系统属性
		Properties p = System.getProperties(); // 得到所有属性值
		p.list(System.out);
		System.getProperty("file.separator"); // 文件分隔符（在 UNIX 系统中是“/”）
		System.getProperty("path.separator"); // 路径分隔符（在 UNIX 系统中是“:”）
		System.getProperty("line.separator"); // 行分隔符（在 UNIX 系统中是“/n”）
		System.getProperty("user.name"); // 用户的账户名称
		System.getProperty("user.home"); // 用户的主目录
		System.getProperty("user.dir"); // 用户的当前工作目录

		// 4. StringBuffer(同步)和StringBuilder(非同步)
		StringBuilder sb = new StringBuilder();
		sb.append("Hello");
		sb.append("World");
		sb.reverse();// 反转字符串
		sb.toString();

		// 5. 数字
		Integer integer = 1;
		integer.intValue(); // 数字与对象之间互相转换 - Integer转int

		Integer.toBinaryString(15); // 整数 -> 二进制字符串
		Integer.toOctalString(15); // 整数 -> 八进制字符串
		Integer.toHexString(15); // 整数 -> 十六进制字符串

		Math.round(3.4); // // 浮点数的舍入,结果为3

		// 随机数
		Random r = new Random();
		r.nextDouble();
		r.nextInt();

		// 6. 日期和时间
		Date today = new Date(); // 查看当前日期
		// System.out.println(today);

		// 按要求格式打印日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sdf.format(today);

		// 记录耗时
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		long elapsed = end - start;
	}
}
