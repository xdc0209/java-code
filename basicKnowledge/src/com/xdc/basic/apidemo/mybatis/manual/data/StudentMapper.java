package com.xdc.basic.apidemo.mybatis.manual.data;

import com.xdc.basic.apidemo.mybatis.manual.model.Student;

public interface StudentMapper
{
	int insert(Student record);

	int deleteByPrimaryKey(Integer id);

	Student selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(Student record);
}