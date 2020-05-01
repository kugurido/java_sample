package action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

public class ChangeMailAction extends Action{

	@Override
	public String execute(HttpServletRequest request) {

		String jsp = null;
		// aタグから飛んだ場合と再設定画面から飛んできた場合でif分岐
		String re = request.getParameter("re");
		if(re == null) {
			return "/changepass.jsp";
		}

		// 再設定画面の本人確認のタイミング
		if(re.equals("first")) {
			jsp = firstInput(request);
		}else if(re.equals("second")) {
			jsp = secondInput(request);
		}else {
			// 想定していないエラー
			return "/error.jsp";
		}

		return jsp;


	}



//=============================================================

	private String firstInput(HttpServletRequest request) {

		// 入力された値を取得
		String loginID = request.getParameter("loginID");
		String mail = request.getParameter("mail");
		// 入力された値が届いているかチェック
		if(loginID == null || loginID.equals("") || mail == null || mail.equals("")) {
			request.setAttribute("error", "");
			return "/changemail.jsp";
		}

		try {
			UserDAO udao = new UserDAO();
			User l_user = udao.checkUser(loginID, mail);
		// ユーザーがnull => 見つからなければエラー値を格納してreturn
			if(l_user == null) {
				request.setAttribute("error", "");
				return "/changemail.jsp";
			}
		// ユーザーが見つかった場合は、パスワードの再設定を行わせる
		// そのための合図をセットする
			request.setAttribute("firstcheck", "");
			HttpSession session = request.getSession();
			session.setAttribute("mailChangeUser", l_user);

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return "/changemail.jsp";
	}

//=============================================================

	private String secondInput(HttpServletRequest request) {

		String passA = request.getParameter("mail");
		String passB = request.getParameter("remail");
		String l_jsp = null;

		// 入力されてるかのチェック
		if(!passA.equals("") && !passB.equals("")) {

			//(入力はされている) => AとBの値が同じかチェック
			if(passA.equals(passB)) {
				// (AとBの値が同じ) => DBをアップデートするための準備
				HttpSession session = request.getSession();
				User l_user = (User) session.getAttribute("mailChangeUser");
				// 本人確認したユーザー情報を取得出来るかチェック
				if(l_user == null) {
					l_jsp = errorSetSecond(request,"notUser");
					return l_jsp;
				}
				// ユーザー情報がある（期待している挙動）=>DBの内容を変更する
				try {
					UserDAO udao = new UserDAO();
					int rs = udao.changeMail(l_user.getU_id(), passA);

					// アップデート出来ているかのチェック
					if(rs == 0) {
						l_jsp = errorSetSecond(request,"notUpdate");
						return l_jsp;
					}else {
						// アップデートできた => 理想とする挙動
						l_user.setU_pass(passA);
						session.setAttribute("user",l_user);
						session.removeAttribute("changeMailUser");
						request.setAttribute("chMail", "");
						l_jsp = "/endUpdate.jsp";
						return l_jsp;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			// ( AとBの値が同じじゃない )
			}else {
				l_jsp = errorSetSecond(request,"notEquals");
				return l_jsp;
			}
		// ( AないしBに未入力があった )
		}else {
			l_jsp = errorSetSecond(request,"required");
			return l_jsp;
		}
	// バグ避けに最後にもl_jspを戻すreturnを書いたけど、各部分でreturnさせてるので使われることはない
		return l_jsp;

	}

//================================================================

	private String errorSetSecond(HttpServletRequest request, String p_error) {

		String message = null;
		request.setAttribute("firstcheck", "");

		if(p_error.equals("notUser")) {
			message = "本人確認ができませんでした。もう一度IDとメールアドレスを入力してください";
			// 1段階の認証が出来ていない状態なので認証の合図であるチェックを外す
			request.removeAttribute("firstcheck");
		}
		if(p_error.equals("notUpdate")) {
			message = "アップデートに失敗しました。";
		}
		if(p_error.equals("notEquals")) {
			message = "パスワードを二回入力してください";
		}
		if(p_error.equals("required")) {
			message = "未入力があります";
		}

		request.setAttribute("error", "");
		request.setAttribute("eMessage", message);
		return "/changemail.jsp";

	}


}
