package com.xdc.basic.api.json.jsonsmart;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;

import org.junit.Test;

public class JsonTool
{
    @Test
    public void jsonTest()
    {
        // json object
        Student student = new Student("xudachao", 100, 25);
        List<String> goodFriends = new ArrayList<String>();
        goodFriends.add("chenchong");
        goodFriends.add("duquan");
        student.setGoodFriends(goodFriends);

        String studentString = JsonTool.toJSONString(student);
        System.out.println(studentString);

        Student parsedStudent = JsonTool.parse(studentString, Student.class);
        System.out.println(parsedStudent);

        // json array
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(student);

        String studentsString = JSONValue.toJSONString(students);
        System.out.println(studentsString);

        List<Student> parsedArray = JsonTool.parseArray(studentsString, Student.class);
        System.out.println(parsedArray);
    }

    public static String toJSONString(Object o)
    {
        return JSONValue.toJSONString(o);
    }

    public static <T> T parse(String s, Class<T> clazz)
    {
        return JSONValue.parse(s, clazz);
    }

    public static <T> List<T> parseArray(String s, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        Object object = JSONValue.parse(s);
        if (object instanceof JSONArray)
        {
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