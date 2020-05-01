<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
 <%@page  import="bean.Spil" %>
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
<link rel="stylesheet" href="css/page.css">

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
		<h3><%=user.getU_name() %>さんのマイページ</h3>

		<form action="top" method="post">
		<div class="content">
			<img src="<%=user.getU_img() %>"  alt="エラー" class="prof" >
			<textarea name="tweet" rows="5" cols="40" placeholder="言葉をこぼしてみよう(150文字以内)" ></textarea>
			<input type="submit" value="OK" class="spilBtn"><input type="hidden" name="act" value="spil" >
		</div>
		</form>

	<div class="menus">
		<form action="top" method="post">
			<div class="contents">
				<input type="submit" value="設定" class="menuBtn">
				<input type="hidden" name="act" value="jump">
				<input type="hidden" name="link" value="config"><br/>
					登録情報を変更する
		</div></form>
		<form action="top" method="post"><div class="contents">
		<input type="submit" value="追加" class="menuBtn">
		<input type="hidden" name="act" value="jump">
		<input type="hidden" name="link" value="uploadpage"><br/>
				サイトに本を載せる</div></form>

	</div>
		<div class="clear"></div>
		<hr size="2">

		<div class="panelstart">
			<ul class="tab-group">
				<li class="tab tab-A is-active">こぼれ言</li>
				<li class="tab tab-B" >読了一覧</li>
				<li class="tab tab-C" >積読の山</li>
			</ul>

		    <div class="panel-group">
				<div class="panel tab-A is-show">
				<%@include file="../include/spil_list.jsp" %>
				</div>
				<div class="panel tab-B">
					<%@include file="../include/read_list.jsp" %>
				</div>
				<div class="panel tab-C">
				<%@include file="../include/pile_list.jsp" %>

				</div>
			</div>

		</div>

	</div>

</div>

<%@include file="../include/footer.jsp" %>
<script src="<%=request.getContextPath() %>/js/upload.js"></script>
</body>
</html>