package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.User;
import dao.ItemDAO;
import dao.ListDAO;

public class PileListAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		String l_read = request.getParameter("pileRead");
		User l_user = (User) session.getAttribute("user");
		ArrayList<Item> list = new ArrayList<>();


		// 自分のセッションが切れてたらエラーで
		if(l_user == null) {

			return "/error.jsp";
		}
		// 完読ボタン押した
		if(l_read != null){
			try {
				// 本の情報を次にもっていく
				int r_id = Integer.parseInt(l_read);
				ItemDAO l_idao = new ItemDAO();
				Item l_item = l_idao.detail(r_id);
				request.setAttribute("item", l_item);
				// ジャンルを取得
				ItemDAO ll_idao = new ItemDAO();
				ArrayList<String> g_list = ll_idao.searchGenreAll();
				session.setAttribute("genre", g_list);
				return "/book_upload.jsp";

			} catch (SQLException e) {
				e.printStackTrace();
				return "/error.jsp";
			}
		}


		// マイページから【積読】ボタンを押した
		list = getPileList(l_user.getU_id());
		session.setAttribute("piles", list);

		return "/mypage.jsp";
	}



	public ArrayList<Item> getPileList(int p_id) {

		ListDAO l_ldao = new ListDAO();
		ArrayList<Item> l_list = l_ldao.searchPileAll(p_id);
		return l_list;

	}




}







