package com.xdc.basic.api.json.jsonsmart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class JsonTool
{
    @Test
    public void jsonTest()
    {
        // ----------------------------------------------------------
        // json object ----------------------------------------------
        Student student = new Student("xudachao", 100, 25);
        List<String> goodFriends = new ArrayList<String>();
        goodFriends.add("chenchong");
        goodFriends.add("duquan");
        student.setGoodFriends(goodFriends);

        // object --> json string
        String studentString = JsonTool.toJsonString(student);
        System.out.println(studentString);

        // json string --> object
        Student studentObject = JsonTool.fromJsonString(studentString, Student.class);
        System.out.println(studentObject);

        // json string --> object
        Map<String, Object> studentMap = JsonTool.fromJsonStringToMap(studentString);
        System.out.println(studentMap);

        // ----------------------------------------------------------
        // json array -----------------------------------------------
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(student);

        // array --> json string
        String studentsString = JsonTool.toJsonString(students);
        System.out.println(studentsString);

        // json string --> array
        List<Student> studentsList = JsonTool.fromJsonStringToList(studentsString, Student.class);
        System.out.println(studentsList);

        // json string --> array
        Student[] studentsArray = JsonTool.fromJsonString(studentsString, Student[].class);
        System.out.println(Arrays.toString(studentsArray));
    }

    public static String toJsonString(Object o)
    {
        return JSONValue.toJSONString(o);
    }

    public static <T> T fromJsonString(String s, Class<T> clazz)
    {
        return JSONValue.parse(s, clazz);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromJsonStringToMap(String s)
    {
        Map<String, Object> map = null;
        Object object = JSONValue.parse(s);
        if (object instanceof JSONObject)
        {
            // JSONObject继承自HashMap
            map = (Map<String, Object>) object;
        }
        return map;
    }

    public static <T> List<T> fromJsonStringToList(String s, Class<T> clazz)
    {
        List<T> list = null;
        Object object = JSONValue.parse(s);
        if (object instanceof JSONArray)
        {
            list = new ArrayList<T>();
            JSONArray jsonArray = (JSONArray) object;
            for (Object object2 : jsonArray)
            {
                String string = JSONValue.toJSONString(object2);
                list.add(JSONValue.parse(string, clazz));
            }
        }
        return list;
    }
}