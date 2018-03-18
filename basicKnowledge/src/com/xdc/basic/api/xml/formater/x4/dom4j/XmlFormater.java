package com.xdc.basic.api.xml.formater.x4.dom4j;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.xdc.basic.commons.ExceptionUtil;

/**
 * 参考：http://www.jb51.net/article/96831.htm
 */
public class XmlFormater
{
    public static String format(String xml)
    {
        try
        {
            // 创建一个串的字符输入流。
            StringReader in = new StringReader(xml);

            // 创建一个SAX输入流。
            SAXReader reader = new SAXReader();

            // 生成XML文档。
            Document doc = reader.read(in);

            // 创建输出格式。
            OutputFormat formater = OutputFormat.createPrettyPrint();
            // OutputFormat formater = OutputFormat.createCompactFormat();

            // 设置xml的输出编码
            formater.setEncoding("UTF-8");

            // 创建输出(目标)。
            StringWriter out = new StringWriter();

            // 创建输出流。
            XMLWriter writer = new XMLWriter(out, formater);

            // 输出格式化的串到目标中，执行后。格式化后的串保存在out中。
            writer.write(doc);

            // 关闭输出流。
            writer.close();

            // 返回我们格式化后的结果。
            return out.toString();
        }
        catch (DocumentException | IOException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    public static void main(String[] args)
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><users><user><name>小李</name><sex>男</sex><age>24</age></user><user><name>小丽</name><sex>女</sex><age>18</age></user><user><name>小王</name><sex>男</sex><age>22</age></user></users>";
        String formatedXml = format(xml);
        System.out.println(formatedXml);
    }
}
