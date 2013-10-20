package com.xdc.basic.api.apache.commons.lang3;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串常用操作
 * 
 * @author xdc
 * 
 */
public class StringUtilsTest
{
    public static void main(String[] args)
    {
        // 判断某字符串是否为空，为空的标准是 str==null 或 str.length()==0
        StringUtils.isEmpty(null);
        StringUtils.isEmpty("");

        // 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
        StringUtils.isBlank(null);
        StringUtils.isBlank("");
        StringUtils.isBlank(" ");
        StringUtils.isBlank("\t \n \f \r"); // 对于制表符、换行符、换页符和回车符

        // 字符串变成小写，要设置区域，防止不同语言转换结果不一样，是程序有不一样的行为
        String name = "XuDachao";
        String lowerCaseName = StringUtils.lowerCase(name, Locale.US);

        // 比较两字符串是相等
        StringUtils.equals(lowerCaseName, name); // false
        StringUtils.equals(lowerCaseName, "xudachao"); // true

        // 忽略大小写，比较字符串
        StringUtils.equalsIgnoreCase(lowerCaseName, name); // true

        String[] ips = StringUtils.split("127.0.0.1;10.10.10.10", ';');
        for (String ip : ips)
        {
            System.out.println(ip);
        }

        // 获得之间的字符串
        StringUtils.substringBetween("name[xudachao]", "[", "]");

        // 去除前缀
        StringUtils.removeStart("http://127.0.0.1", "http://");

        // 去除前缀2
        StringUtils.substringAfter("http://127.0.0.1", "http://");
    }
}
