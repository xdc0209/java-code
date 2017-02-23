package com.xdc.basic.api.hibernate.orm3.spring.framwaork.service.impl;

import com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao.CourseDao;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao.ScoreDao;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.service.CourseService;

public class CourseServiceImpl implements CourseService
{
    @SuppressWarnings("unused")
    private CourseDao courseDao;

    @SuppressWarnings("unused")
    private ScoreDao  scoreDao;

    public void setScoreDao(ScoreDao scoreDao)
    {
        this.scoreDao = scoreDao;
    }

    public void setCourseDao(CourseDao courseDao)
    {
        this.courseDao = courseDao;
    }
}
