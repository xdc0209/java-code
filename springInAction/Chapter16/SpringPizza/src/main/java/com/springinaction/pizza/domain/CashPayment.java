package com.springinaction.pizza.domain;

public class CashPayment extends Payment
{
    public CashPayment()
    {
    }

    public String toString()
    {
        return "CASH:  $" + getAmount();
    }
}
