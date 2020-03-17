package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Spil;
import bean.User;

public class SpilDAO {

	private Connection connection;
	private PreparedStatement ps;
	private PreparedStatement ps2;

	public SpilDAO() throws SQLException {

		String url = "jdbc:mysql://localhost:3306/wataroom?characterEncoding=UTF-8&serverTimezone=JST";
		String user = "root";
		String password = "root";
		connection = DriverManager.getConnection(url, user, password);
	}



//=========================================================================

	// 【呟き登録】
	public int insertSpil(int p_id, String p_spil) {
		int l_result = 0;


		try {
			String sql = "INSERT INTO tweet(user_id,tweet_day,tweets) VALUES(?,cast(now() as datetime),?);";
			ps = connection.prepareStatement(sql);

			ps.setInt(1, p_id);
			ps.setString(2, p_spil);

			l_result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return l_result;
	}


//================================================================================

	// 【ユーザーの呟き取得】
	public ArrayList<Spil> getSpil(int p_id) {

		ResultSet rs = null;
		ArrayList<Spil> list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM tweet WHERE user_id=? ORDER BY tweet_id DESC;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Spil l_spil = new Spil();
				l_spil = createSpil(rs);
				list.add(l_spil);

			}


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return list;

	}

//================================================================================

		//【呟き削除】
		public int deleteSpil(int p_id) {

			int l_result=0;

			try {
				String sql = "DELETE FROM tweet WHERE tweet_id=?";
				ps = connection.prepareStatement(sql);
				ps.setInt(1, p_id);
				l_result = ps.executeUpdate();

			}catch(SQLException e) {

				e.printStackTrace();
			}

			close(ps);
			return l_result;
		}

//================================================================================
//================================================================================
//================================================================================


//================================================================================

	// 【呟きオブジェクト作成】
	public Spil createSpil(ResultSet p_rs) {

		Spil spil = new Spil();
		try {
			spil.setSpil_id(p_rs.getInt("tweet_id"));
			spil.setUser_id(p_rs.getInt("user_id"));
			spil.setSpil_time(p_rs.getString("tweet_day"));
			spil.setSpil(p_rs.getString("tweets"));

	// ユーザーの画像URLはユーザーbeanから参照する必要があるのでユーザーID検索をして取得したユーザーオブジェクトから拝借する
			UserDAO l_udao = new UserDAO();
			User l_user = l_udao.searchID(p_rs.getInt("user_id"));
			spil.setUser_img(l_user.getU_img());
		}catch(SQLException e) {
			e.printStackTrace();

		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		return spil;
	}

//================================================================================

	public void close(PreparedStatement p_ps) {
		try {
			if(p_ps != null) {p_ps.close();}
			if(connection != null) {connection.close();}
		}catch(SQLException e) {

			e.printStackTrace();
		}
	}
}
