package com.xdc.basic.commons.json;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xdc.basic.api.json.jackson.User;

class JsonUtilTest
{
    @Test
    @SuppressWarnings("unchecked")
    void testJsonObject()
    {
        String userString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false}";
        System.out.println(userString);

        // json object-->java bean
        User userBean = JsonUtil.fromJson(userString, User.class);
        System.out.println(userBean);

        // json object-->java map 1
        Map<String, Object> userMap1 = (Map<String, Object>) JsonUtil.fromJson(userString, Map.class);
        System.out.println(userMap1);

        // json object-->java map 2
        Map<String, Object> userMap2 = JsonUtil.fromJson(userString, new TypeReference<Map<String, Object>>()
        {
        });
        System.out.println(userMap2);

        // json object-->java map 3
        Map<String, Object> userMap3 = JsonUtil.fromJson(userString,
                JsonUtil.newJavaType(Map.class, String.class, Object.class));
        System.out.println(userMap3);

        // java-->json
        String userBeanString = JsonUtil.toJson(userBean);
        System.out.println(userBeanString);

        // java-->json
        String userMap1String = JsonUtil.toJson(userMap1);
        System.out.println(userMap1String);

        // java-->json
        String userMap2String = JsonUtil.toJson(userMap2);
        System.out.println(userMap2String);

        // java-->json
        String userMap3String = JsonUtil.toJson(userMap3);
        System.out.println(userMap3String);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testJsonArray()
    {
        String userStrings = "[{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false},{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false}]";
        System.out.println(userStrings);

        // json array-->java beans 1
        List<User> userBeans1 = JsonUtil.fromJson(userStrings, new TypeReference<List<User>>()
        {
        });
        System.out.println(userBeans1);

        // json array-->java beans 2
        List<User> userBeans2 = JsonUtil.fromJson(userStrings, JsonUtil.newJavaType(List.class, User.class));
        System.out.println(userBeans2);

        // json array-->java maps 1
        List<Map<String, Object>> userMaps1 = (List<Map<String, Object>>) JsonUtil.fromJson(userStrings, List.class);
        System.out.println(userMaps1);

        // json array-->java maps 2
        List<Map<String, Object>> userMaps2 = JsonUtil.fromJson(userStrings,
                new TypeReference<List<Map<String, Object>>>()
                {
                });
        System.out.println(userMaps2);

        // json array-->java maps 3
        List<Map<String, Object>> userMaps3 = JsonUtil.fromJson(userStrings,
                JsonUtil.newJavaType(List.class, JsonUtil.newJavaType(Map.class, String.class, Object.class)));
        System.out.println(userMaps3);

        // java-->json
        String userBeans1String = JsonUtil.toJson(userBeans1);
        System.out.println(userBeans1String);

        // java-->json
        String userBeans2String = JsonUtil.toJson(userBeans2);
        System.out.println(userBeans2String);

        // java-->json
        String userMaps1String = JsonUtil.toJson(userMaps1);
        System.out.println(userMaps1String);

        // java-->json
        String userMaps2String = JsonUtil.toJson(userMaps2);
        System.out.println(userMaps2String);

        // java-->json
        String userMaps3String = JsonUtil.toJson(userMaps3);
        System.out.println(userMaps3String);

        // 特别地，json array-->java beans
        User[] userArray = JsonUtil.fromJson(userStrings, User[].class);
        System.out.println(Arrays.toString(userArray));
    }

    @Test
    void testJsonFormat()
    {
        String userString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false}";
        System.out.println(userString);

        // json object-->java bean
        User userBean = JsonUtil.fromJson(userString, User.class);
        System.out.println(userBean);

        // java-->json
        String userString1 = JsonUtil.toJson(userBean);
        System.out.println(userString1);

        // format
        String userString2 = JsonUtil.format(userString);
        System.out.println(userString2);
    }
}
