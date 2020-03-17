package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Spil;
import bean.User;
import dao.SpilDAO;

public class DeleteAction extends Action {

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String l_spilID = request.getParameter("spilID");
		int spilID = Integer.parseInt(l_spilID);
		try {
			SpilDAO l_sdao = new SpilDAO();
			int rs = l_sdao.deleteSpil(spilID);

			if(rs == 0) { // Delete失敗
				request.setAttribute("error", "");
				return "/mypage.jsp";

			}else { // Delete成功
				User l_user = (User) session.getAttribute("user");
				SpilDAO ll_sdao = new SpilDAO();
				ArrayList<Spil> s_list = ll_sdao.getSpil(l_user.getU_id());
				session.setAttribute("spils", s_list); //ログインするユーザーの呟きを取得してセット
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return "/mypage.jsp";
	}

}
