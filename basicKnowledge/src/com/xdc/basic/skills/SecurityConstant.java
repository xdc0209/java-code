package com.xdc.basic.skills;

public interface SecurityConstant
{
    /**
     * 密码关键字：常见的作为密码字段的名词，用于ReflectionToStringBuilder过滤密码字段。安全要求：不允许明文密码和密文密码打印到日志中。
     * 
     * @see com.xdc.basic.api.apache.commons.lang3.builder.PersonReflectionToStringBuilder.toString3()
     * 
     */
    public static final String[] PASSWORD_KEYS = new String[] { "password", "passWord", "passwd", "passWd", "pwd" };
}
