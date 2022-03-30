package common;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;

import dao.IpSettingDao;

public class DbFile {
	
	private final static String fileName = "miner.db";

	public static String getJarPath() {
		return System.getProperty("user.dir");
	}
	
	public static String configFilePath() {
		return getJarPath()+"\\"+fileName;
	}
	
	public static void initDbFile() {
		String dbFileName = configFilePath();
		File file=new File(dbFileName);
		if(!file.exists()) {
			try {
				if(file.createNewFile()) {
					//初始化数据库表
					initData();//建表;初始化数据
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void initData() {
		Db db = new Db();
		String sql1 = "CREATE TABLE t_ips " 
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " ips_index int unique NOT NULL,"
                + " ips_name CHAR(50) NOT NULL," 
                + " ips_str CHAR(50) NOT NULL)";
		
		String sql2 = "CREATE TABLE t_miner_point " 
				+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " point_x INT NOT NULL,"
                + " point_y INT NOT NULL,"
                + " point_z INT NOT NULL,"
                + " shelf_num INT NOT NULL,"
                + " mac CHAR(50) unique NOT NULL," 
                + " create_at CHAR(50) NOT NULL,"
                + " update_at CHAR(50) NOT NULL,"
                + " point_code CHAR(50) NOT NULL,"
                + " extends_json text"
                + " )";
		
		String sql3 = "CREATE TABLE t_miner_config " 
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " miner_code CHAR(50) unique NOT NULL," 
                + " miner_info text"
                + ")";
		
		String sql4 = "CREATE TABLE t_config " 
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " dic_key CHAR(50) unique NOT NULL," 
                + " dic_value CHAR(50) NOT NULL"
                + ")";
		
		String sql5 = "CREATE TABLE t_point_log " 
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " create_at CHAR(50) NOT NULL," 
                + " mac CHAR(50) unique NOT NULL," 
                + " point_code CHAR(50) NOT NULL,"
                + " curr_ip CHAR(50) NOT NULL," 
                + " detail CHAR(50) NOT NULL"
                + ")";
		
		StringBuffer sb1 = new StringBuffer();
	    sb1.append("INSERT INTO t_ips (ips_index,ips_name,ips_str) "
	               + "VALUES (0,'LAN1','192.168.0.1-255' );\n");
	    String insert_sql1 = sb1.toString(); 
	    
	    StringBuffer sb2 = new StringBuffer();
	    sb2.append("INSERT INTO t_config (dic_key,dic_value) "
	               + "VALUES ('SCAN_TIMEOUT','"+AppSettingConfig.SCAN_TIMEOUT+"' );\n");
	    sb2.append("INSERT INTO t_config (dic_key,dic_value) "
	               + "VALUES ('MONITOR_TIME','"+AppSettingConfig.MONITOR_TIME+"' );\n");
	    sb2.append("INSERT INTO t_config (dic_key,dic_value) "
	               + "VALUES ('LANG','zh_CN' );\n");
	    String insert_sql2 = sb2.toString(); 
	    
	    StringBuffer sb3 = new StringBuffer();
	    sb3.append("INSERT INTO t_miner_config (miner_code,miner_info) "
	               + "VALUES ('F5','"+JSON.toJSONString(AppSettingConfig.initMinerF5())+"' );\n");
	    sb3.append("INSERT INTO t_miner_config (miner_code,miner_info) "
	               + "VALUES ('F5+','"+JSON.toJSONString(AppSettingConfig.initMinerF5i())+"' );\n");
	    String insert_sql3 = sb3.toString(); 
		try {
			//创建表
			db.create(sql1);
			db.create(sql2);
			db.create(sql3);
			db.create(sql4);
			db.create(sql5);
			//插入数据
			db.insert(insert_sql1);
			db.insert(insert_sql2);
			db.insert(insert_sql3);
			db.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		//initDbFile();
		System.out.println(IpSettingDao.getIpsList());
	}
}
