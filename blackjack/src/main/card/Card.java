package main.card;

public class Card {
	private int number;
	private String suit;

	public Card(int number, String suit) {
		this.number = number;
		this.suit = suit;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return this.number;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getSuit() {
		return this.suit;
	}
	public String toString() {
		return this.suit + this.number;
	}
}
