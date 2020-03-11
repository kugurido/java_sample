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
	Item item = null;
	if(request.getAttribute("detail") != null){
		item = (Item)request.getAttribute("detail");
	}

	String error = null;
	if(request.getAttribute("error") != null){
		error = "まだ誰も何も読んでないみたいね";
	}

%>


<link rel="stylesheet" href="css/common.css">

<title>詳細ページ</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div><h3>書籍詳細</h3></div>

		<div>

		<%=item.getI_name() %>

		</div>

	</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>