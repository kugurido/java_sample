<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <div class="sub_menu"><span>ページメニュー</span></div>
		<div class="subInput">
			<form action="top" method="get">
				<input type="submit" value="このサイトについて"  class="btn"/>
				<input type="hidden" name="act" value="jump"/>
				<input type="hidden" name="link" value="index">
			</form>
		</div>
		<div class="subInput">
			<form action="top" method="post">
				<input type="submit" value="登録された書籍を見る"  class="btn"/>
				<input type="hidden" name="act" value="itemlist"/>
			</form>
		</div>
		<div class="subInput">
			<form action="top" method="get">
				<input type="submit" value="お知らせ"  class="btn"/>
				<input type="hidden" name="act" value="jump"/>
				<input type="hidden" name="link" value="topix">
			</form>
		</div>
		<div class="subInput">
			<form action="top" method="get">
				<input type="submit" value="お問い合わせ"  class="btn"/>
				<input type="hidden" name="act" value="jump"/>
				<input type="hidden" name="link" value="question">
			</form>
		</div>
				<div class="subInput">
			<form action="top" method="get">
				<input type="submit" value="開発情報"  class="btn"/>
				<input type="hidden" name="act" value="jump"/>
				<input type="hidden" name="link" value="develop">
			</form>
		</div>
		<div align="center"><span class="toptext">TOPページへ行くには<br/>ロゴをクリック</span></div>
