package com.xdc.basic.api.restserver.jersey.application.database.school;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.restserver.jersey.application.domain.school.Student;

/**
 * 数据库不是本例的重点，使用此类模拟数据库操作
 */
public class StudentDao
{
    public static final List<Student> STUDENTS = new ArrayList<Student>();

    static
    {
        STUDENTS.add(new Student(20082890, "xudachao", "M"));
        STUDENTS.add(new Student(20082891, "wangziwei", "M"));
        STUDENTS.add(new Student(20082892, "chenchong", "F"));
    }

    public static void insert(Student student)
    {
        STUDENTS.add(student);
    }

    public static void delete(long id)
    {
        Student studentToDeleteInDb = null;
        for (Student student : STUDENTS)
        {
            if (student.getId() == id)
            {
                studentToDeleteInDb = student;
                break;
            }
        }
        if (studentToDeleteInDb != null)
        {
            STUDENTS.remove(studentToDeleteInDb);
        }
    }

    public static void update(Student studentToUpdate)
    {
        Student studentToUpdateInDb = null;
        for (Student student : STUDENTS)
        {
            if (student.getId() == studentToUpdate.getId())
            {
                studentToUpdateInDb = student;
                break;
            }
        }
        if (studentToUpdateInDb != null)
        {
            studentToUpdateInDb.setName(studentToUpdate.getName());
            studentToUpdateInDb.setSex(studentToUpdate.getSex());
        }
    }

    public static Student query(long id)
    {
        for (Student student : STUDENTS)
        {
            if (student.getId() == id)
            {
                return student;
            }
        }
        return null;
    }

    public static List<Student> queryAll()
    {
        return new ArrayList<Student>(STUDENTS);
    }
}