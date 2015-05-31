package com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao.BaseDao;

public abstract class BaseDaoImpl<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport
        implements BaseDao<T, PK>
{
    // 实体类类型(由构造方法自动赋值)
    private Class<T> entityClass;

    // 构造方法，根据实例类自动获取实体类类型
    @SuppressWarnings("unchecked")
    public BaseDaoImpl()
    {
        Class<?> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType)
        {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    // //////////////////////////////////////////////////////////////////////
    // -------------------- 基本检索、增加、修改、删除操作 --------------------
    // //////////////////////////////////////////////////////////////////////

    @Override
    public void save(T entity)
    {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity)
    {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<T> entities)
    {
        getHibernateTemplate().saveOrUpdateAll(entities);
    }

    @Override
    public void delete(T entity)
    {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteWithLock(T entity, LockMode mode)
    {
        getHibernateTemplate().delete(entity, mode);
        flush(); // 立即刷新，否则锁不会生效。
    }

    @Override
    public void deleteById(PK id)
    {
        delete(load(id));
    }

    @Override
    public void deleteByIdWithLock(PK id, LockMode mode)
    {
        deleteWithLock(load(id), mode);
    }

    @Override
    public void deleteAll(Collection<T> entities)
    {
        getHibernateTemplate().deleteAll(entities);
    }

    @Override
    public void update(T entity)
    {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void updateWithLock(T entity, LockMode mode)
    {
        getHibernateTemplate().update(entity, mode);
        this.flush(); // 立即刷新，否则锁不会生效。
    }

    @Override
    public T get(PK id)
    {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public T getWithLock(PK id, LockMode mode)
    {
        T t = getHibernateTemplate().get(entityClass, id, mode);
        if (t != null)
        {
            this.flush(); // 立即刷新，否则锁不会生效。
        }
        return t;
    }

    @Override
    public T load(PK id)
    {
        return getHibernateTemplate().load(entityClass, id);
    }

    @Override
    public T loadWithLock(PK id, LockMode mode)
    {
        T t = getHibernateTemplate().load(entityClass, id, mode);
        if (t != null)
        {
            this.flush(); // 立即刷新，否则锁不会生效。
        }
        return t;
    }

    @Override
    public List<T> loadAll()
    {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadAll(int firstResult, int maxResults)
    {
        DetachedCriteria criteria = createDetachedCriteria();
        List<T> findByCriteria = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);

        return findByCriteria;
    }

    @Override
    public int allRowCount()
    {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

    // //////////////////////////////////////////////////////////////////////
    // ---------------------- Criteria [kraɪ'tɪrɪə] 条件---------------------
    // //////////////////////////////////////////////////////////////////////

    @Override
    public DetachedCriteria createDetachedCriteria()
    {
        return DetachedCriteria.forClass(entityClass);
    }

    @Override
    public Criteria createCriteria()
    {
        return createDetachedCriteria().getExecutableCriteria(getSession());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(DetachedCriteria detachedCriteria)
    {
        return getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(int firstResult, int maxResults, DetachedCriteria detachedCriteria)
    {
        return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
    }

    @Override
    public int criteriaRowCount(DetachedCriteria detachedCriteria)
    {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    @Override
    public List<T> findByCriterions(Criterion... criterions)
    {
        DetachedCriteria detachedCriteria = createDetachedCriteria();
        for (Criterion criterion : criterions)
        {
            detachedCriteria.add(criterion);
        }
        return findByCriteria(detachedCriteria);
    }

    @Override
    public List<T> findByCriterions(int firstResult, int maxResults, Criterion... criterions)
    {
        DetachedCriteria detachedCriteria = createDetachedCriteria();
        for (Criterion criterion : criterions)
        {
            detachedCriteria.add(criterion);
        }
        return findByCriteria(firstResult, maxResults, detachedCriteria);
    }

    @Override
    public int criterionsRowCount(Criterion... criterions)
    {
        DetachedCriteria detachedCriteria = createDetachedCriteria();
        for (Criterion criterion : criterions)
        {
            detachedCriteria.add(criterion);
        }
        return criteriaRowCount(detachedCriteria);
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
        Criteria criteria = this.createCriteria();

        Example example = Example.create(entity);
        example.excludeZeroes(); // exclude zero valued properties
        if (ArrayUtils.isNotEmpty(propertyNames))
        {
            ClassMetadata classMetadata = getSessionFactory().getClassMetadata(entityClass);
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

        return criteria.list();
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
        Criteria criteria = this.createCriteria();

        Example example = Example.create(entity);
        example.excludeZeroes(); // exclude zero valued properties
        example.ignoreCase(); // perform case insensitive string comparisons
        example.enableLike(); // use like for string comparisons
        if (ArrayUtils.isNotEmpty(propertyNames))
        {
            ClassMetadata classMetadata = getSessionFactory().getClassMetadata(entityClass);
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

        return criteria.list();
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

        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        return (Long) criteria.uniqueResult();
    }

    // //////////////////////////////////////////////////////////////////////
    // -------------------------------- Others ------------------------------
    // //////////////////////////////////////////////////////////////////////

    @Override
    public void lock(T entity, LockMode mode)
    {
        getHibernateTemplate().lock(entity, mode);
    }

    @Override
    public void flush()
    {
        getHibernateTemplate().flush();
    }

}
