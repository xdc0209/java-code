package com.xdc.basic.api.apache.commons.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.SystemUtils;

import com.xdc.basic.skills.GetCurPath;

public class FileUtilsTest
{
	public static void main(String[] args)
	{
		String curPath = GetCurPath.getCurPath();

		File fromFile = FileUtils.getFile(curPath + "from.txt");
		File toFile = FileUtils.getFile(curPath + "to.txt");

		// 列出指定目录的所有目录和文件
		IOFileFilter fileFilter = CanReadFileFilter.CAN_READ;
		IOFileFilter dirFilter = CanReadFileFilter.READ_ONLY;
		Collection<File> listFilesAndDirs = FileUtils.listFilesAndDirs(new File(curPath), fileFilter, dirFilter);
		System.out.println(listFilesAndDirs);

		try
		{
			String fileString = FileUtils.readFileToString(fromFile, Charsets.UTF_8);
			FileUtils.writeStringToFile(toFile, fileString, Charsets.UTF_8, false);

			List<String> lines = FileUtils.readLines(fromFile, Charsets.UTF_8);
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
