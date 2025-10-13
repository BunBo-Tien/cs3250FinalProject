/*
 * Relationship: Dealer - Player Inheritance: Dealer la mot dang dac biet cua player (cung co Hand
 * , rut bai, nhung co them quy tac rieng)
 */
public class Dealer extends Player {
    //Inherits attributes and methods from the Player class
    public Dealer(String name) {
        super(name); //Calls the constructor of the parent class (Player)
    }

    //Special Dealer logic: must draw cards until the score is 17 or higher
    public void play(Deck deck) {
        while (this.getHand().calculateScore() < 17) {      
            this.hit(deck);
        }
    }
}
