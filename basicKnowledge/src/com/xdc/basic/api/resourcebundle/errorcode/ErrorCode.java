package com.xdc.basic.api.resourcebundle.errorcode;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 错误码国际化
 * 
 * 参考此类编写：org.kohsuke.args4j.Messages
 */
public enum ErrorCode
{
    PHONE_NUMBER_NOT_VALID("1001"), SERVICE_BUSY("1002");

    // 静态成员，所以对象使用此成员访问国际化文件
    private static ResourceBundle rb;

    private static Locale         locale = Locale.getDefault();

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
                    rb = ResourceBundle.getBundle(ErrorCode.class.getName(), locale);
                }
            }
        }
        return MessageFormat.format(rb.getString(getCode()), args);
    }

    public static void changeLacale(Locale locale)
    {
        if (locale == null)
        {
            return;
        }

        synchronized (ErrorCode.class)
        {
            ErrorCode.locale = locale;
            rb = ResourceBundle.getBundle(ErrorCode.class.getName(), locale);
        }
    }
}
