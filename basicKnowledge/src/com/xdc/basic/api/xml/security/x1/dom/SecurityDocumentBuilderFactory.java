package com.xdc.basic.api.xml.security.x1.dom;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityDocumentBuilderFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityDocumentBuilderFactory.class);

    public static DocumentBuilderFactory newInstance()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }
        catch (ParserConfigurationException e)
        {
            logger.error("Failed to set feature http://apache.org/xml/features/disallow-doctype-decl to true.", e);
        }

        return factory;
    }
}
