<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="bean.User" %>
 <%@page  import="bean.Item" %>
 <%@page  import="bean.Spil" %>
 <%@page  import="bean.Revue" %>
 <%@page  import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%

	String error = null;
	if(request.getAttribute("error") != null){
		error = "まだ誰も何も読んでないみたいね";
	}

	User user = new User();
	if(request.getAttribute("rUser")!=null){
		user = (User)request.getAttribute("rUser");
	}


%>


<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/detailUser.css">
<title>ユーザーページ</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div class="titleDiv"><h3>ユーザー詳細</h3></div>
		<div class="imgDiv">
			<div><img src="<%=user.getU_img() %>" alt="エラー" class="userImg"></div>
			<div><%=user.getU_name() %></div>
		</div>

		<hr/>
		<div class="panelstart">
			<ul class="tab-group">
				<li class="tab tab-A is-active">こぼれ言</li>
				<li class="tab tab-B" >読了一覧</li>
				<li class="tab tab-C" >積読の山</li>
			</ul>

		    <div class="panel-group">
				<div class="panel tab-A is-show">
				<%@include file="../include/another_spil.jsp" %>
				</div>
				<div class="panel tab-B">
					<%@include file="../include/another_read.jsp" %>
				</div>
				<div class="panel tab-C">
				<%@include file="../include/another_pile.jsp" %>

				</div>
			</div>

		</div>

	</div>

</div>

	<%@include file="../include/footer.jsp" %>
<script src="<%=request.getContextPath() %>/js/upload.js"></script>
</body>
</html>