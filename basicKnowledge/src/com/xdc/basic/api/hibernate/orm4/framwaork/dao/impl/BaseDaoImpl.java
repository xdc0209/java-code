package com.xdc.basic.api.hibernate.orm4.framwaork.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;

import com.xdc.basic.api.hibernate.orm4.framwaork.dao.BaseDao;
import com.xdc.basic.api.hibernate.orm4.framwaork.util.HibernateUtil;

public class BaseDaoImpl<T extends Serializable, PK extends Serializable> implements BaseDao<T, PK>
{
    private static final Log log = LogFactory.getLog(BaseDaoImpl.class);

    // 获得泛型类型
    @SuppressWarnings("unchecked")
    protected Class<T> getTypeClass()
    {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    // 获得session
    protected Session getSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    // 获得SessionFactory
    protected SessionFactory getSessionFactory()
    {
        return HibernateUtil.getSessionFactory();
    }

    // //////////////////////////////////////////////////////////////////////
    // -------------------- 基本检索、增加、修改、删除操作 --------------------
    // //////////////////////////////////////////////////////////////////////

    @Override
    public void save(T entity)
    {
        log.debug("Save entity begin.");

        Session session = getSession();
        try
        {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit(); // commit后，session自动关闭，不必再调用close方法

            log.debug("Save entity successful.");
        }
        catch (RuntimeException re)
        {
            log.error("Save entity failed.", re);
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveOrUpdate(T entity)
    {
        Session session = getSession();
        session.getTransaction().begin();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
    }

    @Override
    public void saveOrUpdateAll(Collection<T> entities)
    {
        Session session = getSession();
        session.getTransaction().begin();
        for (Object entity : entities)
        {
            session.saveOrUpdate(entity);
        }
        session.getTransaction().commit();
    }

    @Override
    public void delete(T entity)
    {
        Session session = getSession();
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(PK id)
    {
        delete(load(id));
    }

    @Override
    public void deleteAll(Collection<T> entities)
    {
        Session session = getSession();
        session.getTransaction().begin();
        for (Object entity : entities)
        {
            session.delete(entity);
        }
        session.getTransaction().commit();
    }

    @Override
    public void update(T entity)
    {
        Session session = getSession();
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(PK id)
    {
        Session session = getSession();
        session.getTransaction().begin();

        T t = (T) session.get(getTypeClass(), id);

        session.getTransaction().commit();
        return t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T load(PK id)
    {
        Session session = getSession();
        session.getTransaction().begin();

        T t = (T) session.load(getTypeClass(), id);

        session.getTransaction().commit();
        return t;
    }

    @Override
    public List<T> loadAll()
    {
        return loadAll(-1, -1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadAll(int firstResult, int maxResults)
    {
        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = session.createCriteria(getTypeClass());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (firstResult >= 0)
        {
            criteria.setFirstResult(firstResult);
        }
        if (maxResults > 0)
        {
            criteria.setMaxResults(maxResults);
        }
        List<T> ts = criteria.list();

        session.getTransaction().commit();
        return ts;
    }

    @Override
    public int allRowCount()
    {
        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = session.createCriteria(getTypeClass());
        criteria.setProjection(Projections.rowCount());
        int count = ((Number) criteria.uniqueResult()).intValue();

        session.getTransaction().commit();
        return count;
    }

    // //////////////////////////////////////////////////////////////////////
    // ---------------------- Criteria [kraɪ'tɪrɪə] 条件---------------------
    // //////////////////////////////////////////////////////////////////////

    @Override
    public DetachedCriteria createDetachedCriteria()
    {
        return DetachedCriteria.forClass(getTypeClass());
    }

    @Override
    public Criteria createCriteria()
    {
        return createDetachedCriteria().getExecutableCriteria(getSession());
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria detachedCriteria)
    {
        return findByCriteria(detachedCriteria, -1, -1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults)
    {
        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        if (firstResult >= 0)
        {
            criteria.setFirstResult(firstResult);
        }
        if (maxResults > 0)
        {
            criteria.setMaxResults(maxResults);
        }
        List<T> ts = criteria.list();

        session.getTransaction().commit();
        return ts;
    }

    @Override
    public int criteriaRowCount(DetachedCriteria detachedCriteria)
    {
        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        criteria.setProjection(Projections.rowCount());
        criteria.setProjection(Projections.rowCount());
        int count = ((Number) criteria.uniqueResult()).intValue();

        session.getTransaction().commit();
        return count;
    }

    @Override
    public List<T> findEqualByEntity(T entity)
    {
        return findEqualByEntity(entity, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEqualByEntity(T entity, String[] propertyNames)
    {
        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = session.createCriteria(getTypeClass());

        Example example = Example.create(entity);
        example.excludeZeroes(); // exclude zero valued properties
        if (ArrayUtils.isNotEmpty(propertyNames))
        {
            ClassMetadata classMetadata = getSessionFactory().getClassMetadata(getTypeClass());
            String[] properties = classMetadata.getPropertyNames();
            for (String property : properties)
            {
                int i = 0;
                for (i = 0; i < propertyNames.length; ++i)
                {
                    if (property.equals(propertyNames[i]))
                    {
                        criteria.addOrder(Order.asc(property));
                        break;
                    }
                }
                if (i == propertyNames.length)
                {
                    example.excludeProperty(property); // exclude the property
                }
            }
        }
        criteria.add(example);

        List<T> ts = criteria.list();

        session.getTransaction().commit();
        return ts;
    }

    @Override
    public List<T> findLikeByEntity(T entity)
    {
        return findLikeByEntity(entity, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findLikeByEntity(T entity, String[] propertyNames)
    {
        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = session.createCriteria(getTypeClass());

        Example example = Example.create(entity);
        example.excludeZeroes(); // exclude zero valued properties
        example.ignoreCase(); // perform case insensitive string comparisons
        example.enableLike(); // use like for string comparisons
        if (ArrayUtils.isNotEmpty(propertyNames))
        {
            ClassMetadata classMetadata = getSessionFactory().getClassMetadata(getTypeClass());
            String[] properties = classMetadata.getPropertyNames();
            for (String property : properties)
            {
                int i = 0;
                for (i = 0; i < propertyNames.length; ++i)
                {
                    if (property.equals(propertyNames[i]))
                    {
                        criteria.addOrder(Order.asc(property));
                        break;
                    }
                }
                if (i == propertyNames.length)
                {
                    example.excludeProperty(property); // exclude the property
                }
            }
        }
        criteria.add(example);

        List<T> ts = criteria.list();

        session.getTransaction().commit();
        return ts;
    }

    // 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
    @Override
    public Long getStatisticsValue(DetachedCriteria detachedCriteria, String propertyName, String statisticsName)
    {
        if (statisticsName.toLowerCase().equals("max"))
            detachedCriteria.setProjection(Projections.max(propertyName));
        else if (statisticsName.toLowerCase().equals("min"))
            detachedCriteria.setProjection(Projections.min(propertyName));
        else if (statisticsName.toLowerCase().equals("avg"))
            detachedCriteria.setProjection(Projections.avg(propertyName));
        else if (statisticsName.toLowerCase().equals("sum"))
            detachedCriteria.setProjection(Projections.sum(propertyName));
        else
            return null;

        Session session = getSession();
        session.getTransaction().begin();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        Long result = (Long) criteria.uniqueResult();

        session.getTransaction().commit();
        return result;
    }
}
