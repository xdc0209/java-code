package com.xdc.basic.tools.sqlplus;

import java.util.HashMap;
import java.util.Map;

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

    private final String  option;
    private final boolean required;
    private final boolean withArgument;

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

    public boolean isRequired()
    {
        return required;
    }

    public boolean isWithArgument()
    {
        return withArgument;
    }

    @Override
    public String toString()
    {
        return option;
    }

    // Implementing a fromString method on an enum type
    private static final Map<String, Option> stringToEnum = new HashMap<String, Option>();

    static
    {
        // Initialize map from constant name to enum constant
        for (Option op : values())
        {
            stringToEnum.put(op.toString(), op);
        }
    }

    // Returns Operation for string, or null if string is invalid
    public static Option fromString(String symbol)
    {
        return stringToEnum.get(symbol);
    }
}