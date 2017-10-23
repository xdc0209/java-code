package com.xdc.basic.api.hibernate.orm3.spring.framwaork.core;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class SessionFactoryBean extends LocalSessionFactoryBean
{
    @Override
    protected void postProcessConfiguration(Configuration config) throws HibernateException
    {
        super.postProcessConfiguration(config);

        for (Class<?> clazz : DomainClasses.getDomainClasses())
        {
            config.addAnnotatedClass(clazz);
        }
    }
}
