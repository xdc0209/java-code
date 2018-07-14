package com.xdc.basic.api.java4android.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import com.xdc.basic.commons.PathUtil;

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
        String curPath = PathUtil.getRelativePath();

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
            int bufferUsed = 0;
            // 调用输入流的read方法，读取数据
            while ((bufferUsed = fis.read(buffer, 0, buffer.length)) > 0)
            {
                // 调用输出流的write方法，写入数据
                fos.write(buffer, 0, bufferUsed);
                // 将字节还原成字符
                String s = new String(buffer, 0, bufferUsed, Charset.forName("UTF-8"));
                // 调用一个String对象的trim方法，将会去除这个字符串的前导空白和后导空白
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
