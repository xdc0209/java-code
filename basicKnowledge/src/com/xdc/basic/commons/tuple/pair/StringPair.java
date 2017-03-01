package com.xdc.basic.commons.tuple.pair;

import java.io.Serializable;

public class StringPair implements Serializable
{
    private static final long serialVersionUID = 3118267258639752674L;

    private String            first;

    private String            second;

    public StringPair(String first, String second)
    {
        this.first = first;
        this.second = second;
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
        StringPair other = (StringPair) obj;
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
        return true;
    }

    @Override
    public String toString()
    {
        return first + ":" + second;
    }
}
