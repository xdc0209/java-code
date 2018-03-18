package com.xdc.basic.api.hibernate.orm3.spring.framwaork.service.impl;

import java.util.List;

import com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao.ScoreDao;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao.StudentDao;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.entity.Score;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.entity.Student;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.service.StudentService;
import com.xdc.basic.commons.idgen.IdGenerate;

public class StudentServiceImpl implements StudentService
{
    private StudentDao studentDao;

    @SuppressWarnings("unused")
    private ScoreDao   scoreDao;

    public void setStudentDao(StudentDao studentDao)
    {
        this.studentDao = studentDao;
    }

    public void setScoreDao(ScoreDao scoreDao)
    {
        this.scoreDao = scoreDao;
    }

    @Override
    public void addStudent()
    {
        Student student = new Student();
        student.setId("20082890");
        student.setName("xudachao");
        student.setAge(25);
        student.setImage(IdGenerate.randomBytes(1234));
        student.setContent("123456789");
        student.getExtProperties().put("score", new Score());
        studentDao.save(student);

        Student student2 = new Student();
        student2.setId("20082891");
        student2.setName("xudachao");
        student2.setAge(25);
        student2.setImage(IdGenerate.randomBytes(1234));
        student2.setContent("123456789");
        student2.getExtProperties().put("score", new Score());
        studentDao.save(student2);
    }

    @Override
    public void delStudent()
    {
    }

    @Override
    public void getStudents()
    {
        List<Student> loadAll = studentDao.loadAll();
        System.out.println(loadAll);
    }
}
