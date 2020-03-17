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

	ArrayList<Spil> s_list = null;
	if(session.getAttribute("spils") != null){
		s_list = (ArrayList<Spil>)session.getAttribute("spils");
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
		<div class="content">
			<img src="<%=user.getU_img() %>"  alt="エラー" class="prof" ><br/>
			<input type="submit" value="画像を変更する" class="profBtn">
		</div>




		<form action="top" method="post">
			<div class="content">
				<textarea name="tweet" rows="5" cols="40" placeholder="言葉をこぼしてみよう(150文字以内)" ></textarea></div>
			<div class="content_Btn">
				<input type="submit" value="OK" class="spilBtn"><input type="hidden" name="act" value="spil" >
			</div>
		</form>

	<div class="menus">
		<form><div class="content01"><input type="submit" value="積読" class="menuBtn"><br/>
				積読を確認する</div></form>
		<form><div class="content02"><input type="submit" value="完読" class="menuBtn"><br/>
				書籍を読み終えた</div></form>
		<form><div class="content03"><input type="submit" value="読破" class="menuBtn"><br/>
				今までの書籍</div></form>
	</div>
		<div class="clear"></div>

		<!-- つぶやきを取得して表示↓ -->
	<% if(s_list != null&&s_list.size() != 0){ %>
		<hr size="2">
		<div class="spilDiv">
		<%for(int i=0; i<s_list.size(); i++){
				Spil spil = s_list.get(i); %>
			<table border="1" class="spil_table">
			<tr class="tr1">
				<td rowspan="2" class="td1">
					<img alt="エラー" src="<%=spil.getUser_img() %>" class="spil_img">
				</td>
				<td colspan="2" class="td2" ><%=spil.getSpil() %></td>
			</tr>
			<tr class="tr2">
				<td class="td3"><%=spil.getSpil_time() %> </td>
				<td align="center" class="td4"><form action="top" method="post">
					<input type="hidden" name="act" value="delete">
					<input type="hidden" name="spilID" value="<%=spil.getSpil_id() %>">
					<input type="submit" value="削除" class="deleteBtn"></form>
				</td>
			</tr>
			</table>
		<%} %>
		</div>
	<%} %>
	</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>