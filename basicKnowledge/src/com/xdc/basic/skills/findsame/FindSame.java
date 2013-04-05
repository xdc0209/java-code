package com.xdc.basic.skills.findsame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

public class FindSame
{
	public static void main(String[] args) throws IOException
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		FileReader fr1 = null;
		FileReader fr2 = null;

		BufferedReader br1 = null;
		BufferedReader br2 = null;

		try
		{
			fr1 = new FileReader(curPath + "1.txt");
			fr2 = new FileReader(curPath + "2.txt");

			br1 = new BufferedReader(fr1);
			br2 = new BufferedReader(fr2);

			ArrayList<Integer> list1 = new ArrayList<Integer>();
			ArrayList<Integer> list2 = new ArrayList<Integer>();

			String str1 = null;
			String str2 = null;

			while ((str1 = br1.readLine()) != null)
			{
				list1.add(Integer.valueOf(str1));
			}

			while ((str2 = br2.readLine()) != null)
			{
				list2.add(Integer.valueOf(str2));
			}

			// 交替输出
			int i = 0, j = 0;
			while (i < list1.size() && j < list2.size())
			{
				if (list1.get(i) < list2.get(j))
				{
					System.out.println(list1.get(i));
					i++;
				}
				else if (list1.get(i).equals(list2.get(j)))
				{
					System.out.println(list1.get(i) + "  " + list2.get(j));
					i++;
					j++;
				}
				else
				{
					System.out.println("       " + list2.get(j));
					j++;
				}
			}

			// 输出剩余元素
			while (i < list1.size())
			{
				System.out.println(list1.get(i));
				i++;
			}
			while (j < list2.size())
			{
				System.out.println("       " + list2.get(j));
				j++;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly(br1);
			IOUtils.closeQuietly(br2);
			IOUtils.closeQuietly(fr1);
			IOUtils.closeQuietly(fr2);
		}

	}
}
