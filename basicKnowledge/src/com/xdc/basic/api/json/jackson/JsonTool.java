package com.xdc.basic.api.json.jackson;

import java.io.IOException;
import java.util.List;

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
        String jsonString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"verified\":false,\"userImage\":\"Rm9vYmFyIQ==\"}";
        User user = JsonTool.parse(jsonString, User.class);
        System.out.println(user);

        String jsonString2 = JsonTool.toJSONString(user);
        System.out.println(jsonString2);
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

    public static <T> List<T> parseArray(String s, Class<T> clazz)
    {
        // TODO 待研究
        // List<T> result = mapper.readValue(s, TypeFactory.collectionType(ArrayList.class, clazz));
        // return result;
        return null;
    }
}