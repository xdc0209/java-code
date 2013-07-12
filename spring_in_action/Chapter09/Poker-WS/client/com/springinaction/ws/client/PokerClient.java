package com.springinaction.ws.client;

import java.io.IOException;

import com.springinaction.poker.Card;
import com.springinaction.poker.PokerHandType;

public interface PokerClient {
  public PokerHandType evaluateHand(Card[] cards) 
      throws IOException;
}
