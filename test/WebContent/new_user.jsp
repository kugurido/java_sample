<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String error = "";
	if(request.getAttribute("error") != null){
		error = (String)request.getAttribute("error");
	}
%>

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/new_user.css">
<script type="text/javascript">
<!--
	function insertCheck(){
	var len = document.insertList.length;
		for(var i=0; i<len-1; i++){
			if(document.forms.insertList.elements[i].value == ""){
				alert('未入力の項目があります');
				return false;
			}
		}
	}
//-->
</script>
<title>新規登録</title>
</head>

<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">
		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<h2>新規ユーザー登録</h2>
		<div class="formDIV">
		<div>入力項目はすべて必須です。入力が完了したら【登録】ボタンをクリックしてください</div>
		<div class="caution">※ユーザー名は他のユーザーから見えるので、不快にならない名前にしてください</div>

		<div class="error"><%=error %></div>
		<!-- 未入力チェックはrequiredだけで事足りたけど念のためjavascriptとServletの方でもチェックしてる↓ -->

		<form action="top" method="post" name="insertList" onsubmit="return insertCheck()">
		<div class="table">
		<table border="5" align="center">
		<tr>
			<td>ユーザー名</td>
			<td><input type="text" name="userName"  required></td>
			<td>(15文字以内)</td>
		</tr>
		<tr>
			<td>フリガナ</td>
			<td><input type="text" name="userKana"  required></td>
			<td>(15文字以内)</td>
		</tr>
		<tr>
			<td>Eメール</td>
			<td><input type="email" name="eMail" required></td>
		</tr>
		<tr>
			<td>ユーザーID</td>
			<td><input type="text" name="loginID" pattern="^[0-9A-Za-z]+$" required></td>
			<td>(半角英数字15文字以内)</td>
		</tr>
		<tr>
			<td>パスワード</td>
			<td><input type="password" name="loginPassword" required></td>
			<td>(半角英数字15文字以内)</td>
		</tr>
		</table>
		</div>
		<div class="insertBtn">
			<input type="submit" value="登録" class="insertBtn">
			<input type="hidden" name="act" value="newUser">
			<input type="hidden" name="insert" value="ok">
		</div>
		</form>
		</div>

	</div>
</div>
<%@include file="../include/footer.jsp" %>

</body>
</html>