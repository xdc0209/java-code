package com.xdc.basic.tools.restframework.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

import com.xdc.basic.tools.restframework.core.MethodType;
import com.xdc.basic.tools.restframework.message.CallerException;

public class CallerTool
{
    // 匹配{}之间的字符串: http://localhost:8080/rest/products/{keyword}?apikey={apikey}&page={page}&max={max}
    private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

    public static String expandUrl(String url, Object[] urlVariables)
    {
        if (url == null)
        {
            return null;
        }
        if (url.indexOf('{') == -1)
        {
            return url;
        }

        Iterator<Object> urlVariablesIterator = Arrays.asList(urlVariables).iterator();

        Matcher matcher = NAMES_PATTERN.matcher(url);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            String variableName = matcher.group(1);
            if (!urlVariablesIterator.hasNext())
            {
                throw new IllegalArgumentException(
                        "Not enough variable values available to expand '" + variableName + "'");
            }
            Object variableValue = urlVariablesIterator.next();

            String variableValueString = getVariableValueAsString(variableValue);
            String replacement = Matcher.quoteReplacement(variableValueString);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String getVariableValueAsString(Object variableValue)
    {
        return variableValue != null ? variableValue.toString() : "";
    }

    /**
     * 解析body体
     * 
     * Restful消息规范：1. POST创建 PUT修改 2. get方法不允許有body体
     * 
     * 但是在项目中，一般delete方法也没有body体，为了框架的易用性，delete方法不支持有body体
     */
    public static Object resolveBody(MethodType methodType, Object[] args) throws CallerException
    {
        if (methodType == MethodType.POST || methodType == MethodType.PUT)
        {
            if (ArrayUtils.isEmpty(args))
            {
                throw new CallerException(
                        "Method " + methodType + " must has at least on arg for the first used as body.");
            }

            return args[0];
        }
        else if (methodType == MethodType.GET || methodType == MethodType.DELETE)
        {
            return null;
        }
        else
        {
            throw new UnsupportedOperationException("Method " + methodType + " is not support.");
        }
    }

    /**
     * 解析url变量
     * 
     * @throws CallerException
     */
    public static Object[] resolveUrlVariables(MethodType methodType, Object[] args) throws CallerException
    {
        if (methodType == MethodType.POST || methodType == MethodType.PUT)
        {
            if (ArrayUtils.isEmpty(args))
            {
                throw new CallerException(
                        "Method " + methodType + " must has at least on arg for the first used as body.");
            }

            // 第一个参数以后的都作为url变量
            return ArrayUtils.subarray(args, 1, args.length);
        }
        else if (methodType == MethodType.GET || methodType == MethodType.DELETE)
        {
            // 全部都作为url变量
            return args;
        }
        else
        {
            throw new UnsupportedOperationException("Method " + methodType + " is not support.");
        }
    }
}
