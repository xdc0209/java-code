package com.xdc.basic.api.xml.security.avoid.xxe.x5.xmlpull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * XmlPull不支持设置factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
 * 设置时会抛出异常，提示：unsupported feature。所以是只能采用不完全禁止DTD的方式。
 */
public class SecurityXmlPullParserFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityXmlPullParserFactory.class);

    public static XmlPullParserFactory newInstance() throws XmlPullParserException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        try
        {
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        }
        catch (XmlPullParserException e)
        {
            logger.error("Failed to set feature http://xml.org/sax/features/external-general-entities to false.", e);
        }

        try
        {
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        }
        catch (XmlPullParserException e)
        {
            logger.error("Failed to set feature http://xml.org/sax/features/external-parameter-entities to false.", e);
        }

        return factory;
    }
}
