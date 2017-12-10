package com.xdc.basic.api.thread.threadlocal.demo2;

public class Player extends Thread
{
    private final int playerId;

    public Player(int playerId)
    {
        this.playerId = playerId;
    }

    @Override
    public void run()
    {
        boolean success = false;
        do
        {
            int value = Attempt.guess(Judge.MAX_VALUE);
            success = Judge.judge(value);
            println(String.format("Player %s attempts %s and %s.", playerId, value, success ? "success" : "failed"));
        } while (!success);

        println(String.format("Player %s completed. %s.", playerId, Attempt.getRecord()));
    }

    private void println(String string)
    {
        System.out.println(string);
    }
}
