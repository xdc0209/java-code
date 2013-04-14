package com.xdc.basic.example.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
		return this.xml2java(xml.getBytes());
	}

	public Object xml2java(byte[] xml) throws JAXBException
	{
		InputStream is = new ByteArrayInputStream(xml);
		Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(is);
	}

	public String java2xml(Object obj) throws JAXBException
	{
		OutputStream os = new ByteArrayOutputStream();
		Marshaller marshaller = this.jaxbContext.createMarshaller();
		marshaller.marshal(obj, os);
		return os.toString();
	}
}
