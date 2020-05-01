package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import bean.Item;
import bean.Revue;
import bean.Spil;
import bean.User;
import dao.ListDAO;
import dao.SpilDAO;
import dao.UserDAO;

public class UserDetailAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {


		String idString = request.getParameter("userID");
		int u_id = Integer.parseInt(idString);

		// ユーザー情報を取得
		try {
			UserDAO udao = new UserDAO();
			User l_user = udao.searchID(u_id);
			if(l_user == null) {
				return "/error.jsp";
			}

			// 各種データ取得・格納
			ArrayList<Spil> spils = getSpil(l_user);
			request.setAttribute("rSpil", spils);
			ArrayList<Revue> revue = getRevue(l_user);
			request.setAttribute("rRevue", revue);
			ArrayList<Item> pile = getPile(l_user);
			request.setAttribute("rPile", pile);
			request.setAttribute("rUser", l_user);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return "/detailUser.jsp";
	}



	public ArrayList<Spil> getSpil(User p_user) throws SQLException {

		ArrayList<Spil> spils = new ArrayList<>();
		SpilDAO sdao = new SpilDAO();
		spils = sdao.getSpil(p_user.getU_id());
		return spils;
	}


	public ArrayList<Revue> getRevue(User p_user){

		ArrayList<Revue> revue = new ArrayList<>();
		ListDAO ldao = new ListDAO();
		revue = ldao.searchRevueForUser(p_user.getU_id());
		return revue;

	}

	public ArrayList<Item> getPile(User p_user){

		ArrayList<Item> pile = new ArrayList<>();
		ListDAO ldao = new ListDAO();
		pile = ldao.searchPileAll(p_user.getU_id());
		return pile;
	}

}
