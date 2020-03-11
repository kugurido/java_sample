<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	ArrayList<User>user = (ArrayList<User>)request.getAttribute("test");
	User l_user = null;
	if(user != null){l_user = user.get(0);}
%>

<link rel="stylesheet" href="css/common.css">
<title>トップページ</title>
</head>

<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">
		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<h2>～書籍から得られる感情を残し、伝える～</h2>
	<br/>
		<div>ゲストでサイトを閲覧したい方はコチラ</div>
	<br/>
		<div>他の人が読んだ本を見る⇒</div>
	<br/>
		<div><form action="top" method="post">
			<input type="submit" value="チェック" />
			<input type="hidden" name="act" value="develop"  /></form></div>
	</div>

	<div><% if(l_user != null) {%><%=l_user.getU_name() %><%} %></div>
</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>