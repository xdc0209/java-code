package com.xdc.basic.api.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xdc.basic.api.xml.security.avoid.xxe.x1.dom.SecurityDocumentBuilderFactory;
import com.xdc.basic.skills.GetPath;

public class X1_DomTest
{
    public static void main(String arge[])
    {
        String curPath = GetPath.getRelativePath();
        File file = new File(curPath + "students.xml");
        try
        {
            DocumentBuilderFactory factory = SecurityDocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            Element element = doc.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Element student = (Element) nodeList.item(i);
                String id = student.getAttribute("id");

                Element studentName = (Element) student.getElementsByTagName("name").item(0);
                String name = studentName.getFirstChild().getNodeValue();

                Element studentAge = (Element) student.getElementsByTagName("age").item(0);
                String age = studentAge.getFirstChild().getNodeValue();

                Element studentSex = (Element) student.getElementsByTagName("sex").item(0);
                String sex = studentSex.getFirstChild().getNodeValue();

                Element studentAddress = (Element) student.getElementsByTagName("address").item(0);
                String address = studentAddress.getFirstChild().getNodeValue();

                System.out.println(new Student(id, name, age, sex, address));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
