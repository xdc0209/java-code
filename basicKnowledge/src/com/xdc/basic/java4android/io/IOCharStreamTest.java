package com.xdc.basic.java4android.io;

import java.io.FileReader;
import java.io.FileWriter;

// io字符流测试
class IOCharStreamTest
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		String clazzName = new SecurityManager()
		{
			public String getClassName()
			{
				return getClassContext()[1].getName();
			}
		}.getClassName();
		System.out.println(clazzName);

		FileReader fr = null;
		FileWriter fw = null;
		try
		{
			fr = new FileReader(curPath + "from.txt");
			fw = new FileWriter(curPath + "to.txt");
			char[] buffer = new char[1024];
			int len;
			while ((len = fr.read(buffer, 0, buffer.length)) != -1)
			{
				fw.write(buffer, 0, len);
				String s = new String(buffer);
				s = s.trim();
				System.out.println(s);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				fr.close();
				fw.close();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
