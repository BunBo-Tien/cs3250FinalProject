/*
 * Relationship: Player – Hand  Composition: Moi player luon co mot hand. Neu player bien mat, Hand cua Player
 * cung bien mat.
 * 
 * Relationship: Player - Game Dependency: Player can Game de choi (vi luat va luot duoc dieu khien boi Game)
 * Nhung PLayer ban than co the ton tai nhu mot object doc lap
 */
public class Player {
    private Hand hand;
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public Hand getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }

    //Phương thức để người chơi rút thêm bài từ bộ bài chính
    public void hit(Deck deck) {
        this.hand.addCard(deck.drawCard());
    }

    //Phương thức để người chơi dừng lại
    public void stand() {
    	
    }

    //Hiển thị các lá bài trên tay
    public void showHand(boolean hideFirstCard) {
        if (hideFirstCard) {
            System.out.print("[Lá bài úp] ");
            //Hiển thị các lá bài còn lại
            for (int i = 1; i < this.hand.getCards().size(); i++) {
                System.out.print(this.hand.getCards().get(i).toString() + " ");
            }
        } else {
            System.out.println(this.hand.toString() + " (Điểm: " + this.hand.calculateScore() + ")");
        }
    }
}
