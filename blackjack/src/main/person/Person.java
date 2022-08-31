package main.person;

import java.util.LinkedList;
import java.util.List;

import main.card.Card;
import main.util.constants.Constants;
import main.util.exception.SystemException;
import main.util.properties.MessageProperties;

public abstract class Person {

	/**
	 * 名前
	 */
	private String name;

	/**
	 * 手持ちカード
	 */
	private List<Card> hand;

	/**
	 * バースト判定
	 */
	private boolean isBurst;

	/**
	 * スタンド判定
	 */
	private boolean isStand;

	/**
	 * ブラックジャック判定
	 */
	private boolean isBlackJack;

	/**
	 * 手持ちカードの合計点
	 */
	private int total;

	public abstract void checkStatus() throws SystemException;
	
	/**
	 * 初期化メソッド
	 * 
	 * 
	 */
	public void initialize() {
		this.hand = new LinkedList<Card>();
		this.isBurst = false;
		this.isStand = false;
		this.isBlackJack = false;
		this.total = 0;
	}
	
	/**
	 * 
	 * 
	 *
	 */
	public Person(String name) {
		this.name = name;
	}

	/**
	 * ヒット処理メソッド
	 *
	 *
	 */
	public void hit(Card card) throws SystemException {
		if (!this.isBurst && !this.isStand) {
			System.out.println(MessageProperties.getMessage("blackjack.hit"));
			this.add(card);
		} else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.hit"));
		}
	}

	/**
	 * スタンド処理メソッド
	 *
	 *
	 */
	public void stand() throws SystemException {
		if (!this.isBurst && !this.isStand) {
			System.out.println(MessageProperties.getMessage("blackjack.stand"));
			this.isStand = true;
		} else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.stand"));
		}
	}

	/**
	 * 点数計算メソッド
	 *
	 *
	 */
	private void calculation() {
		int total = 0;
		boolean existAce = false;

		if (!this.isBurst) {
			for (Card card : this.hand) {

				//取得カード10以上の点数処理
				if (card.getNumber() >= 10) {
					total += 10;
					//エース取得時1換算処理
				} else {
					if ((!existAce) && (card.getNumber() == 1)) {
						existAce = true;
					}
					total += card.getNumber();
				}
			}
			//エース取得時10換算処理
			if (existAce && ((total + 10) <= 21)) {
				total += 10;
			} else if (total > 21) {
				total = Constants.BURST;
			}
			this.total = total;
		}
	}

	/**
	 * 点数による判定チェックメソッド
	 *
	 *
	 */
	protected void updateStatus() throws SystemException {
		this.calculation();

		//スタンド判定＆ブラックジャック判定チェック
		if ((this.total == 21) && (this.hand.size() == 2)) {
			this.isStand = true;
			this.isBlackJack = true;
		}

		//バースト判定チェック
		if (this.total == Constants.BURST) {
			this.isBurst = true;
		}
	}

	/**
	 * 手持ちカードを増やすメソッド
	 *
	 *
	 */
	public void add(Card card) {
		this.hand.add(card);
	}

	public void open() {
		System.out.println(this.toString());
	}

	/**
	 * メソッド
	 *
	 * @return
	 */
	public String toString() {

		//名前・ハンド一覧作成処理
		StringBuilder sb = new StringBuilder().append(this.name).append(" : ");
		for (Card card : this.hand) {
			sb.append(card).append(" ");
		}

		return sb.toString();
	}

	/**
	 * 名前取得
	 *
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 手持ちカード取得
	 *
	 * @return
	 */
	public List<Card> getHand() {
		return this.hand;
	}

	/**
	 * バースト判定取得
	 *
	 * @return
	 */
	public boolean getIsBurst() {
		return this.isBurst;
	}

	/**
	 * スタンド判定取得
	 *
	 * @return
	 */
	public boolean getIsStand() {
		return this.isStand;
	}

	/**
	 * ブラックジャック判定取得
	 *
	 * @return
	 */
	public boolean getIsBlackJack() {
		return this.isBlackJack;
	}

	/**
	 * 合計点取得
	 *
	 * @return
	 */
	public int getTotal() {
		return this.total;
	}

	/**
	 * @param total 合計点 セットする
	 *
	 *
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @param bool バースト判定 セットする
	 *
	 *
	 */
	public void setIsBurst(boolean bool) {
		this.isBurst = bool;
	}

	/**
	 * @param bool スタンド判定 セットする
	 *
	 *
	 */
	public void setIsStand(boolean bool) {
		this.isStand = bool;
	}
}
