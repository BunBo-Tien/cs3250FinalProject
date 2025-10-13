/*
 * Relationship: Hand - Card Aggregation: Mot Hand chua nhieu Card. Nhung Card van co the ton tai ngoai Hand
 */
import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;

 
    public Hand() {
        this.cards = new ArrayList<Card>();
    }
    

    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    public ArrayList<Card> getCards() {
        return this.cards;
    }
    
    
    //Clear all the card on hand
    public void clear() {
        cards.clear();
    }
    
    //This method help calculate the score for Ace
    public int calculateScore() {
        int score = 0;
        int numAces = 0;

        for (Card card : this.cards) {
            String rank = card.getRank();

            switch (rank) {
                case "J":
                case "Q":
                case "K":
                    score += 10;
                    break;
                case "A":
                    score += 11;
                    numAces++;
                    break;
                default:
                    //Chuyển đổi chuỗi số (ví dụ: "2") thành số nguyên
                    score += Integer.parseInt(rank);
                    break;
            }
        }

        //This loop will decide if Ace card value got bust, it will minus 10 to make the Ace card value become 1
        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }
    
}