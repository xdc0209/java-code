package com.xdc.basic.api.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.xdc.basic.api.xml.security.avoid.xxe.x5.xmlpull.SecurityXmlPullParserFactory;
import com.xdc.basic.skills.GetPath;

public class X5_xmlPullTest
{
    public static void main(String[] args)
    {
        String curPath = GetPath.getRelativePath();
        try
        {
            XmlPullParserFactory pullParserFactory = SecurityXmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = pullParserFactory.newPullParser();

            File file = new File(curPath + "students.xml");
            Reader reader = new FileReader(file);
            xmlPullParser.setInput(reader);

            Student student = null;
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String nodeName = xmlPullParser.getName();
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        if ("student".equals(nodeName))
                        {
                            student = new Student();
                            student.setId(xmlPullParser.getAttributeValue(0));
                        }
                        else if ("name".equals(nodeName))
                        {
                            student.setName(xmlPullParser.nextText());
                        }
                        else if ("age".equals(nodeName))
                        {
                            student.setAge(xmlPullParser.nextText());
                        }
                        else if ("sex".equals(nodeName))
                        {
                            student.setSex(xmlPullParser.nextText());
                        }

                        else if ("address".equals(nodeName))
                        {
                            student.setAddress(xmlPullParser.nextText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        if ("student".equals(nodeName))
                        {
                            System.out.println(student);
                            student = null;
                        }
                        break;

                    default:
                        break;
                }
                xmlPullParser.next();
                eventType = xmlPullParser.getEventType();
            }
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
