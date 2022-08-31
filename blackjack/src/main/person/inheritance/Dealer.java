package main.person.inheritance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.card.Card;
import main.person.Person;
import main.util.constants.Constants;
import main.util.exception.SystemException;
import main.util.properties.MessageProperties;

public class Dealer extends Person {
	/**
	 * 山札
	 */
	private List<Card> deck;

	public Dealer() throws SystemException {
		super(MessageProperties.getMessage("blackjack.dealer.name"));
	}

	/**
	 * ディーラー初期化メソッド
	 * 手持ちのカードを無くし、合計を０，フラグを初期化
	 * 前回のトランプを破棄し新しく山札を作成する
	 */
	@Override
	public void initialize() {
		//Dealerクラス担当者変数箇所
		//ここから
		super.initialize();
		createDeck();
		//ここまで
	}

	/**
	 * 状況確認メソッド
	 * 手札、合計、ベット額、合計チップ、合計借チップの表示
	 * @throws SystemException
	 */
	@Override
	public void checkStatus() throws SystemException {
		//Dealerクラス担当者変数箇所
		//ここから
		
		//点数処理
		updateStatus();
		
		
		List<Card> list = getHand();
		boolean check = getIsStand();

		if (list.size() == 2 && getIsStand() == false) {
			String name = getName();
			String str = list.get(0).toString();
			System.out.println(MessageProperties.getMessage("blackjack.msg.dealer.info.init", name, str));
		}
	}

	//ここまで
	/**
	 * 山札作成メソッド
	 * 新品のトランプを模倣し一番上から同柄の1~13の4種類、52枚を用意
	 */
	private void createDeck() {
		//Dealerクラス担当者変数箇所
		//ここから
		this.deck = new ArrayList<Card>();

		for (int i = 0; i < Constants.SUIT_LIST.size(); i++) {
			for (int j = 1; j < 14; j++) {
				
				//カード・デッキ作成処理
				Card card = new Card(j, Constants.SUIT_LIST.get(i));
				this.deck.add(card);
			}
		}
	}
	//ここまで

	/**
	 * 山札をシャフルするメソッド
	 * 乱数を用いて1回以上9回以下でCollectionsのshuffleメソッドで山札をシャフル
	 */
	public void shuffle() {
		//Dealerクラス担当者変数箇所
		//ここから
		Collections.shuffle(this.deck);
		//ここまで
	}

	/**
	 * カードを配るメソッド
	 * シャフルした山札の一番上からカードを配る
	 * リストからそのカードを消さないと重複する
	 * @return Card
	 */
	public Card deal() {
		//Dealerクラス担当者変数箇所
		//ここから

		//デッキの一番上からカード取得
		Card card = this.deck.get(0);

		//デッキからカード削除
		deck.remove(card);
		return card;
		//ここまで
	}

	public Card dealTest(int i) {
		//Dealerクラス担当者変数箇所
		//ここから
		Card card = this.deck.get(i * 11);
		deck.remove(card);
		return card;
		//ここまで
	}

}