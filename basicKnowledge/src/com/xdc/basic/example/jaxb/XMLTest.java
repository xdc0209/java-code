package com.xdc.basic.example.jaxb;

import javax.xml.bind.JAXBException;

import com.xdc.basic.example.jaxb.user.Users;

/**
 * 明天继续
 * 
 * @author xdc
 * 
 */
public class XMLTest
{
	private static String	usersXML	= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><users><user><name>小李</name><sex>男</sex><age>24</age></user><user><name>小丽</name><sex>女</sex><age>18</age></user><user><name>小王</name><sex>男</sex><age>22</age></user></users>";

	public static void main(String[] args) throws JAXBException
	{
		final String XML_MODEL_MEMBER = "com.xdc.basic.example.jaxb.user";

		XMLConvertor xmlConvertor = null;
		xmlConvertor = new XMLConvertor(XML_MODEL_MEMBER);

		// xml2java
		Users users = (Users) xmlConvertor.xml2java(usersXML);
		System.out.println(users);

		// java2xml
		String string = xmlConvertor.java2xml(users);
		System.out.println(string);
	}
}
