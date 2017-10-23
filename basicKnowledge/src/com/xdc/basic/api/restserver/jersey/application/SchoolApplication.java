package com.xdc.basic.api.restserver.jersey.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.xdc.basic.api.restserver.jersey.application.resource.school.CourseResource;
import com.xdc.basic.api.restserver.jersey.application.resource.school.StudentResouce;
import com.xdc.basic.api.restserver.jersey.application.resource.school.TeacherResource;

public class SchoolApplication extends Application
{
    @Override
    public Set<Object> getSingletons()
    {
        Set<Object> objects = new HashSet<Object>();

        // 注册resource
        objects.add(new StudentResouce());
        objects.add(new CourseResource());
        objects.add(new TeacherResource());

        return objects;
    }
}
