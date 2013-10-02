package com.xdc.basic.apidemo.jaxb;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLConvertor
{
	private JAXBContext		jaxbContext		= null;
	private Marshaller		marshaller		= null;
	private Unmarshaller	unmarshaller	= null;

	public XMLConvertor(String conextPath) throws JAXBException
	{
		jaxbContext = JAXBContext.newInstance(conextPath);
		marshaller = jaxbContext.createMarshaller();
		unmarshaller = jaxbContext.createUnmarshaller();
	}

	public Object xml2java(Reader rd) throws JAXBException
	{
		return unmarshaller.unmarshal(rd);
	}

	public void java2xml(Object obj, Writer wt) throws JAXBException
	{
		marshaller.marshal(obj, wt);
	}

	public Object xml2java(InputStream is) throws JAXBException
	{
		return unmarshaller.unmarshal(is);
	}

	public void java2xml(Object obj, OutputStream os) throws JAXBException
	{
		marshaller.marshal(obj, os);
	}

	public Object xml2java(File file) throws JAXBException
	{
		return unmarshaller.unmarshal(file);
	}

	public void java2xml(Object obj, File file) throws JAXBException
	{
		marshaller.marshal(obj, file);
	}
}
