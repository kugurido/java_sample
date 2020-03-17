package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession();

		request.setAttribute("message", "ログアウトしました");
		session.invalidate();

		return "/top.jsp";
	}

}
