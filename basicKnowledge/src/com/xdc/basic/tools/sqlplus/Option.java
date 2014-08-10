package com.xdc.basic.tools.sqlplus;

public enum Option
{
    /** * 输入为文件，可选，带参数 */
    FILE("-f", false, true),

    /** * 输入为sql语句，可选，带参数 */
    SQL("-s", false, true),

    /** * oralce主机名或ip，必选，带参数 */
    HOST("-H", true, true),

    /** * oralce端口，必选，带参数 */
    PORT("-P", true, true),

    /** * oralce的SID，必选，带参数 */
    SID("-S", true, true),

    /** * oralce用户名，必选，带参数 */
    USER("-u", true, true),

    /** * oralce密码，必选，带参数 */
    PASSWORD("-p", true, true),

    /** * 以dba角色登陆oralce，可选，不带参数 */
    DBA("--dba", false, false);

    private String  option;
    private boolean required;
    private boolean withArgument;

    private Option(String option, boolean required, boolean withArgument)
    {
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

    @Override
    public String toString()
    {
        return option;
    }

    public static Option fromString(String str)
    {
        for (Option option : Option.values())
        {
            if (option.option.equalsIgnoreCase(str))
            {
                return option;
            }
        }
        return null;
    }
}