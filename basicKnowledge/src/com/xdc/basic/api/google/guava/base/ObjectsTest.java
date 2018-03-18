package com.xdc.basic.api.google.guava.base;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

/**
 * 参考：https://github.com/stuartgunter/guava-demo/blob/master/src/main/java/org/stuartgunter/demo/guava/basics/Person.java
 */
public class ObjectsTest
{
    public static void main(String[] args)
    {
        // 注意：JDK7引入的Objects类提供了一样的方法Objects.equals。
        Objects.equal("abc", "abc"); // returns true
        Objects.equal(null, "abc"); // returns false
        Objects.equal("abc", null); // returns false
        Objects.equal(null, null); // returns true
    }
}

class Person implements Comparable<Person>
{
    private int    id;
    private String firstName;
    private String lastName;

    public Person(int id, String firstName, String lastName)
    {
        // if (id <= 0)
        // {
        // throw new IllegalArgumentException("id must be greater than zero. Given [" + id + "].");
        // }
        //
        // if (firstName == null)
        // {
        // throw new NullPointerException("firstName may not be null.");
        // }
        //
        // if (lastName == null)
        // {
        // throw new NullPointerException("lastName may not be null.");
        // }

        Preconditions.checkArgument(id > 0, "id must be greater than zero. Given [%s].", id);
        Preconditions.checkNotNull(firstName, "firstName may not be null.");
        Preconditions.checkNotNull(lastName, "lastName may not be null.");

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        // if (id <= 0)
        // {
        // throw new IllegalArgumentException("id must be greater than zero. Given [" + id + "].");
        // }

        Preconditions.checkArgument(id > 0, "id must be greater than zero. Given [%s].", id);
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        // if (id <= 0)
        // {
        // throw new IllegalStateException("id must be greater than zero.");
        // }
        //
        // if (firstName == null)
        // {
        // throw new NullPointerException("firstName may not be null.");
        // }

        Preconditions.checkState(id > 0, "id must be greater than zero.");
        Preconditions.checkNotNull(firstName, "firstName may not be null.");
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        // if (id <= 0)
        // {
        // throw new IllegalStateException("id must be greater than zero.");
        // }
        //
        // if (lastName == null)
        // {
        // throw new NullPointerException("lastName may not be null.");
        // }

        Preconditions.checkState(id > 0, "id must be greater than zero.");
        Preconditions.checkNotNull(lastName, "lastName may not be null.");
        this.lastName = lastName;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id, firstName, lastName);
    }

    @Override
    public boolean equals(Object obj)
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
        return Objects.equal(this.id, that.id) && Objects.equal(this.firstName, that.firstName)
                && Objects.equal(this.lastName, that.lastName);
    }

    @Override
    public int compareTo(Person that)
    {
        if (that == null)
        {
            return 1;
        }

        return ComparisonChain.start().compare(this.id, that.id).compare(this.firstName, that.firstName)
                .compare(this.lastName, that.lastName).result();
    }
}
