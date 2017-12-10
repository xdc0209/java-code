package com.xdc.basic.api.java4android.io;

import java.io.BufferedReader;
import java.io.FileReader;

import com.xdc.basic.skills.GetPath;

/**
 * 处理流示例
 * 
 * @author xdc
 * 
 */
class BufferedReaderTest
{
    public static void main(String[] args)
    {
        String curPath = GetPath.getRelativePath();

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try
        {
            fileReader = new FileReader(curPath + "from.txt");
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
                System.out.println(line);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            try
            {
                bufferedReader.close();
                fileReader.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
