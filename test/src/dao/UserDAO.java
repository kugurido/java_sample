package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.User;

public class UserDAO {

	private Connection connection;
	private PreparedStatement ps;
	private PreparedStatement ps2;

	public UserDAO() throws SQLException, ClassNotFoundException{
		String url = "jdbc:mysql://localhost:3306/wataroom?characterEncoding=UTF-8&serverTimezone=JST";
		String user = "root";
		String password = "root";
		//実行=>構成から外部jarを選択してDBでやったときに選んだjarを入れる（入れておかないと↓の部分でエラーが出る）
		Class.forName("com.mysql.jdbc.Driver");

		connection = DriverManager.getConnection(url, user, password);

		String sql = "SELECT * FROM user;";
		ps = connection.prepareStatement(sql);
		String sql2 = "SELECT * FROM user WHERE login_id=? and passward=?;";
		ps2 = connection.prepareStatement(sql2);
	}

	public void close() {
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
		}
	}



//=======================================================================================

	// 【ログインフォーム用】
	public User checkLogin(String p_login,String p_pass) {

		ResultSet l_rs = null;
		User l_user = null;

		try {
			ps2.setString(1, p_login);
			ps2.setString(2, p_pass);
			l_rs = ps2.executeQuery();
			if(l_rs.next()) {

				l_user = create(l_rs);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close();
		return l_user;

	}




	// DB接続用に作った。後で消す
	public ArrayList<User> test() {

		ResultSet l_rs = null;
		ArrayList<User> list = new ArrayList<>();

		try {
			l_rs = ps.executeQuery();
			while(l_rs.next()) {
				User l_user = new User();
				l_user = create(l_rs);
				list.add(l_user);
			}


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close();
		return list;
	}


//================================================================

	public User create(ResultSet p_result) throws SQLException {

		User l_user = new User();
		l_user.setU_id(p_result.getInt("user_id"));
		l_user.setU_name(p_result.getString("user_name"));
		l_user.setU_kana(p_result.getString("user_kana"));
		l_user.setU_mail(p_result.getString("mail"));
		l_user.setU_login(p_result.getString("login_id"));
		l_user.setU_pass(p_result.getString("passward"));
		return l_user;
	}




}


