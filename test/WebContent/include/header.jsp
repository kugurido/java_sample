<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="bean.User" %>
<%

	String loginMessage = "ログイン／新規登録";
	if(session.getAttribute("user") != null){
		loginMessage = "マイページ";
	}
	String message = "";
	if(request.getAttribute("message") != null){
		message = (String)request.getAttribute("message");
	}
%>

    <div class="headline">
	    <div class="logo"><a href="top">
		<img src="img/logo.png" alt="エラー" class="logo"></a>
		</div>
		<form action="top" method="post">
		<div class="loginBtn"><input type="submit" value="<%=loginMessage %>" class="loginBtn" />
								<input type="hidden" name="act" value="login"/></div>
		</form>
	<%if(session.getAttribute("user") != null){ %>

		<div class="logoutBtn"><a href="top?act=logout">ログアウト</a></div>

	<%} %>
		<div class="logined"><%=message %></div>
		<div class="clear"></div>
    </div>