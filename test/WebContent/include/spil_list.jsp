<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
 <%@page  import="bean.Spil" %>
 <%@page  import="bean.Item" %>
<%

	ArrayList<Spil> s_list = null;
	if(session.getAttribute("spils") != null){
		s_list = (ArrayList<Spil>)session.getAttribute("spils");
	}




%>

<% if(s_list != null && s_list.size() != 0){ %>
	<div class="spilDiv">
	<table border="1" class="spil_table">
		<%for(int i=0; i<s_list.size(); i++){
				Spil spil = s_list.get(i); %>

			<tr class="tr1">
				<td rowspan="2" class="td1">
					<img alt="エラー" src="<%=spil.getUser_img() %>" class="spil_img">
				</td>
				<td colspan="2" class="td2" ><pre><%=spil.getSpil() %></pre></td>
			</tr>
			<tr class="tr2">
				<td class="td3"><%=spil.getSpil_time() %> </td>
				<td align="center" class="td4"><form action="top" method="post">
					<input type="hidden" name="act" value="delete">
					<input type="hidden" name="dName" value="spil" >
					<input type="hidden" name="dID" value="<%=spil.getSpil_id() %>">
					<input type="submit" value="削除" class="deleteBtn"></form>
				</td>
			</tr>

		<%} %>
	</table>
	</div>
<%} %>




