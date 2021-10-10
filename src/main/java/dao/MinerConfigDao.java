package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import common.Db;
import vo.MinerParamVO;

public class MinerConfigDao {

	public static List<MinerParamVO> getMinerList(){
		List<MinerParamVO> list = new ArrayList<MinerParamVO>();
		try {
			Db db = new Db();
			ResultSet rs = db.select("select * from t_miner_config;");
			while(rs.next()) {
				MinerParamVO minerVO = JSON.parseObject(rs.getString("miner_info"), MinerParamVO.class);
				list.add(minerVO);
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static MinerParamVO getMinerByCode(String minerCode) {
		MinerParamVO minerVO = null;
		try {
			Db db = new Db();
			ResultSet rs = db.select("select * from t_miner_config where miner_code ='" + minerCode + "' limit 1");
			while(rs.next()) {
				minerVO = JSON.parseObject(rs.getString("miner_info"), MinerParamVO.class);
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return minerVO;
	}
	
	public static Boolean delete() {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "delete from t_miner_config";
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
	
	public static Boolean insert(MinerParamVO minerVO) {
		Boolean flag = false;
		try {
			Db db = new Db();
			StringBuffer sb = new StringBuffer();
		    sb.append("INSERT INTO t_miner_config (miner_code,miner_info) "
		               + "VALUES ('"+minerVO.getType()+"','"+JSON.toJSONString(minerVO)+"' );\n");
		    String sql = sb.toString();
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
