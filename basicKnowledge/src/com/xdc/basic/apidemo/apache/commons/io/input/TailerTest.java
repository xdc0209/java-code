package com.xdc.basic.apidemo.apache.commons.io.input;

import java.io.File;

import org.apache.commons.io.input.Tailer;

public class TailerTest
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		Tailer tailer = Tailer.create(new File(curPath + "from.txt"), new MyTailerListener());

		try
		{
			Thread.sleep(100000L);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			tailer.stop();
		}
	}
}
