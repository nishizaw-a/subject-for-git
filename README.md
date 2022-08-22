# subject-for-git
## アクセストークンの作成
1. GitHubにログイン後、右上のユーザーアイコンをクリック

2. Settingsを選択

3. 左側の項目欄の一番下にあるDevelopper settingsを選択

4. 左側の項目欄の一番下にあるPersonal access tokensを選択

5. 真ん中の右上にGenerate new tokenをクリック

6. Noteにアクセストークンを管理するための名前を任意で入力

7. Expiration(有効期限)は任意を選択

8. Select scopes(権限)はrepoを選択

9. 一番下にあるGenerate tokenをクリック

<span style="color: red;">トークンはEclipseなど外部からGitHubを利用するためのパスワードなので秘密にしておく</span>

## Eclipse上でクローンを作成する
1. プロジェクトエクスプローラーで右クリック

2. インポート->インポート->Git->Gitからプロジェクト->クローンURIを選択

	URI:https://github.com/nishizaw-a/subject-for-git.git
	ホスト:URI入力時に自動入力される
	リポジトリーパス:URI入力時に自動入力される
	ユーザー:自分のGitHubのユーザーネーム
	パスワード:作成したアクセストークン
	セキュア・ストアに保管をチェック(リポジトリ操作で毎回入力が求められないようにするため)

3. 次へを選択

4. ブランチ選択では選択をすべて解除を選択後に指定されたブランチにチェックを入れる

5. 次へを選択

	ディレクトリー(クローンの作成場所を指定するため、変更したいなら参照を選択して場所を指定する)
	リモート名(リモートリポジトリ名を指定するため、変更したいなら指定する)

6. 次へを選択

7. 次へを選択

8. 既存のプロジェクトのインポートを選択

9. 次へを選択

10. 完了を選択

## ブランチを作成する
1. Eclipse右上のGitパースペクティブを開く(無ければ選択バーのウィンドウ->パースペクティブ->パースペクティブを開く->その他->Gitを選択)

2. Gitリポジトリビューのsubject-for-git->ブランチ->ローカルで右クリック

3. 切り替え->新規ブランチを選択

	ソース:指定されたブランチになっているか確認
	ブランチ名:任意(Player または Deler + 苗字 など)
	新規ブランチをチェックアウトする:選択されていることを確認

4. 完了を選択

<span style="color: red;">以後、プッシュ先は作成したブランチを指定</span>

## コーディング
プロジェクトのsrc->main->person->inheritanceの中にあるDealerとPlayerクラスを各々が編集する

	Delaerクラス:比較的簡単
	Playerクラス:Dealerよりは難しいがそこまで難しくない

BlackJackクラスにはあえてコンフリクトが発生するようにあるメソッドを両者が同じところを修正する箇所がある


## リモートへプッシュする方法
プロジェクト・エクスプローラービューから変更したファイルを右クリック->チーム->コミットを選択

Gitステージングビューが表示されるのでCommit Message欄にメッセージを入力してコミットおよびプッシュを選択

詳細画面が表示される

	ブランチ:作成したブランチが選択されていることを確認する

プッシュする

## リモートからプルする方法
Gitパースペクティブを開く

レポジトリを右クリック→一番目のプルを選択

## マージする方法
Gitパースペクティブを開く

Branches→Local→マージしたいブランチを右クリック

マージを選択
