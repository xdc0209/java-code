package com.xdc.basic.api.apache.poi.utils;

import org.apache.commons.lang3.math.NumberUtils;

import com.xdc.basic.api.apache.poi.model.bean.Course;
import com.xdc.basic.api.apache.poi.model.bean.Score;
import com.xdc.basic.api.apache.poi.model.bean.Student;
import com.xdc.basic.api.apache.poi.model.xls.CourseRowObj;
import com.xdc.basic.api.apache.poi.model.xls.ScoreRowObj;
import com.xdc.basic.api.apache.poi.model.xls.StudentRowObj;

public class XlsToBeanBuilder
{
    public static Student build(StudentRowObj studentRowObj)
    {
        Student student = new Student();

        student.setNumber(NumberUtils.toLong(studentRowObj.getNumber()));
        student.setName(studentRowObj.getName());
        student.setGender(studentRowObj.getGender());

        return student;
    }

    public static Course build(CourseRowObj courseRowObj)
    {
        Course course = new Course();
        // 本例只是演示，对student写具体业务逻辑，course和score就不写了
        return course;
    }

    public static Score build(ScoreRowObj scoreRowObj)
    {
        Score score = new Score();
        // 本例只是演示，对student写具体业务逻辑，course和score就不写了
        return score;
    }
}
