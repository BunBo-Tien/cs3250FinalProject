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

    //this will be extend later to let player put their name in and display on the GUI
    public String getName() {
        return this.name;
    }

    //Method for player to hit from the Deck
    public void hit(Deck deck) {
        this.hand.addCard(deck.drawCard());
    }
}
