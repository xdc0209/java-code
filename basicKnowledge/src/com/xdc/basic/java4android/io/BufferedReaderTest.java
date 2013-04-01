package com.xdc.basic.java4android.io;

import java.io.BufferedReader;
import java.io.FileReader;

// 处理流示例
class BufferedReaderTest
{
	public static void main(String[] args)
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try
		{
			fileReader = new FileReader("src\\com\\xdc\\basic\\java4android\\io\\from.txt");
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
