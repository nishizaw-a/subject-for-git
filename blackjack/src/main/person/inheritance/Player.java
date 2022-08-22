package main.person.inheritance;

import java.util.ArrayList;
import java.util.List;

import main.card.Card;
import main.person.Person;
import main.util.constants.Constants;
import main.util.exception.SystemException;
import main.util.keyboard.Keyboard;
import main.util.properties.MessageProperties;

public class Player extends Person {
	private int totalChip; //合計チップ
	private int debt;//借チップ
	private int bettingValue;//ベットした額
	private boolean canSurrender;//サレンダーできるか

	public Player(String name, int totalChip) {
		super(name);
		this.totalChip = totalChip;
		this.debt = 0;
	}

	//清水担当
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
		super.initialize();

		if (totalChip < Constants.MIN_BET_VALUE) {
			totalChip += Constants.DEPT_VALUE;
			debt += Constants.DEPT_VALUE;
		} else if ((totalChip > Constants.REPAYMENT_BORDER) && (debt >= Constants.DEPT_VALUE)) {
			totalChip -= Constants.DEPT_VALUE;
			debt -= Constants.DEPT_VALUE;
		} else {

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
		super.updateStatus();
		StringBuilder sb = new StringBuilder();
		List<Card> sbList = new ArrayList<Card>();
		sbList = super.getHand();

		for (Card card : sbList) {
			sb.append(card);
			sb.append(" ");
		}
		String str1 = super.getName();
		String str2 = sb.toString();
		String str3;
		boolean bln = super.getIsBurst();

		if (bln == true) {
			str3 = MessageProperties.getMessage("blackjack.burst");
		} else {
			boolean bln2 = super.getIsBlackJack();

			if (bln2 == true) {
				str3 = MessageProperties.getMessage("blackjack.blackjack");
			} else {
				int num = super.getTotal();
				str3 = String.valueOf(num);
			}
		}
		String str4 = String.valueOf(bettingValue);
		String str5 = String.valueOf(totalChip);
		String str6 = String.valueOf(debt);

		System.out.println(
				MessageProperties.getMessage("blackjack.msg.player.info", str1, str2, str3, str4, str5, str6));

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

		System.out.println(MessageProperties.getMessage("blackjack.msg.player.info.total.chip",totalChip));
		int num;
		if (totalChip > Constants.MAX_BET_VALUE) {
			num = Constants.MAX_BET_VALUE;
		} else {
			num = totalChip;
		}
		System.out.println(MessageProperties.getMessage("blackjack.msg.player.bet",1, num));

		String str = super.getName();
		System.out.println(MessageProperties.getMessage("blackjack.msg.player.turn", str));

		int num2;
		if (totalChip > Constants.MAX_BET_VALUE) {
			num2 = Constants.MAX_BET_VALUE;
		} else {
			num2 = totalChip;
		}
		int num3 = Keyboard.getInt(Constants.MIN_BET_VALUE, num2);
		bettingValue = num3;
		totalChip -= num3;

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
		boolean bln = super.getIsBurst();
		boolean bln2 = super.getIsStand();

		if (getIsBurst() == false && getIsStand() == false) {

			if (canSurrender == true) {
				System.out.println(MessageProperties.getMessage("blackjack.surrender"));
				totalChip += (bettingValue / 2);
				bettingValue = 0;
				setIsBurst(true);
				setTotal(Constants.BURST);
			} else {
				System.out.println(MessageProperties.getMessage("blackjack.msg.player.cannot.surrender"));
			}

		} else
			throw new SystemException(MessageProperties.getMessage("blackjack.error.surrender"));

		//ここまで
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

	public int getTotalChip() {
		return this.totalChip;
	}

	public int getDept() {
		return this.debt;
	}

	public boolean getCanSurrender() {
		return this.canSurrender;
	}

	public int getBettingValue() {
		return this.bettingValue;
	}

	public void setCanSurrender(boolean bool) {
		this.canSurrender = bool;
	}
}