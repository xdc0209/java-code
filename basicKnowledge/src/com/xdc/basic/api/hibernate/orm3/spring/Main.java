package com.xdc.basic.api.hibernate.orm3.spring;

import com.xdc.basic.api.hibernate.orm3.spring.framwaork.core.DomainClasses;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.core.SpringBeanFactory;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.entity.Course;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.entity.Score;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.entity.Student;
import com.xdc.basic.api.hibernate.orm3.spring.framwaork.service.StudentService;
import com.xdc.basic.skills.GetPath;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            DomainClasses.addDomainClasses(Student.class);
            DomainClasses.addDomainClasses(Course.class);
            DomainClasses.addDomainClasses(Score.class);

            SpringBeanFactory.addSpringConfigPath(GetPath.connect(GetPath.getPackagePath(),
                    "framwaork/core/application-context.xml"));
            SpringBeanFactory.init();

            StudentService studentService = SpringBeanFactory.getBean("studentService", StudentService.class);
            studentService.addStudent();
            studentService.getStudents();
            studentService.delStudent();

            System.out.println();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println();
    }
}
