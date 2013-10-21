package com.xdc.basic.api.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person implements Comparable<Person>
{
    String  name;
    int     age;
    boolean smoker;

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
        Person that = (Person) obj;
        return new EqualsBuilder().append(name, that.name).append(age, that.age).append(smoker, that.smoker).isEquals();
    }

    public boolean equals2(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (!(obj instanceof Person))
        {
            return false;
        }
        Person that = (Person) obj;
        return new EqualsBuilder().append(name, that.name).append(age, that.age).append(smoker, that.smoker).isEquals();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name).append("age", age)
                .append("smoker", smoker).toString();
    }

    // Two Objects that compare equal using equals(Object) should normally also
    // compare equal using compareTo(Object).
    // 先比较年龄，再比较姓名，再比较是否抽烟
    @Override
    public int compareTo(Person o)
    {
        // 用不用判断null呢？
        Person that = o;
        return new CompareToBuilder().append(this.age, that.age).append(this.name, that.name)
                .append(this.smoker, that.smoker).toComparison();
    }
}
