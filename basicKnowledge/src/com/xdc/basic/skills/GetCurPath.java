package com.xdc.basic.skills;

public class GetCurPath
{
	public static String getCurPath()
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[1].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";
		return curPath;
	}
}
