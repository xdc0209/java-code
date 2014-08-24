package com.xdc.basic.api.hibernate.orm3.dao;

import java.util.List;

import com.xdc.basic.api.hibernate.orm3.entity.Event;

public interface EventDao extends BaseDao<Event, Long>
{
    public List<Event> idGreaterThan(long id);

    public List<Event> searchTitle(String str);
}
