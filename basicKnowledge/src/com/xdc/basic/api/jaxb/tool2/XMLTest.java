package com.xdc.basic.api.jaxb.tool2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.xdc.basic.api.jaxb.user.Users;
import com.xdc.basic.api.xml.security.avoid.xxe.x2.sax.SecuritySAXSource;

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
    public void xmlString2Java() throws JAXBException, ParserConfigurationException, SAXException
    {
        StringReader stringReader = new StringReader(usersXML);
        Users users = JaxbXmlTool.xml2Java(Users.class, SecuritySAXSource.newSecuritySAXSource(stringReader));
        System.out.println(users);

        StringWriter stringWriter = new StringWriter();
        JaxbXmlTool.java2Xml(Users.class, users, stringWriter);
        System.out.println(stringWriter);
    }

    @Test
    public void xmlBytes2Java() throws JAXBException, ParserConfigurationException, SAXException
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(usersXML.getBytes());
        Users users = JaxbXmlTool.xml2Java(Users.class, SecuritySAXSource.newSecuritySAXSource(inputStream));
        System.out.println(users);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JaxbXmlTool.java2Xml(Users.class, users, outputStream);
        System.out.println(outputStream.toString());
    }
}
