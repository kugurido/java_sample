<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common.css">
<title>このサイトについて</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div><h1>WataRoomとは</h1></div>
		<div>このサイトは利用者の方が本を通じて新しい出会いを見つけるきっかけを作ります。</div>
		<div><h3>用途1：記録</h3>
				本棚に本を置くことで自身の読書を記録していくことは出来ますが、物理的スペースには限りあるもの。<br/>
				やむを得ず処分をすることになった本も、記憶としてではなく記録として残すことができます。<br/><br/>

				また、読んだ日付も入れることが出来るので読書遍歴を作っていくことも可能です。　⇒記録する
		</div>
		<div><h3>用途2：共有</h3>
			小説やマンガを読んで感動した経験はございますか？<br/>
			その感動を誰かと分かち合ったことはありますか？<br/><br/>

			感情を突き動かされる本との出会いは素晴らしい体験です。そしてその感情を共有できる人との出会いもまた素晴らしいものです。<br/>
			WataRoomではそういった様々な出会いを作り出す場を提供します。あなたが今読んだその本をWataRoomで共有しましょう！　⇒共有している本を見る
		</div>

	</div>

</div>

<div class="bottom">フッター</div>

</body>
</html>