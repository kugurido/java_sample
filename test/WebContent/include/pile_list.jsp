<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.Item" %>

<%
	ArrayList<Item> i_list = null;
	if(session.getAttribute("piles") != null){
	i_list = (ArrayList<Item>)session.getAttribute("piles");
	}
%>




<!-- こっから積読パターン -->

<% if(i_list != null){ %>
	<div class="spilDiv">
		<%for(int i=0; i<i_list.size(); i++){
				Item item = i_list.get(i); %>
			<table border="2" class="pile_table">
			<tr class="tr1">
				<td rowspan="4" class="td1p">
					<img alt="エラー" src="<%=item.getI_img() %>" class="item_img" border="1">
				</td>
				<td colspan="2" class="tdp" ><%=item.getI_name() %></td>
			</tr>
			<tr class="tr2">
				<td class="tdp">著者：<%=item.getI_author() %> </td>
				<td rowspan="2" align="center" class="tdp"><form action="top" method="post">
					<input type="hidden" name="act" value="pileList">
					<input type="hidden" name="pileRead" value="<%=item.getI_id() %>">
					<input type="submit" value="完読" class="readBtn"></form>
				</td>
			</tr>
			<tr>
				<td class="tdp">出版社：<%=item.getI_publisher() %></td>
			</tr>
			<tr>
				<td align="center" class="tdp"><%=item.getI_genre() %></td>
				<td align="center" class="tdp"><form action="top" method="post">
					<input type="hidden" name="act" value="delete">
					<input type="hidden" name="dName" value="pile" >
					<input type="hidden" name="dID" value="<%=item.getI_id() %>">
					<input type="submit" value="削除" class="deleteBtn"></form>
				</td>
			</tr>
			</table>
		<%} %>
	</div>
	<%} %>