package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DAOFactory {
	FACTORY;

	private ConnectionManager connectionManager;
	private static ThreadLocal<Connection> connectionTL=new ThreadLocal<Connection>();
	private static ThreadLocal<Boolean> errorTL=new ThreadLocal<Boolean>();
	private final Logger logger=LoggerFactory.getLogger(DAOFactory.class);

	private DAOFactory(){
		connectionManager=ConnectionManager.getInstance();
	}

	public Connection getConnection(){
		if(connectionTL.get()==null){
			try {
				connectionTL.set(connectionManager.getConnection());
				if(errorTL.get()==null)
					errorTL.set(false);
			} catch (SQLException e) {
				logger.warn("Unnable to retrieve a connection from the pool");
				e.printStackTrace();
				errorTL.set(true);
			}
		}
		return connectionTL.get();
	}

	public void startTransaction(){
		Connection c= this.getConnection();
		try {
			c.setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Connection will commit automatically: no transaction");
			errorTL.set(true);
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try{
			if(connectionTL.get()!=null){
				connectionTL.get().close();
			}
		} catch (SQLException e) {
			// TODO Relay to servlet to break web-app
		}finally{
			connectionTL.set(null);
			errorTL.set(null);
		}
	}

	public void endTransaction() {
		if(errorTL.get()){
			try {
				connectionTL.get().rollback();
				logger.debug("rollback");
			} catch (SQLException e) {
				logger.warn("Rollback failed");
				e.printStackTrace();
			}
		}else{
			try {
				connectionTL.get().commit();
			} catch (SQLException e) {
				logger.warn("Commit failed");
				//errorTL.set(true); endTransaction();
				e.printStackTrace();
			}
		}
	}

	public static ThreadLocal<Connection> getConnectionTL() {
		return connectionTL;
	}

	public static ThreadLocal<Boolean> getErrorTL() {
		return errorTL;
	}

}
