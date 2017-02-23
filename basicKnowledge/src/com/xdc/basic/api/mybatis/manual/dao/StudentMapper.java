package com.xdc.basic.api.mybatis.manual.dao;

import com.xdc.basic.api.mybatis.manual.model.Student;

public interface StudentMapper
{
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Student record);
}