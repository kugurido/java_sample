<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String error = null;
	if(request.getAttribute("error") != null){
		if(request.getAttribute("eMessage") != null){
			error = (String)request.getAttribute("eMessage");

		}else{
			error = "IDまたはメールアドレスが正しくありません";
		}
	}

	String indexText = "登録ユーザーの確認を行うため、「ユーザID」と「パスワード」を入力してください。";
	String textA = "ユーザーID";
	String iNameA = "loginID";
	String textB = "パスワード";
	String iNameB = "pass";
	String restriction = "";
	int len = 100;
	String reValue = "first";

	if(request.getAttribute("firstcheck") != null){
		indexText = "本人確認ができました。新しいメールアドレスを設定してください。";
		textA = "Eメール";
		iNameA = "mail";
		textB = "確認のため再入力";
		iNameB = "remail";
		restriction = "";
		len = 100;
		reValue = "second";
	}

%>

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/login.css">

<title>メールアドレス再設定画面</title>
</head>
<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">

		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

<div class="main_main">
	<h3  class="logText">メールアドレス再設定</h3>
	<br/>
	<div class="indexText"><%=indexText %></div>
	<div><p class="error"><% if(error != null){ %><%=error %><%} %></p></div>

	<form action="top" method="post" name="loginform">
	<div class="logText">
	  <table class="tableText">
		<tr>
		  <td class="tdIndex"><%=textA %></td>
		  <td>：</td>
		  <td><input type="text" name="<%=iNameA %>" maxlength="100" required /></td>
		  <td><%=restriction %></td>
		</tr>
		<tr>
		  <td class="tdIndex"><%=textB %></td>
		  <td>：</td>
		  <td><input type="text" name="<%=iNameB %>" maxlength="<%=len %>" required /></td>
		  <td><%=restriction %></td>
		</tr>
	  </table>
	</div>
	<br/>
	<div  class="logText">
		<input type="submit" value="確認"  class="forgetCheck"/>
		<input type="hidden" name="act" value="changeMail" />
		<input type="hidden" name="re" value="<%=reValue %>">
	</div>
	</form>

</div>

</div>

<%@include file="../include/footer.jsp" %>

</body>
</html>