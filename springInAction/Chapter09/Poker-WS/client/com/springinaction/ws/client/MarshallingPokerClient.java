package com.springinaction.ws.client;

import java.io.IOException;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.springinaction.poker.Card;
import com.springinaction.poker.PokerHandType;
import com.springinaction.poker.webservice.EvaluateHandRequest;
import com.springinaction.poker.webservice.EvaluateHandResponse;

public class MarshallingPokerClient 
    implements PokerClient {
  public PokerHandType evaluateHand(Card[] cards) 
      throws IOException {
    EvaluateHandRequest request = new EvaluateHandRequest();
    
    request.setHand(cards);
    
    EvaluateHandResponse response = (EvaluateHandResponse)
        webServiceTemplate.marshalSendAndReceive(request);
    
    return response.getPokerHand();
  }
  
  // INJECTED
  private WebServiceTemplate webServiceTemplate;
  public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
    this.webServiceTemplate = webServiceTemplate;
  }
}
