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
	private List<Player> winnerList;
	private List<Player> looserList;
	private List<Player> drawerList;
	private List<Player> playerList;
	private Dealer dealer;
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
				player.add(this.dealer.deal());
			}
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
			this.dealer.checkStatus();
			player.checkStatus();

			while (!player.getIsBurst() && !player.getIsStand()) {
				System.out.println(MessageProperties.getMessage("blackjack.msg.player.action", Constants.HIT,
						Constants.STAND, Constants.SURRENDER));
				System.out.print(MessageProperties.getMessage("blackjack.msg.player.turn", player.getName()));

				switch (Keyboard.getInt(Constants.HIT, Constants.SURRENDER)) {
				case Constants.HIT:
					player.hit(this.dealer.deal());
					break;
				case Constants.STAND:
					player.stand();
					break;
				case Constants.SURRENDER:
					player.surrender();
					break;
				}

				if (player.getCanSurrender()) {
					player.setCanSurrender(false);
				}

				player.checkStatus();
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
		for (Player player : this.playerList) {
			if (player.getIsBlackJack() && !this.dealer.getIsBlackJack()) {
				//勝ち
				player.collect((int) (player.getBettingValue() * Constants.RATE_BLACKJACK));
				this.winnerList.add(player);
			} else if (player.getTotal() > this.dealer.getTotal()) {
				//勝ち
				player.collect((int) (player.getBettingValue() * Constants.RATE_NORMAL));
				this.winnerList.add(player);
			} else if (!player.getIsBlackJack() && this.dealer.getIsBlackJack()) {
				//負け
				this.looserList.add(player);
			} else if (player.getTotal() < this.dealer.getTotal()) {
				//負け
				this.looserList.add(player);
			} else if (player.getIsBurst() && this.dealer.getIsBurst()) {
				//負け
				this.looserList.add(player);
			} else if (player.getTotal() == this.dealer.getTotal()) {
				//引き分け
				player.collect(player.getBettingValue());
				this.drawerList.add(player);
			} else {
				throw new SystemException(MessageProperties.getMessage("errpr.stop"));
			}
		}
	}

	/**
	 * 1ゲームの結果を表示するメソッド
	 * ディーラーからプレイヤー全員のカードをオープンする
	 * @throws SystemException
	 */
	public void printResult() throws SystemException {
		System.out.println(MessageProperties.getMessage("blackjack.result.game", this.gameNumber));
		this.dealer.open();

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
