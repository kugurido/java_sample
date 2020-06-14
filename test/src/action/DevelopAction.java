package action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.util.codec.binary.Base64;

public class DevelopAction  extends Action{

	private ServletContext context;

	@Override
	public String execute(HttpServletRequest request)  {

		context = request.getServletContext();
		String path = context.getRealPath("img");
		System.out.println(path);
		String name = "kimetsu017.jpg";

		// 画像ファイル名が保存先に既に存在していた際にナンバリングする
		File file = new File(path + "/"+ name);
		//ドットは正規表現になるためカンマのようにただ(".")と書くと正しくsplitされない
		String splitName = name.split(Pattern.quote("."))[0];
		String split = name.split(Pattern.quote("."))[1];
		// 指定したファイルが見つからなくなる(=重複していない)まで繰り返す
		for(int i=2; file.exists(); i++) {
			String f_name = splitName + "("+i+")."+split; // よくみる「ファイル名(2).jpg」の形
			System.out.println(f_name);
			file = new File(path + "/" + f_name);
		}




		return "";
	}




	// ファイルアップロードのひな型
	public void uploadDevelop(HttpServletRequest request) {

		context = request.getServletContext();
		String path = context.getRealPath("img");
		System.out.println(path);
		List list = (List) request.getAttribute("filelist");
		FileItem baseItem = null;
		// FileItemの中身をまとめるためのオブジェクト<キー,値>で格納
		Map<String,String> map = new HashMap<>();

	try {
		for(Object o : list) {
			//formのデータ1つ1つをFileItemとして扱う
			FileItem item = (FileItem) o;

			// FileItemが<type=file>かそれ以外のtypeかで分岐させるが、
			// Base64の文字列を<type=hidden>で送っているのでif文不要
			if(item.isFormField()) {
				//fileではない時にtrue判定
				// <input name="フィールドネーム"とvalue="入力内容">を取得
				String fieldName = item.getFieldName();
				String value = item.getString("UTF-8");

				//取得したnameの値がfilebaseなら、この後のメソッドのためにFileItemを個別にしておく。
				if(fieldName.equals("filebase")) {
					baseItem = item;
				//filebaseじゃなかったら、そのキーとvalueをMap形式でセットしておく
				}else {
					map.put(fieldName, value);
					System.out.println(map.get(fieldName));
				}
			}// if文終わり.<input type="file">の時はここで処理する必要はないので省略
		}//for

		//メソッドのために必要な引数を準備して、ファイル保存メソッドを起動する
		if(!baseItem.getString().equals("")) {
			String f_name = map.get("insertName");
			fileup(baseItem,f_name,path);
		}

	} catch (UnsupportedEncodingException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	} catch (Exception e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
		System.out.println(baseItem);

	}



	public void fileup(FileItem p_item , String p_name , String p_path) {

		String base = p_item.getString();
		System.out.println(base);
		String splitBase = base.split(",")[1];
		System.out.println(splitBase);

		// 受け取ったBase64 形式のStringをdecodeBase64メソッドを用いてbyte[]に変換する
		byte[] imageBinary = Base64.decodeBase64(splitBase);

		// byte[]をByteArrayInputStreamメソッドを用いてInputStream型に変換する
		InputStream bais = new ByteArrayInputStream(imageBinary);

		try {
			// BufferedImageオブジェクトを作る。
			BufferedImage bfImg = ImageIO.read(bais);

			// OutputStream（保存先）オブジェクトを作る
			OutputStream out = new FileOutputStream(p_path +"/" + p_name);

			// 画像の拡張子を確認して書き込む
			if(p_name.endsWith("jpg")) {
				Boolean bool = ImageIO.write(bfImg, "jpg", out);
				out.close();
				System.out.println(bool);
			}else if(p_name.endsWith("png")) {
				Boolean bool = ImageIO.write(bfImg, "png", out);
				out.close();
				System.out.println(bool);
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


	}


	// multitypeを使わずに、getParameterだけで保存しようとしたが、
	// Base64の文字列を送っても意味がなかったのでおじゃんになりました。







}



