package com.xdc.basic.skills.tuple.triple;

import java.io.Serializable;

public class StringTriple implements Serializable
{
    private static final long serialVersionUID = 3294424131662177114L;

    private String            first;

    private String            second;

    private String            third;

    public StringTriple(String first, String second, String third)
    {
        super();
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    public String getSecond()
    {
        return second;
    }

    public void setSecond(String second)
    {
        this.second = second;
    }

    public String getThird()
    {
        return third;
    }

    public void setThird(String third)
    {
        this.third = third;
    }

    /**
     * 通过eclipse生成：右键--》源码--》生成hashCode()和equals()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        result = prime * result + ((third == null) ? 0 : third.hashCode());
        return result;
    }

    /**
     * 通过eclipse生成：右键--》源码--》生成hashCode()和equals()
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StringTriple other = (StringTriple) obj;
        if (first == null)
        {
            if (other.first != null)
                return false;
        }
        else if (!first.equals(other.first))
            return false;
        if (second == null)
        {
            if (other.second != null)
                return false;
        }
        else if (!second.equals(other.second))
            return false;
        if (third == null)
        {
            if (other.third != null)
                return false;
        }
        else if (!third.equals(other.third))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return first + ":" + second + ":" + third;
    }
}
