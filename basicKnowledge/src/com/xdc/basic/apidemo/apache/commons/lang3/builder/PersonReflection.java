package com.xdc.basic.apidemo.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
public class PersonReflection implements Comparable<PersonReflection>
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
		// 根据反射获取hashcode ,除去某些字段
		// HashCodeBuilder.reflectionHashCode(this, new String[]{"age","smoker"})
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
		// 根据反射比较对象 ,除去某些字段
		// EqualsBuilder.reflectionEquals(this, obj, new String[]{"age","smoker"});
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		// 不具有除去某些字段的功能，如果有需求请使用ReflectionToStringBuilder
	}

	// Two Objects that compare equal using equals(Object) should normally also compare equal using compareTo(Object).
	// 不推荐。由于该类涉及到比较字段的顺序，所以并不推荐使用该族中的方法自动映射字段，而最好自己决定那些字段参与比较及它们的顺序。该族方法的返回值为一整数，意义同Comparable接口的compareTo方法。
	@Override
	public int compareTo(PersonReflection o)
	{
		return CompareToBuilder.reflectionCompare(this, o);
	}
}
