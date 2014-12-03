package com.xdc.basic.api.jmx.virgo.cli.core;

public class Option
{
    private String  option;
    private boolean required;
    private boolean withArgument;

    public Option()
    {
        super();
    }

    public Option(String option, boolean required, boolean withArgument)
    {
        super();
        this.option = option;
        this.required = required;
        this.withArgument = withArgument;
    }

    public String getOption()
    {
        return option;
    }

    public void setOption(String option)
    {
        this.option = option;
    }

    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public boolean isWithArgument()
    {
        return withArgument;
    }

    public void setWithArgument(boolean withArgument)
    {
        this.withArgument = withArgument;
    }
}