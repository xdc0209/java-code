package com.xdc.basic.skills.getmapfromlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * List转换成Map
 * 
 * @author xdc
 * 
 */
public class GetMapFromList
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        List<Student> tallStudents = new ArrayList<Student>();
        tallStudents.add(new Student("20082889", "wangziwei"));
        tallStudents.add(new Student("20082890", "xudachao"));

        List<Student> richStudents = new ArrayList<Student>();
        richStudents.add(new Student("20082890", "xudachao"));
        richStudents.add(new Student("20082891", "yulong"));

        Map<String, Student> tallStudentMap = getMapFromList(tallStudents);
        Map<String, Student> richStudentMap = getMapFromList(richStudents);

        Set<String> tallNumberSet = tallStudentMap.keySet();
        Set<String> richNumberSet = richStudentMap.keySet();

        // 这个的返回结果其实是ArrayList，否则报错java.util.ArrayList cannot be cast to ***
        List<String> tallAndRichNumberList = (List<String>) CollectionUtils.intersection(tallNumberSet, richNumberSet);

        for (String number : tallAndRichNumberList)
        {
            Student student = tallStudentMap.get(number);
            System.out.println(student);
        }
    }

    private static Map<String, Student> getMapFromList(List<Student> students)
    {
        Map<String, Student> result = new HashMap<String, Student>();

        for (Student student : students)
        {
            result.put(student.getNumber(), student);
        }
        return result;

    }
}
