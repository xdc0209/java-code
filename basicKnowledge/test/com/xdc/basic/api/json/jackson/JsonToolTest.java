package com.xdc.basic.api.json.jackson;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class JsonToolTest
{
    @Test
    void testJsonObject()
    {
        String userString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false}";
        System.out.println(userString);

        // json object-->java bean
        User userBean = JsonTool.toBean(userString, User.class);
        System.out.println(userBean);

        // json object-->java map
        Map<String, Object> userMap = JsonTool.toMap(userString);
        System.out.println(userMap);

        // java-->json
        String userString1 = JsonTool.toJson(userBean);
        System.out.println(userString1);

        // java-->json
        String userString2 = JsonTool.toJson(userMap);
        System.out.println(userString2);
    }

    @Test
    void testJsonArray()
    {
        String userStrings = "[{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false},{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false}]";
        System.out.println(userStrings);

        // json array-->java beans
        List<User> userBeans = JsonTool.toBeans(userStrings, User.class);
        System.out.println(userBeans);

        // json array-->java maps
        List<Map<String, Object>> userMaps = JsonTool.toMaps(userStrings);
        System.out.println(userMaps);

        // java-->json
        String userStrings1 = JsonTool.toJson(userBeans);
        System.out.println(userStrings1);

        // java-->json
        String userStrings2 = JsonTool.toJson(userMaps);
        System.out.println(userStrings2);

        // 特别地，json array-->java beans
        User[] userArray = JsonTool.toBean(userStrings, User[].class);
        System.out.println(Arrays.toString(userArray));
    }

    @Test
    void testJsonFormat()
    {
        String userString = "{\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"gender\":\"MALE\",\"userImage\":\"Rm9vYmFyIQ==\",\"verified\":false}";
        System.out.println(userString);

        // json object-->java bean
        User userBean = JsonTool.toBean(userString, User.class);
        System.out.println(userBean);

        // java-->json
        String userString1 = JsonTool.toJson(userBean, true);
        System.out.println(userString1);

        // format
        String userString2 = JsonTool.format(userString);
        System.out.println(userString2);
    }
}
