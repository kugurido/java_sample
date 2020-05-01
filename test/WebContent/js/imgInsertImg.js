
var file_reader = new FileReader();
var file_reader_pile = new FileReader();
var button = document.getElementById("pileBtn");


function btnClick(){
	document.getElementsByClassName("imgCh")[0].click();
}


function changeImg(){
	var input_file = document.getElementsByClassName("imgCh")[0];
	var result = Boolean(window.FileReader);
	if(result){
		console.log('サポートしている');
	}else{
		console.log('サポートしてない');
	}
	var file = input_file.files;
	if(file[0]){
		console.log(file[0].name);
	}else{
		return;
	}
	var input_name = document.getElementById("readName");
	input_name.value = input_file.files[0].name;

	var blob = new Blob(file);
	file_reader.readAsDataURL(blob);
	console.log('動作確認');
	console.log(input_name);
}

file_reader.onload = function(){
	var img = document.getElementsByClassName("item_img")[0];
	var rf = document.getElementById("readFile");
	rf.value = this.result;
	img.src = this.result;
	console.log(this.result);
}


// 積読フォームのメソッド
// 画像変更のボタンを押した時に起動させる
var btnClick_pile = function(){
	// display:none;にした<input type="file">をクリックした処理を行う
	document.getElementById("ic").click();
}

// <input type="file">で画像が選択された時=> onchangeの時に起動させる
var changeImg_pile = function(){

	//<input>のdocumentを取得
	var input_file = document.getElementById("ic");

	// FileReaderのAPIが使えるかチェック
	var result = Boolean(window.FileReader);
	var rs = Boolean(window.FormData);
	if(result){

		console.log('サポートしてる');
	}else{

		console.log('サポートしてぬ');
	}
	if(rs){
		console.log('フォームOK');
	}else{
		console.log('フォームNG');
	}

	// 選択されたファイルのファイル名を特定の<input hidden>の中に格納する
	var ins = document.getElementById("insertName");
	ins.value = input_file.files[0].name;
	console.log(ins.value); // 格納できているかログでチェック


	// 選択されたファイルを取得
	var file = input_file.files;
	// 取得したファイルをBlob型に変換（Binary Large OBject の略）
	// DBのfield定義などで用いられるデータ型の一つで、テキストや数値以外の任意の形式のバイナリデータを格納することができる。
	var blob = new Blob(file);
	// BlobをDataURIとして読み込む。（画像やらJavascriptやらそういったHTMLのコンテンツを文字列として定義出来てしまう）
	file_reader_pile.readAsDataURL(blob);

}

// DataURIを読み込むことができた時に起動する
file_reader_pile.onload = function(){
	var img = document.getElementsByClassName("ins_img")[0];
	var base = document.getElementById("filebase");
	base.value = this.result; // DataURI形式になった画像情報を特定の<input hidden>に格納する
	img.src = this.result;
	console.log(this.result);

}

button.addEventListener("click",btnClick_pile);

var form = document.getElementById("pile");


// 使わなくなった入力チェック
function check(){
	var act = document.getElementById("act").value;
	var pilename = document.getElementById("pileName").value;
	var pileauthor = document.getElementById("pileAuthor").value;
	var pilepublisher = document.getElementById("pilePublisher").value;
	var pilegenre = document.getElementById("genre").list;
	var pileimage = document.getElementById("ic").value;

	if(act != "" && pilename != "" && pileauthor != "" && pilepublisher != "" && pileimage != ""){
		sendData();
	}else{
		alert("Please input all");
		return false;
	}
}
// 使わなくなったXMLRequest方式の送信メソッド
// XML方式だとforwardで受け取った情報などを処理できない=>ページが切り替えられない
// そもそもXML方式はページを切り替えずにpost通信を行うための主な手段なので出来なくて当然
function sendData(){

	var formData = new FormData(form);
	console.log(formData);
	var req = new XMLHttpRequest();
	var file = document.getElementsByClassName("imgCh")[1];
	var filename = file.files[0].name;
	console.log(file.files[0]);
	formData.append("img",file.files[0],filename);


	req.addEventListener("error",function(event){
		alert("Error");
	});
	req.onload = function(){
		alert("Load");
		console.log(this.response);
	}

	req.open("POST","/test/top",true);
	req.send();




}





