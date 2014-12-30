package com.xdc.basic.skills.finderrorcode;

public enum ErrorEnums
{
    DATABASE_ERROR("database_error", 1), RESOUCE_NOT_EXISTS("resouce_not_exists", 2);

    private String name;
    private int    code;

    private ErrorEnums(String name, int code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}
