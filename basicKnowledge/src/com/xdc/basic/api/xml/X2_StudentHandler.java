package com.xdc.basic.api.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class X2_StudentHandler extends DefaultHandler
{
    private Student       newStudent;
    private String        curTag;
    private List<Student> studentList;

    public List<Student> getStudentList()
    {
        return studentList;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String data = new String(ch, start, length);
        if (null != curTag)
        {
            if ("name".equalsIgnoreCase(curTag))
            {
                newStudent.setName(data);
            }
            else if ("age".equalsIgnoreCase(curTag))
            {
                newStudent.setAge(data);
            }
            else if ("sex".equalsIgnoreCase(curTag))
            {
                newStudent.setSex(data);
            }
            else if ("address".equalsIgnoreCase(curTag))
            {
                newStudent.setAddress(data);
            }
        }
    }

    @Override
    public void startDocument() throws SAXException
    {
        studentList = new ArrayList<Student>();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
        if ("student".equals(name))
        {
            newStudent = new Student();
            if (attributes != null)
            {
                newStudent.setId(attributes.getValue("id"));
            }
        }
        curTag = name;
    }

    @Override
    public void endDocument() throws SAXException
    {
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException
    {
        if ("student".equalsIgnoreCase(name))
        {
            studentList.add(newStudent);
            newStudent = null;
        }
        curTag = null;
    }
}
