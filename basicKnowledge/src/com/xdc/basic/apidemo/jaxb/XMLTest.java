package com.xdc.basic.apidemo.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.xdc.basic.apidemo.jaxb.user.Users;

/**
 * xml解析
 * 
 * @author xdc
 * 
 */
public class XMLTest
{
    private final String usersXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><users><user><name>小李</name><sex>男</sex><age>24</age></user><user><name>小丽</name><sex>女</sex><age>18</age></user><user><name>小王</name><sex>男</sex><age>22</age></user></users>";

    @Test
    public void xmlString2Java() throws JAXBException
    {
        final String XML_MODEL_MEMBER = Users.class.getPackage().getName();

        XMLConvertor xmlConvertor = new XMLConvertor(XML_MODEL_MEMBER);

        StringReader stringReader = new StringReader(usersXML);
        Users users = (Users) xmlConvertor.xml2java(stringReader);
        System.out.println(users);

        StringWriter stringWriter = new StringWriter();
        xmlConvertor.java2xml(users, stringWriter);
        System.out.println(stringWriter);
    }

    @Test
    public void xmlBytes2Java() throws JAXBException
    {
        final String XML_MODEL_MEMBER = Users.class.getPackage().getName();

        XMLConvertor xmlConvertor = new XMLConvertor(XML_MODEL_MEMBER);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(usersXML.getBytes());
        Users users = (Users) xmlConvertor.xml2java(inputStream);
        System.out.println(users);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xmlConvertor.java2xml(users, outputStream);
        System.out.println(outputStream.toString());
    }
}
