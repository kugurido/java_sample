package action;

import javax.servlet.http.HttpServletRequest;

public class TopAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		return "/top.jsp";

	}

}
