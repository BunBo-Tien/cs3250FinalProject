/*
 * Realtionship: Deck - Card Composition: la bai khong ton tai doc lap ngoai Deck (neu khong co Deck thi 
 * khong co Card de choi)
 * 
 */

public class Card {
	public String rank;
	private String suit;
	
	//Constructor for Card class
	
	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public String getSuit() {
		return suit;
	}

	public String getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return rank + suit;
	}
}
