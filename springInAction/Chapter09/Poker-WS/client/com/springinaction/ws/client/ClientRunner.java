package com.springinaction.ws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springinaction.poker.Card;
import com.springinaction.poker.Face;
import com.springinaction.poker.PokerHandType;
import com.springinaction.poker.Suit;

public class ClientRunner {
  public static void main(String[] args) throws Exception {
    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("com/springinaction/ws/client/poker-ws-client.xml");
    
    PokerClient client = (PokerClient) ctx.getBean("pokerClientGateway");
    
    Card card1 = new Card();
    card1.setFace(Face.ACE); card1.setSuit(Suit.CLUBS);
    Card card2 = new Card();
    card2.setFace(Face.ACE); card2.setSuit(Suit.HEARTS);
    Card card3 = new Card();
    card3.setFace(Face.TWO); card3.setSuit(Suit.CLUBS);
    Card card4 = new Card();
    card4.setFace(Face.TWO); card4.setSuit(Suit.SPADES);
    Card card5 = new Card();
    card5.setFace(Face.TWO); card5.setSuit(Suit.DIAMONDS);
    
    Card[] cards = new Card[] {
        card1, card2, card3, card4, card5
    };    
    
    PokerHandType pokerHandType = client.evaluateHand(cards);
    System.out.println(pokerHandType);
  }
}
