package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.Db;

public class PointDao {

	public static String findPoint(String mac) {
		String result = "";
		try {
			Db db = new Db();
			ResultSet rs = db.select("select * from t_miner_point where mac = '" + mac +"'");
			while(rs.next()) {
				result = rs.getString("point_code");
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
