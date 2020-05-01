package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import action.Action;

/**
 * Servlet implementation class Control
 */
@WebServlet("/top")
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
		System.out.println("doGET起動");
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
			if(!ServletFileUpload.isMultipartContent(request)) {
				actName = request.getParameter("act");
			}
			if(actName != null && !actName.equals("")) {
				actionClassName = props.getProperty(actName);

			}else {
				// multのformで送られた場合はgetParameterがnullなのでmultで送信されているかチェック

				if(!ServletFileUpload.isMultipartContent(request)) {
					actionClassName = props.getProperty("top");

				}else {
					DiskFileItemFactory factory = new DiskFileItemFactory();
					// この2つを設定しないとjspからfile受け取れないっぽい
					factory.setSizeThreshold(1426);
					factory.setRepository(new File("C:\\temp\\file_up"));

					ServletFileUpload sfu = new ServletFileUpload(factory);
					List list = sfu.parseRequest(request);
					request.setAttribute("filelist",list);
					for(Object o : list) {
						//formのデータ1つ1つをFileItemとして扱う
						FileItem item = (FileItem) o;

						//FileItemが<type=file>かそれ以外のtypeかで分岐
						if(item.isFormField()) {
							//fileではない時にtrue判定
							// <input name="フィールドネーム"とvalue="入力内容">を取得
							String fieldName = item.getFieldName();
							String value = item.getString("UTF-8");

							//取得したnameの値がactなら共通処理に繋がるように処理
							if(fieldName.equals("act")) {
								actionClassName = props.getProperty(value);
							}
						}	// if文終わり.<input type="file">の時はここで処理する必要はないので省略
					}//for
				}
			}

			// 再び共通処理
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



