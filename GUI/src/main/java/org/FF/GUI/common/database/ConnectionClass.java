package org.FF.GUI.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

	public static Connection conn = null;
	private String url = "jdbc:mysql://145.24.222.168:3306/flfl_bank?serverTimezone=Europe/Amsterdam"
			+ "&allowMultiQueries=true";
	private final String password = "password"; // change to actual password
	private final String username = "username"; // change to actual password
 
	/**
	 * 
	 * @return {@code Connection} with the FLFL database
	 * or if its already connection return that
	 */
	public Connection getConnection() {
		if (conn != null)
			return conn;
		try {
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return conn;
		}

	}

	/**
	 * 
	 * @return {@code Boolean} if its closed return true else false
	 */
	public boolean closeConnection() {
		if (conn == null)
			return true;
		try {
			conn.close();
			conn = null;
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
}
