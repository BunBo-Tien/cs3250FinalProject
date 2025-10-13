/*
 * Relationship: Dealer - Player Inheritance: Dealer la mot dang dac biet cua player (cung co Hand
 * , rut bai, nhung co them quy tac rieng)
 */
public class Dealer extends Player {
    // Kế thừa các thuộc tính và phương thức từ lớp Player
    public Dealer(String name) {
        super(name); // Gọi constructor của lớp cha (Player)
    }

    // Logic đặc biệt của Dealer: phải rút bài đến khi điểm >= 17
    public void play(Deck deck) {
        while (this.getHand().calculateScore() < 17) {      
            this.hit(deck);
        }
    }
}
