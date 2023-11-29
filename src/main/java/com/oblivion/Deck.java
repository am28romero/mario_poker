package com.oblivion;

import com.oblivion.Card.Suit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {

    static private Card[] sortedDeck = {
        new Card(Suit.HEARTS, 1),
        new Card(Suit.HEARTS, 2),
        new Card(Suit.HEARTS, 3),
        new Card(Suit.HEARTS, 4),
        new Card(Suit.HEARTS, 5),
        new Card(Suit.HEARTS, 6),
        new Card(Suit.HEARTS, 7),
        new Card(Suit.HEARTS, 8),
        new Card(Suit.HEARTS, 9),
        new Card(Suit.HEARTS, 10),
        new Card(Suit.HEARTS, 11),
        new Card(Suit.HEARTS, 12),
        new Card(Suit.HEARTS, 13),
        new Card(Suit.DIAMONDS, 1),
        new Card(Suit.DIAMONDS, 2),
        new Card(Suit.DIAMONDS, 3),
        new Card(Suit.DIAMONDS, 4),
        new Card(Suit.DIAMONDS, 5),
        new Card(Suit.DIAMONDS, 6),
        new Card(Suit.DIAMONDS, 7),
        new Card(Suit.DIAMONDS, 8),
        new Card(Suit.DIAMONDS, 9),
        new Card(Suit.DIAMONDS, 10),
        new Card(Suit.DIAMONDS, 11),
        new Card(Suit.DIAMONDS, 12),
        new Card(Suit.DIAMONDS, 13),
        new Card(Suit.SPADES, 1),
        new Card(Suit.SPADES, 2),
        new Card(Suit.SPADES, 3),
        new Card(Suit.SPADES, 4),
        new Card(Suit.SPADES, 5),
        new Card(Suit.SPADES, 6),
        new Card(Suit.SPADES, 7),
        new Card(Suit.SPADES, 8),
        new Card(Suit.SPADES, 9),
        new Card(Suit.SPADES, 10),
        new Card(Suit.SPADES, 11),
        new Card(Suit.SPADES, 12),
        new Card(Suit.SPADES, 13),
        new Card(Suit.CLUBS, 1),
        new Card(Suit.CLUBS, 2),
        new Card(Suit.CLUBS, 3),
        new Card(Suit.CLUBS, 4),
        new Card(Suit.CLUBS, 5),
        new Card(Suit.CLUBS, 6),
        new Card(Suit.CLUBS, 7),
        new Card(Suit.CLUBS, 8),
        new Card(Suit.CLUBS, 9),
        new Card(Suit.CLUBS, 10),
        new Card(Suit.CLUBS, 11),
        new Card(Suit.CLUBS, 12),
        new Card(Suit.CLUBS, 13)
    };

    int index = 0;
    static Card[] deck = new Card[52];

    public Deck() {
        deck = getRandomDeck();
    }

    public static Card[] getRandomDeck() {
        List<Card> shuffledDeck = Arrays.asList(sortedDeck);
        Collections.shuffle(shuffledDeck);
        return shuffledDeck.toArray(new Card[52]);
    }

    public Card[] getDeck() { return deck; }

    public Card getNextCard() {
        if (index < 52) {
            return deck[index++];
        } else {
            deck = getRandomDeck();
            index = 0;
            return deck[index++];
        }
    }
}
