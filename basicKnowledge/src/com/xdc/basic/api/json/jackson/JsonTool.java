package com.xdc.basic.api.json.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTool
{
    private static final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

    static
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 设置此参数后，Java对象转换为的Json字符串带有类型信息。对于Java对象成员包含Object类型时，Json字符串转换为Java对象转换时，Object类型对应的字段能还原当时对应的类。不指定的话，默认Object类型的将转换为LinkedHashMap。
        // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    }

    public static void main(String[] args)
    {
        // ----------------------------------------------------------
        // json object ----------------------------------------------
        String userString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"verified\":false,\"userImage\":\"Rm9vYmFyIQ==\"}";

        // json string --> object
        User userObject = JsonTool.fromJsonString(userString, User.class);
        System.out.println(userObject);

        Map<String, Object> userMap = JsonTool.fromJsonStringToMap(userString);
        System.out.println(userMap);

        // object --> json string
        String userString2 = JsonTool.toJsonString(userObject);
        System.out.println(userString2);

        // ----------------------------------------------------------
        // json array -----------------------------------------------
        ArrayList<User> users = new ArrayList<User>();
        users.add(userObject);
        users.add(userObject);

        // array --> json string
        String usersString = JsonTool.toJsonString(users);
        System.out.println(usersString);

        // json string --> array
        User[] usersArray = JsonTool.fromJsonString(usersString, User[].class);
        System.out.println(Arrays.toString(usersArray));
    }

    public static byte[] toJsonBytes(Object o)
    {
        byte[] bytes = null;
        try
        {
            bytes = mapper.writeValueAsBytes(o);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return bytes;
    }

    public static <T> T fromJsonBytes(byte[] bytes, Class<T> clazz)
    {
        T t = null;
        try
        {
            t = mapper.readValue(bytes, clazz);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return t;
    }

    public static String toJsonString(Object o)
    {
        String s = null;
        try
        {
            s = mapper.writeValueAsString(o);

            // 漂亮的输出
            // mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T fromJsonString(String s, Class<T> clazz)
    {
        T t = null;
        try
        {
            t = mapper.readValue(s, clazz);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromJsonStringToMap(String s)
    {
        Map<String, Object> map = null;
        try
        {
            map = mapper.readValue(s, Map.class);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return map;
    }

    public static <T> List<T> fromJsonStringToArray(String s, Class<T> clazz)
    {
        // TODO 待研究
        // List<T> list = mapper.readValue(s, TypeFactory.collectionType(ArrayList.class, clazz));
        // return list;
        return null;
    }
}