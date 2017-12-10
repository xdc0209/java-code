package com.springinaction.poker.webservice;

import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;

import com.springinaction.poker.PokerHand;
import com.springinaction.poker.PokerHandEvaluator;
import com.springinaction.poker.PokerHandType;

/**
 * A poker hand evaluation endpoint that processes marshaled messages.
 * 
 * From Listing 9.3
 * 
 * @author wallsc
 */
public class EvaluateHandMarshallingEndpoint extends
                  AbstractMarshallingPayloadEndpoint {

   @Override
   protected Object invokeInternal(Object object) throws Exception {

      EvaluateHandRequest request = (EvaluateHandRequest) object;

      PokerHand pokerHand = new PokerHand();
      pokerHand.setCards(request.getHand());

      PokerHandType pokerHandType = pokerHandEvaluator.evaluateHand(pokerHand);

      return new EvaluateHandResponse(pokerHandType);
   }

   // injected
   private PokerHandEvaluator pokerHandEvaluator;

   public void setPokerHandEvaluator(PokerHandEvaluator pokerHandEvaluator) {
      this.pokerHandEvaluator = pokerHandEvaluator;
   }
}
