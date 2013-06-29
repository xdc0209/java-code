package com.xdc.basic.apidemo.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class CopyOfXMLConvertor
{
	private JAXBContext	jaxbContext	= null;

	public CopyOfXMLConvertor(String conextPath) throws JAXBException
	{
		this.jaxbContext = JAXBContext.newInstance(conextPath);
	}

	public Object xml2java(byte[] xml) throws JAXBException
	{
		InputStream is = new ByteArrayInputStream(xml);
		Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(is);
	}

	public byte[] java2xml(Object obj) throws JAXBException
	{
		OutputStream os = new ByteArrayOutputStream();
		Marshaller marshaller = this.jaxbContext.createMarshaller();
		marshaller.marshal(obj, os);
		return os.toString().getBytes();
	}
}
