<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String error = null;
	if(request.getAttribute("error") != null){
		error = "IDまたはパスワードが正しくありません";
	}
%>

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/login.css">
<script type="text/javascript">
<!--
	function check(){
		var name = document.loginform.loginID.value;
		var pass = document.loginform.pass.value;
		if(name.length==0 || pass.length==0){
			alert('未入力の項目があります');
			return false;
		}

	}

//-->
</script>
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
	<p class="error"><% if(error != null){ %><%=error %><%} %></p>
	<form action="top" method="post" name="loginform" onsubmit="return check()">
	<div class="logText">ユーザーID：<input type="text" name="loginID" maxlength="20" />&nbsp;(半角英数字15文字以内)</div>
	<br/>
	<div  class="logText">パスワード：<input type="password" name="pass"  maxlength="20" />&nbsp;(半角英数字15文字以内)</div>
	<br/>
	<div  class="logText"><input type="submit" value="ログイン" />
		<input type="hidden" name="act" value="login" /></div>
	</form>
</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>