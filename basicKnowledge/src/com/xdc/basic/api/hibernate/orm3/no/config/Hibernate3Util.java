package com.xdc.basic.api.hibernate.orm3.no.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

public class Hibernate3Util
{
    private static SessionFactory sessionFactory = null;

    private static List<String>   poClazzes      = new ArrayList<String>();

    static
    {
        poClazzes.add("com.xdc.basic.api.hibernate.orm3.framwaork.entity.Event");
    }

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null)
        {
            Properties jdbcProperties = loadProperties("jdbc.properties");

            String driver = jdbcProperties.getProperty("jdbc.driverClassName");
            String url = jdbcProperties.getProperty("jdbc.url");
            String user = jdbcProperties.getProperty("jdbc.username");

            // 解密密码
            String enPassword = jdbcProperties.getProperty("jdbc.password");
            String dePassword = enPassword;

            String dialect = jdbcProperties.getProperty("hibernate.dialect");
            String schema = jdbcProperties.getProperty("hibernate.schema");

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(dePassword);

            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty(Environment.DIALECT, dialect);
            hibernateProperties.setProperty(Environment.DEFAULT_SCHEMA, schema);
            hibernateProperties.setProperty(Environment.AUTOCOMMIT, Boolean.TRUE.toString());
            // hibernate4去掉了该属性，因为构建路径都是hibernate4的包，为了无编译错误，临时注释掉此行
            // hibernateProperties.setProperty(Environment.CACHE_PROVIDER, "org.hibernate.cache.HashtableCacheProvider");
            hibernateProperties.setProperty(Environment.USE_QUERY_CACHE, Boolean.TRUE.toString());
            hibernateProperties.setProperty(Environment.USE_SECOND_LEVEL_CACHE, Boolean.TRUE.toString());

            LocalSessionFactory localSessionFactory = new LocalSessionFactory();
            localSessionFactory.setLobHandler(new DefaultLobHandler());
            localSessionFactory.setDataSource(dataSource);
            localSessionFactory.getHibernateProperties().putAll(hibernateProperties);
            localSessionFactory.setPoClazzes(poClazzes);

            try
            {
                sessionFactory = localSessionFactory.buildSessionFactory();
            }
            catch (Exception e)
            {
                System.err.println("ERROR: BuildSessionFactory database failed.");
                e.printStackTrace();
                System.exit(1);
            }
        }

        return sessionFactory;
    }

    private static Properties loadProperties(String string)
    {
        // 读取配置文件
        return null;
    }
}
