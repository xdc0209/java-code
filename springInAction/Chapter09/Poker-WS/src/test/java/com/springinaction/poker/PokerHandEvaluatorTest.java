package com.springinaction.poker;

import junit.framework.TestCase;
import junitx.framework.Assert;

public class PokerHandEvaluatorTest extends TestCase
{
    private static PokerHand   FOUR_OF_A_KIND  = new PokerHand(new Card[] { new Card(Suit.CLUBS, Face.EIGHT),
            new Card(Suit.HEARTS, Face.ACE), new Card(Suit.SPADES, Face.EIGHT), new Card(Suit.HEARTS, Face.EIGHT),
            new Card(Suit.DIAMONDS, Face.EIGHT) });

    private static PokerHand   THREE_OF_A_KIND = new PokerHand(new Card[] { new Card(Suit.CLUBS, Face.EIGHT),
            new Card(Suit.HEARTS, Face.ACE), new Card(Suit.SPADES, Face.TWO), new Card(Suit.HEARTS, Face.EIGHT),
            new Card(Suit.DIAMONDS, Face.EIGHT) });

    private static PokerHand   FULL_HOUSE      = new PokerHand(new Card[] { new Card(Suit.CLUBS, Face.EIGHT),
            new Card(Suit.HEARTS, Face.ACE), new Card(Suit.SPADES, Face.ACE), new Card(Suit.HEARTS, Face.EIGHT),
            new Card(Suit.DIAMONDS, Face.EIGHT) });

    private static PokerHand   TWO_PAIR        = new PokerHand(new Card[] { new Card(Suit.CLUBS, Face.EIGHT),
            new Card(Suit.HEARTS, Face.ACE), new Card(Suit.SPADES, Face.ACE), new Card(Suit.HEARTS, Face.EIGHT),
            new Card(Suit.DIAMONDS, Face.NINE) });

    private static PokerHand   FLUSH           = new PokerHand(new Card[] { new Card(Suit.HEARTS, Face.EIGHT),
            new Card(Suit.HEARTS, Face.ACE), new Card(Suit.HEARTS, Face.TWO), new Card(Suit.HEARTS, Face.SIX),
            new Card(Suit.HEARTS, Face.NINE) });

    private static PokerHand   ROYAL_FLUSH     = new PokerHand(new Card[] { new Card(Suit.DIAMONDS, Face.ACE),
            new Card(Suit.DIAMONDS, Face.JACK), new Card(Suit.DIAMONDS, Face.KING), new Card(Suit.DIAMONDS, Face.TEN),
            new Card(Suit.DIAMONDS, Face.QUEEN) });

    private static PokerHand   STRAIGHT_FLUSH  = new PokerHand(new Card[] { new Card(Suit.SPADES, Face.ACE),
            new Card(Suit.SPADES, Face.TWO), new Card(Suit.SPADES, Face.THREE), new Card(Suit.SPADES, Face.FOUR),
            new Card(Suit.SPADES, Face.FIVE) });

    private static PokerHand   STRAIGHT        = new PokerHand(new Card[] { new Card(Suit.SPADES, Face.ACE),
            new Card(Suit.HEARTS, Face.TWO), new Card(Suit.SPADES, Face.THREE), new Card(Suit.SPADES, Face.FOUR),
            new Card(Suit.DIAMONDS, Face.FIVE) });

    private static PokerHand   NONE            = new PokerHand(new Card[] { new Card(Suit.SPADES, Face.ACE),
            new Card(Suit.HEARTS, Face.SEVEN), new Card(Suit.SPADES, Face.THREE), new Card(Suit.SPADES, Face.FOUR),
            new Card(Suit.DIAMONDS, Face.FIVE) });

    private PokerHandEvaluator evaluator;

    protected void setUp() throws Exception
    {
        evaluator = new PokerHandEvaluatorImpl();
    }

    public void testFourOfAKind()
    {
        assertEquals(PokerHandType.FOUR_OF_A_KIND, evaluator.evaluateHand(FOUR_OF_A_KIND));

        Assert.assertNotEquals(PokerHandType.FOUR_OF_A_KIND, evaluator.evaluateHand(THREE_OF_A_KIND));

        Assert.assertNotEquals(PokerHandType.FOUR_OF_A_KIND, evaluator.evaluateHand(FULL_HOUSE));

        Assert.assertNotEquals(PokerHandType.FOUR_OF_A_KIND, evaluator.evaluateHand(TWO_PAIR));
    }

    public void testThreeOfAKind()
    {
        assertEquals(PokerHandType.THREE_OF_A_KIND, evaluator.evaluateHand(THREE_OF_A_KIND));

        Assert.assertNotEquals(PokerHandType.THREE_OF_A_KIND, evaluator.evaluateHand(FOUR_OF_A_KIND));

        Assert.assertNotEquals(PokerHandType.THREE_OF_A_KIND, evaluator.evaluateHand(FULL_HOUSE));

        Assert.assertNotEquals(PokerHandType.THREE_OF_A_KIND, evaluator.evaluateHand(TWO_PAIR));
    }

    public void testTwoPair()
    {
        assertEquals(PokerHandType.TWO_PAIR, evaluator.evaluateHand(TWO_PAIR));

        Assert.assertNotEquals(PokerHandType.TWO_PAIR, evaluator.evaluateHand(THREE_OF_A_KIND));

        Assert.assertNotEquals(PokerHandType.TWO_PAIR, evaluator.evaluateHand(FULL_HOUSE));

        Assert.assertNotEquals(PokerHandType.TWO_PAIR, evaluator.evaluateHand(FOUR_OF_A_KIND));
    }

    public void testFlush()
    {
        assertEquals(PokerHandType.FLUSH, evaluator.evaluateHand(FLUSH));

        Assert.assertNotEquals(PokerHandType.FLUSH, evaluator.evaluateHand(THREE_OF_A_KIND));

        Assert.assertNotEquals(PokerHandType.FLUSH, evaluator.evaluateHand(FULL_HOUSE));

        Assert.assertNotEquals(PokerHandType.FLUSH, evaluator.evaluateHand(FOUR_OF_A_KIND));
    }

    public void testRoyalFlush()
    {
        assertEquals(PokerHandType.ROYAL_FLUSH, evaluator.evaluateHand(ROYAL_FLUSH));

        Assert.assertNotEquals(PokerHandType.ROYAL_FLUSH, evaluator.evaluateHand(FLUSH));

        Assert.assertNotEquals(PokerHandType.ROYAL_FLUSH, evaluator.evaluateHand(FULL_HOUSE));

        Assert.assertNotEquals(PokerHandType.ROYAL_FLUSH, evaluator.evaluateHand(FOUR_OF_A_KIND));
    }

    public void testStraightFlush()
    {
        assertEquals(PokerHandType.STRAIGHT_FLUSH, evaluator.evaluateHand(STRAIGHT_FLUSH));

        Assert.assertNotEquals(PokerHandType.STRAIGHT_FLUSH, evaluator.evaluateHand(FLUSH));

        Assert.assertNotEquals(PokerHandType.STRAIGHT_FLUSH, evaluator.evaluateHand(ROYAL_FLUSH));

        Assert.assertNotEquals(PokerHandType.STRAIGHT_FLUSH, evaluator.evaluateHand(STRAIGHT));
    }

    public void testStraight()
    {
        assertEquals(PokerHandType.STRAIGHT, evaluator.evaluateHand(STRAIGHT));

        Assert.assertNotEquals(PokerHandType.STRAIGHT, evaluator.evaluateHand(STRAIGHT_FLUSH));

        Assert.assertNotEquals(PokerHandType.STRAIGHT, evaluator.evaluateHand(ROYAL_FLUSH));

        Assert.assertNotEquals(PokerHandType.STRAIGHT, evaluator.evaluateHand(FLUSH));
    }

    public void testNone()
    {
        assertEquals(PokerHandType.NONE, evaluator.evaluateHand(NONE));

        Assert.assertNotEquals(PokerHandType.NONE, evaluator.evaluateHand(STRAIGHT_FLUSH));

        Assert.assertNotEquals(PokerHandType.NONE, evaluator.evaluateHand(ROYAL_FLUSH));

        Assert.assertNotEquals(PokerHandType.NONE, evaluator.evaluateHand(FLUSH));
    }
}
