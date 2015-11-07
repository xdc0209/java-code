package com.xdc.basic.tools.file2string;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
        int lineLength = 1800;
        String fromFileName = "activemq.zip";
        String toFileName = "activemq.zip.txt";

        String prefix = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");

        String curPath = GetPath.getRelativePath();

        InputStream fromFileInputStream = new FileInputStream(curPath + fromFileName);
        byte[] fromFileBytes = IOUtils.toByteArray(fromFileInputStream);
        String fromFileBase64String = Base64.encodeBase64String(fromFileBytes);
        IOUtils.closeQuietly(fromFileInputStream);

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

        Writer toFileWriter = new FileWriter(curPath + toFileName);
        IOUtils.writeLines(toFileLines, null, toFileWriter);
        IOUtils.closeQuietly(toFileWriter);

        System.out.println("fromFileName: " + fromFileName);
        System.out.println("toFileName:   " + toFileName);
    }
}
