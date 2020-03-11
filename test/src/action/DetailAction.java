package action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import bean.Item;
import dao.ItemDAO;

public class DetailAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		String r_id = request.getParameter("itemNo");
		int l_id = Integer.parseInt(r_id);
		try {
			ItemDAO l_idao = new ItemDAO();
			Item l_item = l_idao.detail(l_id);
			if(l_item.getI_name() == null) {

				return "/error.jsp";

			}else {

				request.setAttribute("detail", l_item);
			}


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// TODO 自動生成されたメソッド・スタブ
		return "/detail.jsp";
	}

}
