package com.xdc.basic.api.hibernate.orm3.no.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class LocalSessionFactory extends LocalSessionFactoryBean
{
    private List<String> poClazzes = new ArrayList<String>();

    public void setPoClazzes(List<String> poClazzes)
    {
        this.poClazzes = poClazzes;
    }

    @Override
    public SessionFactory buildSessionFactory() throws Exception
    {
        return super.buildSessionFactory();
    }

    @Override
    protected void postProcessConfiguration(Configuration config) throws HibernateException
    {
        super.postProcessConfiguration(config);
        addAnnotatedClass(config, poClazzes);
    }

    private static void addAnnotatedClass(Configuration config, List<String> clazzes)
    {
        if (CollectionUtils.isEmpty(clazzes))
        {
            return;
        }

        for (String classString : clazzes)
        {
            Class<?> clazz = null;
            try
            {
                clazz = Class.forName(classString);
            }
            catch (ClassNotFoundException e)
            {
                System.err.println("ERROR: Init Hibernate failed.");
                e.printStackTrace();
                System.exit(1);
            }

            if (clazz != null)
            {
                config.addAnnotatedClass(clazz);
            }
        }
    }
}
