package com.xdc.basic.example.mybatis.manual.data;

import com.xdc.basic.example.mybatis.manual.model.Student;

public interface StudentMapper
{
	int insert(Student record);

	int deleteByPrimaryKey(Integer id);

	Student selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(Student record);
}