package com.xdc.basic.api.apache.commons.codec.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

import com.xdc.basic.skills.GetPath;

public class DigestUtilsTest
{
	public static void main(String[] args)
	{
		String curPath = GetPath.getRelativePath();

		File file = new File(curPath + "data.txt");

		InputStream inputMd5 = null;
		InputStream inputSha1 = null;
		InputStream inputSha256 = null;

		String md5Hex;
		try
		{
			// 算法强度小于sha1
			inputMd5 = new FileInputStream(file);
			md5Hex = DigestUtils.md5Hex(inputMd5);
			System.out.println("MD5值:" + md5Hex);

			// 算法强度小于sha256
			inputSha1 = new FileInputStream(file);
			String sha1Hex = DigestUtils.sha1Hex(inputSha1);
			System.out.println("SHA1值:" + sha1Hex);

			inputSha256 = new FileInputStream(file);
			String sha256Hex = DigestUtils.sha256Hex(inputSha256);
			System.out.println("SHA256值:" + sha256Hex);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
