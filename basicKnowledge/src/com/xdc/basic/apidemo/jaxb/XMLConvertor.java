package com.xdc.basic.apidemo.jaxb;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLConvertor
{
	private JAXBContext	jaxbContext	= null;

	public XMLConvertor(String conextPath) throws JAXBException
	{
		this.jaxbContext = JAXBContext.newInstance(conextPath);
	}

	public Object xml2java(String xml) throws JAXBException
	{
		Reader rd = new StringReader(xml);
		Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(rd);
	}

	public String java2xml(Object obj) throws JAXBException
	{
		Writer wt = new StringWriter();
		Marshaller marshaller = this.jaxbContext.createMarshaller();
		marshaller.marshal(obj, wt);
		return wt.toString();
	}
}
