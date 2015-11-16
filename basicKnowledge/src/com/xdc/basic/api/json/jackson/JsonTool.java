package com.xdc.basic.api.json.jackson;

import java.io.IOException;
import java.util.ArrayList;
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
    }

    public static void main(String[] args)
    {
        // ----------------------------------------------------------
        // json object ----------------------------------------------
        String jsonString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"verified\":false,\"userImage\":\"Rm9vYmFyIQ==\"}";

        // json string --> object
        User user = JsonTool.parse(jsonString, User.class);
        System.out.println(user);

        Map<String, Object> parseToMap = JsonTool.parseToMap(jsonString);
        System.out.println(parseToMap);

        // object --> json string
        String jsonString2 = JsonTool.toJSONString(user);
        System.out.println(jsonString2);

        // ----------------------------------------------------------
        // json array -----------------------------------------------
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user);

        // array --> json string
        String jsonString3 = JsonTool.toJSONString(users);
        System.out.println(jsonString3);

        // json string --> array
        User[] parsedUsers = JsonTool.parse(jsonString3, User[].class);
        System.out.println(parsedUsers);
    }

    public static byte[] toJSONBytes(Object o)
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

    public static <T> T parse(byte[] bytes, Class<T> clazz)
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

    public static String toJSONString(Object o)
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

    public static <T> T parse(String s, Class<T> clazz)
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
    public static Map<String, Object> parseToMap(String s)
    {
        Map<String, Object> retult = null;
        try
        {
            retult = mapper.readValue(s, Map.class);
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
        return retult;
    }

    public static <T> List<T> parseToArray(String s, Class<T> clazz)
    {
        // TODO 待研究
        // List<T> result = mapper.readValue(s, TypeFactory.collectionType(ArrayList.class, clazz));
        // return result;
        return null;
    }
}