package com.springinaction.poker.client;

import com.springinaction.poker.webservice.EvaluateHandRequest;
import com.springinaction.poker.webservice.EvaluateHandResponse;

public interface PokerService {
  public EvaluateHandResponse EvaluateHand(EvaluateHandRequest request);
}
