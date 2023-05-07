# gui_registerform
## 概要
java Swingを使用した新規登録画面。</br>
<img width="511" alt="image" src="https://user-images.githubusercontent.com/114673266/236656855-42f74772-6e27-4854-8454-f8bc54c621f1.png">

## 機能
<ul>
<li>名前、苗字、email、電話番号、ユーザー名、パスワードが入力可能 </li>
<li>パスワードは「MessageDigest」を使用し、暗号化。</li>
<li>すでにパスワードがMySQLに登録されているか確認し、登録済みであればメッセージダイアログから別のパスワードを入力するように促す</li>
<li>登録失敗した場合、メッセージダイアログから登録失敗を伝える</li>
<li>電話番号は11桁以外の場合、メッセージダイアログから11桁で入力するように促す</li>
</ul>

## 言語
java

## ミドルウェア
Tomcat, MySQL

## ツール
Eclipse, Maven
