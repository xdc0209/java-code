package com.xdc.basic.api.thread.lockfree.database.pessimisticlock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("deprecation")
public class SequenceDao extends SqlMapClientDaoSupport
{
    public int getValueForUpdate(String name)
    {
        // SELECT value FROM t_sequenceWHERE name = #name# FOR UPDATE
        return (Integer) getSqlMapClientTemplate().queryForObject("Sequence.getValueForUpdate", name);
    }

    public void set(String name, int value)
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", name);
        parameters.put("value", value);
        // UPDATE t_sequence SET value = #value# WHERE name = #name#
        getSqlMapClientTemplate().update("Sequence.set", parameters);
    }
}