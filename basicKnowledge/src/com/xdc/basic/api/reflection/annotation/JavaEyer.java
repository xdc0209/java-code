package com.xdc.basic.api.reflection.annotation;

@Description("javaeye,做最棒的软件开发交流社区")
public class JavaEyer
{
    @Name(originate = "创始人:robbin", community = "javaEye")
    public String getName()
    {
        return null;
    }

    @Name(originate = "创始人:江南白衣", community = "springside")
    public String getName2()
    {
        return "借用两位的id一用,写这一个例子,请见谅!";
    }
}