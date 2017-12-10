package com.xdc.basic.api.apache.poi.check.result;

import com.xdc.basic.api.apache.poi.model.bean.Student;

public class StudentRowObjCheckResult extends Result
{
    private String  number;

    private String  name;

    /**
     * 校验结果为成功时，生成对应的bean
     */
    private Student student;

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

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }
}
