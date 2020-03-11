package action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

public class LoginAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		HttpSession session = request.getSession();
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

					request.setAttribute("error", "");
					return "/login.jsp";
				}else {
					session.setAttribute("user", l_user);
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
