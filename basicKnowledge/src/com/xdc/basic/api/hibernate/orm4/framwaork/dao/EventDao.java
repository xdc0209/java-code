package com.xdc.basic.api.hibernate.orm4.framwaork.dao;

import java.util.List;

import com.xdc.basic.api.hibernate.orm4.framwaork.entity.Event;

public interface EventDao extends BaseDao<Event, Long>
{
    public List<Event> idGreaterThan(long id);

    public List<Event> idBetween(long startId, long endId);

    public List<Event> searchTitle(String str);
}
