package com.xdc.basic.apidemo.apache.commons.lang3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.xdc.basic.skills.GetCurPath;

public class SerializationUtilsTest
{
	public static void main(String[] args)
	{
		String curPath = GetCurPath.getCurPath();

		// =========================================================================
		Date date = new Date();
		// Serializes an Object to a byte array for storage/serialization.
		byte[] bytes = SerializationUtils.serialize(date);
		System.out.println("date to bytes: " + ArrayUtils.toString(bytes));
		System.out.println("date: " + date);

		// Deserializes a single Object from an array of bytes.
		Date reDate = (Date) SerializationUtils.deserialize(bytes);
		System.out.println("reDate: " + reDate);

		System.out.println("ObjectUtils.equals(date, reDate): " + ObjectUtils.equals(date, reDate));
		System.out.println("date == reDate: " + (date == reDate));

		// =========================================================================
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try
		{
			fos = new FileOutputStream(new File(curPath + "test.txt"));
			fis = new FileInputStream(new File(curPath + "test.txt"));
			// Serializes an Object to the specified stream.
			SerializationUtils.serialize(date, fos);
			// Deserializes an Object from the specified stream.
			Date reDate2 = (Date) SerializationUtils.deserialize(fis);
			System.out.println("date.equals(reDate2): " + date.equals(reDate2));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(fis);
		}
	}
}
