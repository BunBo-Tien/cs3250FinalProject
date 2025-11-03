/*
 * Realtionship: Deck - Card Composition: la bai khong ton tai doc lap ngoai Deck (neu khong co Deck thi 
 * khong co Card de choi)
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    /**
     * Constructor for Deck. Initializes a new deck of 52 cards and shuffles it.
     */
    public Deck() {
        // We create the deck immediately when a Deck object is made.
        this.cards = new ArrayList<>();
        createNewDeck();
    }
    
    /**
     * Fills the cards list with 52 fresh cards and shuffles them.
     * This helper method is used by the constructor and when the deck runs out of cards.
     */
    private void createNewDeck() {
        cards.clear(); // Clear any old cards
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value;
                if (rank.equals("A")) {
                    value = 11;
                } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                    value = 10;
                } else {
                    value = Integer.parseInt(rank);
                }
                cards.add(new Card(suit, rank, value));
            }
        }
        shuffle(); // Shuffle the newly created deck
    }

    /**
     * Shuffles the cards in the deck randomly.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws one card from the top of the deck.
     * If the deck is empty, it automatically creates a new, shuffled deck before drawing.
     * @return The card removed from the deck.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty. Reshuffling a new deck.");
            createNewDeck();
        }
        return cards.remove(0);
    }
}



