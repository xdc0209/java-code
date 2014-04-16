package com.xdc.basic.tools.restclient.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlTool
{
    private JAXBContext  jaxbContext  = null;
    private Marshaller   marshaller   = null;
    private Unmarshaller unmarshaller = null;

    public XmlTool(String conextPath) throws JAXBException
    {
        jaxbContext = JAXBContext.newInstance(conextPath);
        marshaller = jaxbContext.createMarshaller();
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public Object xml2Java(Reader rd) throws JAXBException
    {
        return unmarshaller.unmarshal(rd);
    }

    public void java2Xml(Object obj, Writer wt) throws JAXBException
    {
        marshaller.marshal(obj, wt);
    }

    public Object xml2Java(InputStream is) throws JAXBException
    {
        return unmarshaller.unmarshal(is);
    }

    public void java2Xml(Object obj, OutputStream os) throws JAXBException
    {
        marshaller.marshal(obj, os);
    }

    public Object xml2Java(File file) throws JAXBException
    {
        return unmarshaller.unmarshal(file);
    }

    public void java2Xml(Object obj, File file) throws JAXBException
    {
        marshaller.marshal(obj, file);
    }

    public Object xmlString2Java(String xmlString) throws JAXBException
    {
        StringReader stringReader = new StringReader(xmlString);
        return this.xml2Java(stringReader);
    }

    public String java2XmlString(Object obj) throws JAXBException
    {
        StringWriter stringWriter = new StringWriter();
        this.java2Xml(obj, stringWriter);
        return stringWriter.toString();
    }

    public Object xmlByte2Java(byte[] xmlByte) throws JAXBException
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlByte);
        return this.xml2Java(inputStream);
    }

    public byte[] java2XmlByte(Object obj) throws JAXBException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.java2Xml(obj, outputStream);
        return outputStream.toByteArray();
    }

    public static String format(String xmlString)
    {
        SAXBuilder builder = new SAXBuilder();

        // Create the document
        Document doc = null;
        try
        {
            doc = builder.build(xmlString);
        }
        catch (JDOMException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Output the document, use standard formatter
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        return outputter.outputString(doc);
    }
}
