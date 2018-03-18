package com.xdc.basic.api.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.xdc.basic.api.xml.security.avoid.xxe.x2.sax.SecuritySAXParserFactory;
import com.xdc.basic.commons.PathUtil;

public class X2_SaxTest extends DefaultHandler
{
    public static void main(String[] args) throws Exception
    {
        String curPath = PathUtil.getRelativePath();
        InputSource is = new InputSource(curPath + "students.xml");
        SAXParserFactory parserFactory = SecuritySAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        X2_StudentHandler studentHandler = new X2_StudentHandler();
        parser.parse(is, studentHandler);

        for (Student student : studentHandler.getStudentList())
        {
            System.out.println(student);
        }
    }
}
