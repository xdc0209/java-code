package com.xdc.basic.api.json.jackson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonTool
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
     * java-->json
     */
    public static String toJson(Object o)
    {
        return toJson(o, false);
    }

    /**
     * java-->json
     */
    public static String toJson(Object o, boolean pretty)
    {
        String s = null;
        try
        {
            if (pretty)
            {
                // 漂亮，体积稍大。
                s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            }
            else
            {
                // 正常，体积稍小。
                s = mapper.writeValueAsString(o);
            }
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
        return s;
    }

    /**
     * json object-->java bean
     */
    public static <T> T toBean(String s, Class<T> clazz)
    {
        T t = null;
        try
        {
            t = mapper.readValue(s, clazz);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * json array-->java beans
     */
    public static <T> List<T> toBeans(String s, Class<T> clazz)
    {
        List<T> ts = null;
        try
        {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            ts = mapper.readValue(s, collectionType);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return ts;
    }

    /**
     * json object-->java map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String s)
    {
        Map<String, Object> map = null;
        try
        {
            map = (Map<String, Object>) mapper.readValue(s, Object.class);

            // 替代方案：map = mapper.readValue(s, Map.class);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * json array-->java maps
     */
    public static List<Map<String, Object>> toMaps(String s)
    {
        List<Map<String, Object>> maps = null;
        try
        {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Object.class);
            maps = mapper.readValue(s, collectionType);

            // 替代方案：maps = mapper.readValue(s, List.class);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return maps;
    }

    /**
     * 格式化json字符串。
     */
    public static String format(String s)
    {
        return toJson(toBean(s, Object.class), true);
    }
}
