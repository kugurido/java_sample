<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>マイページ</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

<div class="main_main">
	<h3>ようこそ<%=user.getU_name() %>さん！</h3>

</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>