package com.xdc.basic.api.apache.commons.lang3;

import org.apache.commons.lang3.SystemUtils;

public class SystemUtilsTest
{
    public static void main(String[] args)
    {
        // 判断操作系统类型
        System.out.println(SystemUtils.IS_OS_LINUX);
        System.out.println(SystemUtils.IS_OS_WINDOWS);

        // 换行符
        System.out.println(SystemUtils.LINE_SEPARATOR);
        // 路径中的文件分隔符
        System.out.println(SystemUtils.FILE_SEPARATOR);
        // 环境变量中的路径分隔符
        System.out.println(SystemUtils.PATH_SEPARATOR);

        // java class path
        System.out.println(SystemUtils.JAVA_CLASS_PATH);

        // 用户的账户名称
        System.out.println(SystemUtils.USER_NAME);
        // 用户语言
        System.out.println(SystemUtils.USER_LANGUAGE);
        // 用户的主目录
        System.out.println(SystemUtils.USER_HOME);
        // 用户的当前工作目录, eclipse中默认为该工程根目录
        System.out.println(SystemUtils.USER_DIR);
    }
}
