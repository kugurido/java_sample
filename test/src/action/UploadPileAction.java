package action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import bean.Item;
import bean.User;
import dao.ItemDAO;
import dao.ListDAO;
import method.FileMethod;

public class UploadPileAction extends Action{

	private ServletContext context;

	@Override
	public String execute(HttpServletRequest request) {

		// Sessionが生きてるか確認して、生きてれば取得・死んでたらエラーを飛ばす
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "/error.jsp";
		}
		// 絶対パスを取得するためのcontextを取得する
		context = request.getServletContext();
		// ファイルの保存場所を絶対パスで取得する
		String path = context.getRealPath("img");
		// C:\eclipse_workspace\sample\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\test\img
		// C:\eclipse_workspace\sample\git\java_sample\test\WebContent\img
		System.out.println(path);

		// 各種メソッドで使用するための変数を用意する
		Item l_item = new Item();
		User l_user = (User) session.getAttribute("user");
		FileItem baseItem = null;
		String f_name = null;
		String c_img = null;
		FileMethod fm = new FileMethod();
		// FileItemの中身をまとめるためのオブジェクトを用意。<キー,値>の形式で格納
		Map<String,String> map = new HashMap<>();
		// Controlから用意してもらったmultipartのListを取得する
		List list = (List) request.getAttribute("filelist");
		// 万が一取得出来なかった場合はエラーを飛ばす
		if(list == null) {
			return "/error.jsp";
		}

// ----------Listの中身を順番に取り出していくフェイズ----------
		map = fm.fileAction(list, map, session);

// ----------画像を保存するフェイズ(画像がある場合だけ)----------
	// 画像データは↑のメソッドでセッションに格納し直したのでそこから取得
		baseItem = (FileItem) session.getAttribute("filebase");
	// 取得できて且つちゃんとファイル名が存在する場合 => 画像の書き込み処理
		if(!baseItem.getString().equals("") && baseItem != null) {
				f_name = map.get("insertName");
				c_img = fm.imgWrite(baseItem, f_name, path);
				session.removeAttribute("filebase");
				if(c_img.equals("error")) {
					return "/error.jsp"; //画像データはあるが書き込みが出来なかったケース
				}
		}

// ----------DBにインサートするフェイズ----------
		try {
			// リクエストパラメータをItemオブジェクトに格納
			l_item = setData(l_item,f_name,map);

			// 格納されたデータのチェック
			if(l_item.getI_name() == null || l_item.getI_name().equals("")) {
				System.out.println("本の名前が無い");
				return "/book_upload.jsp";
			}
			if(l_item.getI_author() == null || l_item.getI_author().equals("")) {
				System.out.println("著者の名前が無い");
				return "/book_upload.jsp";
			}

			// ItemのIDを取得するためにDBをチェック
			ItemDAO idao = new ItemDAO();
			Item ch_item = idao.searchNameAndAuthor(l_item.getI_name(), l_item.getI_author());
			if(ch_item == null || ch_item.getI_id()==0) {
			// サーチできなかった =>DBに登録されていないのでIDがない =>新たに登録する
				ItemDAO l_idao = new ItemDAO();
				int l_rs = l_idao.insertItem(l_item);

				// insertが成功した場合、newIDの値を戻り値に設定しているのでそれをitemに格納する
				if(l_rs != 0) {
					l_item.setI_id(l_rs);

				}else {
					System.out.println("DB登録時にエラー");
					return "/error.jsp";
				}
			// サーチできた =>既に登録されている本である =>取得できたIDをもらう
			}else{
				l_item.setI_id(ch_item.getI_id());
			}// if(ch_item)...終わり

			// 積読データに追加
			ListDAO lldao = new ListDAO();
			int rs = lldao.insertPile(l_user.getU_id(), l_item.getI_id());
			if(rs == 0) {
				return "/error.jsp";
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		// 積読情報セット
		ArrayList<Item> p_list = new ArrayList<>();
		ListDAO ldao = new ListDAO();
		list = ldao.searchPileAll(l_user.getU_id());
		session.setAttribute("piles", p_list);
		request.setAttribute("message", "積読しました");
		return "/mypage.jsp";
	}

//========================================================================================================

	// insertする際のデータを格納する
	public Item setData(Item p_item,String p_fName,Map<String,String> p_map) {

		p_item.setI_name(p_map.get("itemName"));
		p_item.setI_author(p_map.get("author"));
		p_item.setI_publisher(p_map.get("publisher"));
		p_item.setI_genre(p_map.get("genre"));
		// 画像を新しく用意していればその名前を、用意してなければnoImageをセットする
		if(p_fName==null || p_fName.equals("")) {
			p_item.setI_img("img/noImage.png");
		}else {
			p_item.setI_img("img/" + p_fName);
		}

		return p_item;
	}



}
