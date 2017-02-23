package com.xdc.basic.api.hibernate.orm3.framwaork;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.google.common.collect.Lists;
import com.xdc.basic.api.hibernate.orm3.framwaork.dao.impl.EventDaoImpl;
import com.xdc.basic.api.hibernate.orm3.framwaork.entity.Event;
import com.xdc.basic.api.hibernate.orm3.framwaork.util.Hibernate3Util;

public class HibernateDaoTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        // //////////////////////////////
        // 初始化dao
        // //////////////////////////////
        EventDaoImpl eventDao = new EventDaoImpl();
        eventDao.setSessionFactory(Hibernate3Util.getSessionFactory());

        // //////////////////////////////
        // 初始化数据
        // //////////////////////////////
        Event event = new Event();
        event.setTitle("Event1: " + UUID.randomUUID().toString());
        event.setDate(new Date());

        Event event2 = new Event();
        event2.setTitle("Event2: " + UUID.randomUUID().toString());
        event2.setDate(new Date());

        Collection<Event> events = Lists.newArrayList(event, event2);

        // //////////////////////////////
        // 基本检索、增加、修改、删除操作
        // //////////////////////////////

        // 增
        eventDao.save(event);
        eventDao.saveOrUpdate(event2);
        eventDao.saveOrUpdateAll(events);

        // 删
        eventDao.delete(event);
        eventDao.deleteById(event2.getId());

        eventDao.save(event);
        eventDao.save(event2);
        eventDao.deleteAll(events);

        // 改
        eventDao.save(event);
        event.setTitle("Event1: xdc title.");
        eventDao.update(event);

        // 查
        Event getEvent = eventDao.get(event.getId());
        // load() 存在延迟加载问题，最好使用get()
        Event loadEvent = eventDao.load(event.getId());
        List<Event> loadAllEvents = eventDao.loadAll();

        int pageCount = 5;
        int allRowCount = eventDao.allRowCount();
        for (int i = 0; i < allRowCount; i = i + pageCount)
        {
            List<Event> loadAllEventsPage = eventDao.loadAll(i, pageCount);
            System.out.println(loadAllEventsPage);
        }

        // ////////////////////////////////////////////////////////////////////////
        // Criteria的使用，下面的只是演示Criteria的参数写法，与此例中的event类关系不大
        // 这里只是展示Criteria的用法，Criteria要写在dao内类，才能使分层清晰明了
        // ////////////////////////////////////////////////////////////////////////

        DetachedCriteria detachedCriteria = eventDao.createDetachedCriteria();

        // 限制结果集内容,包括查询示例 Example
        detachedCriteria = congfigCriteria(detachedCriteria);

        // 结果集排序
        detachedCriteria = congfigOrder(detachedCriteria);

        // 投影, 如rowCount,max,min,avg,sum
        detachedCriteria = congfigProjection(detachedCriteria);

        List<Event> findByCriteria = eventDao.findByCriteria(detachedCriteria);
        List<Event> findByCriteriaPage = eventDao.findByCriteria(1, 3, detachedCriteria);

        List<Event> findEqualByEntity = eventDao.findEqualByEntity(event);
        List<Event> findLikeByEntity = eventDao.findLikeByEntity(event);

        List<Event> idGreaterThan = eventDao.idGreaterThan(10L);

        List<Event> searchTitle = eventDao.searchTitle("xdc");
    }

    /**
     * 投影, 如rowCount,max,min,avg,sum
     */
    private static DetachedCriteria congfigProjection(DetachedCriteria detachedCriteria)
    {
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.rowCount());
        projectionList.add(Projections.avg("weight"));
        projectionList.add(Projections.max("weight"));
        projectionList.add(Projections.groupProperty("color"));

        return detachedCriteria.setProjection(projectionList);
    }

    /**
     * 结果集排序
     */
    private static DetachedCriteria congfigOrder(DetachedCriteria detachedCriteria)
    {
        detachedCriteria.add(Restrictions.like("name", "F%"));
        // 与上句一样 detachedCriteria.add(Property.forName("name").like("F%"));
        detachedCriteria.addOrder(Order.asc("name"));
        detachedCriteria.addOrder(Order.desc("age"));

        return detachedCriteria;
    }

    /**
     * 限制结果集内容,包括查询示例 Example
     */
    private static DetachedCriteria congfigCriteria(DetachedCriteria detachedCriteria)
    {
        // 基本用法
        detachedCriteria.add(Restrictions.like("name", "Fritz%"));
        detachedCriteria.add(Restrictions.between("weight", 50, 100));
        detachedCriteria.add(Restrictions.in("name", new String[] { "Fritz", "Izi", "Pk" }));

        detachedCriteria.add(Property.forName("name").in(new String[] { "Fritz", "Izi", "Pk" }));

        detachedCriteria.add(Restrictions.or(Restrictions.eq("age", new Integer(0)), Restrictions.isNull("age")));
        detachedCriteria.add(Restrictions.and(Restrictions.eq("age", new Integer(0)), Restrictions.isNull("weight")));

        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.isNull("age"));
        disjunction.add(Restrictions.eq("age", new Integer(0)));
        disjunction.add(Restrictions.eq("age", new Integer(1)));
        disjunction.add(Restrictions.eq("age", new Integer(2)));
        detachedCriteria.add(disjunction);

        Property age = Property.forName("age");
        Disjunction disjunction2 = Restrictions.disjunction();
        disjunction2.add(age.isNull());
        disjunction2.add(age.eq(new Integer(0)));
        disjunction2.add(age.eq(new Integer(1)));
        disjunction2.add(age.eq(new Integer(2)));
        detachedCriteria.add(disjunction2);

        // 示例查询
        Example example = Example.create(new Object());
        example.excludeZeroes(); // exclude zero valued properties
        example.ignoreCase(); // perform case insensitive string comparisons
        example.enableLike(); // use like for string comparisons
        example.excludeProperty("color"); // exclude the property named "color"
        detachedCriteria.add(example);

        // 混合演示，当你添加一个投影到一个投影列表中时 你可以为它指定一个别名：
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.rowCount(), "catCountByColor");
        projectionList.add(Projections.avg("weight"), "avgWeight");
        projectionList.add(Projections.max("weight"), "maxWeight");
        projectionList.add(Projections.groupProperty("color"), "color");
        detachedCriteria.setProjection(projectionList);
        detachedCriteria.addOrder(Order.desc("catCountByColor"));
        detachedCriteria.addOrder(Order.desc("avgWeight"));

        return detachedCriteria;
    }
}