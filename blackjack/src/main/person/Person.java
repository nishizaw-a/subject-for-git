package main.person;

import java.util.LinkedList;
import java.util.List;

import main.card.Card;
import main.util.constants.Constants;
import main.util.exception.SystemException;
import main.util.properties.MessageProperties;

public abstract class Person {
	private String name;//ディーラーネーム
	private List<Card> hand;//手持ちカード
	private boolean isBurst;//バーストしているか
	private boolean isStand;//スタンドしているか
	private boolean isBlackJack;//役がブラックジャックかどうか
	private int total;//現在の手持ちカードの合計 サレンダー、バースト時はConstants.BURST

	public abstract void checkStatus() throws SystemException;

	public void initialize() {
		this.hand = new LinkedList<Card>();
		this.isBurst = false;
		this.isStand = false;
		this.isBlackJack = false;
		this.total = 0;
	}

	public Person(String name) {
		this.name = name;
	}

	public void hit(Card card) throws SystemException{
		if(!this.isBurst && !this.isStand) {
			System.out.println(MessageProperties.getMessage("blackjack.hit"));
			this.add(card);
		}else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.hit"));
		}
	}

	public void stand() throws SystemException{
		if(!this.isBurst && !this.isStand) {
			System.out.println(MessageProperties.getMessage("blackjack.stand"));
			this.isStand = true;
		}else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.stand"));
		}
	}

	private void calculation() {
		int total = 0;
		boolean existAce = false;

		if(!this.isBurst) {
			for(Card card : this.hand) {
				if(card.getNumber() >= 10) {
					total += 10;
				}else {
					if((!existAce) && (card.getNumber() == 1)) {
						existAce = true;
					}
					total+=card.getNumber();
				}
			}

			if(existAce && ((total + 10) <= 21)) {
				total += 10;
			}else if(total > 21) {
				total = Constants.BURST;
			}
			this.total = total;
		}
	}

	protected void updateStatus()  throws SystemException{
		this.calculation();

		if((this.total == 21) && (this.hand.size() == 2)) {
			this.isStand = true;
			this.isBlackJack = true;
		}

		if(this.total == Constants.BURST) {
			this.isBurst = true;
		}
	}

	public void add(Card card) {
		this.hand.add(card);
	}

	public void open() {
		System.out.println(this.toString());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder().append(this.name).append(" : ");

		for(Card card : this.hand) {
			sb.append(card).append(" ");
		}

		return sb.toString();
	}

	public String getName() {
		return this.name;
	}

	public List<Card> getHand(){
		return this.hand;
	}

	public boolean getIsBurst() {
		return this.isBurst;
	}

	public boolean getIsStand() {
		return this.isStand;
	}

	public boolean getIsBlackJack() {
		return this.isBlackJack;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setIsBurst(boolean bool) {
		this.isBurst = bool;
	}
}
