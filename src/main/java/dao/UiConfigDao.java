package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.Db;

public class UiConfigDao {

	public static Map<String,String> selectConfig(){
		Map <String,String> map = new HashMap<String,String>();
		try {
			Db db = new Db();
			ResultSet rs = db.select("select * from t_config;");
			while(rs.next()) {
				String key = rs.getString("dic_key");
				String value = rs.getString("dic_value");
				map.put(key, value);
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Boolean delete() {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "delete from t_config";
			int success = db.update(sql);
			if(success > 0) {
				flag = true;
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static Boolean deleteByKey(String key) {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "delete from t_config where dic_key = '" + key + "'";
			int success = db.update(sql);
			if(success > 0) {
				flag = true;
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static Boolean updateByKey(String key,String value) {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "update t_config set dic_value = '" + value +"' where dic_key ='" + key + "'";
			int success = db.update(sql);
			if(success > 0) {
				flag = true;
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static Boolean insert(String key,String value) {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "insert into t_config(dic_key,dic_value) values ('"+key+"','"+value+"')";
			int success = db.update(sql);
			if(success > 0) {
				flag = true;
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
