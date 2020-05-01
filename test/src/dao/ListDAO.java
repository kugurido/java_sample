package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Item;
import bean.Revue;
import bean.User;

public class ListDAO {

	private Connection connection;
	private PreparedStatement ps;
	private PreparedStatement ps2;

	public ListDAO() {
		try {
			String url = "jdbc:mysql://localhost:3306/wataroom?characterEncoding=UTF-8&serverTimezone=JST";
			String user = "root";
			String password = "root";
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

//=========================================================================================

	//【積読全件検索】
	public ArrayList<Item> searchPileAll(int p_id){

		ArrayList<Item> list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM pileup WHERE user_id=? ORDER BY pile_day DESC";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int l_id = rs.getInt("item_id");
				Item item = new Item();
				ItemDAO idao = new ItemDAO();
				item = idao.detail(l_id);
				list.add(item);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		close(ps);
		return list;
	}

//================================================================================
	//【積読登録】
	public int insertPile(int p_user,int p_item) {

		int rs = 0;
		try {
			String sql = "INSERT INTO pileup VALUES(?,?,cast(now() as date))";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_user);
			ps.setInt(2, p_item);
			rs = ps.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}



	//================================================================================
	//【積読解除】
	public int deletePile(int p_user, int p_item) {

		int rs = 0;

		try {
			String sql = "DELETE FROM pileup WHERE user_id=? and item_id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_user);
			ps.setInt(2, p_item);
			rs = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return rs;
	}


//================================================================================

	// 【完読に追加】
	public int insertReadList(int p_user, int p_item, String p_revue) {

		int rs = 0;
		try {
			String sql = "INSERT INTO readitem VALUES(?,?,?,?,cast(now() as date))";
			ps = connection.prepareStatement(sql);

			int id = newReadID();
			if(id == 0) {
				return rs;
			}
			ps.setInt(1, id);
			ps.setInt(2, p_user);
			ps.setInt(3, p_item);
			ps.setString(4, p_revue);

			rs = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return rs;

	}

//================================================================================
	//【完読解除】
	public int deleteRead(int p_user, int p_item) {

		int rs = 0;

		try {
			String sql = "DELETE FROM readitem WHERE user_id=? and item_id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_user);
			ps.setInt(2, p_item);
			rs = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return rs;
	}


//================================================================================

	// 【完読DBの新規ID発行】
	public int newReadID() {
		int l_id = 0;
		ResultSet rs = null;
		try {
			String sql = "SELECT MAX(read_id) as maxid FROM readitem";
			ps2 = connection.prepareStatement(sql);
			rs = ps2.executeQuery();
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

//================================================================================

	// 【1アイテムに対する総レビュー】
	public ArrayList<Revue> searchRevueForItem(int p_id){
		ArrayList<Revue> r_list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM readitem WHERE item_id=? ORDER BY read_time DESC";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Revue revue = new Revue();

				int l_id = rs.getInt("user_id");
				User user = new User();
				UserDAO udao = new UserDAO();
				user = udao.searchID(l_id);

				Item item = new Item();
				ItemDAO idao = new ItemDAO();
				item = idao.detail(p_id);

				revue = createRevueParts(user,item);
				revue.setR_id(rs.getInt("read_id"));
				revue.setRevue(rs.getString("revue"));
				r_list.add(revue);

			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return r_list;
	}

//================================================================================

	//【1ユーザーが行なった総レビュー】
	public ArrayList<Revue> searchRevueForUser(int p_id){
		ArrayList<Revue> r_list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM readitem WHERE user_id=? ORDER BY read_time DESC";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, p_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Revue revue = new Revue();

				int l_id = rs.getInt("item_id");
				Item item = new Item();
				ItemDAO idao = new ItemDAO();
				item = idao.detail(l_id);

				User user = new User();
				UserDAO udao = new UserDAO();
				user = udao.searchID(p_id);

				revue = createRevueParts(user,item);
				revue.setR_id(rs.getInt("read_id"));
				revue.setRevue(rs.getString("revue"));
				r_list.add(revue);

			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		close(ps);
		return r_list;
	}




//================================================================================

	// 【レビューの一部分を作る】メソッド
	public Revue createRevueParts(User p_user,Item p_item) {
		Revue revue = new Revue();
		revue.setU_id(p_user.getU_id());
		revue.setU_name(p_user.getU_name());
		revue.setU_img(p_user.getU_img());
		revue.setI_id(p_item.getI_id());
		revue.setI_name(p_item.getI_name());
		revue.setI_img(p_item.getI_img());

		return revue;
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
