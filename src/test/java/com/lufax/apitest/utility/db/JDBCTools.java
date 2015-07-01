package com.lufax.apitest.utility.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTools {
	static Connection conn =null;
	static Statement statement = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	
	/**
	 *连接数据库
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		//读取配置文件jdbc.properties
		InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		properties.load(in);
		
		String driver = properties.getProperty("driverClass");
		String url = properties.getProperty("jdbcUrl");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	/**
	 * 释放数据库资源
	 * @param ps
	 * @param conn
	 */
	public static void releaseBD(PreparedStatement ps,Connection conn){
		if(ps != null){
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 释放数据库资源
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public static void releaseBD(ResultSet rs,PreparedStatement ps,Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(ps != null){
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
