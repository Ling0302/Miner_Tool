package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Db;
import vo.IpsVO;

public class IpSettingDao {
	
	/**
	 * 读取IP列表配置
	 * @return
	 */
	public static List<IpsVO> getIpsList(){
		List<IpsVO> list = new ArrayList<IpsVO>();		
		try {
			Db db = new Db();
			ResultSet rs = db.select("select * from t_ips");
			while(rs.next()) {
				IpsVO ipsVO = new IpsVO();
				ipsVO.setIndex(String.valueOf(rs.getInt("ips_index")));
				ipsVO.setIpsName(rs.getString("ips_name"));
				ipsVO.setIpsStr(rs.getString("ips_str"));
				list.add(ipsVO);
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取下一个最大索引的IP
	 * @return
	 */
	public static int getNextIpIndex() {
		int result = 0;
		try {
			Db db = new Db();
			ResultSet rs = db.select("select max(ips_index) from t_ips");
			while(rs.next()) {				
				result = rs.getInt(1) + 1;				
			}
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	/**
	 * 编辑IP范围
	 * @param ipsVO
	 * @return
	 */
	public static Boolean updateIpStr(IpsVO ipsVO) {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "update t_ips set ips_name = '"+ipsVO.getIpsName()+"'"
					+ ",ips_str = '"+ipsVO.getIpsStr()
					+"' where ips_index = "+ipsVO.getIndex();
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
	
	/**
	 * 新增IP范围
	 * @param ipsVO
	 * @return
	 */
	public static Boolean insertIpStr(IpsVO ipsVO) {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "insert into t_ips(ips_index,ips_name,ips_str) values('"+ipsVO.getIndex()+"','"+ipsVO.getIpsName()+"','"+ipsVO.getIpsStr()+"')";
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
	
	/**
	 * 删除IP
	 * @param ipsVO
	 * @return
	 */
	public static Boolean deleteIpStr(int str_index) {
		Boolean flag = false;
		try {
			Db db = new Db();
			String sql = "delete from t_ips where ips_index = "+str_index;
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
