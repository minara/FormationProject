package com.excilys.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComputerDAO {
	private final static ComputerDAO dao= new ComputerDAO();
	private Connection connection;

	public ComputerDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
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

	public static ComputerDAO getDao() {
		return dao;
	}

	public Connection getConnection() {
		try {
			this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db","jee-cdb","password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
