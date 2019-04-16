package com.xdc.basic.api.apache.poi.business.model;

import org.apache.commons.lang3.math.NumberUtils;

import com.xdc.basic.api.apache.poi.business.model.bean.Course;
import com.xdc.basic.api.apache.poi.business.model.bean.Score;
import com.xdc.basic.api.apache.poi.business.model.bean.Student;
import com.xdc.basic.api.apache.poi.business.model.xls.CourseRowObj;
import com.xdc.basic.api.apache.poi.business.model.xls.ScoreRowObj;
import com.xdc.basic.api.apache.poi.business.model.xls.StudentRowObj;

public class XlsToBeanConverter
{
    public static Student converter(StudentRowObj studentRowObj)
    {
        Student student = new Student();

        student.setNumber(NumberUtils.toLong(studentRowObj.getNumber()));
        student.setName(studentRowObj.getName());
        student.setGender(studentRowObj.getGender());

        return student;
    }

    public static Course converter(CourseRowObj courseRowObj)
    {
        Course course = new Course();
        // 本例只是演示，对student写具体业务逻辑，course和score就不写了
        return course;
    }

    public static Score converter(ScoreRowObj scoreRowObj)
    {
        Score score = new Score();
        // 本例只是演示，对student写具体业务逻辑，course和score就不写了
        return score;
    }
}
