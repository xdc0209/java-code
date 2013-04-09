package com.xdc.basic.java4android.io;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 处理流示例
 * 
 * @author xdc
 * 
 */
class BufferedReaderTest
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try
		{
			fileReader = new FileReader(curPath + "from.txt");
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
				System.out.println(line);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				bufferedReader.close();
				fileReader.close();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
