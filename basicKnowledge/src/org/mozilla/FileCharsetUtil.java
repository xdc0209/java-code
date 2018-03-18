package org.mozilla;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsPSMDetector;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.commons.IOUtil;
import com.xdc.basic.commons.PathUtil;

public class FileCharsetUtil
{
    // 中文编码：GB2312<GBK<GB18030，GBK兼容GB2312，GB18030兼容GBK。

    private static Logger logger = LoggerFactory.getLogger(FileCharsetUtil.class);

    private static String detectFileCharsetByUniversalChardet(File file)
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
        catch (IOException e)
        {
            logger.error(String.format("Detect file charset failed. file=[%s]", file), e);
            return null;
        }
        finally
        {
            IOUtil.closeQuietly(fis);
        }
    }

    private static String detectFileCharsetByJchardet(File file)
    {
        // Initalize the nsDetector();
        nsDetector det = new nsDetector(nsPSMDetector.ALL);

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int len;
            boolean done = false;
            boolean isAscii = true;
            while ((len = fis.read(buf, 0, buf.length)) != -1)
            {
                // Check if the stream is only ascii.
                if (isAscii)
                {
                    isAscii = det.isAscii(buf, len);
                }

                // DoIt if non-ascii and not done yet.
                if (!isAscii && !done)
                {
                    done = det.DoIt(buf, len, false);
                }
            }
            det.DataEnd();

            if (isAscii)
            {
                return "ASCII";
            }

            return det.getDetectedCharset();
        }
        catch (IOException e)
        {
            logger.error(String.format("Detect file charset failed. file=[%s]", file), e);
            return null;
        }
        finally
        {
            IOUtil.closeQuietly(fis);
        }
    }

    public static String[] detectFileProbableCharsetsByJchardet(File file)
    {
        // Initalize the nsDetector();
        nsDetector det = new nsDetector(nsPSMDetector.ALL);

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int len;
            boolean done = false;
            boolean isAscii = true;
            while ((len = fis.read(buf, 0, buf.length)) != -1)
            {
                // Check if the stream is only ascii.
                if (isAscii)
                {
                    isAscii = det.isAscii(buf, len);
                }

                // DoIt if non-ascii and not done yet.
                if (!isAscii && !done)
                {
                    done = det.DoIt(buf, len, false);
                }
            }
            det.DataEnd();

            if (isAscii)
            {
                String[] prob = new String[] { "ASCII" };
                return prob;
            }

            String detectedCharset = det.getDetectedCharset();
            if (StringUtils.isNotBlank(detectedCharset))
            {
                String[] prob = new String[] { detectedCharset };
                return prob;
            }

            String[] prob = det.getProbableCharsets();
            return prob;
        }
        catch (IOException e)
        {
            logger.error(String.format("Detect file probable charsets failed. file=[%s]", file), e);
            return new String[0];
        }
        finally
        {
            IOUtil.closeQuietly(fis);
        }
    }

    public static String detectFileCharset(File file)
    {
        String detectedCharset = FileCharsetUtil.detectFileCharsetByUniversalChardet(file);

        if (StringUtils.isBlank(detectedCharset))
        {
            detectedCharset = FileCharsetUtil.detectFileCharsetByJchardet(file);
        }

        if (StringUtils.isBlank(detectedCharset))
        {
            String[] probableCharsets = FileCharsetUtil.detectFileProbableCharsetsByJchardet(file);
            logger.error(String.format("Unable to detect file charset. file=[%s], probableCharsets=[%s]", file,
                    Arrays.toString(probableCharsets)));
        }

        return detectedCharset;
    }

    public static void main(String[] args) throws IOException
    {
        String curPath = PathUtil.getRelativePath();

        Collection<File> txtFiles = FileUtils.listFiles(new File(curPath), new String[] { "txt" }, false);
        for (File file : txtFiles)
        {
            String detectedCharset = FileCharsetUtil.detectFileCharset(file);
            System.out.println(String.format("File: %-80s FileCharset: %-20s", file, detectedCharset));
            if (StringUtils.isBlank(detectedCharset))
            {
                String[] probableCharsets = FileCharsetUtil.detectFileProbableCharsetsByJchardet(file);
                for (String probableCharset : probableCharsets)
                {
                    System.out.println("Probable charset: " + probableCharset);
                }
            }

            if (StringUtils.isNotBlank(detectedCharset))
            {
                String readFileToString = FileUtils.readFileToString(file, detectedCharset);
                System.out.println(readFileToString);
            }

            System.out.println();
        }
    }
}
