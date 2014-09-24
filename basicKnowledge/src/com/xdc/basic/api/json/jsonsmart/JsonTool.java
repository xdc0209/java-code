package com.xdc.basic.api.json.jsonsmart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.junit.Test;

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
        String studentString = JsonTool.toJSONString(student);
        System.out.println(studentString);

        // json string --> object
        Student parsedStudent = JsonTool.parse(studentString, Student.class);
        System.out.println(parsedStudent);

        // json string --> object
        Map<String, Object> parseToMap = JsonTool.parseToMap(studentString);
        System.out.println(parseToMap);

        // ----------------------------------------------------------
        // json array -----------------------------------------------
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(student);

        // array --> json string
        String studentsString = JSONValue.toJSONString(students);
        System.out.println(studentsString);

        // json string --> array
        List<Student> parsedArray = JsonTool.parseArray(studentsString, Student.class);
        System.out.println(parsedArray);

        // json string --> array
        Student[] parsedStudents = JsonTool.parse(studentsString, Student[].class);
        System.out.println(parsedStudents);
    }

    public static String toJSONString(Object o)
    {
        return JSONValue.toJSONString(o);
    }

    public static <T> T parse(String s, Class<T> clazz)
    {
        return JSONValue.parse(s, clazz);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseToMap(String s)
    {
        Map<String, Object> result = null;
        Object object = JSONValue.parse(s);
        if (object instanceof JSONObject)
        {
            // JSONObject继承自HashMap
            result = (Map<String, Object>) object;
        }
        return result;
    }

    public static <T> List<T> parseArray(String s, Class<T> clazz)
    {
        List<T> result = null;
        Object object = JSONValue.parse(s);
        if (object instanceof JSONArray)
        {
            result = new ArrayList<T>();
            JSONArray jsonArray = (JSONArray) object;
            for (Object object2 : jsonArray)
            {
                String string = JSONValue.toJSONString(object2);
                result.add(JSONValue.parse(string, clazz));
            }
        }
        return result;
    }
}