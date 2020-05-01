<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.Revue" %>
 <%
	ArrayList<Revue> revues = new ArrayList<>();
	if(request.getAttribute("rRevue") != null){
		revues = (ArrayList<Revue>)request.getAttribute("rRevue");
	}
 %>


	<% if(revues!=null && revues.size()>0){ %>
		<div class="table_div">
		<table border="3" class="pile_table">
		<% for(int i=0; i<revues.size(); i++){
		   Revue revue = revues.get(i); %>
			<tr class="tr1">
				<td rowspan="2" class="td1 pileTd">
					<img alt="エラー" src="<%=revue.getI_img() %>" class="read_img" width="100" height="150">
				</td>
				<td  class="td_r" ><%=revue.getI_name() %></td>
			</tr>
			<tr class="tr2">
				<td class="td_r"><div class="revue"><%=revue.getRevue() %></div> </td>
			</tr>

		<%} %>
		</table>
		</div>
	<%}%>