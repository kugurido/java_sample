<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="bean.User" %>
 <%@page  import="bean.Item" %>
 <%@page  import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%
	ArrayList<Item> list = new ArrayList<>();
	if(session.getAttribute("list") != null){
		list = (ArrayList<Item>)session.getAttribute("list");
	}

	String error = null;
	if(request.getAttribute("error") != null){
		error = "まだ誰も何も読んでないみたいね";
	}

%>


<link rel="stylesheet" href="css/common.css">

<title>エラーページ</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div><h3>エラー</h3></div>

	</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>