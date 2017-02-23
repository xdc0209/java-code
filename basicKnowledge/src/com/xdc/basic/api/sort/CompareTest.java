package com.xdc.basic.api.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareTest
{
    /**
     * 本例所有排序：分数降序，年龄降序
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        // 列表
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("xudachao", 100, 25));
        students.add(new Student("wangzhiwei", 88, 24));
        students.add(new Student("gengxue", 98, 24));

        listSort(students);

        // 数组
        Student[] studentArr = students.toArray(new Student[students.size()]);

        arraySort(studentArr);
    }

    private static void arraySort(Student[] studentArr)
    {
        // 方式一
        Arrays.sort(studentArr, new Comparator<Student>()
        {
            @Override
            public int compare(Student o1, Student o2)
            {
                int scoreDiff = o1.getScore() - o2.getScore();
                if (scoreDiff != 0)
                    return scoreDiff;

                int ageDiff = o1.getAge() - o2.getAge();
                return ageDiff;
            }
        });

        // 方式二
        Arrays.sort(studentArr);

        System.out.println(Arrays.toString(studentArr));
    }

    private static void listSort(List<Student> students)
    {
        // 方式一
        Collections.sort(students, new Comparator<Student>()
        {
            @Override
            public int compare(Student o1, Student o2)
            {
                int scoreDiff = o1.getScore() - o2.getScore();
                if (scoreDiff != 0)
                    return scoreDiff;

                int ageDiff = o1.getAge() - o2.getAge();
                return ageDiff;
            }
        });

        // 方式二
        Collections.sort(students);

        System.out.println(students);
    }
}
