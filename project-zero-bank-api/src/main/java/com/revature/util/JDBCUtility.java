package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {

	public static Connection getConnection() throws SQLException {
		
//		// AWS
//		String url = System.getenv("db_url");
//		String username = System.getenv("db_username");
//		String password = System.getenv("db_password");
		
		// local host
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "vmUKeg3zHsuYa$";
		
		Driver postgresDriver = new Driver();
		
		DriverManager.registerDriver(postgresDriver);
		
		Connection con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
}
