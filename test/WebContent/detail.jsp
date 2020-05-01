<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="bean.User" %>
 <%@page  import="bean.Item" %>
 <%@page  import="bean.Revue" %>
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

	ArrayList<Revue> r_list = null;
	if(request.getAttribute("revuelist") != null){
		r_list = (ArrayList<Revue>)request.getAttribute("revuelist");
	}

%>


<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/detail.css">
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

		<div class="detail">
			<img src="<%=item.getI_img() %>" alt="準備中" >
		</div>
		<div class="detail">
			<!-- ここに書籍ジャンル -->
			書籍名：<%=item.getI_name() %><br/>
			出版社：<%=item.getI_publisher() %><br/>
			著者名：<%=item.getI_author() %><br/>
		</div>
		<div class="clear"></div>
		<hr size="2">

	<%if(r_list != null && r_list.size() != 0){ %>
		<div class="revueTitle">レビュー</div>
		<%for(int i=0; i<r_list.size(); i++){
			Revue revue = r_list.get(i);%>

			<table border="1" class="itemRevue">
			<tr>
				<td rowspan="2" width="75%" class="revueWrite"><%=revue.getRevue() %></td>
				<td class="u tdImg">
					<a href="top?act=userdetail&userID=<%=revue.getU_id() %>" ><img alt="エラー" src="<%=revue.getU_img() %>" class="revueImg"></a>
				</td>
			</tr>
			<tr>
				<td class="u tdName"><%=revue.getU_name() %></td>
			</tr>
			</table>


		<% } %>


	<%} %>

	</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>