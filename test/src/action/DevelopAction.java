package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

public class DevelopAction  extends Action{

	@Override
	public String execute(HttpServletRequest request)  {

		ArrayList<User> l_list = new ArrayList<>();
		HttpSession session = request.getSession();
		//String test = "";

		try {
			UserDAO l_udao = new UserDAO();
			l_list = l_udao.test();
			request.setAttribute("test", l_list);


		} catch (SQLException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		return "/top.jsp";
	}

}
