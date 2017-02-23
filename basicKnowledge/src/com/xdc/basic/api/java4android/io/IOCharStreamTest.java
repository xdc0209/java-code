package com.xdc.basic.api.java4android.io;

import java.io.FileReader;
import java.io.FileWriter;

import com.xdc.basic.skills.GetPath;

/**
 * io字符流测试
 * 
 * @author xdc
 * 
 */
class IOCharStreamTest
{
    public static void main(String[] args)
    {
        String curPath = GetPath.getRelativePath();

        FileReader fr = null;
        FileWriter fw = null;
        try
        {
            fr = new FileReader(curPath + "from.txt");
            fw = new FileWriter(curPath + "to.txt");
            char[] buffer = new char[1024];
            int len;
            while ((len = fr.read(buffer, 0, buffer.length)) != -1)
            {
                fw.write(buffer, 0, len);
                String s = new String(buffer);
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
                fr.close();
                fw.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
