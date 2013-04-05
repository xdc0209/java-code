package com.xdc.basic.skills.crackmybase;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class CrackApache
{
	public static void main(String[] args) throws IOException
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		FileReader input = new FileReader(curPath + "nyfedit.ini");
		List<String> lines = IOUtils.readLines(input);
		input.close();

		String time = String.valueOf(System.currentTimeMillis() / 1000);
		for (int i = 0; i < lines.size(); i++)
		{
			String line = lines.get(i);
			if (line.startsWith("App.UserLic.FirstUseOn"))
			{
				System.out.println(line);
				String newLine = line.replaceAll("[0-9]{10}", time);
				System.out.println(newLine);
				lines.set(i, newLine);
			}
		}

		FileWriter output = new FileWriter(curPath + "nyfedit.ini");
		IOUtils.writeLines(lines, null, output);
		output.close();
	}
}
