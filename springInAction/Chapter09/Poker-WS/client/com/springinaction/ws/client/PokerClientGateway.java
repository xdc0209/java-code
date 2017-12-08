package com.springinaction.ws.client;

import java.io.IOException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.springinaction.poker.Card;
import com.springinaction.poker.PokerHandType;
import com.springinaction.poker.webservice.EvaluateHandRequest;
import com.springinaction.poker.webservice.EvaluateHandResponse;

public class PokerClientGateway 
    extends WebServiceGatewaySupport
    implements PokerClient {

  public PokerHandType evaluateHand(Card[] cards) 
      throws IOException {
    EvaluateHandRequest request = new EvaluateHandRequest();
    
    request.setHand(cards);
    
    EvaluateHandResponse response = (EvaluateHandResponse)
        getWebServiceTemplate().marshalSendAndReceive(request);
    
    return response.getPokerHand();
  }
}
