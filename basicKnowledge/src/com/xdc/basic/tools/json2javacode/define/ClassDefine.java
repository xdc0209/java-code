package com.xdc.basic.tools.json2javacode.define;

import java.util.ArrayList;
import java.util.List;

public class ClassDefine
{
    private String            javaPackageName;
    private String            objectClassName;
    private List<FieldDefine> fields = new ArrayList<FieldDefine>();

    public ClassDefine(String javaPackageName, String objectClassName)
    {
        super();
        this.javaPackageName = javaPackageName;
        this.objectClassName = objectClassName;
    }

    public String getJavaPackageName()
    {
        return javaPackageName;
    }

    public void setJavaPackageName(String javaPackageName)
    {
        this.javaPackageName = javaPackageName;
    }

    public String getObjectClassName()
    {
        return objectClassName;
    }

    public void setObjectClassName(String objectClassName)
    {
        this.objectClassName = objectClassName;
    }

    public List<FieldDefine> getFields()
    {
        return fields;
    }

    public void setFields(List<FieldDefine> fields)
    {
        this.fields = fields;
    }

    public void addField(FieldDefine field)
    {
        this.fields.add(field);
    }

    public void addField(String name, String type)
    {
        this.fields.add(new FieldDefine(name, type));
    }

    public void addField(String name, String type, String parameterizedType)
    {
        this.fields.add(new FieldDefine(name, type, parameterizedType));
    }
}
