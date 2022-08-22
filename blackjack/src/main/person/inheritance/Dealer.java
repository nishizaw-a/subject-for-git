package main.person.inheritance;

import java.util.List;

import main.card.Card;
import main.person.Person;
import main.util.exception.SystemException;
import main.util.properties.MessageProperties;

public class Dealer extends Person{
	private List<Card> deck;//山札

	public Dealer() throws SystemException{
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
		this.createDeck();
		//ここまで
	}

	/**
	 * 状況確認メソッド
	 * 手札、合計、ベット額、合計チップ、合計借チップの表示
	 * @throws SystemException
	 */
	@Override
	public void checkStatus() throws SystemException{
		//Dealerクラス担当者変数箇所
		//ここから
		this.updateStatus();

		if(!(this.getHand().size() > 2) && !(this.getIsStand())) {
			System.out.println(MessageProperties.getMessage("blackjack.msg.dealer.info.init", this.getName(), this.getHand().get(0).toString()));
		}
		//ここまで
	}

	/**
	 * 山札作成メソッド
	 * 新品のトランプを模倣し一番上から同柄の1~13の4種類、52枚を用意
	 */
	private void createDeck() {
		//Dealerクラス担当者変数箇所
		//ここから

		//ここまで
	}

	/**
	 * 山札をシャフルするメソッド
	 * 乱数を用いて1回以上9回以下でCollectionsのshuffleメソッドで山札をシャフル
	 */
	public void shuffle() {
		//Dealerクラス担当者変数箇所
		//ここから

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

		//ここまで
	}
}