package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Revue;
import bean.Spil;
import bean.User;
import dao.ListDAO;
import dao.SpilDAO;
import dao.UserDAO;

public class LoginAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession();

		User s_user = (User) session.getAttribute("user");
		if(s_user != null) { // 既にログイン済みかチェック

			return "/mypage.jsp";
		}

		String l_loginID  = request.getParameter("loginID");
		String l_pass = request.getParameter("pass");

	// リクエストが届いているか（＝ログインページから来たか）を判定
		if(l_loginID==null || l_pass==null) {

			return "/login.jsp";
	// 入力欄が空欄の場合
		}else if(l_loginID.equals("") || l_pass.equals("")) {

			request.setAttribute("error", "");
			return "/login.jsp";
	// 入力欄に入力されている場合
		}else {

			try {
				UserDAO l_udao = new UserDAO();
				User l_user = l_udao.checkLogin(l_loginID, l_pass);
				// ユーザー情報が取得できなかった場合
				if(l_user == null) {

					request.setAttribute("error", ""); //JSP側でエラーメッセージのifチェック用
					return "/login.jsp";
				}else {
					// 呟き情報セット
					SpilDAO l_sdao = new SpilDAO();
					ArrayList<Spil> s_list = l_sdao.getSpil(l_user.getU_id());
					session.setAttribute("user", l_user);
					session.setAttribute("spils", s_list); //ログインするユーザーの呟きを取得してセット
					// 積読情報セット
					ArrayList<Item> list = new ArrayList<>();
					ListDAO ldao = new ListDAO();
					list = ldao.searchPileAll(l_user.getU_id());
					session.setAttribute("piles", list);
					// 完読情報セット
					ArrayList<Revue> revue = new ArrayList<>();
					ListDAO lldao = new ListDAO();
					revue = lldao.searchRevueForUser(l_user.getU_id());
					session.setAttribute("read", revue);

					request.setAttribute("message", "ログインしました"); //JSPのログイン完了メッセージ
					return "/mypage.jsp";
				}

			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return "/login.jsp";
	}

}
