package com.xdc.basic.api.xml.security.x2.sax;

import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecuritySAXParserFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SecuritySAXParserFactory.class);

    /**
     * 完全禁止DTD，推荐
     * factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
     * 
     * 不完全禁止DTD
     * factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
     * factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
     */
    public static SAXParserFactory newInstance()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try
        {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }
        catch (Exception e)
        {
            logger.error("Failed to set feature http://apache.org/xml/features/disallow-doctype-decl to true.", e);
        }

        return factory;
    }
}