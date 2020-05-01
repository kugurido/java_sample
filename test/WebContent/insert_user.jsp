<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	User user = null;
	if(session.getAttribute("user") != null){
		user = (User)session.getAttribute("user");
	}
%>

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/new_user.css">
<title>登録完了</title>
</head>

<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">
		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<h2>ユーザー登録完了</h2>
		<div class="formDIV">
			<div><%=user.getU_name() %>さん登録ありがとうございます！</div>
			<div>引き続きWataRoomをお楽しみください</div>
			<div><a href="top?act=login">マイページ</a></div>
		</div>
	</div>
<%@include file="../include/footer.jsp" %>

</body>
</html>