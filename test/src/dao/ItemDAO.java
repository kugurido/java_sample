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








