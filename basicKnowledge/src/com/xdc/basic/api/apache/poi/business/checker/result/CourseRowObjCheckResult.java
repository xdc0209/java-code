package com.xdc.basic.api.apache.poi.business.checker.result;

import com.xdc.basic.api.apache.poi.business.model.bean.Course;
import com.xdc.basic.api.apache.poi.core.checker.result.Result;

public class CourseRowObjCheckResult extends Result
{
    private String number;

    private String name;

    /**
     * 校验结果为成功时，生成对应的bean
     */
    private Course course;

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

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }
}
