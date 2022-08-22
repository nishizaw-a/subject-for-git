package main.util.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final List<String> SUIT_LIST = new ArrayList<>(Arrays.asList("♠", "♣", "♦", "♥"));
	public static final int BURST = -1;//バースト時の合計
	public static final int HIT = 1;//ヒット
	public static final int STAND = 2;//スタンド
	public static final int SURRENDER = 3;//サレンダー
	public static final int CONTINUE = 1;//継続
	public static final int EXIT = 2;//終了
	public static final int DEALER_HIT_BORDER = 17;

	//設定
	public static final int MAX_PLAYER = 5;//最大プレイヤー数
	public static final int MIN_PLAYER = 1;//最小プレイヤー数
	public static final int MAX_BET_VALUE = 1000;//最大ベット数
	public static final int MIN_BET_VALUE = 1;//最小ベット数
	public static final int INITIAL_CHIP_TOTAL = 1000;//初期所持チップ数
	public static final int DEBT_VALUE = 1000;//最小借入チップ数
	public static final int REPAYMENT_BORDER = 1500;//返済ボーダー
	public static final double RATE_BLACKJACK = 2.5; //ブラックジャック時のレート
	public static final double RATE_NORMAL = 2.0; //勝利時のレート

	//未実装
	public static final int SPLIT = 4;
	public static final int DOUBLE_DOWN = 5;
}
