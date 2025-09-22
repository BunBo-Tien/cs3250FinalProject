/*
 * Relationship: Hand - Card Aggregation: Mot Hand chua nhieu Card. Nhung Card co the ton tai ngoai Hand
 */
import java.util.ArrayList;

public class Hand {
    // Đây là danh sách các lá bài trong tay. 
    // Chúng ta dùng 'private' để đảm bảo chỉ có các phương thức của lớp này mới có thể thay đổi nó.
    private ArrayList<Card> cards;

    //Hàm khởi tạo (constructor) của lớp Hand.
    //Khi một đối tượng Hand được tạo, nó sẽ khởi tạo một ArrayList rỗng.
    public Hand() {
        this.cards = new ArrayList<Card>();
    }
    
    //Phương thức này cho phép thêm một lá bài vào tay.
    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    public ArrayList<Card> getCards() {
        return this.cards;
    }
    
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

        //Vòng lặp này sẽ xử lý các lá Ace nếu tổng điểm bị bù. Neu nhu la Ace cong lai hon 21 no se tu dong tru di 10 dem tro thanh 1
        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }
    
    @Override
    public String toString() {
    	//Chuyen doi de in dong lenh acutal card chu khong phai la memory address.
        StringBuilder handString = new StringBuilder();
        for (Card card : this.cards) {
            handString.append(card.toString()).append(" ");
        }
        return handString.toString().trim();
    }
}