package com.xdc.basic.api.hibernate.orm3.spring.framwaork.entity;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "Student")
public class Student implements Serializable
{
    private static final long       serialVersionUID = 1561892538504989863L;

    private String                  id;
    private String                  name;
    private int                     age;
    private byte[]                  image;
    private String                  content;

    // 注意：这里一定要使用Hashmap，否则会报错
    private HashMap<String, Object> extProperties    = new HashMap<String, Object>();

    @Id
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    // @Lob 这个与下面的作用相同，对于byte[]，hibernate默认使用BlobByteArrayType
    @Type(type = "org.springframework.orm.hibernate3.support.BlobByteArrayType")
    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    // @Lob 这个与下面的作用相同，对于String，hibernate默认使用ClobStringType
    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    // @Lob 这个与下面的作用相同，对于HashMap，hibernate默认使用BlobSerializableType
    @Type(type = "org.springframework.orm.hibernate3.support.BlobSerializableType")
    public HashMap<String, Object> getExtProperties()
    {
        return extProperties;
    }

    public void setExtProperties(HashMap<String, Object> extProperties)
    {
        this.extProperties = extProperties;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
