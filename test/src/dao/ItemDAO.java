package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Item;

public class ItemDAO {

	private Connection connection;
	private PreparedStatement ps;
	private PreparedStatement ps2;
	private PreparedStatement ps3;
	private PreparedStatement ps4;

	public ItemDAO() throws SQLException {

		String url = "jdbc:mysql://localhost:3306/wataroom?characterEncoding=UTF-8&serverTimezone=JST";
		String user = "root";
		String password = "root";
		connection = DriverManager.getConnection(url, user, password);

		String sql = "SELECT * FROM item;";
		ps = connection.prepareStatement(sql);
		String sql2 = "SELECT * FROM item WHERE item_id=?;";
		ps2 = connection.prepareStatement(sql2);
	}




//====================================================================================
	// 【全件検索】
	public ArrayList<Item> searchAllItem(){

		ArrayList<Item>l_list = new ArrayList<>();
		ResultSet l_rs = null;
		try {
			l_rs = ps.executeQuery();
			while(l_rs.next()) {

				Item item = new Item();
				item = create(l_rs);
				l_list.add(item);

			}


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		closePS(ps);
		close();
		return l_list;

	}

//====================================================================================

	public Item detail(int p_id) {
		Item l_item = new Item();
		ResultSet l_rs = null;

		try {
			ps2.setInt(1, p_id);
			l_rs = ps2.executeQuery();
			if(l_rs.next()) {
				l_item = create(l_rs);

			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		closePS(ps2);
		close();
		return l_item;


	}

//====================================================================================

	// 【登録済ItemのIDを取得するためのメソッド】
	public Item searchNameAndAuthor(String p_name,String p_author) {

		Item l_item = new Item();
		try {
			String sql = "SELECT * FROM item WHERE item_name=? and author=?";
			ps3 = connection.prepareStatement(sql);
			ps3.setString(1, p_name);
			ps3.setString(2, p_author);
			ResultSet rs = ps3.executeQuery();
			if(rs.next()) {

				l_item = create(rs);
			}else {

				l_item = null;
			}

		}catch(SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		closePS(ps3);
		close();
		return l_item;
	}

//====================================================================================

	//【ジャンル全件抽出】
	public ArrayList<String> searchGenreAll(){

		ArrayList<String> list = new ArrayList<>();

		try {
			String sql = "SELECT DISTINCT genre from item";
			ps3 = connection.prepareStatement(sql);
			ResultSet rs = ps3.executeQuery();
			while(rs.next()) {

				String str = rs.getString("genre");
				list.add(str);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		closePS(ps3);
		close();
		return list;

	}

//====================================================================================

	//【データベース登録】
	public int insertItem(Item p_item) {

		int l_result = 0;
		int id = 0;
		try {
			String sql = "INSERT INTO item VALUES(?,?,?,?,?,?)";
			ps3 = connection.prepareStatement(sql);

			id = newID();
			if(id == 0) {
				return l_result;
			}
			ps3.setInt(1, id);
			ps3.setString(2, p_item.getI_name());
			ps3.setString(3,p_item.getI_img());
			ps3.setString(4,p_item.getI_genre());
			ps3.setString(5,p_item.getI_publisher());
			ps3.setString(6,p_item.getI_author());
			l_result = ps3.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		closePS(ps3);
		close();

		if(l_result == 0) {
			return l_result;
		}else {
			return id;
		}

	}
//====================================================================================

	// 【新規ID発行】
	public int newID() {
		int l_id = 0;
		ResultSet rs = null;
		try {
			String sql = "SELECT MAX(item_id) as maxid FROM item";
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


//====================================================================================
	public Item create(ResultSet p_rs) throws SQLException {

		Item item = new Item();
		item.setI_id(p_rs.getInt("item_id"));
		item.setI_name(p_rs.getString("item_name"));
		item.setI_img(p_rs.getString("item_img"));
		item.setI_genre(p_rs.getString("genre"));
		item.setI_publisher(p_rs.getString("publisher"));
		item.setI_author(p_rs.getString("author"));

		return item;
	}

//====================================================================================

	public void close()  {

		if(connection != null) {

			try {
				connection.close();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	public void closePS(PreparedStatement p_ps) {

		if(p_ps != null) {

			try {
				p_ps.close();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}








