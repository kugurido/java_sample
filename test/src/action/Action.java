package action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class Action extends HttpServlet{

	public abstract String execute(HttpServletRequest request);

}
