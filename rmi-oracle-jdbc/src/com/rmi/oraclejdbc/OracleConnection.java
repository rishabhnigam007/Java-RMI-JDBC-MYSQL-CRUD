package com.rmi.oraclejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {

	public static Connection con = null;

	public static Connection getConnection() throws SQLException {

		try {
			// This class is just like a application.properties file
			// (driver-class-name)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Connection Establised !!");

			// This is just like a Session interface and Transaction Interface
			// having commit and rollback methods
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "jdbc_db", "1234");
			System.out.println("Database Matched !!");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return (Connection) e;
		}
		return con;
	}

	public static void closeConnection() throws SQLException {
		System.out.println("Closing Connection !!");
		con.close();
	}
}