<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.Spil" %>
 <%
	ArrayList<Spil> spils = new ArrayList<>();
	if(request.getAttribute("rSpil") != null){
		spils = (ArrayList<Spil>)request.getAttribute("rSpil");
	}
 %>


	<% if(spils!=null && spils.size()>0){ %>
		<div class="table_div">
		<table border="3" class="spil_table">
		<% for(int i=0; i<spils.size(); i++){
		   Spil spil = spils.get(i); %>
			<tr class="tr1">
				<td rowspan="2" class="td1">
					<img alt="エラー" src="<%=spil.getUser_img() %>" class="spil_img">
				</td>
				<td  class="td2" ><pre><%=spil.getSpil() %></pre></td>
			</tr>
			<tr class="tr2">
				<td class="td3"><%=spil.getSpil_time() %> </td>
			</tr>
		<%} %>
		</table>
		</div>
	<%}%>