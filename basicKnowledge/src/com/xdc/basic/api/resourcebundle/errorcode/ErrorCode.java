package com.xdc.basic.api.resourcebundle.errorcode;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.xdc.basic.api.resourcebundle.language.LocaleConfig;

/**
 * 错误码国际化
 * 
 * 参考此类编写：org.kohsuke.args4j.Messages
 */
public enum ErrorCode
{
    PHONE_NUMBER_NOT_VALID("1001"), SERVICE_BUSY("1002");

    // 静态成员，所以对象使用此成员访问文件
    private static ResourceBundle rb;

    private final String          code;

    // 注意构造函数要为私有，java枚举采用单例模式，无需外接调用构造方法
    private ErrorCode(String code)
    {
        this.code = code;
    }

    // 不要实现set方法
    public String getCode()
    {
        return code;
    }

    public String format(Object... args)
    {
        if (rb == null)
        {
            synchronized (ErrorCode.class)
            {
                if (rb == null)
                {
                    rb = ResourceBundle.getBundle(ErrorCode.class.getName(), LocaleConfig.getLocale());
                }
            }
        }
        return MessageFormat.format(rb.getString(getCode()), args);
    }

    public static void reset()
    {
        if (rb != null)
        {
            synchronized (ErrorCode.class)
            {
                rb = null;
            }
        }
    }
}
