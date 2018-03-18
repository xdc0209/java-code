package com.xdc.basic.api.xml.formater.x3.jdom2;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.xdc.basic.api.xml.security.avoid.xxe.x3.jdom2.SecuritySAXBuilder;
import com.xdc.basic.commons.ExceptionUtil;

public class XmlFormater
{
    public static String format(String xml)
    {
        try
        {
            // Build the document with SAX and Xerces, no validation.
            SAXBuilder builder = SecuritySAXBuilder.newInstance();

            // Create the document.
            Document doc = builder.build(new StringReader(xml));

            // Output the document, use standard formatter.
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

            return outputter.outputString(doc);
        }
        catch (JDOMException | IOException e)
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
