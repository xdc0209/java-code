package com.xdc.basic.api.xml;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdc.basic.api.xml.security.avoid.xxe.x4.dom4j.SecuritySAXReader;
import com.xdc.basic.commons.PathUtil;

public class X4_Dom4jTest
{
    public static void main(String arge[])
    {
        String curPath = PathUtil.getRelativePath();
        File file = new File(curPath + "students.xml");
        try
        {
            SAXReader reader = SecuritySAXReader.newInstance();
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            List<?> elements = root.elements("student");

            for (Object object : elements)
            {
                Element element = (Element) object;

                String id = element.attribute("id").getValue();
                String name = element.element("name").getText();
                String age = element.element("age").getText();
                String sex = element.element("sex").getText();
                String address = element.element("address").getText();

                System.out.println(new Student(id, name, age, sex, address));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
