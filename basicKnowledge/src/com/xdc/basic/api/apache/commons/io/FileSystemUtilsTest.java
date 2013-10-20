package com.xdc.basic.api.apache.commons.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;

public class FileSystemUtilsTest
{
	public static void main(String[] args)
	{
		try
		{
			String path = new File(".").getAbsolutePath();
			long freeSpaceKb = FileSystemUtils.freeSpaceKb(path, 5000L);
			System.out.println(freeSpaceKb + "KB");
			System.out.println(FileUtils.byteCountToDisplaySize(freeSpaceKb * FileUtils.ONE_KB));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
