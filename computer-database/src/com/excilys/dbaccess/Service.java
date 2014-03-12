package com.excilys.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Service {
	private ComputerDAO dao;
	protected Connection connection;

	public Service() {
		this.dao=ComputerDAO.getDao();
		this.connection=this.dao.getConnection();
	}
	
	public void closeConnection(){
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

}
