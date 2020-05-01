<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="bean.User" %>
 <%@page  import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%
	User user = new User();
	if(session.getAttribute("user") != null){
		user = (User)session.getAttribute("user");
	}

	String error = null;
	if(request.getAttribute("error") != null){
		error = "まだ誰も何も読んでないみたいね";
	}

%>


<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/config.css">
<title>登録情報設定ページ</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div class="title"><h3>登録情報の変更</h3></div>
	<form id="pile" action="top" method="post" enctype="multipart/form-data">
		<div class="configParent">
			<div class="configImg">
				<div><img alt="エラー" src="<%=user.getU_img()%>" class="ins_img"></div>
	 			<div>
	 				<button type="button" class="imgInsert" id="pileBtn">画像を変更する</button>
					 <input type="file"  class="imgCh"  id="ic" onchange="changeImg_pile()">
				</div>
			</div>

			<div class="configDB">
				<div class="dbText">名前：<input type="text" name="cName" class="solid" placeholder="<%=user.getU_name() %>"></div>
				<div class="dbText">かな：<input type="text" name="cKana" class="solid" placeholder="<%=user.getU_kana() %>"></div>
				<div class="dbText">自己紹介：</div>
				<div class="dbtext"><textarea rows="4" cols="" name="pr" class="cHello solid"></textarea></div>
				<div class="dbText dbsubmit">
					<input type="hidden" name="act" value="config">
					<input type="hidden" name="filebase" value="" id="filebase">
					<input type="hidden" name="insertName" value="" id="insertName">
					<input type="submit" value="変更">
				</div>
				<div class="dbtext dbsubmit"><a href="top?act=jump&link=changeMail">メールアドレスを変更する場合はコチラ</a></div>
				<div class="dbtext dbsubmit"><a href="top?act=jump&link=changePass">パスワードを変更する場合はコチラ</a></div>
			</div>

		</div>
	</form>
	</div>

</div>

<%@include file="../include/footer.jsp" %>
<script src="<%=request.getContextPath() %>/js/imgInsertImg.js"></script>
</body>
</html>