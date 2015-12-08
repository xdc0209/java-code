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
        String fromFileName = "data.txt";

        String curPath = GetPath.getRelativePath();

        // 读取文件
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

        // 解析名称
        String toFileNameBase64String = StringUtils.substringBefore(toFileBase64String, ">>>");
        byte[] toFileNameBytes = Base64.decodeBase64(toFileNameBase64String);
        String toFileName = org.apache.commons.codec.binary.StringUtils.newStringUtf8(toFileNameBytes);

        // 解析内容
        String toFileContentBase64String = StringUtils.substringAfter(toFileBase64String, ">>>");
        byte[] toFileContentBytes = Base64.decodeBase64(toFileContentBase64String);

        // 写入文件
        OutputStream toFileContentOutputStream = new FileOutputStream(curPath + toFileName);
        IOUtils.write(toFileContentBytes, toFileContentOutputStream);
        IOUtils.closeQuietly(toFileContentOutputStream);

        System.out.println("fromFileName: " + fromFileName);
        System.out.println("toFileName:   " + toFileName);
    }
}
