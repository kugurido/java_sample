package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;

/**
 * Servlet implementation class Control
 */
@WebServlet("/control")
public class Control extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public Control() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 変数用意
		String actName = "";
		String actionClassName = "";
		Action action = null;
		String forwardJSP = "";
		RequestDispatcher rd = null;
		context = this.getServletContext();


		request.setCharacterEncoding("UTF-8");

		try {
			// プロパティの絶対パスを取得する
			String realPath = this.context.getRealPath("/");
			String path = realPath + "/WEB-INF/action.properties";

			FileInputStream stream = new FileInputStream(path);
			Properties props = new Properties();
			props.load(stream);

			actName = request.getParameter("act");
			if(actName != null) {
				actionClassName = props.getProperty(actName);

			}else {
				actionClassName = props.getProperty("top");
			}

			Class<?> actionClass = Class.forName(actionClassName);
			action = (Action)actionClass.newInstance();
			forwardJSP = action.execute(request);

			if(forwardJSP != null && !forwardJSP.equals("")) {

				rd = context.getRequestDispatcher(forwardJSP);
			}else {
				rd = context.getRequestDispatcher("/top.jsp");
			}

		}catch(Exception e) {

			System.out.println("エラー");
		}

		rd.forward(request, response);



	}

}



