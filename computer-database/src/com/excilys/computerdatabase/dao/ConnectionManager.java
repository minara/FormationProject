package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManager {
	private final static ConnectionManager cm= new ConnectionManager();
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/computer-database-db";
	private String user="jee-cdb";
	private String pwd="password";
	private BoneCP connectionPool=null;

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
		
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(url); 
		config.setUsername(user); 
		config.setPassword(pwd);
		config.setMinConnectionsPerPartition(3);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static ConnectionManager getInstance() {
		return cm;
	}

	public Connection getConnection() throws SQLException {
		Connection connection=null;
		connection=connectionPool.getConnection();
		
		return connection;
	}
	
	public void endAllConnections(){
		connectionPool.shutdown();
	}
	
	@Override
	protected void finalize() throws Throwable{
		endAllConnections();
		super.finalize();
	}

}
