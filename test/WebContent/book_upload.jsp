<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page  import="java.util.ArrayList"%>
 <%@page  import="bean.User" %>
 <%@page  import="bean.Item" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	Item item = null;
	if(request.getAttribute("item") != null){
		item = (Item)request.getAttribute("item");
	}

	ArrayList<String> genres = new ArrayList<>();
	if(session.getAttribute("genre") != null){
		genres = (ArrayList<String>)session.getAttribute("genre");
	}

	String phName = "";
	String phAuthor = "";
	String phPublisher = "";
	String phImg = "img/noImage.png";
	if(item != null){
		phName = item.getI_name();
		phAuthor = item.getI_author();
		phPublisher = item.getI_publisher();
		phImg = item.getI_img();
	}



%>

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/upload.css">

<title>アップロード</title>
</head>

<body>

<%@include file="../include/header.jsp" %>
<div class="main">
	<div class="main_sub">
		<!-- ボタンリンクの部分を外部JSPファイルにした -->
		<%@include file="../include/sub_menu.jsp" %>
	</div>

	<div class="main_main">
		<h2>本を追加しよう</h2>
		<div class="clear"></div>
		<div class="tab-panel">
	<!--タブ-->

			<ul class="tab-group">
				<li class="tab tab-A is-active">完読報告</li>
				<li class="tab tab-B">積読追加</li>
			</ul>

	<!--タブを切り替えて表示するコンテンツ-->
    		<div class="panel-group">
				<div class="panel tab-A is-show">
					<div class="plan">あなたが読み終えた本を紹介してみましょう！<br/></div>
					<div class="tableDiv">
					<form action="top" method="post"  enctype="multipart/form-data">
					<input type="hidden" name="act" value="uploadread"/>
					<table align="center">
						<tr>
							<td rowspan="5" class="td_left"><img alt="エラー" src="<%=phImg %>" class="item_img"></td>
						</tr>
						<tr>
							<td class="td_right">タイトル：</td>
							<td><input type="text" name="itemName" class="readinput"  placeholder="<%=phName %>"  required><br/>(※)入力必須</td>
						</tr>
						<tr>
							<td class="td_right">著者：</td>
							<td><input type="text" name="author" class="readinput"  placeholder="<%=phAuthor %>"  required><br/>(※)入力必須</td>
						</tr>
						<tr>
							<td class="td_right">出版社：</td>
							<td><input type="text" name="publisher" class="readinput"  placeholder="<%=phPublisher %>"  required><br/>(※)入力必須</td>
						</tr>
						<tr>
							<td class="td_right">ジャンル：</td>
							<td><label><input list="genre_list" name="genre" id="pileGenre">
								<datalist id="genre_list">
								<%for(int i=0; i<genres.size(); i++){
										String genre = genres.get(i);%>
									<option value="<%= genre %>">
								<%} %>

								</datalist></label>
							</td>
						</tr>
						<tr>
							<td class="insertBtn"><label>
								<button type="button" class="imgInsert" onclick="btnClick()">書影を変更する</button>
								<input type="file" accept="img/png, img/jpeg" class="imgCh" onchange="changeImg()"  >
							</label></td>
							<td class="td_right">
								感想：
							</td>
							<td><textarea cols="40" rows="10"  name="revue"></textarea></td>
						</tr>
					</table>
					<div class="insert">
						<input type="hidden" name="readFile" value="" id="readFile">
						<input type="hidden" name="readName" value="" id="readName">
						<input type="submit" value="完了"  class="insert" >
					</div>
					</form>
					</div>
				</div>

<!-- ここから積読のフロントページ -->
				<div class="panel tab-B">
					<div class="plan">あなたが見つけた本を登録してみよう！<br/></div>
					<div class="tableDiv">
					<form id="pile" action="top" method="post" enctype="multipart/form-data">
					<input type="hidden" name="act" value="uploadpile" id="act"/>
					<table align="center">
						<tr>
							<td rowspan="5" class="td_left"><img alt="エラー" src="<%=phImg %>" class="ins_img" ></td>
						</tr>
						<tr>
							<td class="td_right">タイトル：</td>
							<td><input type="text" name="itemName" class="pileinput" id="pileName" required><br/>(※)入力必須</td>
						</tr>
						<tr>
							<td class="td_right">著者：</td>
							<td><input type="text" name="author" class="pileinput" id="pileAuthor"  required><br/>(※)入力必須</td>
						</tr>
						<tr>
							<td class="td_right">出版社：</td>
							<td><input type="text" name="publisher" class="pileinput"  id="pilePublisher" required><br/>(※)入力必須</td>
						</tr>
						<tr>
							<td class="td_right">ジャンル：</td>
							<td><label><input list="genre_list" name="genre" id="genre"></label>
								<datalist id="genre_list">
								<%for(int i=0; i<genres.size(); i++){
										String genre = genres.get(i);%>
									<option value="<%= genre %>">
								<%} %>
								</datalist>
							</td>
						</tr>
						<tr>
							<td class="insertBtn">
								<!-- type=buttonをつけないとクリック処理時にJSの処理に加えてsubmit処理もされてしまうから注意！ -->
								<button type="button" class="imgInsert" id="pileBtn">書影を変更する</button>
								<input type="file"  class="imgCh"  id="ic" onchange="changeImg_pile()">
							</td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="insert">
						<input type="hidden" name="filebase" value="" id="filebase">
						<input type="hidden" name="insertName" value="" id="insertName">
						<input type="submit" value="完了"  class="insert" />

					</div>
					</form>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="../include/footer.jsp" %>
<script src="<%=request.getContextPath() %>/js/upload.js"></script>
<script src="<%=request.getContextPath() %>/js/imgInsertImg.js"></script>

</body>
</html>