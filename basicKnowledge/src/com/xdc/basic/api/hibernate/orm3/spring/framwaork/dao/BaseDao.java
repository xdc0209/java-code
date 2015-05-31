package com.xdc.basic.api.hibernate.orm3.spring.framwaork.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T extends Serializable, PK extends Serializable>
{
    // //////////////////////////////////////////////////////////////////////
    // -------------------- 基本检索、增加、修改、删除操作 --------------------
    // //////////////////////////////////////////////////////////////////////

    // 增加实体, getHibernateTemplate没有带锁的sava，因为LockMode为行锁
    public void save(T entity);

    // 增加或更新实体
    public void saveOrUpdate(T entity);

    // 增加或更新集合中的全部实体
    public void saveOrUpdateAll(Collection<T> entities);

    // 删除指定的实体
    public void delete(T entity);

    // 加锁并删除指定的实体
    public void deleteWithLock(T entity, LockMode mode);

    // 根据主键删除指定实体
    public void deleteById(PK id);

    // 根据主键加锁并删除指定的实体
    public void deleteByIdWithLock(PK id, LockMode mode);

    // 删除集合中的全部实体
    public void deleteAll(Collection<T> entities);

    // 更新实体
    public void update(T entity);

    // 加锁并更新实体
    public void updateWithLock(T entity, LockMode mode);

    // 根据主键获取实体。如果没有相应的实体，返回 null。
    public T get(PK id);

    // 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
    public T getWithLock(PK id, LockMode mode);

    // 根据主键获取实体。如果没有相应的实体，抛出异常。
    public T load(PK id);

    // 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
    public T loadWithLock(PK id, LockMode mode);

    // 获取全部实体。
    public List<T> loadAll();

    // 获取全部实体(分页)。
    public List<T> loadAll(int firstResult, int maxResults);

    public int allRowCount();

    // //////////////////////////////////////////////////////////////////////
    // ---------------------- Criteria [kraɪ'tɪrɪə] 条件---------------------
    // //////////////////////////////////////////////////////////////////////

    // 创建与会话无关的检索标准对象
    public DetachedCriteria createDetachedCriteria();

    // 创建与会话绑定的检索标准对象
    public Criteria createCriteria();

    // 使用指定的检索标准检索数据
    public List<T> findByCriteria(DetachedCriteria detachedCriteria);

    // 使用指定的检索标准检索数据，返回部分记录
    public List<T> findByCriteria(int firstResult, int maxResults, DetachedCriteria detachedCriteria);

    // 使用指定的检索标准检索数据，返回指定范围的记录数目
    public int criteriaRowCount(DetachedCriteria detachedCriteria);

    // 使用指定的检索标准检索数据
    public List<T> findByCriterions(Criterion... criterions);

    // 使用指定的检索标准检索数据，返回部分记录
    public List<T> findByCriterions(int firstResult, int maxResults, Criterion... criterions);

    // 使用指定的检索标准检索数据，返回指定范围的记录数目
    public int criterionsRowCount(Criterion... criterions);

    // 使用指定的实体及属性检索(满足除主键外属性＝实体值)数据
    public List<T> findEqualByEntity(T entity);

    // 使用指定的实体及属性检索(满足除主键外属性＝实体值)数据(参数propertyNames为需排除的属性)
    public List<T> findEqualByEntity(T entity, String[] propertyNames);

    // 使用指定的实体及属性(非主键)检索(满足属性 like 串实体值)数据
    public List<T> findLikeByEntity(T entity);

    // 使用指定的实体及属性(非主键)检索(满足属性 like 串实体值)数据(参数propertyNames为需排除的属性)
    public List<T> findLikeByEntity(T entity, String[] propertyNames);

    // 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
    public Long getStatisticsValue(DetachedCriteria detachedCriteria, String propertyName, String statisticsName);

    // // //////////////////////////////////////////////////////////////////////
    // // -------------------- HSQL --------------------------------------------
    // // //////////////////////////////////////////////////////////////////////
    //
    // // 使用HSQL语句直接增加、更新、删除实体
    // public int bulkUpdate(String queryString);
    //
    // // 使用带参数的HSQL语句增加、更新、删除实体
    // public int bulkUpdate(String queryString, Object[] values);
    //
    // // 使用HSQL语句检索数据
    // public List find(String queryString);
    //
    // // 使用带参数的HSQL语句检索数据
    // public List find(String queryString, Object[] values);
    //
    // // 使用带命名的参数的HSQL语句检索数据
    // public List findByNamedParam(String queryString, String[] paramNames,
    // Object[] values);
    //
    // // 使用命名的HSQL语句检索数据
    // public List findByNamedQuery(String queryName);
    //
    // // 使用带参数的命名HSQL语句检索数据
    // public List findByNamedQuery(String queryName, Object[] values);
    //
    // // 使用带命名参数的命名HSQL语句检索数据
    // public List findByNamedQueryAndNamedParam(String queryName, String[]
    // paramNames, Object[] values);
    //
    // // 使用HSQL语句检索数据，返回 Iterator
    // public Iterator iterate(String queryString);
    //
    // // 使用带参数HSQL语句检索数据，返回 Iterator
    // public Iterator iterate(String queryString, Object[] values);
    //
    // // 关闭检索返回的 Iterator
    // public void closeIterator(Iterator it);

    // //////////////////////////////////////////////////////////////////////
    // -------------------------------- Others ------------------------------
    // //////////////////////////////////////////////////////////////////////

    // 加锁指定的实体
    public void lock(T entity, LockMode mode);

    // 强制立即更新缓冲数据到数据库(否则仅在事务提交时才更新)
    public void flush();
}
