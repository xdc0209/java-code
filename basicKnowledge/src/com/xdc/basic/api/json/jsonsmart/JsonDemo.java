package com.xdc.basic.api.json.jsonsmart;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

/*
 * Home page: http://code.google.com/p/json-smart/
 * 
 */
public class JsonDemo
{
    @Test
    public void encodeJsonObject()
    {
        // Json Object is an HashMap<String, Object> extends
        JSONObject obj = new JSONObject();
        obj.put("name", "foo");
        obj.put("num", 100);
        obj.put("balance", 1000.21);
        obj.put("is_vip", true);
        obj.put("nickname", null);

        System.out.println("Standard RFC4627 JSON");
        System.out.println(obj.toJSONString());
        System.out.println("Compacted JSON Value");
        System.out.println(obj.toJSONString(JSONStyle.MAX_COMPRESS));

        // if obj is an common map you can use:
        System.out.println("Standard RFC4627 JSON");
        System.out.println(JSONValue.toJSONString(obj));
        System.out.println("Compacted JSON Value");
        System.out.println(JSONValue.toJSONString(obj, JSONStyle.MAX_COMPRESS));
    }

    @Test
    public void decodingJsonText() throws ParseException
    {
        System.out.println("=======decode=======");

        String s = "[0,{'1':{'2':{'3':{'4':[5,{'6':7}]}}}}]";
        Object obj = JSONValue.parse(s);
        JSONArray array = (JSONArray) obj;
        System.out.println("======the 2nd element of array======");
        System.out.println(array.get(1));
        System.out.println();

        JSONObject obj2 = (JSONObject) array.get(1);
        System.out.println("======field \"1\"==========");
        System.out.println(obj2.get("1"));

        s = "{}";
        obj = JSONValue.parse(s);
        System.out.println(obj);

        s = "{\"key\":\"Value\"}";
        // JSONValue.parseStrict()
        // can be use to be sure that the input is wellformed
        obj = JSONValue.parseStrict(s);
        JSONObject obj3 = (JSONObject) obj;
        System.out.println("====== Object content ======");
        System.out.println(obj3.get("key"));
        System.out.println();
    }

    @Test
    public void decodingJsonText2() throws ParseException
    {
        Student student = new Student("xudachao", 100, 25);
        List<String> goodFriends = new ArrayList<String>();
        goodFriends.add("chenchong");
        goodFriends.add("duquan");
        student.setGoodFriends(goodFriends);

        String studentString = JSONValue.toJSONString(student);
        System.out.println(studentString);

        // Student必须有无参构造函数，因为利用反射获取对象
        Student parsedStudent = JSONValue.parse(studentString, Student.class);
        System.out.println(parsedStudent);

        // smart json 还没提供直接数组串转会数组的方法
        String goodFriendsString = JSONValue.toJSONString(goodFriends);
        System.out.println(goodFriendsString);

        // parse的结果只有两种可能 JSONObject或JSONArray
        Object object = JSONValue.parse(studentString);
        if (object instanceof JSONObject)
        {
            System.out.println("It,s a json object.");
        }
        else
        {
            System.out.println("It,s a json array.");
        }
    }

    @Test
    public void merge2JsonObject() throws ParseException
    {
        String json1 = "{'car':{'color':'blue'}}";
        String json2 = "{'car':{'size':'3.5m'}}";

        @SuppressWarnings("deprecation")
        JSONParser p = new JSONParser();
        JSONObject o1 = (JSONObject) p.parse(json1);
        JSONObject o2 = (JSONObject) p.parse(json2);

        o1.merge(o2);

        System.out.println(o1);
    }

    @Test
    public void validatingJsonInput() throws ParseException
    {
        // JSONValue.isValidJson(String) To validate a string that conforms to the (non-strict) JSON Smart mode.
        // JSONValue.isValidStrictJson(String) To validate a string of JSON for strict conformance to RFC4627.

        String s = "{intValue:123}";
        if (JSONValue.isValidJson(s))
        {
            System.out.println(s + " validates as Smart JSON");
        }

        if (JSONValue.isValidJsonStrict(s))
        {
            System.out.println(s + " validates as strict JSON");
        }
        else
        {
            System.out.println(s + " does not validate as strict JSON");
        }
    }
}
