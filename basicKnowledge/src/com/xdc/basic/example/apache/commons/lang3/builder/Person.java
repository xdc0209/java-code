package com.xdc.basic.example.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person
{
	String	name;
	int	    age;
	boolean	smoker;

	public Person(String name, int age, boolean smoker)
	{
		super();
		this.name = name;
		this.age = age;
		this.smoker = smoker;
	}

	@Override
	public int hashCode()
	{
		// you pick a hard-coded, randomly chosen, non-zero, odd number
		// ideally different for each class
		return new HashCodeBuilder(17, 37).append(name).append(age).append(smoker).toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (obj == this)
		{
			return true;
		}
		if (obj.getClass() != getClass())
		{
			return false;
		}
		Person other = (Person) obj;
		return new EqualsBuilder().append(name, other.name).append(age, other.age).append(smoker, other.smoker)
		        .isEquals();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name).append("age", age)
		        .append("smoker", smoker).toString();
	}
}
