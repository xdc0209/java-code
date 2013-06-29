package com.xdc.basic.apidemo.java4android.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * io字节流示例
 * 
 * @author xdc
 * 
 */
class IOByteStreamTest
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		// 声明输入流、输出流引用
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try
		{
			// 生成代表输入流、输出流的对象
			fis = new FileInputStream(curPath + "from.txt");
			fos = new FileOutputStream(curPath + "to.txt");
			// 生成一个字节数组
			byte[] buffer = new byte[1024];
			int len;
			// 调用输入流的read方法，读取数据
			while ((len = fis.read(buffer, 0, buffer.length)) != -1)
			{
				// 调用输出流的write方法，写入数据
				fos.write(buffer, 0, len);
				// 将字节还原成字符
				String s = new String(buffer);
				// 调用一个String对象的trim方法，将会去除这个字符串的首位空格和空字符。
				s = s.trim();
				System.out.println(s);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				fis.close();
				fos.close();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
