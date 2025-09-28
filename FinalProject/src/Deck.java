/*
 * Realtionship: Deck - Card Composition: la bai khong ton tai doc lap ngoai Deck (neu khong co Deck thi 
 * khong co Card de choi)
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards; // List of the cards

    //Constructor: tao 52 la bai
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", 
                          "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    //Phương thức Xáo bài
    public void shuffle() {
        Collections.shuffle(cards);
    }

    //Rút một lá bài từ trên cùng
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck run out of card!");
        }
        return cards.remove(0);
    }

    //Kiểm tra số lượng còn lại
    public int size() {
        return cards.size();
    }
}

