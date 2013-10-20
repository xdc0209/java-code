package com.xdc.basic.api.apache.commons.io.input;

import java.io.File;

import org.apache.commons.io.input.Tailer;

import com.xdc.basic.skills.GetCurPath;

public class TailerTest
{
	public static void main(String[] args)
	{
		String curPath = GetCurPath.getCurPath();

		Tailer tailer = Tailer.create(new File(curPath + "from.txt"), new MyTailerListener());

		try
		{
			Thread.sleep(100000L);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			tailer.stop();
		}
	}
}
