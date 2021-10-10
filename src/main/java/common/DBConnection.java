package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * 数据库连接层MYSQL
 * @author Administrator
 *
 */
public class DBConnection {
  
  
 /**
  * 连接数据库
  * @return
  */
 public static Connection getDBConnection()
 {
  // 1. 注册驱动
  try {
   Class.forName("org.sqlite.JDBC");
  } catch (ClassNotFoundException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  // 获取数据库的连接
  try {
   Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DbFile.configFilePath());
   return conn;
  } catch (SQLException e1) {
   e1.printStackTrace();
  }
  return null;
 }
  
}
