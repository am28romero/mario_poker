package com.oblivion;

public class Card {
    public enum Suit {
        HEARTS,
        DIAMONDS,
        SPADES,
        CLUBS
    }

    protected Suit suit;
    protected int value;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Card() {
        this.suit = getRandomSuit();
        this.value = getRandomValue();
    }

    public Suit getSuit() { return suit; }
    public int getValue() { return value; }
    public String getSuitString() { return suit.name(); }
    public String getValueString() {
        switch (value) {
            case 1:
                return "Ace";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
        }
        return Integer.toString(value);
    }
    public Card getCard() { return new Card(suit, value); }

    public void setSuit(Suit suit) { this.suit = suit; }
    public void setValue(int value) { this.value = value; }


    public static Suit getRandomSuit() {
        int suitIndex = (int) (Math.random() * 4);
        switch (suitIndex) {
            case 0:
                return Suit.HEARTS;
            case 1:
                return Suit.DIAMONDS;
            case 2:
                return Suit.SPADES;
            case 3:
                return Suit.CLUBS;
            default:
                throw new IllegalStateException("Unexpected value: " + suitIndex);
        }
    }

    public static int getRandomValue() {
        return (int) (Math.random() * 13) + 1;
    }

    public static Card[] sort(Card[] cards) {
        Card[] sortedCards = cards.clone();
        // sort the cards by value
        for (int i = 0; i < sortedCards.length; i++) {
            for (int j = i; j < sortedCards.length; j++) {
                if (sortedCards[i].getValue() < sortedCards[j].getValue()) {
                    Card temp = sortedCards[i];
                    sortedCards[i] = sortedCards[j];
                    sortedCards[j] = temp;
                }
            }
        }
        return sortedCards;
    }
}
