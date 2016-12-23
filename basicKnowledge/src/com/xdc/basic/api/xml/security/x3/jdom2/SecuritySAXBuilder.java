package com.xdc.basic.api.xml.security.x3.jdom2;

import org.jdom2.input.SAXBuilder;

public class SecuritySAXBuilder
{
    public static SAXBuilder newInstance()
    {
        SAXBuilder builder = new SAXBuilder();
        builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        return builder;
    }
}
