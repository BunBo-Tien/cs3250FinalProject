/*
 * Realtionship: Deck - Card Composition: la bai khong ton tai doc lap ngoai Deck (neu khong co Deck thi 
 * khong co Card de choi)
 * 
 */

public class Card {
    private final String suit;
    private final String rank;
    private final int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getRank() {
        return rank;
    }
    
    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " " + suit;
    }

    /**
     * Converts card info to match the image filename format (e.g., "K-S.png").
     * @return A path string like "/cards/K-S.png"
     */
    public String getImagePath() {
        String rankName = this.rank; 
        String suitChar;

        //Convert suit symbol to single character for filename
        switch (this.suit) {
            case "♠": suitChar = "S"; break; //Spades
            case "♥": suitChar = "H"; break; //Hearts
            case "♦": suitChar = "D"; break; //Diamonds
            case "♣": suitChar = "C"; break; //Clubs
            default:  suitChar = ""; break;
        }

        return "/cards/" + rankName + "-" + suitChar + ".png";
    }
}
