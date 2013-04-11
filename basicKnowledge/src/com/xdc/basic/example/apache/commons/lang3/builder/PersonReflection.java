package com.xdc.basic.example.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ReflectionToStringBuilder 继承自 ToStringBuilder，所以更加强大，具有排除功能
 * 
 * @author xdc
 * 
 */
public class PersonReflection
{
	String	name;
	int	    age;
	boolean	smoker;

	public PersonReflection(String name, int age, boolean smoker)
	{
		super();
		this.name = name;
		this.age = age;
		this.smoker = smoker;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
