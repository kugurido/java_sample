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
<link rel="stylesheet" href="css/list.css">
<title>完読トピックス</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<div><h3>読まれた本を見てみよう</h3></div>
		<div>
			<%if(error != null){ %><div><%=error %></div><%} %>
			<%if(list.size() != 0) {
				for(int i=0; i<list.size(); i++){
					Item item = list.get(i); %>
				<div align="center">
				<table border="1">
					<tr>
						<th>書影</th>
						<th>書名</th>
						<th>出版社</th>
						<th>著者</th>
					</tr>

					<tr class="items">
						<form action="top" method="post">
						<td><input type="image" src="<%= item.getI_img() %>"   alt="エラー" width="100" height="150"/></td>
						<input type="hidden" name="act" value="detail"  />
						<input type="hidden" name="itemNo" value="<%=item.getI_id() %>" />
						</form>
						<td><%= item.getI_name() %></td>
						<td><%=item.getI_publisher() %></td>
						<td><%= item.getI_author() %></td>
					</tr>
				</table>
				</div>
			<%}} %>

		</div>

	</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>