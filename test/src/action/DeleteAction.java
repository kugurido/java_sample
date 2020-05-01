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

public class DeleteAction extends Action {



	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String jsp = null;
		String dName = request.getParameter("dName");
		String deleteID = request.getParameter("dID");
		int dID = Integer.parseInt(deleteID);
		// 上記のパラメータの値からそれぞれのDeleteメソッドを起動させる
		if(dName.equals("spil")) {
			jsp = deleteSpil(dID,request,session);
		}
		if(dName.equals("pile")) {
			jsp = deletePile(dID,request,session);
		}
		if(dName.equals("read")) {
			jsp = deleteRead(dID,request,session);
		}

		return jsp;
	}

//===============================================================================================
	public String deleteSpil(int p_id, HttpServletRequest request, HttpSession session) {

		try {
			SpilDAO l_sdao = new SpilDAO();
			int rs = l_sdao.deleteSpil(p_id);

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


	public String deletePile(int p_id, HttpServletRequest request, HttpSession session) {

		ListDAO ldao = new ListDAO();
		User l_user = (User) session.getAttribute("user");
		if(l_user == null) {
			return "/error.jsp";
		}
		int rs = ldao.deletePile(l_user.getU_id(), p_id);
		if(rs == 0) {
			return "/error.jsp";
		}
		// 新しいPileListを取得
		ArrayList<Item> list = new ArrayList<>();
		ldao = new ListDAO();
		list = ldao.searchPileAll(l_user.getU_id());
		session.setAttribute("piles", list);
		request.setAttribute("deleteOK", "");
		return "/mypage.jsp";
	}


	public String deleteRead(int p_id, HttpServletRequest request, HttpSession session) {

		ListDAO ldao = new ListDAO();
		User l_user = (User) session.getAttribute("user");
		if(l_user == null) {
			return "/error.jsp";
		}
		int rs = ldao.deleteRead(l_user.getU_id(), p_id);
		if(rs == 0) {
			return "/error.jsp";
		}
		// 完読情報セット
		ArrayList<Revue> revue = new ArrayList<>();
		ListDAO lldao = new ListDAO();
		revue = lldao.searchRevueForUser(l_user.getU_id());
		session.setAttribute("read", revue);

		return "/mypage.jsp";
	}

}
