package com.xdc.basic.tools.file2string;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.xdc.basic.skills.GetPath;

public class File2String
{
    String fileName   = "bookmarks_15_11_4.rar";
    int    lineLength = 1000;

    /**
     * 文件转换为base编码的字符串
     * 
     * @throws IOException
     */
    @Test
    public void file2String() throws IOException
    {
        String curPath = GetPath.getRelativePath();

        InputStream input = new FileInputStream(curPath + fileName);
        byte[] bytes = IOUtils.toByteArray(input);
        String base64String = Base64.encodeBase64String(bytes);
        IOUtils.closeQuietly(input);

        List<String> lines = new ArrayList<String>();
        int length = base64String.length();
        for (int i = 0, j = 1; i < length; i = i + 3000, j++)
        {
            if (i + 3000 < length)
            {
                lines.add(j + "-" + base64String.substring(i, i + 3000));
            }
            else
            {
                lines.add(j + "-" + base64String.substring(i, length));
            }
        }

        FileWriter output = new FileWriter(curPath + fileName + ".txt");
        IOUtils.writeLines(lines, null, output);
        IOUtils.closeQuietly(output);
    }

    /**
     * base编码的字符串转换为文件
     * 
     * @throws IOException
     */
    @Test
    public void string2File() throws IOException
    {
        String curPath = GetPath.getRelativePath();

        FileReader input = new FileReader(curPath + fileName + ".txt");
        List<String> lines = IOUtils.readLines(input);
        IOUtils.closeQuietly(input);

        StringBuilder sb = new StringBuilder();
        for (String string : lines)
        {
            sb.append(StringUtils.substringAfter(string, "-"));
        }
        String base64String = sb.toString();
        byte[] bytes = Base64.decodeBase64(base64String);

        FileOutputStream fileOutputStream = new FileOutputStream(curPath + fileName + "-new.rar");
        IOUtils.write(bytes, fileOutputStream);
    }
}
