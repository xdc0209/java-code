package com.xdc.basic.api.xml;

import java.io.File;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.xdc.basic.skills.GetCurPath;

public class X3_JdomTest
{
    public static void main(String arge[])
    {
        String curPath = GetCurPath.getCurPath();
        File file = new File(curPath + "students.xml");
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            Element root = doc.getRootElement();
            List<Element> allChildren = root.getChildren();

            for (Element element : allChildren)
            {
                String id = element.getAttribute("id").getValue();
                String name = element.getChild("name").getText();
                String age = element.getChild("age").getText();
                String sex = element.getChild("sex").getText();
                String address = element.getChild("address").getText();

                System.out.println(new Student(id, name, age, sex, address));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
