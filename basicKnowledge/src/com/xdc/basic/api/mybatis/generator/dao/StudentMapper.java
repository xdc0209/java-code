package com.xdc.basic.api.mybatis.generator.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xdc.basic.api.mybatis.generator.model.Student;
import com.xdc.basic.api.mybatis.generator.model.StudentExample;

public interface StudentMapper
{
    int countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}
