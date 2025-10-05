/*
 * Realtionship: Deck - Card Composition: la bai khong ton tai doc lap ngoai Deck (neu khong co Deck thi 
 * khong co Card de choi)
 * 
 */

public class Card {
    private final String suit; //Chất (Cơ, Rô, Chuồn, Bích)
    private final String rank; //Hạng (2, 3, ..., 10, J, Q, K)
    private final int value; //Giá trị

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
     * Chuyển đổi thông tin lá bài để khớp với tên tệp ảnh (ví dụ: "K-S.png").
     * Converts card info to match the image filename format (e.g., "K-S.png").
     * @return A path string like "/cards/K-S.png"
     */
    public String getImagePath() {
        String rankName = this.rank; //Hạng A, 2-10, J, Q, K khớp với tên tệp
        String suitChar;

        //Chuyển đổi ký hiệu suit thành ký tự trong tên tệp
        //Convert suit symbol to single character for filename
        switch (this.suit) {
            case "♠": suitChar = "S"; break; //Spades
            case "♥": suitChar = "H"; break; //Hearts
            case "♦": suitChar = "D"; break; //Diamonds
            case "♣": suitChar = "C"; break; //Clubs
            default:  suitChar = ""; break; //Trường hợp dự phòng, không nên xảy ra
        }

        //Trả về đường dẫn theo định dạng "RANK-SUIT.png"
        return "/cards/" + rankName + "-" + suitChar + ".png";
    }
}
