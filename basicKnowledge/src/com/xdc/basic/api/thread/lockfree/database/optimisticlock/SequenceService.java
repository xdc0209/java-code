package com.xdc.basic.api.thread.lockfree.database.optimisticlock;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * CAS用于更新数据库-乐观锁
 * 
 * @author xdc
 * 
 */
public class SequenceService
{
    private SequenceDao dao;

    // 注意，乐观锁时必须使用：@Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public synchronized void increment(String sequenceName)
    {
        for (;;)
        {
            int value = dao.getValue(sequenceName);
            if (dao.compareAndSet(sequenceName, value + 1, value))
            {
                break;
            }
        }
    }
}
