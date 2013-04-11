package com.xdc.basic.example.apache.commons.lang3;

import org.apache.commons.lang3.SystemUtils;

public class SystemUtilsTest
{
	public static void main(String[] args)
	{
		// 判断操作系统类型
		System.out.println(SystemUtils.IS_OS_LINUX);
		System.out.println(SystemUtils.IS_OS_WINDOWS);

		// 换行符
		System.out.println(SystemUtils.LINE_SEPARATOR);
		// 路径中的文件分隔符
		System.out.println(SystemUtils.FILE_SEPARATOR);
		// 环境变量中的路径分隔符
		System.out.println(SystemUtils.PATH_SEPARATOR);
	}
}
