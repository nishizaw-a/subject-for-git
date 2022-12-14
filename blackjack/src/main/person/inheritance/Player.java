package main.person.inheritance;

import main.person.Person;
import main.util.exception.SystemException;

public class Player extends Person{
	private int totalChip; //合計チップ
	private int debt;//借チップ
	private int bettingValue;//ベットした額
	private boolean canSurrender;//サレンダーできるか

	public Player(String name, int totalChip) {
		super(name);
		this.totalChip = totalChip;
		this.debt = 0;
	}


	/**
	 * プレイヤー初期化メソッド
	 * 手持ちのカードを無くし、合計、ベットを0、フラグを初期化
	 * 手持ちのチップが最低掛け金より小さい場合はゲームが続行できないのでConstants.DEBT_VALUEの額を借金をする
	 * 手持ちのチップがConstants.REPAYMENT_BORDERより多い場合は、Constants.DEBT_VALUEの額を返済する
	 */
	@Override
	public void initialize() {
		//Playerクラス担当者変数箇所
		//ここから

		//ここまで
	}


	/**
	 * 状況確認メソッド
	 * 手札、合計、ベット額、合計チップ、合計借チップの表示
	 * @throws SystemException
	 */
	@Override
	public void checkStatus() throws SystemException{
		//Playerクラス担当者変数箇所
		//ここから

		//ここまで
	}

	/**
	 * ベットメソッド
	 * ベットした時点でtotalChipからベット額を差し引く
	 * @throws SystemException
	 */
	public void bet() throws SystemException{
		//Playerクラス担当者変数箇所
		//ここから

		//ここまで
	}

	/**
	 * サレンダーメソッド
	 * 掛け金の半分をtotalChipに戻す
	 * フラグを変更する
	 * totalをConstants.BURSTにする
	 * @throws SystemException
	 */
	public void surrender() throws SystemException{
		//Playerクラス担当者変数箇所
		//ここから

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

		//ここまで
	}


	public int getTotalChip() {
		return this.totalChip;
	}

	public int getDebt() {
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
