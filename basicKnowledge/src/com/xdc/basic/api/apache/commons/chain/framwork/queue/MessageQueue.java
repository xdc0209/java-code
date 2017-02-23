package com.xdc.basic.api.apache.commons.chain.framwork.queue;

public interface MessageQueue<E>
{
    void put(E e);

    E take();
}
