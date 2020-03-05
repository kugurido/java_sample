<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/login.css">
<title>ログインページ</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

<div class="main_main">
	<h3  class="logText">ログインページ</h3>
	<br/>
	<form action="top" method="post">
	<div class="logText">ユーザーID：<input type="text" name="loginID" maxlength="20" />&nbsp;(半角英数字15文字以内)</div>
	<br/>
	<div  class="logText">パスワード：<input type="passward" name="pass"  maxlength="20" />&nbsp;(半角英数字15文字以内)</div>
	<br/>
	<div  class="logText"><input type="submit" value="ログイン" />
		<input type="hidden" name="act" value="login" /></div>
	</form>
</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>