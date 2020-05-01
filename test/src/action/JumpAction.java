package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.ItemDAO;

public class JumpAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {
		// aタグで各リンクにジャンプする際の共通アクションにしよう
		String jsp = null;
		String j_name = request.getParameter("link");
		jsp = ifLink(j_name,request);
		return jsp;
	}

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public String ifLink(String p_link,HttpServletRequest request) {

		String l_jsp = null;
		if(p_link.equals("index")) {
			l_jsp = "/index.jsp";
		}
//--------------------------------------------------------------------------
		if(p_link.equals("uploadpage")) {

			HttpSession session = request.getSession(false);
			if(session == null) {
				return  "/error.jsp";
			}
			try {
				// ジャンルを取得
				ItemDAO ll_idao;
				ll_idao = new ItemDAO();
				ArrayList<String> g_list = ll_idao.searchGenreAll();
				session.setAttribute("genre", g_list);
				l_jsp = "/book_upload.jsp";

			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
//--------------------------------------------------------------------------
		if(p_link.equals("config")) {
			// リンク先で使うであろうデータが格納されているかのチェック
			HttpSession session = request.getSession(false);
			if(session == null) {
				return  "/error.jsp";
			}
			User l_user = (User) session.getAttribute("user");
			if(l_user == null) {
				return  "/error.jsp";
			}

			l_jsp = "/config.jsp";
		}

//--------------------------------------------------------------------------
		if(p_link.equals("changePass")) {

			l_jsp = "/changepass.jsp";
		}
//--------------------------------------------------------------------------
		if(p_link.equals("changeMail")) {

			l_jsp = "/changemail.jsp";
		}

		return l_jsp;
	}

}
