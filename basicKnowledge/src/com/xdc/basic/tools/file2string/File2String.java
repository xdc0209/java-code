package com.xdc.basic.tools.file2string;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.xdc.basic.skills.GetPath;

/**
 * 文件转换为base编码的字符串
 */
public class File2String
{
    public static void main(String[] args) throws IOException
    {
        String curPath = GetPath.getRelativePath();
        Collection<File> compressedFiles = FileUtils.listFiles(new File(curPath), new String[] { "rar", "zip" }, false);
        for (File compressedFile : compressedFiles)
        {
            String fromFileName = compressedFile.getName();
            String toFileName = fromFileName + ".txt";

            file2String(fromFileName, toFileName);
        }
    }

    private static void file2String(String fromFileName, String toFileName) throws IOException
    {
        String curPath = GetPath.getRelativePath();

        File toFile = new File(curPath + toFileName);
        if (toFile.exists())
        {
            System.out.println("fromFileName: " + fromFileName);
            System.out.println("toFileName:   " + toFileName);
            System.out.println(String.format("File [%s] exists, skip to file2String.", toFileName));
            System.out.println();
            return;
        }

        int lineLength = 1800;
        String prefix = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");

        // 读取文件
        InputStream fromFileContentInputStream = new FileInputStream(curPath + fromFileName);
        byte[] fromFileContentBytes = IOUtils.toByteArray(fromFileContentInputStream);

        // 名称编码
        byte[] fromFileNameBytes = StringUtils.getBytesUtf8(fromFileName);
        String fromFileNameBase64String = Base64.encodeBase64String(fromFileNameBytes);

        // 内容编码
        String fromFileContentBase64String = Base64.encodeBase64String(fromFileContentBytes);
        IOUtils.closeQuietly(fromFileContentInputStream);

        String fromFileBase64String = fromFileNameBase64String + ">>>" + fromFileContentBase64String;

        List<String> toFileLines = new ArrayList<String>();
        int fromFileBase64StringLength = fromFileBase64String.length();
        for (int i = 0, j = 1; i < fromFileBase64StringLength; i = i + lineLength, j++)
        {
            int beginIndex = i;
            int endIndex = i + lineLength;
            if (endIndex > fromFileBase64StringLength)
            {
                endIndex = fromFileBase64StringLength;
            }

            toFileLines.add(String.format("%s:%03d-%s%s", prefix, j,
                    fromFileBase64String.substring(beginIndex, endIndex), SystemUtils.LINE_SEPARATOR));
        }

        // 写入文件
        Writer toFileWriter = new FileWriter(curPath + toFileName);
        IOUtils.writeLines(toFileLines, null, toFileWriter);
        IOUtils.closeQuietly(toFileWriter);

        System.out.println("fromFileName: " + fromFileName);
        System.out.println("toFileName:   " + toFileName);
        System.out.println();
    }
}
