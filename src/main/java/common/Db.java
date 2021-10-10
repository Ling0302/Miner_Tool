package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	
	 private Statement stmt;
	 private Connection conn;
	 private ResultSet rs;	  
	 
	 /**
	  * 打开数据库
	  */
	 public Db() {	  
		try {
			conn = DBConnection.getDBConnection();
			stmt = conn.createStatement();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	 }
	 
	 public Db(int start) {
		 conn = DBConnection.getDBConnection();
	 }	 	 

	/**
	  * 关闭数据库
	  * @throws SQLException
	  */
	 public void closeDB() throws SQLException
	 {
	  if(rs != null)
	  {
	   rs.close();
	  }
	  if(stmt != null)
	  {
	   stmt.close();
	  }
	  if(conn != null)
	  {
	   conn.close();
	  }
	   
	 }
	 
	 /**
	  * 新建表
	 * @param sql
	 * @return
	 */
	public int create(String sql) {
		int isOk = 0;
		try {
			isOk = stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//closeDB();
			}catch(Exception e) {
				e.printStackTrace();
			}			
		}
		return isOk;
	}
	 
	/**
	 * 新增
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int insert(String sql) {
		int isOk = 0;
		try {
			isOk = stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//closeDB();
			}catch(Exception e) {
				e.printStackTrace();
			}			
		}
		return isOk;
	}
	
	/**
	 * 删除
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int delete(String sql) {
		int isOk = 0;
		try {
			isOk = stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//closeDB();
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		return isOk;
	}
	
	/**
	 * 修改
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int update(String sql){ 
		int isOk = 0;
		try {
			isOk = stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//closeDB();
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		return isOk;
	}
	
	 /**
	  * 查询
	 * @param sql
	 * @throws SQLException
	 */
	public ResultSet select(String sql) { 		
		try {
			rs = stmt.executeQuery(sql);
			return rs;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//closeDB();
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}		
		return rs;
	}
	
}
