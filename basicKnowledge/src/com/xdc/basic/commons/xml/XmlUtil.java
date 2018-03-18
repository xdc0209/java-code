package com.xdc.basic.commons.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.xdc.basic.commons.ExceptionUtil;

/**
 * 使用Jaxb2.0实现xml<-->java object的转换。
 * 
 * 在创建时需要设定所有需要序列化的对象的Class。
 * 
 * 参考：https://gitee.com/thinkgem/jeesite4/blob/master/common/src/main/java/com/jeesite/common/mapper/JaxbMapper.java
 */
public class XmlUtil
{
    // 为什么要缓存JAXBContext？
    // 参考：http://colobu.com/2014/09/04/how-to-improve-performance-20-times
    // 参考：https://stackoverflow.com/questions/18607318/how-can-i-improve-jaxb-performance
    // How can i improve JAXB performance:
    // 1. You should avoid creating the same JAXBContext over and over. JAXBContext is thread safe and should be reused to improve performance.
    // 2. Marshaller/Unmarshaller are not thread safe, but are quick to create.
    private static ConcurrentMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class<?>, JAXBContext>();

    /**
     * java object-->xml
     */
    public static String toXml(Object object)
    {
        return toXml(object, null);
    }

    /**
     * java object-->xml
     */
    public static String toXml(Object object, Class<?> clazz)
    {
        if (object == null)
        {
            throw new RuntimeException("'object' must not be null.");
        }

        if (clazz == null)
        {
            clazz = object.getClass();
        }

        try
        {
            StringWriter writer = new StringWriter();
            createMarshaller(clazz).marshal(object, writer);
            return writer.toString();
        }
        catch (JAXBException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * xml-->java object
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> clazz)
    {
        try
        {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller(clazz).unmarshal(reader);
        }
        catch (JAXBException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 创建Marshaller。
     * Marshaller线程不安全，需要每次创建或pooling。
     */
    private static Marshaller createMarshaller(Class<?> clazz)
    {
        try
        {
            JAXBContext jaxbContext = getJaxbContext(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            return marshaller;
        }
        catch (JAXBException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 创建UnMarshaller。
     * UnMarshaller线程不安全，需要每次创建或pooling。
     */
    private static Unmarshaller createUnmarshaller(Class<?> clazz)
    {
        try
        {
            JAXBContext jaxbContext = getJaxbContext(clazz);
            return jaxbContext.createUnmarshaller();
        }
        catch (JAXBException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    private static JAXBContext getJaxbContext(Class<?> clazz)
    {
        if (clazz == null)
        {
            throw new RuntimeException("'clazz' must not be null.");
        }

        JAXBContext jaxbContext = jaxbContexts.get(clazz);
        if (jaxbContext == null)
        {
            try
            {
                jaxbContext = JAXBContext.newInstance(clazz);
                jaxbContexts.putIfAbsent(clazz, jaxbContext);
            }
            catch (JAXBException e)
            {
                throw ExceptionUtil.newRuntimeException(e, "Could not instantiate JAXBContext for class [%s]: %s",
                        clazz, e.getMessage());
            }
        }
        return jaxbContext;
    }

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
}
