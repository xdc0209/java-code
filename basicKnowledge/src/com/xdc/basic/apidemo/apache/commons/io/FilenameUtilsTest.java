package com.xdc.basic.apidemo.apache.commons.io;

import org.apache.commons.io.FilenameUtils;

public class FilenameUtilsTest
{
	public static void main(String[] args)
	{
		// C:\\dev\\project\\file.txt
		// the prefix - C:\
		// the path - dev\project\
		// the full path - C:\dev\project\
		// the name - file.txt
		// the base name - file
		// the extension - txt

		String fileName = "C:\\dev\\project\\file.txt";

		FilenameUtils.getPrefix(fileName);
		FilenameUtils.getPath(fileName);
		FilenameUtils.getFullPath(fileName);
		FilenameUtils.getName(fileName);
		FilenameUtils.getBaseName(fileName);
		FilenameUtils.getExtension(fileName);
	}
}
