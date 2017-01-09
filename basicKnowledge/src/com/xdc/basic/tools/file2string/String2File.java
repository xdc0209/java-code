package com.xdc.basic.tools.file2string;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.skills.GetPath;
import com.xdc.basic.skills.encrypt.aes.aes2.core.EncException;
import com.xdc.basic.skills.encrypt.aes.aes2.util.EncUtil;

/**
 * base编码的字符串转换为文件
 */
public class String2File
{
    public static void main(String[] args) throws IOException, EncException
    {
        String curPath = GetPath.getRelativePath();
        Collection<File> txtFiles = FileUtils.listFiles(new File(curPath), new String[] { "txt" }, false);
        for (File txtFile : txtFiles)
        {
            String fromFileName = txtFile.getName();

            string2File(fromFileName);
        }
    }

    private static void string2File(String fromFileName) throws FileNotFoundException, IOException, EncException
    {
        String curPath = GetPath.getRelativePath();

        // 读取文件内容
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

        // 对文件名称先解码后解密
        String toFileNameBase64String = StringUtils.substringBefore(toFileBase64String, ">>>");
        byte[] toFileNameBytes = Base64.decodeBase64(toFileNameBase64String);
        toFileNameBytes = EncUtil.decode(toFileNameBytes);
        String toFileName = org.apache.commons.codec.binary.StringUtils.newStringUtf8(toFileNameBytes);

        // 对文件内容先解码后解密
        String toFileContentBase64String = StringUtils.substringAfter(toFileBase64String, ">>>");
        byte[] toFileContentBytes = Base64.decodeBase64(toFileContentBase64String);
        toFileContentBytes = EncUtil.decode(toFileContentBytes);

        File toFile = new File(curPath + toFileName);
        if (toFile.exists())
        {
            System.out.println("fromFileName: " + fromFileName);
            System.out.println("toFileName:   " + toFileName);
            System.out.println(String.format("File [%s] exists, skip to string2File.", toFileName));
            System.out.println();
            return;
        }

        // 写入文件
        OutputStream toFileContentOutputStream = new FileOutputStream(curPath + toFileName);
        IOUtils.write(toFileContentBytes, toFileContentOutputStream);
        IOUtils.closeQuietly(toFileContentOutputStream);

        System.out.println("fromFileName: " + fromFileName);
        System.out.println("toFileName:   " + toFileName);
        System.out.println();
    }
}
