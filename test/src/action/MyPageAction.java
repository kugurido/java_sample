package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Spil;
import bean.User;
import dao.SpilDAO;

public class MyPageAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if(session == null) {
			// セッション切れ
			return "/login.jsp";
		}

		User user = (User) session.getAttribute("user");
		if(user == null) {
			// ユーザー情報がセットされていない
			return "/login.jsp";
		}

		ArrayList<Spil> s_list = (ArrayList<Spil>) session.getAttribute("spils");
		if(s_list == null) {
			// 呟きをセットする
			try {
				SpilDAO l_sdao;
				l_sdao = new SpilDAO();
				s_list = l_sdao.getSpil(user.getU_id());
				session.setAttribute("spils", s_list);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return "/mypage.jsp";
	}

}
