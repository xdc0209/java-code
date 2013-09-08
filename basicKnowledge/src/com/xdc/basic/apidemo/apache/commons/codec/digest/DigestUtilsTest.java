package com.xdc.basic.apidemo.apache.commons.codec.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

import com.xdc.basic.skills.GetCurPath;

public class DigestUtilsTest
{
	public static void main(String[] args)
	{
		String curPath = GetCurPath.getCurPath();

		File file = new File(curPath + "data.txt");

		InputStream inputMd5 = null;
		InputStream inputSha1 = null;

		String md5Hex;
		try
		{
			inputMd5 = new FileInputStream(file);
			md5Hex = DigestUtils.md5Hex(inputMd5);
			System.out.println("MD5值:" + md5Hex);

			inputSha1 = new FileInputStream(file);
			String sha1Hex = DigestUtils.sha1Hex(inputSha1);
			System.out.println("SHA1值:" + sha1Hex);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
