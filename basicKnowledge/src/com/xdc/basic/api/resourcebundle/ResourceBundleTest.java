package com.xdc.basic.api.resourcebundle;

import com.xdc.basic.api.resourcebundle.errorcode.ErrorCode;
import com.xdc.basic.api.resourcebundle.language.LocaleConfig;

/**
 * 程序国际化
 */
public class ResourceBundleTest
{
    public static void main(String[] args)
    {
        System.out.println("系统默认语言：");
        System.out.println(ErrorCode.PHONE_NUMBER_NOT_VALID.format("123456"));
        System.out.println(ErrorCode.SERVICE_BUSY.format());
        System.out.println();

        System.out.println("英文：");
        LocaleConfig.setLocale_en_US();
        ErrorCode.reset();
        System.out.println(ErrorCode.PHONE_NUMBER_NOT_VALID.format("123456"));
        System.out.println(ErrorCode.SERVICE_BUSY.format());
        System.out.println();

        System.out.println("中文：");
        LocaleConfig.setLocale_zh_CN();
        ErrorCode.reset();
        System.out.println(ErrorCode.PHONE_NUMBER_NOT_VALID.format("123456"));
        System.out.println(ErrorCode.SERVICE_BUSY.format());
        System.out.println();
    }
}
