package com.springinaction.poker.webservice;

import com.springinaction.poker.Card;

public class EvaluateHandRequest
{
    private Card[] hand;

    public EvaluateHandRequest()
    {
    }

    public Card[] getHand()
    {
        return hand;
    }

    public void setHand(Card[] cards)
    {
        this.hand = cards;
    }
}
