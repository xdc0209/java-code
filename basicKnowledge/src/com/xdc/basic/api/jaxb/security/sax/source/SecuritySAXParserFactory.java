package com.xdc.basic.api.jaxb.security.sax.source;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecuritySAXParserFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SecuritySAXParserFactory.class);

    private SecuritySAXParserFactory()
    {
    }

    public static SAXParserFactory newSecurityInstance()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try
        {
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        }
        catch (Exception ex)
        {
            logger.error("Failed to set feature http://xml.org/sax/features/external-general-entities to false", ex);
        }

        try
        {
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        }
        catch (Exception ex)
        {
            logger.error("Failed to set feature http://xml.org/sax/features/external-parameter-entities to false", ex);
        }

        try
        {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        }
        catch (Exception ex)
        {
            logger.error("Failed to set feature XMLConstants.FEATURE_SECURE_PROCESSING to true", ex);
        }

        return factory;
    }
}