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


    //Hàm khởi tạo của Deck.
    //Tạo ra một bộ bài 52 lá hoàn chỉnh.
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"♠", "♥", "♦", "♣"}; //Bích, Cơ, Rô, Chuồn
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                //Thêm logic để xác định giá trị (value) của lá bài
                //Added logic to determine the card's value.
                int value;
                if (rank.equals("A")) {
                    value = 11; //Ace is initially 11
                } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                    value = 10; //Face cards are 10
                } else {
                    value = Integer.parseInt(rank); //Number cards are their face value
                }
                
                //Gọi hàm khởi tạo đúng với 3 tham số (suit, rank, value)
                //Called the correct constructor with 3 arguments.
                cards.add(new Card(suit, rank, value));
            }
        }
    }


    public void shuffle() {
        Collections.shuffle(cards);
    }


    public Card drawCard() {
        if (cards.isEmpty()) {
            //Nếu hết bài, có thể tạo lại và xáo trộn, hoặc xử lý khác
            //For now, we'll just return null or throw an exception if the deck is empty.
            return null;
        }
        return cards.remove(0);
    }
}

