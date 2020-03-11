package action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Item;
import dao.ItemDAO;

public class ListAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession();

		try {
			ItemDAO l_idao = new ItemDAO();
			ArrayList<Item> l_list = l_idao.searchAllItem();
			if(l_list.size()==0) {
				request.setAttribute("error", "");
			}

			session.setAttribute("list", l_list);

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		return "/itemlist.jsp";
	}

}
