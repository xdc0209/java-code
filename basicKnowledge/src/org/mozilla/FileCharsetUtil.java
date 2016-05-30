package org.mozilla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.skills.GetPath;

public class FileCharsetUtil
{
    private static Logger logger = LoggerFactory.getLogger(FileCharsetUtil.class);

    public static String detectFileCharset(File file)
    {
        FileInputStream fis = null;
        try
        {
            // 获得输入流
            fis = new FileInputStream(file);

            // 缓存数组
            byte[] buffer = new byte[4096];

            // 创建检测器
            UniversalDetector detector = new UniversalDetector(null);

            // 开始检测
            int nread = 0;
            while ((nread = fis.read(buffer)) > 0 && !detector.isDone())
            {
                detector.handleData(buffer, 0, nread);
            }
            detector.dataEnd();

            // 获得文件编码
            return detector.getDetectedCharset();
        }
        catch (FileNotFoundException e)
        {
            logger.error("Detect file charset failed.", e);
            return null;
        }
        catch (IOException e)
        {
            logger.error("Detect file charset failed.", e);
            return null;
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    // TODO 英语重新翻译一下
                    // do nothing, usually not happen
                }
            }
        }
    }

    public static void main(String[] args)
    {
        String curPath = GetPath.getAbsolutePath();

        Collection<File> txtFiles = FileUtils.listFiles(new File(curPath), new String[] { "txt" }, false);
        for (File file : txtFiles)
        {
            String fileCharset = FileCharsetUtil.detectFileCharset(file);

            System.out.println(String.format("File: %-120s FileCharset: %-20s", file, fileCharset));
        }
    }
}
