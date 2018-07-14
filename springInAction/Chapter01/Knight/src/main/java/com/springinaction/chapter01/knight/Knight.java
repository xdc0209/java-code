package com.springinaction.chapter01.knight;

public interface Knight
{
    public Object embarkOnQuest() throws QuestFailedException;

    public String getName();
}
