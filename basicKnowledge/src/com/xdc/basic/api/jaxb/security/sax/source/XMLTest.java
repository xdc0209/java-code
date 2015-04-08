package com.xdc.basic.api.jaxb.security.sax.source;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.xdc.basic.api.jaxb.user.Users;

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

        JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Users users = (Users) unmarshaller.unmarshal(SecuritySAXSource.newSecuritySAXSource(stringReader));

        System.out.println(users);
    }
}