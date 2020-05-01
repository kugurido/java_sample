<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page  import="bean.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%
	String name = "名無し";
	if(session.getAttribute("user") != null){
		User user = (User)session.getAttribute("user");
		name = user.getU_name();
	}

	String chMessage = "パスワード";
	if(request.getAttribute("chMail") != null){
		chMessage = "メールアドレス";
	}
%>


<link rel="stylesheet" href="css/common.css">

<title>再設定完了</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">
		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div><h3>再設定完了</h3></div>
		<div><%=name %>さんの<%=chMessage %>は無事変更されました。</div>
		<div>引き続きWataRoomをご利用ください。</div>
		<br/>
		<div><a href="top?act=mypage">マイページ</a></div>
	</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>