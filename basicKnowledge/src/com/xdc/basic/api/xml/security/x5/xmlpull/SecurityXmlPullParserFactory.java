package com.xdc.basic.api.xml.security.x5.xmlpull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class SecurityXmlPullParserFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityXmlPullParserFactory.class);

    public static XmlPullParserFactory newInstance() throws XmlPullParserException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        try
        {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }
        catch (XmlPullParserException e)
        {
            logger.error("Failed to set feature http://apache.org/xml/features/disallow-doctype-decl to true.", e);
        }

        return factory;
    }
}
