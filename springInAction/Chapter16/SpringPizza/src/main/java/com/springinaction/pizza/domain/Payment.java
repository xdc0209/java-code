package com.springinaction.pizza.domain;

public abstract class Payment {
  private float amount;
  public void setAmount(float amount) {
    this.amount = amount;
  }
  
  public float getAmount() {
    return amount;
  }
}
