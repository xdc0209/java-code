package com.xdc.basic.api.json.bson4jackson;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person
{
    private String _name;

    public void setName(String name)
    {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
