package com.xdc.basic.api.thread.lockfree.database.optimisticlock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("deprecation")
public class SequenceDao extends SqlMapClientDaoSupport
{
    public boolean compareAndSet(String name, int value, int expect)
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", name);
        parameters.put("value", value);
        parameters.put("expect", expect);
        // UPDATE t_sequence SET value = #value# WHERE name = #name# AND value = #expect#
        int updateCount = getSqlMapClientTemplate().update("Sequence.compareAndSet", parameters);
        return updateCount == 1;
    }

    public int getValue(String sequenceName)
    {
        // 在数据库中查询当前值
        return 0;
    }
}
