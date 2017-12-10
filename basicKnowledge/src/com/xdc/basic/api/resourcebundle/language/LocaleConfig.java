package com.xdc.basic.api.resourcebundle.language;

import java.util.Locale;

/**
 * 语言设置
 * 
 * 本应使用配置文件配置程序使用的语言，但为了简化例子，用此类实现
 */
public class LocaleConfig
{
    private static Locale locale = Locale.getDefault();

    public static Locale getLocale()
    {
        return locale;
    }

    public static void setLocale(Locale locale)
    {
        LocaleConfig.locale = locale;
    }

    public static void setLocale_default()
    {
        LocaleConfig.locale = Locale.getDefault();
    }

    public static void setLocale_zh_CN()
    {
        // 等效写法 LocaleConfig.locale = new Locale("zh", "CN");
        LocaleConfig.locale = Locale.CHINA;
    }

    public static void setLocale_en_US()
    {
        // 等效写法 LocaleConfig.locale = new Locale("en", "US");
        LocaleConfig.locale = Locale.US;
    }
}
