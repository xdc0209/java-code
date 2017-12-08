package com.xdc.basic.api.rmi.sbus.test.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.xdc.basic.api.rmi.sbus.test.entity.Student;
import com.xdc.basic.api.rmi.sbus.test.intf.StudentService;

public class StudentServiceImpl implements StudentService
{
    private static Map<String, Student> students = new TreeMap<String, Student>();

    @Override
    public void addStudent(Student student) throws Exception
    {
        if (student == null)
        {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(
                    "Student could not be null.");
            throw illegalArgumentException;
            // throw new Exception("测试异常链", illegalArgumentException);
        }

        students.put(student.getId(), student);
    }

    @Override
    public void delStudent(Student student)
    {
        if (student == null)
        {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(
                    "Student could not be null.");
            throw illegalArgumentException;
            // throw new Exception("测试异常链", illegalArgumentException);
        }

        students.remove(student.getId());
    }

    @Override
    public List<Student> getStudents()
    {
        List<Student> studentList = new ArrayList<Student>();

        for (Student student : students.values())
        {
            studentList.add(student);
        }

        return studentList;
    }
}
