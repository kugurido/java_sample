package method;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.util.codec.binary.Base64;

public class FileMethod {


	public Map<String,String> fileAction(List list, Map<String,String> map,HttpSession session) {

// >>>>>>>>>>>>>>>>>>>Listの中身を順番に取り出していくフェイズ<<<<<<<<<<<<<<<<<<<<<<<
		try {
			for(Object o : list) {
				// formのデータ1つ1つをFileItemとして扱う
				FileItem f_item = (FileItem) o;

				// FileItemが<type=file>ならfalse
				// ただ<type=file>として送るはずのデータは<type=hidden>からBase64形式で送ったため全部true判定になり特に意味はない
				if(f_item.isFormField()) {

					// <input name="フィールドネーム"とvalue="入力内容">を取得
					String fieldName = f_item.getFieldName();
					String value = f_item.getString("UTF-8"); // <=文字コード記載しないと文字化けする

					//取得したnameの値がfilebaseなら、この後のメソッドのためにFileItemを個別にセット。
					if(fieldName.equals("filebase")) {
						session.setAttribute("filebase", f_item);

					//filebaseじゃなかったら、そのキーとvalueをMap形式でセットしておく
					}else {
						map.put(fieldName, value);
						System.out.println(map.get(fieldName));
					}
				}// if文終わり
			}//for

		}catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return map;
	}

//==============================================================================================

	public String imgWrite(FileItem baseItem,String f_name,String path) {

			// 保存先に同じファイル名があるかチェックするためのオブジェクト
			File file = new File(path + "/"+ f_name);
			//ドットは正規表現になるためカンマのようにただ(".")と書くと正しくsplitされない
			String splitName = f_name.split(Pattern.quote("."))[0];
			String split = f_name.split(Pattern.quote("."))[1];
			// 指定したファイルが見つからなくなる(=重複していない)まで繰り返す
			for(int i=2; file.exists(); i++) {
				f_name = splitName + "("+i+")."+split; // よくみる「ファイル名(2).jpg」の形
				file = new File(path + "/" + f_name); // forループ解消のため
			}

			// 重複した時に(i)をメソッドで足したけどキチンと足されているか確認
			System.out.println(f_name);

			Boolean bool = fileup(baseItem,f_name,path);
			// fileupの結果がtrue(書き込み完了)なら以降の処理
			if(bool) {
				System.out.println("画像書き込み出来てる");

			}else {
				System.out.println("画像書き込みでエラー");
				return "error";
			}

			return f_name;

	}

//===================================================================================

	// ファイルをフォルダに保存するためのメソッド
	public Boolean fileup(FileItem p_item , String p_name , String p_path) {

		// 戻り値（保存できたか判定する）用の変数
		Boolean l_bool = false;
		// 送られたBase64形式の文字列を取得
		String base = p_item.getString();
		// 上で送られた文字列は「データの説明文＋Base64」の文字列なので、Base64形式の部分だけを切り取る
		String splitBase = base.split(",")[1];

		// Base64 形式のStringをdecodeBase64メソッドを用いてbyte[]に変換する
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
				l_bool = ImageIO.write(bfImg, "jpg", out);
				out.close();
			}else if(p_name.endsWith("png")) {
				l_bool = ImageIO.write(bfImg, "png", out);
				out.close();
			}

			// gitフォルダにも書き込み
			Boolean g_bool = false;
			String g_path = "C:\\eclipse_workspace\\sample\\git\\java_sample\\test\\WebContent\\img";
			OutputStream g_out = new FileOutputStream(g_path +"/" + p_name);
			if(p_name.endsWith("jpg")) {
				g_bool = ImageIO.write(bfImg, "jpg", g_out);
				g_out.close();
			}else if(p_name.endsWith("png")) {
				g_bool = ImageIO.write(bfImg, "png", g_out);
				g_out.close();
			}


		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return l_bool;
	}

}
