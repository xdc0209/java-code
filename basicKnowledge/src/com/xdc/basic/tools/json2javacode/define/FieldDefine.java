package com.xdc.basic.tools.json2javacode.define;

public class FieldDefine
{
    private String name;
    private String type;

    // 参数化类型，主要用于list的泛型化
    private String parameterizedType;

    public FieldDefine()
    {
        super();
    }

    public FieldDefine(String name, String type)
    {
        super();
        this.name = name;
        this.type = type;
    }

    public FieldDefine(String name, String type, String parameterizedType)
    {
        super();
        this.name = name;
        this.type = type;
        this.parameterizedType = parameterizedType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getParameterizedType()
    {
        return parameterizedType;
    }

    public void setParameterizedType(String parameterizedType)
    {
        this.parameterizedType = parameterizedType;
    }
}