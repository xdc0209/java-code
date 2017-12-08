package com.xdc.basic.api.rmi.sbus.test.intf;

import java.util.List;

import com.xdc.basic.api.rmi.sbus.test.entity.Student;

public interface StudentService
{
    void addStudent(Student student) throws Exception;

    void delStudent(Student student);

    List<Student> getStudents();
}
