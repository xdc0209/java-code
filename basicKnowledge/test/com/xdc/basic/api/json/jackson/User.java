package com.xdc.basic.api.json.jackson;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User
{
    public static class Name
    {
        private String _first, _last;

        public String getFirst()
        {
            return _first;
        }

        public String getLast()
        {
            return _last;
        }

        public void setFirst(String s)
        {
            _first = s;
        }

        public void setLast(String s)
        {
            _last = s;
        }

        @Override
        public String toString()
        {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

    public enum Gender
    {
        MALE, FEMALE
    }

    private Name    _name;
    private Gender  _gender;
    private byte[]  _userImage;
    private boolean _isVerified;

    public Name getName()
    {
        return _name;
    }

    public Gender getGender()
    {
        return _gender;
    }

    public byte[] getUserImage()
    {
        return _userImage;
    }

    public boolean isVerified()
    {
        return _isVerified;
    }

    public void setName(Name n)
    {
        _name = n;
    }

    public void setGender(Gender g)
    {
        _gender = g;
    }

    public void setUserImage(byte[] b)
    {
        _userImage = b;
    }

    public void setVerified(boolean b)
    {
        _isVerified = b;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
