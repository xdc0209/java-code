package com.springinaction.poker.webservice;

import com.springinaction.poker.PokerHandType;

public class EvaluateHandResponse
{
    private PokerHandType pokerHand;

    public EvaluateHandResponse()
    {
        this(PokerHandType.NONE);
    }

    public EvaluateHandResponse(PokerHandType pokerHand)
    {
        this.pokerHand = pokerHand;
    }

    public PokerHandType getPokerHand()
    {
        return this.pokerHand;
    }

    public void setPokerHand(PokerHandType pokerHand)
    {
        this.pokerHand = pokerHand;
    }
}
