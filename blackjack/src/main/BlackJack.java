package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.person.inheritance.Dealer;
import main.person.inheritance.Player;
import main.util.constants.Constants;
import main.util.exception.SystemException;
import main.util.keyboard.Keyboard;
import main.util.properties.MessageProperties;

public class BlackJack {
	/**
	 * 勝者リスト
	 */
	private List<Player> winnerList;

	/**
	 * 敗者リスト
	 */
	private List<Player> looserList;

	/**
	 * 引き分けリスト
	 */
	private List<Player> drawerList;

	/**
	 * 参加者リスト
	 */
	private List<Player> playerList;

	/**
	 * スプリット時管理リスト(action用)
	 */
	private List<Player> sList;

	private List<Player> addList;

	/**
	 * ディーラー
	 */
	private Dealer dealer;

	/**
	 * ゲーム数
	 */
	private int gameNumber;

	public static void main(String[] args) {
		try {
			BlackJack bj = new BlackJack();
			while (true) {
				System.out.println(MessageProperties.getMessage("blackjack.start"));
				//準備
				bj.initialize();
				//ベット
				bj.bettingPhase();
				//配布
				bj.dealingPhase();
				//選択
				bj.actionPhase();
				//判定
				bj.judgePhase();
				//結果表示
				bj.printResult();
				//継続選択
				if (!bj.hasContinued()) {
					System.out.println(MessageProperties.getMessage("blackjack.end"));
					for (Player player : bj.playerList) {
						int result = player.getTotalChip() - player.getDept() - Constants.INITIAL_CHIP_TOTAL;
						System.out.println(MessageProperties.getMessage("blackjack.result.chip", player.getName(),
								(result > 0 ? "+" : result < 0 ? "-" : "+-") + (result)));
					}
					break;
				}
			}
		} catch (SystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ブラックジャックを表すオブジェクトのコンストラクタ
	 * ゲーム開始時はプレイヤーがいる前提で始めるためcreatePlayerメソッドでディーラー、プレイヤーを作成
	 * @throws SystemException
	 */
	public BlackJack() throws SystemException {
		this.gameNumber = 1;
		this.createPlayer();
	}

	/**
	 * ディーラー、プレイヤーを作成するメソッド
	 * ディーラー:1人
	 * プレイヤー:入力した人数
	 * @throws SystemException
	 */
	public void createPlayer() throws SystemException {
		//Dealerクラス担当者変数箇所
		//ここから
		Dealer dealerIns = new Dealer();
		this.dealer = dealerIns;
		//ここまで

		this.playerList = new ArrayList<Player>();

		System.out.println(MessageProperties.getMessage("blackjack.input", Constants.MIN_PLAYER, Constants.MAX_PLAYER));

		int number = Keyboard.getInt(Constants.MIN_PLAYER, Constants.MAX_PLAYER);

		for (int i = 0; i < number; i++) {
			//Playerクラス担当者変数箇所
			//ここから
			Player player = new Player("player" + (i + 1), Constants.INITIAL_CHIP_TOTAL);
			playerList.add(player);
			//ここまで
		}
	}

	/**
	 * ゲームを初期化するメソッド
	 * 勝者、敗者、引き分けのプレイヤーを保持するリストを初期化
	 * ディーラーが管理する情報を初期化
	 * プレイヤーが管理する情報を初期化
	 * @throws SystemException
	 */
	public void initialize() throws SystemException {
		this.winnerList = new LinkedList<Player>();
		this.looserList = new LinkedList<Player>();
		this.drawerList = new LinkedList<Player>();
		this.sList = new LinkedList<Player>();
		this.addList = new LinkedList<Player>();
		this.dealer.initialize();
		for (Player player : this.playerList) {
			player.initialize();
		}
	}

	/**
	 * 各プレイヤーのベットを行うメソッド
	 * @throws SystemException
	 */
	public void bettingPhase() throws SystemException {
		for (Player player : this.playerList) {
			System.out.println(MessageProperties.getMessage("blackjack.msg.player.name", player.getName()));

			//プレイヤーベット処理
			player.bet();
		}
	}

	/**
	 * ディーラーがカードを配るメソッド
	 * 毎回新品のトランプを用意しディーラーがシャッフル
	 * 各プレイヤー、ディーラーの順に山の上から1枚カードを配るを2回繰り返す
	 * 最後に各プレイヤー、ディーラーの順に手札を計算する
	 */
	public void dealingPhase() throws SystemException {
		this.dealer.shuffle();

		for (int i = 0; i < 2; i++) {
			for (Player player : this.playerList) {
				//test中
				//player.add(this.dealer.dealTest(i));

				//プレイヤーカード取得処理
				player.add(this.dealer.deal());
			}

			//ディーラーカード取得処理
			this.dealer.add(this.dealer.deal());
		}
	}

	/**
	 * プレイヤーが行動を選択するメソッド
	 * 行動初回時はサレンダーが可能、2回目の行動以降は選択できるがサレンダーはできない
	 * ヒットするとディーラーからカードをもらう
	 * スタンドすると行動選択を終了する
	 * バーストすると行動選択を終了する
	 * 一回の行動終了後にプレイヤーの状況を確認する
	 * プレイヤー全員の行動が終了したら、ディーラーが手持ちのカードの合計が17以上になるまでヒットする
	 * @throws SystemException
	 */
	public void actionPhase() throws SystemException {
		/*プレイヤーのアクション*/
		for (Player player : this.playerList) {

			//プレイヤー・ディーラー情報取得処理
			this.dealer.checkStatus();
			player.checkStatus();

			while (true) {
				if (player.getIsSplit()) {

					//スプリット時の行動処理リスト作成
					this.sList.clear();
					this.sList.add(player);
					this.sList.add(player.getSplitList().get(0));

					int count = 0;

					for (Player spPlayer : this.sList) {

						//バースト判定＆スタンド判定チェック
						if (spPlayer.getIsBurst() == true || spPlayer.getIsStand() == true) {
							count++;
							continue;
						}
						player.count();

						//ダブルダウン可能時処理分岐
						if (spPlayer.getCount() == 1) {
							System.out.println(
									MessageProperties.getMessage("blackjack.msg.player.action.double", Constants.HIT,
											Constants.STAND, Constants.SURRENDER, Constants.DOUBLE_DOWN));
						} else {

							System.out
									.println(MessageProperties.getMessage("blackjack.msg.player.action", Constants.HIT,
											Constants.STAND, Constants.SURRENDER));
						}
						System.out.print(MessageProperties.getMessage("blackjack.msg.player.turn", spPlayer.getName()));

						switch (Keyboard.getInt(Constants.HIT, Constants.DOUBLE_DOWN)) {
						case Constants.HIT:
							spPlayer.hit(this.dealer.deal());
							break;
						case Constants.STAND:
							spPlayer.stand();
							break;
						case Constants.SURRENDER:
							spPlayer.surrender();
							break;
						case Constants.DOUBLE_DOWN:
							spPlayer.doubleDown(this.dealer.deal());
							break;
						}

						spPlayer.checkStatus();

					}

					//スプリットプレイヤー行動終了チェック
					if (count == sList.size()) {
						break;
					}

				} else {
					player.count();

					//スプリット可能時処理分岐
					if (player.getHand().get(0).getNumber() == player.getHand().get(1).getNumber()
							|| (player.getHand().get(0).getNumber() >= 10
									&& player.getHand().get(1).getNumber() >= 10)) {
						System.out.println(
								MessageProperties.getMessage("blackjack.msg.player.action.split", Constants.HIT,
										Constants.STAND, Constants.SURRENDER, Constants.SPLIT, Constants.DOUBLE_DOWN));

						//ダブルダウン可能時処理分岐
					} else if (player.getCount() == 1) {
						System.out.println(
								MessageProperties.getMessage("blackjack.msg.player.action.double", Constants.HIT,
										Constants.STAND, Constants.SURRENDER, Constants.DOUBLE_DOWN));
					} else {

						System.out.println(MessageProperties.getMessage("blackjack.msg.player.action", Constants.HIT,
								Constants.STAND, Constants.SURRENDER));
					}
					System.out.print(MessageProperties.getMessage("blackjack.msg.player.turn", player.getName()));

					switch (Keyboard.getInt(Constants.HIT, Constants.DOUBLE_DOWN)) {
					case Constants.HIT:
						player.hit(this.dealer.deal());
						break;
					case Constants.STAND:
						player.stand();
						break;
					case Constants.SURRENDER:
						player.surrender();
						break;
					case Constants.SPLIT:
						player.split();
						break;
					case Constants.DOUBLE_DOWN:
						player.doubleDown(this.dealer.deal());
						break;
					}

					if (player.getCanSurrender()) {
						player.setCanSurrender(false);
					}

					//スプリット時プレイヤー情報取得処理
					if (player.getIsSplit()) {
						player.checkStatus();
						player.getSplitList().get(0).checkStatus();

					} else {

						player.checkStatus();
					}

					if (player.getIsBurst() == true || player.getIsStand() == true) {
						break;
					}
				}
			}
		}
		/*ディーラーのアクション*/
		System.out.println(MessageProperties.getMessage("blackjack.msg.dealer.name", this.dealer.getName()));
		while (!this.dealer.getIsBurst() && !this.dealer.getIsStand()) {
			if (this.dealer.getTotal() < Constants.DEALER_HIT_BORDER) {
				this.dealer.hit(this.dealer.deal());
				this.dealer.checkStatus();
			} else {
				this.dealer.stand();
			}
		}
	}

	/**
	 * ディーラーと勝負して勝敗を判定するメソッド
	 * (ディーラーより合計が大きい場合)
	 * 勝ちの場合は掛けたチップの2倍を獲得する、bjの場合は2.5倍
	 * (ディーラーより合計が小さい場合か、自分がバーストしたか、両者がバーストした場合)
	 * 負けの場合は掛けたチップが没収される->collectメソッドを発動させない
	 * (ディーラーと合計が同じ場合)
	 * 引き分けの場合はかけたチップを獲得する
	 * それぞれの場合で相当するリストにプレイヤーを加える
	 * @throws SystemException
	 */
	public void judgePhase() throws SystemException {

		//ジャッジ用リスト作成処理
		for (Player player : this.playerList) {
			if (!player.getSplitList().isEmpty()) {
				addList.add(player.getSplitList().get(0));
			}
			addList.add(player);

		}

		for (Player player : this.addList) {

			//ブラックジャック勝利
			if (player.getIsBlackJack() && !this.dealer.getIsBlackJack()) {

				//プレイヤー獲得チップ処理
				player.setGettinglChip((int) (player.getBettingValue() * Constants.RATE_BLACKJACK));

				this.winnerList.add(player);

				//ノーマル勝利
			} else if (player.getTotal() > this.dealer.getTotal()) {

				//プレイヤー獲得チップ処理
				player.setGettinglChip((int) (player.getBettingValue() * Constants.RATE_NORMAL));

				this.winnerList.add(player);

				//敗北
			} else if (!player.getIsBlackJack() && this.dealer.getIsBlackJack()
					|| player.getTotal() < this.dealer.getTotal()
					|| player.getIsBurst() && this.dealer.getIsBurst()) {

				this.looserList.add(player);

				//引き分け
			} else if (player.getTotal() == this.dealer.getTotal()) {

				//プレイヤー獲得チップ処理
				player.setGettinglChip(player.getBettingValue());

				this.drawerList.add(player);

			} else {
				throw new SystemException(MessageProperties.getMessage("errpr.stop"));
			}

			//スプリットを行ったかの判定チェック
			if (!player.getSplitList().isEmpty()) {

				//合計チップ処理
				player.collect(player.getGettingChip() + player.getSplitList().get(0).getGettingChip());
			} else {

				//合計チップ処理
				player.collect(player.getGettingChip());

			}
		}

		/*	for (Player player : this.playerList) {

				//ブラックジャック勝利
				if (player.getIsBlackJack() && !this.dealer.getIsBlackJack()) {

					//プレイヤーチップ加算処理
					player.collect((int) (player.getBettingValue() * Constants.RATE_BLACKJACK));

					this.winnerList.add(player);

					//ノーマル勝利
				} else if (player.getTotal() > this.dealer.getTotal()) {

					//プレイヤーチップ加算処理
					player.collect((int) (player.getBettingValue() * Constants.RATE_NORMAL));

					this.winnerList.add(player);

					//敗北
				} else if (!player.getIsBlackJack() && this.dealer.getIsBlackJack()
						|| player.getTotal() < this.dealer.getTotal()
						|| player.getIsBurst() && this.dealer.getIsBurst()) {

					this.looserList.add(player);

					//引き分け
				} else if (player.getTotal() == this.dealer.getTotal()) {

					//プレイヤーチップ加算処理
					player.collect(player.getBettingValue());

					this.drawerList.add(player);

				} else {
					throw new SystemException(MessageProperties.getMessage("errpr.stop"));
				}

				//スプリットを行ったかの判定チェック
				if (!player.getSplitList().isEmpty()) {
					Player sp = player.getSplitList().get(0);

					//ブラックジャック勝利
					if (sp.getIsBlackJack() && !this.dealer.getIsBlackJack()) {

						//プレイヤーチップ加算処理
						player.collect((int) (sp.getBettingValue() * Constants.RATE_BLACKJACK));



						this.winnerList.add(sp);

						//ノーマル勝利
					} else if (sp.getTotal() > this.dealer.getTotal()) {

						//プレイヤーチップ加算処理
						player.collect((int) (sp.getBettingValue() * Constants.RATE_NORMAL));

						this.winnerList.add(sp);

						//敗北
					} else if (!sp.getIsBlackJack() && this.dealer.getIsBlackJack()
							|| sp.getTotal() < this.dealer.getTotal()
							|| sp.getIsBurst() && this.dealer.getIsBurst()) {

						this.looserList.add(sp);

						//引き分け
					} else if (sp.getTotal() == this.dealer.getTotal()) {

						//プレイヤーチップ加算処理
						player.collect(sp.getBettingValue());

						this.drawerList.add(sp);

					} else {
						throw new SystemException(MessageProperties.getMessage("errpr.stop"));
					}
				}
			} */
	}

	/**
	 * 1ゲームの結果を表示するメソッド
	 * ディーラーからプレイヤー全員のカードをオープンする
	 * @throws SystemException
	 */
	public void printResult() throws SystemException {
		System.out.println(MessageProperties.getMessage("blackjack.result.game", this.gameNumber));
		this.dealer.open();

		//各リスト表示処理
		System.out.println(MessageProperties.getMessage("blackjack.winner"));
		for (Player player : this.winnerList) {
			player.open();
		}

		System.out.println(MessageProperties.getMessage("blackjack.looser"));
		for (Player player : this.looserList) {
			player.open();
		}

		System.out.println(MessageProperties.getMessage("blackjack.drawer"));
		for (Player player : this.drawerList) {
			player.open();
		}
	}

	/**
	 * ゲームを続けるかを確認するメソッド
	 * 続ける場合はtrue, 続けない場合はfalseを返す
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean hasContinued() throws SystemException {
		this.gameNumber++;
		System.out.println(MessageProperties.getMessage("blackjack.continue", Constants.CONTINUE, Constants.EXIT));
		return (Keyboard.getInt(Constants.CONTINUE, Constants.EXIT) == 1);
	}
}
