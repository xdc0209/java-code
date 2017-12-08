package com.springinaction.poker.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springinaction.poker.Card;
import com.springinaction.poker.Face;
import com.springinaction.poker.Suit;
import com.springinaction.poker.webservice.EvaluateHandRequest;
import com.springinaction.poker.webservice.EvaluateHandResponse;

public class PokerClient {
   public static void main(String[] args) throws Exception {
      ApplicationContext ctx = new ClassPathXmlApplicationContext(
                        "com/springinaction/poker/client/client.xml");

      PokerService poker = (PokerService) ctx
                        .getBean("pokerHandEvaluator.xfire");

      EvaluateHandRequest request = new EvaluateHandRequest();

      Card card1 = new Card();
      card1.setFace(Face.ACE);
      card1.setSuit(Suit.CLUBS);
      Card card2 = new Card();
      card2.setFace(Face.ACE);
      card2.setSuit(Suit.HEARTS);
      Card card3 = new Card();
      card3.setFace(Face.TWO);
      card3.setSuit(Suit.CLUBS);
      Card card4 = new Card();
      card4.setFace(Face.TWO);
      card4.setSuit(Suit.SPADES);
      Card card5 = new Card();
      card5.setFace(Face.TWO);
      card5.setSuit(Suit.DIAMONDS);

      Card[] cards = new Card[] { card1, card2, card3, card4, card5 };
      request.setHand(cards);

      EvaluateHandResponse response = poker.EvaluateHand(request);
   }
}
