package com.xdc.basic.api.xml.security.avoid.xxe.x2.sax;

import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecuritySAXParserFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SecuritySAXParserFactory.class);

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
