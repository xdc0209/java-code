package com.xdc.basic.skills.crackmybase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Crack
{

	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		File file = new File(curPath + "nyfedit.ini");
		file.renameTo(new File(curPath + "nyfedit.ini.old"));
		file = new File(curPath + "nyfedit.ini.old");

		String time = String.valueOf(System.currentTimeMillis() / 1000);
		try
		{
			fileReader = new FileReader(curPath + "nyfedit.ini.old");
			bufferedReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(curPath + "nyfedit.ini");
			bufferedWriter = new BufferedWriter(fileWriter);

			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				if (line.startsWith("App.UserLic.FirstUseOn"))
				{
					System.out.println(line);
					line = line.replaceAll("[0-9]{10}", time);
					System.out.println(line);
				}
				bufferedWriter.write(line);
				bufferedWriter.newLine();
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
				bufferedReader.close();
				fileReader.close();

				// 一定要关闭，否则在缓冲区的字符
				bufferedWriter.close();
				fileWriter.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		file.delete();

	}
}
