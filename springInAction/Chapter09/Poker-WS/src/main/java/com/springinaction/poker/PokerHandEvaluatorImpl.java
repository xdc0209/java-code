package com.springinaction.poker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class PokerHandEvaluatorImpl implements PokerHandEvaluator {

  public PokerHandEvaluatorImpl() {}
  
  public PokerHandType evaluateHand(PokerHand hand) {
    Card[] cards = hand.getCards();
    sortByFace(cards);
    
    PokerHandType handType = PokerHandType.NONE;
    
    if(isRoyalFlush(cards)) {
      handType = PokerHandType.ROYAL_FLUSH;
    } else if (isStraightFlush(cards)) {
      handType = PokerHandType.STRAIGHT_FLUSH;
    } else if (isFourOfAKind(cards)) {
      handType = PokerHandType.FOUR_OF_A_KIND;
    } else if (isFullHouse(cards)) {
      handType = PokerHandType.FULL_HOUSE;
    } else if (isFlush(cards)) {
      handType = PokerHandType.FLUSH;
    } else if (isStraight(cards)) {
      handType = PokerHandType.STRAIGHT;
    } else if (isThreeOfAKind(cards)) {
      handType = PokerHandType.THREE_OF_A_KIND;
    } else if (isTwoPair(cards)) {
      handType = PokerHandType.TWO_PAIR;
    } else if (isPair(cards)) {
      handType = PokerHandType.PAIR;
    }
    
    return handType;
  }

  private static final String FULL_HOUSE_PATTERN_1 = "AAABB";
  private static final String FULL_HOUSE_PATTERN_2 = "AABBB";
  private static final String FOUR_OF_A_KIND_PATTERN_1 = "AAAAB";
  private static final String FOUR_OF_A_KIND_PATTERN_2 = "ABBBB";
  private static final String THREE_OF_A_KIND_PATTERN_1 = "AAABC";
  private static final String THREE_OF_A_KIND_PATTERN_2 = "ABBBC";
  private static final String THREE_OF_A_KIND_PATTERN_3 = "ABCCC";
  private static final String TWO_PAIR_PATTERN_1 = "AABBC";
  private static final String TWO_PAIR_PATTERN_2 = "AABCC";
  private static final String TWO_PAIR_PATTERN_3 = "ABBCC";
  
  
  
  private boolean isFlush(Card[] cards) {
    Set<Suit> suits = new HashSet<Suit>(5);
    for (int i = 0; i < cards.length; i++) {
			suits.add(cards[i].getSuit());
		}
    
    return suits.size() == 1;
  }
  
  private boolean isFullHouse(Card[] cards) {
    String pattern = getFacePattern(cards);
    return pattern.equals(FULL_HOUSE_PATTERN_1) || 
        pattern.equals(FULL_HOUSE_PATTERN_2);
  }

  private boolean isFourOfAKind(Card[] cards) {
    String pattern = getFacePattern(cards);
    return pattern.equals(FOUR_OF_A_KIND_PATTERN_1) || 
        pattern.equals(FOUR_OF_A_KIND_PATTERN_2);
  }

  private boolean isThreeOfAKind(Card[] cards) {
    String pattern = getFacePattern(cards);
    return pattern.equals(THREE_OF_A_KIND_PATTERN_1) || 
        pattern.equals(THREE_OF_A_KIND_PATTERN_2) || 
        pattern.equals(THREE_OF_A_KIND_PATTERN_3);
  }

  private boolean isTwoPair(Card[] cards) {
    String pattern = getFacePattern(cards);
    return pattern.equals(TWO_PAIR_PATTERN_1) || 
        pattern.equals(TWO_PAIR_PATTERN_2) || 
        pattern.equals(TWO_PAIR_PATTERN_3);
  }

  private boolean isPair(Card[] cards) {
    String pattern = getFacePattern(cards);
    
    return (pattern.indexOf("AA") >= 0 || 
        pattern.indexOf("BB")>0 || 
        pattern.indexOf("CC") > 0 || 
        pattern.indexOf("DD") > 0);
  }
  
  private boolean isStraight(Card[] cards) {
    int lastFace = cards[0].getFace().ordinal();
    for (int i = 1; i < cards.length; i++) {
			if(cards[i].getFace().ordinal() != lastFace+1) {
        return false;
      }
      lastFace = cards[i].getFace().ordinal();
		}
    
    return true;
  }
  
  private boolean isRoyalStraight(Card[] cards) {    
    if(!cards[0].getFace().equals(Face.ACE)) {
      return false;
    }

    int lastFace = Face.NINE.ordinal();  // fake it out by pretending the first card was a nine
    for (int i = 1; i < cards.length; i++) {
      if(cards[i].getFace().ordinal() != lastFace+1) {
        return false;
      }
      lastFace = cards[i].getFace().ordinal();
    }    
    
    return true;
  }
  
  private boolean isStraightFlush(Card[] cards) {
    return isStraight(cards) && isFlush(cards);
  }
  
  private void sortByFace(Card[] cards) {
    Arrays.sort(cards, new Comparator<Card>() {
      public int compare(Card c1, Card c2) {
        return c1.getFace().ordinal() - c2.getFace().ordinal();
      }      
    });
  }
  
  private boolean isRoyalFlush(Card[] cards) {  
    return isRoyalStraight(cards) && isFlush(cards);
  }
  
  private String getFacePattern(Card[] cards) {
    StringBuffer patternBuffer = new StringBuffer(5);
    patternBuffer.append("A");
    char nextChar = 'A';
    Face lastFace = cards[0].getFace();
    
    for(int i=1; i<cards.length; i++) {
      if(!cards[i].getFace().equals(lastFace)) {
        nextChar++;
        lastFace = cards[i].getFace();
      }
      
      patternBuffer.append(nextChar);
    }
    
    return patternBuffer.toString();
  }
}

