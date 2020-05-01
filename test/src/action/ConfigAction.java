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

import bean.Spil;
import bean.User;
import dao.SpilDAO;
import dao.UserDAO;
import method.FileMethod;

public class ConfigAction extends Action {

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
		System.out.println(path);

		// 各種メソッドで使用するための変数を用意する
		User l_user = (User) session.getAttribute("user");
		FileItem baseItem = null;
		String f_name = null;
		String c_img = null;
		FileMethod fm = new FileMethod();  // 画像書き込み処理を担うクラス（自作）

		// FileItemの中身をまとめるためのオブジェクトを用意。<キー,値>の形式で格納
		Map<String,String> map = new HashMap<>();

		// Controlから用意してもらったmultipartのListを取得する
		List list = (List) request.getAttribute("filelist");
		// 万が一取得出来なかった場合はエラーを飛ばす
		if(list == null) {
			return "/error.jsp";
		}

		// mapにそれぞれのキーと値を入れる
		map = fm.fileAction(list, map,session);
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

		// configで入力された情報をUserオブジェクトに格納する
		String c_name = map.get("cName");
		String c_kana = map.get("cKana");
		String c_pr = map.get("pr");
		if(c_name != null && !c_name.equals("")) {
			l_user.setU_name(c_name);
		}
		if(c_kana != null && !c_kana.equals("")) {
			l_user.setU_kana(c_kana);
		}
		if(c_pr != null && !c_pr.equals("")) {
			l_user.setU_pr(c_pr);
		}
		if(c_img != null && !c_img.equals("") && !c_img.equals("error")) {
			c_img = "img/" + c_img;
			l_user.setU_img(c_img);
		}

		// DBをアップデートする
		try {
			UserDAO udao = new UserDAO();
			int rs = udao.updateConfig(l_user);
			if(rs == 0) {
				return "/error.jsp"; // アップデートがうまくいかなかった
			}
			udao = new UserDAO();
			l_user = udao.searchID(rs); // アップデートされたUserオブジェクトを取得する
			session.setAttribute("user", l_user);

			// 呟き情報セット
			SpilDAO l_sdao = new SpilDAO();
			ArrayList<Spil> s_list = l_sdao.getSpil(l_user.getU_id());
			session.setAttribute("spils", s_list); // 画像変更を呟きリストにも反映させるため

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		return "/mypage.jsp";
	}

}
