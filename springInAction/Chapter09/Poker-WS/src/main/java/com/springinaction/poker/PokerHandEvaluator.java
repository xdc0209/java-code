package com.springinaction.poker;

public interface PokerHandEvaluator {
  public PokerHandType evaluateHand(PokerHand hand);
//  public PokerHandType evaluateHand(Card[] cards);
}
