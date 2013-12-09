package com.xdc.basic.api.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson
{

    public static void main(String[] args)
    {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        try
        {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String jsonString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"verified\":false,\"userImage\":\"Rm9vYmFyIQ==\"}";
            User user = mapper.readValue(jsonString, User.class);
            System.out.println(user);

            String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            System.out.println(prettyJsonString);

            String jsonString2 = mapper.writeValueAsString(user);
            System.out.println(jsonString2);
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

    }
}
