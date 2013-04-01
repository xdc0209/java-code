package com.xdc.basic.java4android.io;

import java.io.FileReader;
import java.io.FileWriter;

// io字符流测试
class IOCharStreamTest
{
	public static void main(String[] args)
	{
		FileReader fr = null;
		FileWriter fw = null;
		try
		{
			fr = new FileReader("src\\com\\xdc\\basic\\java4android\\io\\from.txt");
			fw = new FileWriter("src\\com\\xdc\\basic\\java4android\\io\\to.txt");
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
