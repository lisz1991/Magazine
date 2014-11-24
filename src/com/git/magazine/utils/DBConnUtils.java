package com.git.magazine.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @Use:数据库连接工具类
 * @Date:2014-2-26
 * @Time:上午11:18:25
 */
public class DBConnUtils {

	private static final String MYSQL = "jdbc:mysql://";

	private static final String ORACLE = "jdbc:oracle:thin:@";

	private static final String SQLSERVER = "jdbc:microsoft:sqlserver://";

	public static final String TYPE_MYSQL = "mysql";

	public static final String TYPE_ORACLE = "oracle";

	public static final String TYPE_SQLSERVER = "sqlserver";

	private DBConnUtils() {
	}

	public static Connection getConnection(String DBType, String url,
			String user, String password) throws SQLException {
		if (TYPE_MYSQL.equalsIgnoreCase(DBType))
			return getMySqlConn(url, user, password);
		if (TYPE_ORACLE.equalsIgnoreCase(DBType))
			return getOracleConn(url, user, password);
		if (TYPE_SQLSERVER.equals(DBType)) {
			return getSqlServerConn(url, user, password);
		}
		return null;
	}

	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Connection getMySqlConn(String url, String user,
			String password) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(MYSQL + url, "root", "root");

		return conn;
	}

	private static Connection getOracleConn(String url, String user,
			String password) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(ORACLE + url, "scott", "tiger");

		return conn;
	}

	private static Connection getSqlServerConn(String url, String user,
			String password) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(SQLSERVER + url, "root", "root");

		return conn;
	}
}