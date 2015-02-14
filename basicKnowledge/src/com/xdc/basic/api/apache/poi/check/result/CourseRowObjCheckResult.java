package com.xdc.basic.api.apache.poi.check.result;

import com.xdc.basic.api.apache.poi.model.bean.Course;

public class CourseRowObjCheckResult extends Result
{
    private String number;

    private String name;
    /**
     * 校验结果为成功时，生成对应的bean
     */
    private Course course;

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
