<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.Item" %>
 <%
	ArrayList<Item> piles = new ArrayList<>();
	if(request.getAttribute("rPile") != null){
		piles = (ArrayList<Item>)request.getAttribute("rPile");
	}
 %>


	<% if(piles!=null && piles.size()>0){ %>
		<div class="table_div">
		<table border="3" class="pile_table">
		<% for(int i=0; i<piles.size(); i++){
		   Item pile = piles.get(i); %>
			<tr class="tr1">
				<td rowspan="3" class="td1 pileTd">
					<img alt="エラー" src="<%=pile.getI_img() %>" class="pile_img" width="100" height="150">
				</td>
				<td  class="td_p" >書籍名：<%=pile.getI_name() %></td>
			</tr>
			<tr class="tr2">
				<td class="td_p">出版社：<%=pile.getI_publisher() %> </td>
			</tr>
			<tr>
				<td class="td_p">著者名：<%=pile.getI_author() %></td>
			</tr>
		<%} %>
		</table>
		</div>
	<%}%>