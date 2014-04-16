package com.xdc.basic.api.thread.lockfree.database.pessimisticlock;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用悲观锁版本
 * 
 * @author xdc
 * 
 */
public class SequenceService
{
    private SequenceDao dao;

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void increment2(String sequenceName)
    {
        int value = dao.getValueForUpdate(sequenceName);
        dao.set(sequenceName, value + 1);
    }
}
