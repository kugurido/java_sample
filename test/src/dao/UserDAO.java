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
	private PreparedStatement ps3;
	private PreparedStatement ps4;


	public UserDAO() throws SQLException, ClassNotFoundException{
		String url = "jdbc:mysql://localhost:3306/wataroom?characterEncoding=UTF-8&serverTimezone=JST";
		String user = "root";
		String password = "root";
		//上のメニューにある実行=>構成から外部jarを選択してDBでやったときに選んだjarを入れる（入れておかないと↓の部分でエラーが出る）
		Class.forName("com.mysql.jdbc.Driver");

		connection = DriverManager.getConnection(url, user, password);

		String sql = "SELECT * FROM user;";
		ps = connection.prepareStatement(sql);
		String sql2 = "SELECT * FROM user WHERE login_id=? and password=?;";
		ps2 = connection.prepareStatement(sql2);
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

		close(ps2);
		return l_user;

	}


//=======================================================================================

	// 【ID検索】
	public User searchID(int p_id) {

		User l_user = new User();
		ResultSet rs = null;
		try {
			String sql3 = "SELECT * FROM user WHERE user_id=?;";
			ps3 = connection.prepareStatement(sql3);
			ps3.setInt(1, p_id);
			rs = ps3.executeQuery();
			if(rs.next()) {
				l_user = create(rs);
			}

		}catch(SQLException e) {

			e.printStackTrace();
		}

		close(ps3);
		return l_user;
	}


//=======================================================================================

	// 【ユーザー名検索】
	public User searchName(String p_name) {

		User l_user = null;
		ResultSet rs = null;

		try {
			String sql3 = "SELECT * FROM user WHERE user_name=?";
			ps3 = connection.prepareStatement(sql3);
			ps3.setString(1, p_name);
			rs = ps3.executeQuery();
			if(rs.next()) {
				l_user = create(rs);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps3);
		return l_user;
	}

//=======================================================================================

	// 【本人確認】
	public User checkUser(String p_login, String p_mail ) {
		User l_user = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM user WHERE login_id=? and mail=?";
			ps3 = connection.prepareStatement(sql);
			ps3.setString(1, p_login);
			ps3.setString(2, p_mail);
			rs = ps3.executeQuery();
			if(rs.next()) {
				l_user = create(rs);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		close(ps3);
		return l_user;
	}


//=======================================================================================

	// 【パスワード再設定】
	public int changePassword(int p_id , String p_pass) {

		int rs = 0;

		try {
			String sql3 = "UPDATE user SET password=? WHERE user_id=?";
			ps3 = connection.prepareStatement(sql3);
			ps3.setString(1, p_pass);
			ps3.setInt(2, p_id);
			rs = ps3.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return rs;
	}
//=======================================================================================

	public int changeMail(int p_id , String p_mail) {

		int rs = 0;

		try {
			String sql3 = "UPDATE user SET mail=? WHERE user_id=?";
			ps3 = connection.prepareStatement(sql3);
			ps3.setString(1, p_mail);
			ps3.setInt(2, p_id);
			rs = ps3.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return rs;
	}



//=======================================================================================

	// 【ログインID重複チェック】
	public User searchLoginID(String p_id) {

		User l_user = null;
		ResultSet rs = null;

		try {
			String sql3 = "SELECT * FROM user WHERE login_id=?";
			ps3 = connection.prepareStatement(sql3);
			ps3.setString(1, p_id);
			rs = ps3.executeQuery();
			if(rs.next()) {
				l_user = create(rs);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps3);
		return l_user;

	}

//=======================================================================================

	//【ユーザー登録】
	public int insertUser(User p_user) {

		int l_result = 0;

		try {
			String sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?);";
			ps3 = connection.prepareStatement(sql);

			int id = newID();
			if(id == 0) {
				return l_result;
			}
			ps3.setInt(1, id);
			ps3.setString(2, p_user.getU_name());
			ps3.setString(3,p_user.getU_kana());
			ps3.setString(4,p_user.getU_mail());
			ps3.setString(5,"img/guest.png");
			ps3.setString(6,p_user.getU_login());
			ps3.setString(7,p_user.getU_pass());
			ps3.setString(8, p_user.getU_pr());

			l_result = ps3.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return l_result;



	}

//=======================================================================================

	// 【新規ID発行】
	public int newID() {
		int l_id = 0;
		ResultSet rs = null;
		try {
			String sql = "SELECT MAX(user_id) as maxid FROM user";
			ps4 = connection.prepareStatement(sql);
			rs = ps4.executeQuery();
			if(rs.next()) {
				l_id = rs.getInt("maxid");
				l_id++;
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return l_id;
	}

//=======================================================================================

	// 【ユーザー全件検索】
	public ArrayList<User> searchUserAll() {

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

		close(ps);
		return list;
	}

//================================================================

	// コンフィグで設定したパラメータに変更する
	public int updateConfig(User p_user) {
		String ou = null;
		int rs = 0;
		try {
			String sql = "UPDATE user SET user_name=?,user_kana=?,user_img=?,pr=? WHERE user_id=?";
			ps3 = connection.prepareStatement(sql);
			ps3.setString(1, p_user.getU_name());
			ps3.setString(2, p_user.getU_kana());
			ps3.setString(3, p_user.getU_img());
			ps3.setString(4, p_user.getU_pr());
			ps3.setInt(5, p_user.getU_id());
			rs = ps3.executeUpdate();
			if(rs != 0) {
				rs = p_user.getU_id();
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		close(ps3);
		return rs;
	}




//================================================================

	public User create(ResultSet p_result) throws SQLException {

		User l_user = new User();
		l_user.setU_id(p_result.getInt("user_id"));
		l_user.setU_name(p_result.getString("user_name"));
		l_user.setU_kana(p_result.getString("user_kana"));
		l_user.setU_mail(p_result.getString("mail"));
		l_user.setU_img(p_result.getString("user_img"));
		l_user.setU_login(p_result.getString("login_id"));
		l_user.setU_pass(p_result.getString("password"));
		l_user.setU_pr(p_result.getString("pr"));
		return l_user;
	}

//================================================================

	public void close(PreparedStatement p_ps) {
		try {
			if(p_ps != null) {p_ps.close();}
			if(connection != null) {connection.close();}
		}catch(SQLException e) {

			e.printStackTrace();
		}
	}


}


