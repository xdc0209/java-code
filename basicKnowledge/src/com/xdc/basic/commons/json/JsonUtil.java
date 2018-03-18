package com.xdc.basic.commons.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xdc.basic.commons.ExceptionUtil;

public class JsonUtil
{
    private static final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally.

    static
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 设置此参数后，Java对象转换为的Json字符串带有类型信息。对于Java对象成员包含Object类型时，Json字符串转换为Java对象转换时，Object类型对应的字段能还原当时对应的类。不指定的话，默认Object类型的将转换为LinkedHashMap。
        // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    }

    /**
     * 对象序列化：java-->json
     */
    public static String toJson(Object o)
    {
        try
        {
            return mapper.writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 简单对象反序列化：json-->java
     * 
     * 特别地：
     * Map<String, Object>：fromJson(s, Map.class)
     * List<Map<String, Object>>： fromJson(s, List.class)
     * T[]：fromJson(s, T[].class)
     */
    public static <T> T fromJson(String s, Class<T> clazz)
    {
        try
        {
            return mapper.readValue(s, clazz);
        }
        catch (IOException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 泛型对象反序列化：json-->java
     * 
     * 使用示例：
     * Map<K, V>：fromJson(s, new TypeReference<Map<K, V>>(){});
     * List<T>：fromJson(s, new TypeReference<List<T>>(){});
     * List<Map<K, V>>：fromJson(s, new TypeReference<List<Map<K, V>>>(){});
     */
    public static <T> T fromJson(String s, TypeReference<T> typeReference)
    {
        try
        {
            return mapper.readValue(s, typeReference);
        }
        catch (IOException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 泛型对象反序列化：json-->java
     * 
     * 使用示例：
     * Map<K, V>：fromJson(s, newJavaType(Map.class, K.class, V.class));
     * List<T>：fromJson(s, newJavaType(List.class, T.class));
     * List<Map<K, V>>：fromJson(s, newJavaType(List.class, newJavaType(Map.class, K.class, V.class)));
     */
    public static <T> T fromJson(String s, JavaType javaType)
    {
        try
        {
            return mapper.readValue(s, javaType);
        }
        catch (IOException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 构造JavaType。
     */
    public static JavaType newJavaType(Class<?> parametrized, Class<?>... parameterClasses)
    {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    /**
     * 构造JavaType。
     */
    public static JavaType newJavaType(Class<?> rawType, JavaType... parameterTypes)
    {
        return mapper.getTypeFactory().constructParametricType(rawType, parameterTypes);
    }

    /**
     * 格式化json字符串。
     */
    public static String format(String s)
    {
        try
        {
            Object o = mapper.readValue(s, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        }
        catch (IOException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }
}
