package com.xdc.basic.api.hibernate.orm4.demo.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.xdc.basic.commons.PathUtil;

public class HibernateUtil
{
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        String curPath = PathUtil.getPackagePath();
        try
        {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration cfg = new Configuration().configure(curPath + "hibernate.cfg.xml");
            ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            return cfg.buildSessionFactory(sr);
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
