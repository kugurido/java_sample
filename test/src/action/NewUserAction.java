package action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

public class NewUserAction extends Action {

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String l_insert = request.getParameter("insert");

	// 【登録】ボタンを押していない=>リンクから来た
		if(l_insert == null) {
			return "/new_user.jsp";
		}

	// 【登録】ボタンを押している場合の処理
		User l_user = new User();
		l_user.setU_name(request.getParameter("userName"));
		l_user.setU_kana(request.getParameter("userKana"));
		l_user.setU_mail(request.getParameter("eMail"));
		l_user.setU_login(request.getParameter("loginID"));
		l_user.setU_pass(request.getParameter("loginPassword"));

	// 未記入がないかチェック（JSP側でもチェックしてるけど一応）
		if(!checkNull(l_user)) {
			request.setAttribute("error", "未記入の項目があります");
			return "/new_user.jsp";
		}
	// ユーザー名やログインIDに重複がないかチェック
		if(!checkName(l_user.getU_name())) {
			request.setAttribute("error", "その名前は既に登録されています");
			return "/new_user.jsp";
		}
		if(!checkLoginID(l_user.getU_login())){
			request.setAttribute("error", "そのIDは既に登録されています");
			return "/new_user.jsp";
		}

	// 重複はない==>インサート作業
		try {
			UserDAO udao = new UserDAO();
			int rs = udao.insertUser(l_user);
			if(rs == 0) {
				// インサート失敗
				return "/error.jsp";
			}
			UserDAO l_udao = new UserDAO();
			l_user = l_udao.searchLoginID(l_user.getU_login());
			session.setAttribute("user", l_user);

		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}

		return "/insert_user.jsp";
	}

//=======================================================================================

	public boolean checkName(String p_name) {

		boolean bool = true;
		try {
			UserDAO udao = new UserDAO();
			User user = udao.searchName(p_name);
			if(user != null) { // nullではない =>既に入力したユーザー名は使われている
				bool = false;
				return bool;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return bool;
	}

//=======================================================================================

	public boolean checkLoginID(String p_loginID) {

		boolean bool = true;
		try {
			UserDAO udao = new UserDAO();
			User user = udao.searchLoginID(p_loginID);
			if(user != null) { // nullではない =>既に入力したユーザー名は使われている
				bool = false;
				return bool;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return bool;
	}

//=======================================================================================

	public boolean checkNull(User p_user) {

		 boolean bool = true;

		 if(p_user.getU_name().equals("") || p_user.getU_name() == null) {
			 bool = false;
			 return bool;
		 }
		 if(p_user.getU_kana().equals("") || p_user.getU_kana() == null) {
			 bool = false;
			 return bool;
		 }
		 if(p_user.getU_mail().equals("") || p_user.getU_mail() == null) {
			 bool = false;
			 return bool;
		 }
		 if(p_user.getU_login().equals("") || p_user.getU_login() == null) {
			 bool = false;
			 return bool;
		 }
		 if(p_user.getU_pass().equals("") || p_user.getU_pass() == null) {
			 bool = false;
			 return bool;
		 }

		 return bool;

	}

}
