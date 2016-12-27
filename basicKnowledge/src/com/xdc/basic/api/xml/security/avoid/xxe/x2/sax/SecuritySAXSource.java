package com.xdc.basic.api.xml.security.avoid.xxe.x2.sax;

import java.io.InputStream;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public final class SecuritySAXSource
{
    public static SAXSource newSecuritySAXSource(InputStream in) throws ParserConfigurationException, SAXException
    {
        return newSecuritySAXSource(new InputSource(in));
    }

    public static SAXSource newSecuritySAXSource(Reader reader) throws ParserConfigurationException, SAXException
    {
        return newSecuritySAXSource(new InputSource(reader));
    }

    public static SAXSource newSecuritySAXSource(InputSource inputSource)
            throws ParserConfigurationException, SAXException
    {
        SAXParserFactory securityFactory = SecuritySAXParserFactory.newInstance();

        // as followed copy from javax.xml.bind.helpers.AbstractUnmarshallerImpl.getXMLReader()
        securityFactory.setNamespaceAware(true);
        // there is no point in asking a validation because there is no guarantee that the document will come with a proper schemaLocation.
        securityFactory.setValidating(true);

        XMLReader xmlReader = securityFactory.newSAXParser().getXMLReader();
        xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

        SAXSource saxSource = new SAXSource(xmlReader, inputSource);

        return saxSource;
    }
}