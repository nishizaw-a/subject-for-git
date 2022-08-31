package main.person.inheritance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.card.Card;
import main.person.Person;
import main.util.constants.Constants;
import main.util.exception.SystemException;
import main.util.keyboard.Keyboard;
import main.util.properties.MessageProperties;

public class Player extends Person {

	/**
	 * 合計チップ
	 */
	private int totalChip;

	/**
	 * 借チップ
	 */
	private int debt;

	/**
	 * ベット額
	 */
	private int bettingValue;

	/**
	 * サレンダー判定
	 */
	private boolean canSurrender;
	/**
	 * スプリット判定
	 */
	private boolean isSplit;

	/**
	 * アクションカウント
	 */
	private int count;

	/**
	 * スプリットプレイヤーリスト
	 */
	private List<Player> splitList;
	
	private int gettingChip;

	public Player(String name, int totalChip) {
		super(name);
		this.totalChip = totalChip;
		this.debt = 0;

	}

	/**
	 * プレイヤー初期化メソッド
	 * 手持ちのカードを無くし、合計、ベットを0、フラグを初期化
	 * 手持ちのチップが最低掛け金より小さい場合はゲームが続行できないのでConstants.DEPT_VALUEの額を借金をする
	 * 手持ちのチップがConstants.REPAYMENT_BORDERより多い場合は、Constants.DEPT_VALUEの額を返済する
	 */
	@Override
	public void initialize() {
		//Playerクラス担当者変数箇所
		//ここから
		bettingValue = 0;
		canSurrender = true;
		this.isSplit = false;
		this.count = 0;
		this.splitList = new LinkedList<Player>();
		this.gettingChip=0;
		super.initialize();
		
		//借チップチェック
		if (totalChip < Constants.MIN_BET_VALUE) {
			totalChip += Constants.DEPT_VALUE;
			debt += Constants.DEPT_VALUE;
		} else if (totalChip > Constants.REPAYMENT_BORDER && debt >= Constants.DEPT_VALUE) {
			totalChip -= Constants.DEPT_VALUE;
			debt -= Constants.DEPT_VALUE;
		}

		//ここまで
	}

	/**
	 * 状況確認メソッド
	 * 手札、合計、ベット額、合計チップ、合計借チップの表示
	 * @throws SystemException
	 */
	@Override
	public void checkStatus() throws SystemException {
		//Playerクラス担当者変数箇所
		//ここから
		
		//点数処理
		updateStatus();
		
		//ハンド一覧作成
		StringBuilder sb = new StringBuilder();
		List<Card> cl = new ArrayList<Card>();
		cl = getHand();
		for (Card c : cl) {
			sb.append(c);
			sb.append(" ");

		}
		String name = getName();
		String ts = sb.toString();
		boolean check = getIsBurst();
		String d = null;
		
		//バースト判定チェック
		if (check == true) {
			d = MessageProperties.getMessage("blackjack.burst");

		} else {
			boolean check2 = getIsBlackJack();
			
			//ブランクジャック判定チェック
			if (check2 == true) {
				d = MessageProperties.getMessage("blackjack.blackjack");
			} else {
				int gt = getTotal();
				d = String.valueOf(gt);

			}
		}
		//プレイヤーベット額・合計チップ・借チップ取得
		String value2 = String.valueOf(bettingValue);
		String value3 = String.valueOf(totalChip);
		String value4 = String.valueOf(debt);
		System.out.println();
		
		//プレイヤー情報表示処理
		System.out.println(
				MessageProperties.getMessage("blackjack.msg.player.info", name, ts, d, value2, value3, value4));
		System.out.println();

		//ここまで
	}

	/**
	 * ベットメソッド
	 * ベットした時点でtotalChipからベット額を差し引く
	 * @throws SystemException
	 */
	public void bet() throws SystemException {
		//Playerクラス担当者変数箇所
		//ここから
		System.out.println(MessageProperties.getMessage("blackjack.msg.player.info.total.chip", totalChip));
		System.out
				.println(MessageProperties.getMessage("blackjack.msg.player.bet", Constants.MIN_BET_VALUE, totalChip));

		String name = getName();
		System.out.println(MessageProperties.getMessage("blackjack.msg.player.turn", name));
		
		//ベット額入力処理
		int x = Keyboard.getInt(Constants.MIN_BET_VALUE, totalChip);
		
		//プレイヤーチップ処理
		bettingValue = x;
		totalChip -= x;
		//ここまで
	}

	/**
	 * サレンダーメソッド
	 * 掛け金の半分をtotalChipに戻す
	 * フラグを変更する
	 * totalをConstants.BURSTにする
	 * @throws SystemException
	 */
	public void surrender() throws SystemException {
		//Playerクラス担当者変数箇所
		//ここから

		//バースト判定＆スタンド判定取得
		boolean check = getIsBurst();
		boolean check2 = getIsStand();

		//バースト＆スタンド判定チェック
		if (check == false && check2 == false) {
			if (canSurrender = true) {
				System.out.println(MessageProperties.getMessage("blackjack.surrender"));

				//サレンダー処理
				totalChip += bettingValue / 2;
				bettingValue = 0;
				setIsBurst(true);
				setTotal(Constants.BURST);
			} else {
				System.out.println(MessageProperties.getMessage("blackjack.msg.player.cannot.surrender"));

			}

		} else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.surrender"));
		}
		//ここまで
	}

	/**
	 * スプリットメソッド
	 *
	 *
	 * @throws SystemException
	 */
	public void split() throws SystemException {

		//バースト＆スタンド判定チェック
		if (!getIsBurst() && !getIsStand()) {
			System.out.println(MessageProperties.getMessage("blackjack.split"));

			//スプリットプレイヤー生成
			Player splayer = new Player("split" + getName(), getTotalChip());
			splayer.initialize();
			splayer.setBettingValue(getBettingValue());
			int total = getTotalChip() - splayer.getBettingValue();
			splayer.setTotallChip(total);
			setTotallChip(total);
			splayer.add(getHand().get(1));
			getHand().remove(1);

			//スプリットプレイヤーリスト追加
			getSplitList().add(splayer);

			//アクションカウント初期化
			setCount(0);

			setIsSplit(true);
		} else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.split"));
		}

	}

	/**
	 * ダブルダウンメソッド
	 * @param card
	 *
	 * @throws SystemException
	 */
	public void doubleDown(Card card) throws SystemException {

		//バースト＆スタンド判定チェック
		if (!getIsBurst() && !getIsStand()) {
			System.out.println(MessageProperties.getMessage("blackjack.doubledown"));

			//プレイヤーチップの処理
			totalChip -= bettingValue;
			bettingValue = 2 * bettingValue;

			//カード取得処理
			this.add(card);

			//スタンド判定設定
			setIsStand(true);

		} else {
			throw new SystemException(MessageProperties.getMessage("blackjack.error.doubledown"));
		}

	}

	/**
	 * チップを受け取るメソッド
	 * totalChipに加える
	 * @Param int
	 */
	public void collect(int value) {
		//Playerクラス担当者変数箇所
		//ここから
		totalChip += value;
		//ここまで
	}

	/**
	 * 合計チップ取得
	 *
	 * @return
	 */
	public int getTotalChip() {
		return this.totalChip;
	}

	/**
	 * @param totalChip 合計チップ セットする
	 *
	 *
	 */
	public void setTotallChip(int total) {
		this.totalChip = total;
	}

	/**
	 * 借チップ取得
	 *
	 * @return
	 */
	public int getDept() {
		return this.debt;
	}

	/**
	 * サレンダー判定取得
	 *
	 * @return
	 */
	public boolean getCanSurrender() {
		return this.canSurrender;
	}

	/**
	 * ベット額取得
	 *
	 * @return
	 */
	public int getBettingValue() {
		return this.bettingValue;
	}

	/**
	 * @param bettingValue ベット額 セットする
	 *
	 *
	 */
	public void setBettingValue(int betting) {
		this.bettingValue = betting;
	}

	/**
	 * @param canSurrender サレンダー判定 セットする
	 *
	 *
	 */
	public void setCanSurrender(boolean bool) {
		this.canSurrender = bool;
	}

	/**
	 * スプリット判定取得
	 *
	 * @return
	 */
	public boolean getIsSplit() {
		return this.isSplit;
	}

	/**
	 * @param isSplit スプリット判定 セットする
	 *
	 *
	 */
	public void setIsSplit(boolean bool) {
		this.isSplit = bool;

	}

	/**
	 * アクションカウント取得
	 *
	 * @return
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * アクションカウント処理
	 *
	 *
	 */
	public void count() {
		this.count = count + 1;
	}

	/**
	 * @param count カウント セットする
	 *
	 *
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * スプリットプレイヤーリスト取得
	 *
	 * @return
	 */
	public List<Player> getSplitList() {
		return this.splitList;
	}
	
	
	public int getGettingChip() {
		return this.gettingChip;
	}

	/**
	 * @param totalChip 合計チップ セットする
	 *
	 *
	 */
	public void setGettinglChip(int gettingChip) {
		this.gettingChip = gettingChip;
	}

}
