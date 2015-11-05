package com.xdc.basic.tools.file2string;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.skills.GetPath;

/**
 * base编码的字符串转换为文件
 */
public class String2File
{
    public static void main(String[] args) throws IOException
    {
        String fromFileName = "activemq.zip.txt";
        String toFileName = "activemq.zip.txt.zip";

        String curPath = GetPath.getRelativePath();

        Reader fromFileReader = new FileReader(curPath + fromFileName);
        List<String> fromFileLines = IOUtils.readLines(fromFileReader);
        IOUtils.closeQuietly(fromFileReader);

        StringBuilder sb = new StringBuilder();
        for (String fromLine : fromFileLines)
        {
            if (StringUtils.isNotBlank(fromLine))
            {
                sb.append(StringUtils.substringAfter(fromLine, "-"));
            }
        }
        String toFileBase64String = sb.toString();
        byte[] toFileBytes = Base64.decodeBase64(toFileBase64String);

        OutputStream toFileOutputStream = new FileOutputStream(curPath + toFileName);
        IOUtils.write(toFileBytes, toFileOutputStream);
        IOUtils.closeQuietly(toFileOutputStream);

        System.out.println("fromFileName: " + fromFileName);
        System.out.println("toFileName:   " + toFileName);
    }
}
