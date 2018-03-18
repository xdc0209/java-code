package com.xdc.basic.commons.tuple.triple;

import java.io.Serializable;

public class Triple<T1, T2, T3> implements Serializable
{
    private static final long serialVersionUID = 6073053427205335371L;

    private T1                first;

    private T2                second;

    private T3                third;

    public Triple(T1 first, T2 second, T3 third)
    {
        super();
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T1 getFirst()
    {
        return first;
    }

    public void setFirst(T1 first)
    {
        this.first = first;
    }

    public T2 getSecond()
    {
        return second;
    }

    public void setSecond(T2 second)
    {
        this.second = second;
    }

    public T3 getThird()
    {
        return third;
    }

    public void setThird(T3 third)
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
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
        if (first == null)
        {
            if (other.first != null)
            {
                return false;
            }
        }
        else if (!first.equals(other.first))
        {
            return false;
        }
        if (second == null)
        {
            if (other.second != null)
            {
                return false;
            }
        }
        else if (!second.equals(other.second))
        {
            return false;
        }
        if (third == null)
        {
            if (other.third != null)
            {
                return false;
            }
        }
        else if (!third.equals(other.third))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return first + ":" + second + ":" + third;
    }
}
