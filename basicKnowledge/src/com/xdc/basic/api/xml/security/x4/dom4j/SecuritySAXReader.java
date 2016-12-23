package com.xdc.basic.api.xml.security.x4.dom4j;

import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SecuritySAXReader
{
    private static final Logger logger = LoggerFactory.getLogger(SecuritySAXReader.class);

    public static SAXReader newInstance()
    {
        SAXReader reader = new SAXReader();

        try
        {
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }
        catch (SAXException e)
        {
            logger.error("Failed to set feature http://apache.org/xml/features/disallow-doctype-decl to true.", e);
        }

        return reader;
    }
}
