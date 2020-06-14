<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String times = "";
	if(request.getAttribute("time") != null){
		times = (String)request.getAttribute("time");
	}
%>

<link rel="stylesheet" href="css/common.css">
<title>開発情報</title>
</head>

<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">
		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<h2>開発情報</h2>
	<br/>
		<div>
			<div>開発環境</div>
			<div>Eclipse IDE</div>
			<div>言語:java、javascript</div>
			<div>サーバー:Apache、Tomcat9.0.35、AWS(EC2)</div>
			<div>DB:MySQL</div>
		</div>
	<br/>
		<div></div>
	<br/>

	</div>

	<div><% if(!times.equals("")) {%><%=times %><%} %></div>
</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>