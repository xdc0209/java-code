package com.xdc.basic.apidemo.apache.commons.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.SystemUtils;

public class FileUtilsTest
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		File fromFile = FileUtils.getFile(curPath + "from.txt");
		File toFile = FileUtils.getFile(curPath + "to.txt");

		// 列出指定目录的所有目录和文件
		IOFileFilter fileFilter = TrueFileFilter.INSTANCE;
		IOFileFilter dirFilter = TrueFileFilter.INSTANCE;
		Collection<File> listFilesAndDirs = FileUtils.listFilesAndDirs(new File(curPath), fileFilter, dirFilter);
		System.out.println(listFilesAndDirs);

		try
		{
			String fileString = FileUtils.readFileToString(fromFile, "UTF-8");
			FileUtils.writeStringToFile(toFile, fileString, "UTF-8", false);

			List<String> lines = FileUtils.readLines(fromFile, "UTF-8");
			FileUtils.writeLines(toFile, "UTF-8", lines, SystemUtils.LINE_SEPARATOR, false);

			// linux下的touch命令
			FileUtils.touch(toFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
