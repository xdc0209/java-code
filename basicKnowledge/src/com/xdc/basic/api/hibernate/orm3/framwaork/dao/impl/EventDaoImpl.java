package com.xdc.basic.api.hibernate.orm3.framwaork.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xdc.basic.api.hibernate.orm3.framwaork.dao.EventDao;
import com.xdc.basic.api.hibernate.orm3.framwaork.entity.Event;

public class EventDaoImpl extends BaseDaoImpl<Event, Long> implements EventDao
{
    public static String ID    = "id";
    public static String TITLE = "title";
    public static String DATE  = "date";

    @Override
    public List<Event> idGreaterThan(long id)
    {
        DetachedCriteria detachedCriteria = createDetachedCriteria();
        detachedCriteria.add(Restrictions.gt(ID, id));
        return findByCriteria(detachedCriteria);
    }

    @Override
    public List<Event> idBetween(long startId, long endId)
    {
        return findByCriterions(Restrictions.gt(ID, startId), Restrictions.lt(ID, endId));
    }

    @Override
    public List<Event> searchTitle(String str)
    {
        DetachedCriteria detachedCriteria = createDetachedCriteria();

        // ilike is case-insensitive "like"
        detachedCriteria.add(Restrictions.ilike(TITLE, str, MatchMode.ANYWHERE));

        return findByCriteria(detachedCriteria);
    }
}
