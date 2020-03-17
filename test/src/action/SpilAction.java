package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Spil;
import bean.User;
import dao.SpilDAO;

public class SpilAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		// 呟いたユーザーのIDを取得
		HttpSession session = request.getSession();
		User l_user = (User) session.getAttribute("user");
		int l_id = l_user.getU_id();

		// 呟きを取得
		String l_spil = request.getParameter("tweet");
		if(l_spil.length() < 150) {

			request.setAttribute("message", "投稿できる文字数を超えています");
			return "/mypage.jsp";
		}

		// 呟きをインサート
		try {
			SpilDAO l_sdao = new SpilDAO();
			int rs = l_sdao.insertSpil(l_id, l_spil);
			// インサート失敗
			if(rs == 0) {

				return "/error.jsp";

			}else {// インサート成功

				request.setAttribute("insert","");
				SpilDAO ll_sdao = new SpilDAO();
				ArrayList<Spil> s_list = ll_sdao.getSpil(l_user.getU_id());
				session.setAttribute("spils", s_list); //ログインするユーザーの呟きを取得してセット
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return "/mypage.jsp";
	}

}
