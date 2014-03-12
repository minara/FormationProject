package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private final static ConnectionManager cm= new ConnectionManager();
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/computer-database-db";
	private String user="jee-cdb";
	private String pwd="password";

	public ConnectionManager() {
		try {
			Class.forName(driver).newInstance();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static ConnectionManager getInstance() {
		return cm;
	}

	public Connection getConnection() {
		Connection connection=null;
		try {
			connection=DriverManager.getConnection(url,user,pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
