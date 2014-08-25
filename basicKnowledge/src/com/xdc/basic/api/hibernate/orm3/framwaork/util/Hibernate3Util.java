package com.xdc.basic.api.hibernate.orm3.framwaork.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.xdc.basic.skills.GetPath;

public class Hibernate3Util
{
    private static final SessionFactory sessionFactory = buildSessionFactory();

    @SuppressWarnings("deprecation")
    private static SessionFactory buildSessionFactory()
    {
        String curPath = GetPath.getPackagePath();
        try
        {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration cfg = new Configuration().configure(curPath + "hibernate.cfg.xml");
            return cfg.buildSessionFactory();
        }
        catch (Throwable ex)
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

}