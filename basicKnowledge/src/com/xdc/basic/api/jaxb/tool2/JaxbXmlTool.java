package com.xdc.basic.api.jaxb.tool2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * jaxb解析XML
 */
public class JaxbXmlTool
{
    private JaxbXmlTool()
    {
    }

    private static <T> Unmarshaller createUnmarshaller(Class<T> clazz) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller;
    }

    private static <T> Marshaller createMarshaller(Class<T> clazz) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    @SuppressWarnings("unchecked")
    public static <T> T xml2Java(Class<T> clazz, Reader rd) throws JAXBException
    {
        Unmarshaller unmarshaller = createUnmarshaller(clazz);
        return (T) unmarshaller.unmarshal(rd);
    }

    public static <T> void java2Xml(Class<T> clazz, T t, Writer wt) throws JAXBException
    {
        Marshaller marshaller = createMarshaller(clazz);
        marshaller.marshal(t, wt);
    }

    @SuppressWarnings("unchecked")
    public static <T> T xml2Java(Class<T> clazz, InputStream is) throws JAXBException
    {
        Unmarshaller unmarshaller = createUnmarshaller(clazz);
        return (T) unmarshaller.unmarshal(is);
    }

    public static <T> void java2Xml(Class<T> clazz, T t, OutputStream os) throws JAXBException
    {
        Marshaller marshaller = createMarshaller(clazz);
        marshaller.marshal(t, os);
    }

    @SuppressWarnings("unchecked")
    public static <T> T xml2Java(Class<T> clazz, File file) throws JAXBException
    {
        Unmarshaller unmarshaller = createUnmarshaller(clazz);
        return (T) unmarshaller.unmarshal(file);
    }

    public static <T> void java2Xml(Class<T> clazz, T t, File file) throws JAXBException
    {
        Marshaller marshaller = createMarshaller(clazz);
        marshaller.marshal(t, file);
    }

    @SuppressWarnings("unchecked")
    public static <T> T xml2Java(Class<T> clazz, Source source) throws JAXBException
    {
        Unmarshaller unmarshaller = createUnmarshaller(clazz);
        return (T) unmarshaller.unmarshal(source);
    }

    public static <T> void java2Xml(Class<T> clazz, T t, Result result) throws JAXBException
    {
        Marshaller marshaller = createMarshaller(clazz);
        marshaller.marshal(t, result);
    }

    public static <T> T xmlString2Java(Class<T> clazz, String xmlString) throws JAXBException
    {
        StringReader stringReader = new StringReader(xmlString);
        return xml2Java(clazz, stringReader);
    }

    public static <T> String java2XmlString(Class<T> clazz, T t) throws JAXBException
    {
        StringWriter stringWriter = new StringWriter();
        java2Xml(clazz, t, stringWriter);
        return stringWriter.toString();
    }

    public static <T> T xmlByte2Java(Class<T> clazz, byte[] xmlByte) throws JAXBException
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlByte);
        return xml2Java(clazz, inputStream);
    }

    public static <T> byte[] java2XmlByte(Class<T> clazz, T t) throws JAXBException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        java2Xml(clazz, t, outputStream);
        return outputStream.toByteArray();
    }
}