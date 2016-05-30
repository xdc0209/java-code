package com.xdc.basic.tools.restclient.tools;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xdc.basic.tools.restclient.message.RestClientException;

public class JsonTool
{
    private static final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

    static
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static byte[] toJSONBytes(Object o) throws RestClientException
    {
        byte[] bytes = null;
        try
        {
            bytes = mapper.writeValueAsBytes(o);
        }
        catch (JsonProcessingException e)
        {
            throw new RestClientException(e);
        }
        return bytes;
    }

    public static <T> T parse(byte[] bytes, Class<T> clazz) throws RestClientException
    {
        T t = null;
        try
        {
            t = mapper.readValue(bytes, clazz);
        }
        catch (JsonParseException e)
        {
            throw new RestClientException(e);
        }
        catch (JsonMappingException e)
        {
            throw new RestClientException(e);
        }
        catch (IOException e)
        {
            throw new RestClientException(e);
        }
        return t;
    }

    public static String toJSONString(Object o) throws RestClientException
    {
        String s = null;
        try
        {
            // 漂亮的输出
            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            throw new RestClientException(e);
        }
        return s;
    }

    public static <T> T parse(String s, Class<T> clazz) throws RestClientException
    {
        T t = null;
        try
        {
            t = mapper.readValue(s, clazz);
        }
        catch (JsonParseException e)
        {
            throw new RestClientException(e);
        }
        catch (JsonMappingException e)
        {
            throw new RestClientException(e);
        }
        catch (IOException e)
        {
            throw new RestClientException(e);
        }
        return t;
    }

    public static String format(String s) throws RestClientException
    {
        Object o = JsonTool.parse(s, Object.class);
        return JsonTool.toJSONString(o);
    }
}